package creatures;

import creatures.aiArrive.AIArrive;
import creatures.aiArrive.Box2dSteeringEntity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import player.Player;

import java.util.Map;

public class Creature extends Sprite {
    private World world;
    private Body body;
    private String name;
    private Vector2 vector2;
    private TextureRegion textureRegion;
    private Box2dSteeringEntity steeringEntity;
    private Player player;
    private AIArrive aiArrive;
    private float elapsedTime;
    private CreatureActivity activity;

    private Map<CreatureActivity,TextureAtlas> arrayAnimations;

    public Creature() {
    }

    public Creature(World world, String name, Vector2 vector2, Player player) {
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
        fixtureDef.filter.categoryBits = GameInfo.CREATURE;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT ;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);


        shape.dispose();

        steeringEntity = new Box2dSteeringEntity(textureRegion, body,false, GameInfo.pixelsToMeters((int) getWidth()));
        activity = CreatureActivity.IDLE;
    }

    private void setArrive() {
        aiArrive = new AIArrive(world, this.getSteeringEntity(), player.getSteeringEntity());
    }



    public void drawAnimation(SpriteBatch batch, Map<CreatureActivity, TextureAtlas> arrayAnimations, Vector2 position, float v) {
        TextureAtlas textureAtlas = choseActionAnimation(arrayAnimations);
        float frameDuration = 1f / v;
        elapsedTime += Gdx.graphics.getDeltaTime();
        Array<TextureAtlas.AtlasRegion> frames = textureAtlas.getRegions();

        for (TextureRegion frame : frames) {
            setFlipSide(body.getLinearVelocity().x, frame);
        }

        Animation<TextureAtlas.AtlasRegion> animation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, textureAtlas.getRegions());
        batch.draw(animation.getKeyFrame(elapsedTime, true),
                GameInfo.metersToPixels(position.x) - this.getWidth() / 2f  ,
                GameInfo.metersToPixels(position.y) - (getHeight() / 2f -10)  );
    }

    private TextureAtlas choseActionAnimation(Map<CreatureActivity,TextureAtlas> arrayAnimations) {
        return arrayAnimations.get(activity);
    }


    private void setFlipSide(float x, TextureRegion player) {
        if (x < 0 && !player.isFlipX()) {
            player.flip(true, false);
        } else if (x > 0 && player.isFlipX()) {
            player.flip(true, false);
        }
    }


    public void update() {
        aiArrive.update();
        setActivity();
    }

    private void setActivity() {
        activity = aiArrive.getCreatureActivity();
    }

    public Map<CreatureActivity,TextureAtlas> getArrayAnimations() {
        return arrayAnimations;
    }

    public void setArrayAnimations(Map<CreatureActivity, TextureAtlas> arrayAnimations) {
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



}
