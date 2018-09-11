package ground;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;

public class GroundController {
    private World world;
    private Array<Ground> ground = new Array<Ground>();

    public Array<Ground> getGround() {
        return ground;
    }

    public GroundController(World world) {
        this.world = world;
        createGround();
        positionGrounds();
    }

    private void createGround() {
        for(int i=0; i< GameInfo.REPEAT_BG; i++){
            ground.add(new Ground(world,"ground7"));
        }
    }

    private void positionGrounds(){
        int i = 0;
        ground.get(0).setSpritePosition(0,0);
//        for(Ground ground : ground){
//             ground.setSpritePosition(i* ground.getWidth()+ ground.getWidth()/2,0);
//             i++;
//        }
        // TODO nahradi map generatorm
    }

    public float getWidthGround(){
        return ground.get(0).getWidth();
    }

    public void drawGrounds(SpriteBatch batch){
        int i = 0;
        for(Ground ground : ground){
            ground.drawGround(batch,(ground.getWidth())*i);
            i++;
        }
    }

}


