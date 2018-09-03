package level;

import creatures.Creatures;

import java.util.ArrayList;

public class StopPoint {
    private int maxCreatures;
    private int maxCreaturesInScreen;
    private int startCreatures;
    private int creatures;
    private float stopPointPositionX;

    private ArrayList<Creatures> creaturesType;


    public StopPoint(int maxCreatures, int maxCreaturesInScreen, int startCreatures, float stopPointPositionX, ArrayList<Creatures> creaturesType) {
        this.maxCreatures = maxCreatures;
        this.maxCreaturesInScreen = maxCreaturesInScreen;
        this.startCreatures = startCreatures;
        this.stopPointPositionX = stopPointPositionX;
        this.creaturesType = creaturesType;
    }

    public float getStopPointPositionX() {
        return stopPointPositionX;
    }
}
