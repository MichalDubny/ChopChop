package border;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import gameInfo.GameInfo;
public class StartBorder extends Sprite {
    private World world;
    private Body body;

    public StartBorder(World world) {
        this.world = world;
        setPosition(0 ,GameInfo.HEIGHT/2);
        createBody();
    }

    private void createBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0,GameInfo.HEIGHT/2/GameInfo.PPM );

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((1)/GameInfo.PPM, (GameInfo.HEIGHT/2)/GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4f;
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();

    }

}
