package player;

import combat.CombatEntity;
import creatures.CreatureActivity;
import creatures.aiBehavior.Box2dSteeringEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import gameInfo.UserDataType;
import utils.CountDown;

public class Player extends CombatEntity {
    private World world;
    private Body body;
    private Body weaponBody;
    private PlayerData playerUserData;
    private TextureAtlas playerAtlasWalk;
    private TextureAtlas playerAtlasIdle;
    private TextureAtlas playerAtlasJump;
    private TextureAtlas playerAtlasAttack;
    private float elapsedTime;
    private TextureRegion textureRegion;
//
//    private boolean falling;

    private CreatureActivity activity;
//    private boolean isWalking;
    private boolean isFaceRight;

    private CountDown endAttack,endAnimationAttack;


    private boolean weaponBodyExist;


    public Player(World world) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\player\\hero.png"));
        textureRegion = new TextureRegion(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\player\\hero.png"));

        this.world = world;
        setPosition((getWidth() /2f),((getY()+getHeight()/2f)+88));
        createBody();
        playerAtlasWalk = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\walk\\PlayerAnimationWalk.atlas");
        playerAtlasIdle = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\idle\\PlayerAnimationIdle.atlas");
        playerAtlasJump = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\jump\\PlayerAnimationJump.atlas");
        playerAtlasAttack = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\attack\\PlayerAnimationAttack.atlas");
        playerUserData = new PlayerData();
        this.isFaceRight =true;
        this.healPoints = maxHealPoints = 100;
        this.attackDamage = 20;
        this.attackDistance = 60f;  //20

    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX()/GameInfo.PPM,getY()/GameInfo.PPM );

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()  / 4f  )/GameInfo.PPM ,(getHeight() / 2f )/GameInfo.PPM );


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 70f;  //70f
        fixtureDef.friction = 2f;
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.STOP_POINT ;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.PLAYER);

        shape.dispose();

        steeringEntity = new  Box2dSteeringEntity(textureRegion, body,false, GameInfo.pixelsToMeters((int) getWidth()*2));
        isFaceRight = true;
    }

    public void movePlayer(Vector2 movingLinearImpulse ){
        setWalking(true);
        if(activity != CreatureActivity.JUMP) {
            if(body.getLinearVelocity().x >= PlayerData.MAX_VELOCITY) {
                body.setLinearVelocity(PlayerData.MAX_VELOCITY,movingLinearImpulse.y);
            }else if(body.getLinearVelocity().x <= - PlayerData.MAX_VELOCITY) {
                body.setLinearVelocity(- PlayerData.MAX_VELOCITY,movingLinearImpulse.y);
            }else {
                body.applyLinearImpulse(movingLinearImpulse, getBody().getWorldCenter(), true);
            }
        }
    }

    public void jump(){
        if(activity != CreatureActivity.JUMP){
            body.applyLinearImpulse(playerUserData.getJumpingLinearImpulse(),getBody().getWorldCenter(),true);
//            falling = false;
            activity = CreatureActivity.JUMP;
//            System.out.println("jump");
        }
    }

    public void attack(){
        //TODO  prerobit utok  zasahuje vela nepriatelov je dlhy interval na koliziu. treba dat dalsi interval na animaciu ale nie na utok.
        activity = CreatureActivity.ATTACK;
        createWeapon();
        endAttack = new CountDown(20);  //100
        endAnimationAttack = new CountDown(100);
    }

    private void createWeapon() {
        BodyDef weaponBodyDef = new BodyDef();
        weaponBodyDef.type = BodyDef.BodyType.KinematicBody;
        float weaponPositionX = (isFaceRight)? getX()+(this.attackDistance) : getX()-(this.attackDistance);
        weaponBodyDef.position.set(weaponPositionX /GameInfo.PPM,getY()/GameInfo.PPM );

        weaponBody = world.createBody(weaponBodyDef);
        weaponBody.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((this.attackDistance / 2f)/GameInfo.PPM ,1/GameInfo.PPM );
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.WEAPON;
        fixtureDef.isSensor = true;

        Fixture fixture = weaponBody.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.WEAPON);

        shape.dispose();
        weaponBodyExist = true;
    }

    private void destroyWeapon() {
        if(weaponBodyExist) {
            world.destroyBody(weaponBody);
            weaponBodyExist = false;
        }
    }

    public Body getBody() {
        return body;
    }


    public void drawPlayerAnimation(SpriteBatch batch) {
        drawPlayerIdle(batch);
        drawPlayerWalk(batch);
        drawPlayerJump(batch);
        drawPlayerAttack(batch);
    }


    private void drawPlayerIdle(SpriteBatch batch) {
        if(activity == CreatureActivity.IDLE) {
            drawAnimation(batch, playerAtlasIdle, 9f);
        }
    }

    private void drawPlayerWalk(SpriteBatch batch) {
            if (activity == CreatureActivity.WALK) {
             drawAnimation(batch, playerAtlasWalk, 5f);
        }
    }

    private void drawPlayerJump(SpriteBatch batch) {
        if (activity == CreatureActivity.JUMP) {
             drawAnimation(batch, playerAtlasJump, 5f);
        }
    }


    private void drawPlayerAttack(SpriteBatch batch) {
        if (activity == CreatureActivity.ATTACK) {
            drawAnimation(batch, playerAtlasAttack, 5f);
        }
    }


    private void drawAnimation(SpriteBatch batch, TextureAtlas textureAtlas, float v) {
        float frameDuration = 1f / v;
        elapsedTime += Gdx.graphics.getDeltaTime();
        Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();

        for (TextureRegion frame : frames) {
            setFlipSide(body.getLinearVelocity().x, frame);
        }

        Animation<TextureAtlas.AtlasRegion> animation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, textureAtlas.getRegions());
        batch.draw(animation.getKeyFrame(elapsedTime, true),
                GameInfo.metersToPixels(body.getPosition().x)- 20 - this.getWidth() / 2f ,
                GameInfo.metersToPixels(body.getPosition().y) - (getHeight() / 2f )  );
    }


    private void setFlipSide(float x, TextureRegion player) {
        if (!isFaceRight && !player.isFlipX()) {
            player.flip(true, false);
        } else if (isFaceRight && player.isFlipX()) {
            player.flip(true, false);
        }
    }


    public void update() {
        float bodyX = ((body.getPosition().x) * GameInfo.PPM);
        setPosition(bodyX,body.getPosition().y * GameInfo.PPM);
        if(activity == CreatureActivity.ATTACK){
            if(endAttack.isFinish() ){
                destroyWeapon();
                if(endAnimationAttack.isFinish()){
                    activity = CreatureActivity.IDLE;
                    System.out.println("player end attack");
                }
            }


        }
//        if(activity == CreatureActivity.JUMP){
//            if(body.getLinearVelocity().y < 0) {
//                falling = true;
//            }
//        }

    }

    public float getBodyXPosition(){
        return body.getPosition().x;
    }

    public float getBodyYPosition(){
        return body.getPosition().y;
    }

    public void setWalking(boolean walking) {
        if(walking && activity != CreatureActivity.JUMP){
            activity = CreatureActivity.WALK;
        }else if(activity != CreatureActivity.JUMP) {
            activity = CreatureActivity.IDLE;
        }
    }

    public boolean isJumping() {
        return activity == CreatureActivity.JUMP;
    }

    public void landed(){
        activity = CreatureActivity.IDLE;
    }

    public PlayerData getPlayerUserData() {
        return playerUserData;
    }


    public boolean isAttacking() {
        return activity == CreatureActivity.ATTACK;
    }

    public void setFaceRight(boolean faceRight) {
        isFaceRight = faceRight;
    }
}
