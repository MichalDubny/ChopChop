package level;

import com.badlogic.gdx.physics.box2d.World;
import creatures.Creatures;
import creatures.type.Ghost;
import creatures.type.Skeleton;

import java.util.ArrayList;

public class Level1 {
    ArrayList<StopPoint> stopPointArrayData;
    ArrayList<Creatures> creaturesTypePoint1;


    public Level1(World world) {
        stopPointArrayData = new ArrayList<StopPoint>();
        creaturesTypePoint1 = new ArrayList<Creatures>();
        creaturesTypePoint1.add(new Skeleton());
        creaturesTypePoint1.add(new Ghost());
        stopPointArrayData.add(new StopPoint(30,5,1,384,creaturesTypePoint1));

    }

    public ArrayList<StopPoint> getStopPointArrayData() {
        return stopPointArrayData;
    }
}
