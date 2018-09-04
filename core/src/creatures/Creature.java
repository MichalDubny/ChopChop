package creatures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import gameInfo.GameInfo;

import java.util.ArrayList;

public class Creature extends Sprite {
    private World world;
    private Body body;
    String name;
    Vector2 vector2;

    private ArrayList<TextureAtlas> arrayAnimations;

    public Creature() {
    }

    public Creature(World world, String name, Vector2 vector2) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\" + name + "\\" + name + ".png"));
        this.name = name;
        this.world = world;
        this.vector2 = vector2;
        setPosition(vector2.x,vector2.y);
        createBody();
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

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(name);

        shape.dispose();
    }

    public void drawAnimation(){
//        drawAnimation = new DrawAnimation(body,this);
//        arrayAnimations = getArrayAnimations();

    }

    public ArrayList<TextureAtlas> getArrayAnimations() {
        return arrayAnimations;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

}
