package player;

import combat.CombatEntity;
import creatures.aiArrive.Box2dSteeringEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import gameInfo.UserDataType;

public class Player extends CombatEntity {
    private World world;
    private Body body;
    private PlayerData playerUserData;
    private TextureAtlas playerAtlasWalk;
    private TextureAtlas playerAtlasIdle;
    private TextureAtlas playerAtlasJump;
    private float elapsedTime;
    private TextureRegion textureRegion;

    private boolean isJumping;
    private boolean isWalking;


    public Player(World world) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\player\\hero.png"));
        textureRegion = new TextureRegion(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\player\\hero.png"));

        this.world = world;
        setPosition((getWidth() /2f),((getY()+getHeight()/2f)+200));
        createBody();
        playerAtlasWalk = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\walk\\PlayerAnimationWalk.atlas");
        playerAtlasIdle = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\idle\\PlayerAnimationIdle.atlas");
        playerAtlasJump = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\jump\\PlayerAnimationJump.atlas");
        playerUserData = new PlayerData();
        this.healPoints = 100;
        this.attackDamage = 20;
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
        fixtureDef.density = 70f;
        fixtureDef.friction = 2f;
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT ;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.PLAYER);

        shape.dispose();

        steeringEntity = new  Box2dSteeringEntity(textureRegion, body,false, GameInfo.pixelsToMeters((int) getWidth()*2));
    }

    public void movePlayer(Vector2 movingLinearImpulse ){
        setWalking(true);
        if(!isJumping) {
            if(body.getLinearVelocity().x >= playerUserData.getMaxVelocity()) {
                body.setLinearVelocity(playerUserData.getMaxVelocity(),movingLinearImpulse.y);
            }else if(body.getLinearVelocity().x <= -playerUserData.getMaxVelocity()) {
                body.setLinearVelocity(-playerUserData.getMaxVelocity(),movingLinearImpulse.y);
            }else {
                body.applyLinearImpulse(movingLinearImpulse, getBody().getWorldCenter(), true);
            }
        }
    }

    public void jump(){
        if(!isJumping){
            body.applyLinearImpulse(playerUserData.getJumpingLinearImpulse(),getBody().getWorldCenter(),true);
            isJumping = true;
        }
    }



    public Body getBody() {
        return body;
    }

    public void drawPlayerAnimation(SpriteBatch batch) {
        drawPlayerIdle(batch);
        drawPlayerWalk(batch);
        drawPlayerJump(batch);
    }

    private void drawPlayerIdle(SpriteBatch batch) {
        if(!isWalking && !isJumping) {
            drawAnimation(batch, playerAtlasIdle, 9f);
        }
    }

    private void drawPlayerWalk(SpriteBatch batch) {
        if (isWalking && !isJumping) {
             drawAnimation(batch, playerAtlasWalk, 5f);
        }
    }

    private void drawPlayerJump(SpriteBatch batch) {
        if (isJumping) {
             drawAnimation(batch, playerAtlasJump, 5f);
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
                GameInfo.metersToPixels(body.getPosition().x)-20 - this.getWidth() / 2f ,
                GameInfo.metersToPixels(body.getPosition().y) - (getHeight() / 2f )  );
    }


    private void setFlipSide(float x, TextureRegion player) {
        if (x < 0 && !player.isFlipX()) {
            player.flip(true, false);
        } else if (x > 0 && player.isFlipX()) {
            player.flip(true, false);
        }
    }


    public void updatePlayer() {
        float bodyX = ((body.getPosition().x) * GameInfo.PPM);
        setPosition(bodyX,body.getPosition().y * GameInfo.PPM);
    }

    public float getBodyXPosition(){
        return body.getPosition().x;
    }

    public float getBodyYPosition(){
        return body.getPosition().y;
    }

    public void setWalking(boolean walking) {
        isWalking = walking;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void landed(){
        isJumping = false;
    }

    public PlayerData getPlayerUserData() {
        return playerUserData;
    }

//
//    @Override
//    public int getHealPoints() {
//        return healPoints;
//    }
//
//    @Override
//    public void setHealPoints(int healPoints) {
//        this.healPoints = healPoints;
//    }
//
//    @Override
//    public int getAttackDamage() {
//        return attackDamage;
//    }
//
//    @Override
//    public void setAttackDamage(int attackDamage) {
//        this.attackDamage = attackDamage;
//    }
}
