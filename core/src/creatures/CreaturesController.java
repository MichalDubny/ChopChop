package creatures;

import com.badlogic.gdx.physics.box2d.World;
import level.Level1;
import level.StopPoint;

import java.util.ArrayList;

public class CreaturesController {
    World world;
    ArrayList<Creatures> creatures;
    ArrayList<StopPoint> stopPointArrayData;
    Level1 level;


    public CreaturesController(World world) {
        this.world = world;
        getLevelData();
    }

    private void getLevelData() {
        level = new Level1(world);
    }

    public void createCreatures(){
        stopPointArrayData = level.getStopPointArrayData();

    }
}
