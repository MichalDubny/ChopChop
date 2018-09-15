package creatures;

import combat.CombatEntity;
import creatures.aiArrive.AIArrive;
import creatures.aiArrive.Box2dSteeringEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import gameInfo.UserDataType;
import player.Player;

import java.util.Map;

public class Creature extends CombatEntity {
    private World world;
    private Body body;
    private String name;
    private Vector2 vector2;
    private TextureRegion textureRegion;
    private Player player;
    private AIArrive<Creature,Player> aiArrive;
    private float elapsedTime;
    private CreatureActivity activity;
    private Fixture fixture;

    Map<CreatureActivity,AnimationsParameters> arrayAnimations;


    public Creature(World world, String name, Vector2 vector2, Player player ) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\" + name + "\\" + name + "Slim.png"));
        textureRegion = new TextureRegion(
                new Texture(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\" + name + "\\" + name + "Slim.png"));
        this.name = name;
        this.world = world;
        this.vector2 = vector2;
        this.player = player;
        setPosition(vector2.x,vector2.y);
        createBody();
        setArrive();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(getX()/GameInfo.PPM,getY()/GameInfo.PPM );

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth()  / 2f )/GameInfo.PPM ,(getHeight() / 2f )/GameInfo.PPM );


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4f;
        fixtureDef.friction = 2f;
        fixtureDef.shape = shape;

        fixtureDef.filter.categoryBits = (short) (GameInfo.CREATURE  );
        fixtureDef.filter.groupIndex = -1;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.WEAPON ;


        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.CREATURE);


        shape.dispose();

        steeringEntity = new Box2dSteeringEntity(textureRegion, body,false, GameInfo.pixelsToMeters((int) getWidth()));
        activity = CreatureActivity.IDLE;
    }

    private void setArrive() {
        aiArrive = new AIArrive(world, this, player);
    }



    public void drawAnimation(SpriteBatch batch, Map<CreatureActivity,AnimationsParameters> arrayAnimations, Vector2 position) {
        AnimationsParameters animationsParameters = choseActionAnimation(arrayAnimations);
        float frameDuration = 1f / animationsParameters.getFrameDuration();
        elapsedTime += Gdx.graphics.getDeltaTime();
        Array<TextureAtlas.AtlasRegion> frames = animationsParameters.getTextureAtlas().getRegions();

        for (TextureRegion frame : frames) {
            setFlipSide(body.getLinearVelocity().x, frame);
        }

        Animation<TextureAtlas.AtlasRegion> animation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, animationsParameters.getTextureAtlas().getRegions());
        batch.draw(animation.getKeyFrame(elapsedTime, true),
                GameInfo.metersToPixels(position.x) - this.getWidth() / 2f  ,
                GameInfo.metersToPixels(position.y) - (getHeight() / 2f -5)  );
    }


    private void setFlipSide(float x, TextureRegion frame) {
        if(player.getSteeringEntity().getPosition().x - body.getPosition().x < 0 && !frame.isFlipX()){
            frame.flip(true, false);
        } else if(player.getSteeringEntity().getPosition().x - body.getPosition().x > 0 && frame.isFlipX()){
            frame.flip(true, false);
        }
    }


    private AnimationsParameters choseActionAnimation(Map<CreatureActivity,AnimationsParameters> arrayAnimations) {
        AnimationsParameters animationsParameters = new AnimationsParameters();
        if(arrayAnimations.get(activity) == null){
            animationsParameters = arrayAnimations.get(CreatureActivity.IDLE);
        }else {
            animationsParameters = arrayAnimations.get(activity);
        }
        return animationsParameters;
    }

    public void update() {
        aiArrive.update();
        setActivity();
    }

    private void setActivity() {
        activity = aiArrive.getCreatureActivity();
    }

    public Map<CreatureActivity, AnimationsParameters> getArrayAnimations() {
        return arrayAnimations;
    }

    public void setArrayAnimations(Map<CreatureActivity, AnimationsParameters> arrayAnimations) {
        this.arrayAnimations = arrayAnimations;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Box2dSteeringEntity getSteeringEntity() {
        return steeringEntity;
    }

    public AIArrive getAiArrive() {
        return aiArrive;
    }

    public Fixture getFixture() {
        return fixture;
    }
}
