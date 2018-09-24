package level;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

public class Level1 {
    ArrayList<StopPoint> stopPointArrayData;
    Array<String> creaturesTypePoint1;
    Array<Vector2> spawnPoints;


    public Level1(World world) {
        stopPointArrayData = new ArrayList<StopPoint>();
        creaturesTypePoint1 = new Array<String>();
        creaturesTypePoint1.add("Skeleton");
        creaturesTypePoint1.add("Ghost");
        StopPoint stopPoint1 = new StopPoint(world,30,5,5,
                new Vector2(384*2,0), creaturesTypePoint1);
        spawnPoints = new Array<Vector2>();
        spawnPoints.add(new Vector2(200*2,50*2));
        spawnPoints.add(new Vector2(350*2,50*2));
        spawnPoints.add(new Vector2(300*2,50*2));
        stopPoint1.setSpawnPoints(spawnPoints);
        stopPointArrayData.add(stopPoint1);
    }


    public ArrayList<StopPoint> getStopPointArrayData() {
        return stopPointArrayData;
    }


    /**
     * ak hrac narazi na stop bod tak sa spusti vytvaranie potvor a vytvoria hranice pohybu
     */
    public void turnOnStopPoint() {
        System.out.println("turn On");

        removeStopPointWall();
    }


    public void removeStopPointWall() {
        for (int i = 0; i< stopPointArrayData.size(); i++){
            if(stopPointArrayData.get(i).getFixture().getUserData() == "turnOn"){
                creatureSpawn(stopPointArrayData.get(i));
                stopPointArrayData.get(i).setFilterToRemove();
//                stopPointArrayData.remove(i);
            }
        }
    }

    public void creatureSpawn(StopPoint stopPoint) {
        stopPoint.setSpawnRunning(true);
    }


}
