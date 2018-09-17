package scenes.ground;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import gameInfo.GameInfo;
import gameInfo.UserDataType;

public class Ground extends Sprite {
    private World world;
    private Body body;

    public Ground(World world, String nameGround) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL+"\\ground\\"+nameGround+".png"));
        this.world = world;
    }


    public void setSpritePosition(float x , float y ){
        setPosition(x,y);
        createBody(x);
    }

    private void createBody(float x){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
//        bodyDef.position.set((getX())/GameInfo.PPM,(getY() /2f)/GameInfo.PPM);
        bodyDef.position.set((getX())/GameInfo.PPM,(getY() /2f)/GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(((getWidth())/2)/GameInfo.PPM, ((getHeight())/2)/GameInfo.PPM);
        shape.setAsBox(((getWidth()*GameInfo.REPEAT_BG)/2), (GameInfo.pixelsToMeters((int)((getHeight())/2))));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 74f;
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.GROUND);

        shape.dispose();

    }

    public void drawGround(SpriteBatch batch, float x) {
//        batch.draw(this, this.getX() - this.getWidth() /2f, this.getY() );
        float y = getY();
        float h = getHeight();
         batch.draw(this, x  ,0 );
    }


}
