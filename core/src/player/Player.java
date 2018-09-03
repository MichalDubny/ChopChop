package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import gameInfo.UserDataType;

public class Player extends Sprite {
    private World world;
    private Body body;
    private PlayerUserData playerUserData;
    private TextureAtlas playerAtlasWalk;
    private TextureAtlas playerAtlasIdle;
    private TextureAtlas playerAtlasJump;
    private float elapsedTime;

    private boolean isJumping;
    private boolean isWalking;

    public Player(World world, float widthFloor) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\player\\hero-idle.gif"));
        this.world = world;
        setPosition((getWidth() /2f),((getY()+getHeight()/2f)+200));
        createBody();
        playerAtlasWalk = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\walk\\PlayerAnimationWalk.atlas");
        playerAtlasIdle = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\idle\\PlayerAnimationIdle.atlas");
        playerAtlasJump = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\player\\jump\\PlayerAnimationJump.atlas");
        playerUserData = new PlayerUserData();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX()/GameInfo.PPM,getY()/GameInfo.PPM );

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()  / 4f -15)/GameInfo.PPM ,(getHeight() / 2f -10)/GameInfo.PPM );


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 70f;
        fixtureDef.friction = 2f;
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.PLAYER);

        shape.dispose();
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
                getX() - this.getWidth() / 2f +10, getY() - (getHeight() / 2f -10)  );
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

    public PlayerUserData getPlayerUserData() {
        return playerUserData;
    }
}
