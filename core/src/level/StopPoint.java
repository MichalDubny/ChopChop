package level;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import gameInfo.GameInfo;
import gameInfo.UserData;
import gameInfo.UserDataType;
import utils.CountDown;

import java.util.ArrayList;


/**
 * neviditelny bod
 */
public class StopPoint extends Sprite {
    private World world;
    private Body body;
    private Fixture fixture;

    private int maxCreatures;
    private int maxCreaturesInScreen;
    private int startCreatures;
    private Vector2 stopPointPosition;

    private Array<String> creaturesType;
    private Array<Vector2> spawnPoints;

    private boolean spawnRunning;
    private boolean runningSpawnCD;
    private CountDown spawnCD;


    public StopPoint(World world, int maxCreatures, int maxCreaturesInScreen, int startCreatures, Vector2 stopPointPosition, Array<String> creaturesType) {
        this.world = world;
        this.maxCreatures = maxCreatures;
        this.maxCreaturesInScreen = maxCreaturesInScreen;
        this.startCreatures = startCreatures;
        this.stopPointPosition = stopPointPosition;
        this.creaturesType = creaturesType;

        setPosition(stopPointPosition.x,stopPointPosition.y);
        createStopPoint();
    }

    private void createStopPoint() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(stopPointPosition.x/GameInfo.PPM,stopPointPosition.y/GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((1)/GameInfo.PPM, (GameInfo.HEIGHT/2)/GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.filter.categoryBits = GameInfo.STOP_POINT;
        fixtureDef.filter.maskBits = GameInfo.PLAYER;
        fixtureDef.shape = polygonShape;
        fixtureDef.isSensor = true;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(UserDataType.STOP_POINT);

        polygonShape.dispose();
    }

    public Vector2 getStopPointPosition() {
        return stopPointPosition;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFilterToRemove() {
        Filter filter = new Filter();
        filter.categoryBits = GameInfo.DESTROYED;
        fixture.setFilterData(filter);
    }



    public void setSpawnPoints(Array<Vector2> spawnPoints) {
        this.spawnPoints = spawnPoints;
    }

    public Vector2 getRandomSpawnPoint() {
        spawnPoints.shuffle();
        return spawnPoints.get(0);
    }

    public String getRandomCreatureType() {
        creaturesType.shuffle();
        return creaturesType.get(0);
    }

    public int getStartCreatures() {
        return startCreatures;
    }

    public boolean isSpawnRunning() {
        return spawnRunning;
    }

    public void setSpawnRunning(boolean spawnRunning) {
        this.spawnRunning = spawnRunning;
    }

    public int getMaxCreatures() {
        return maxCreatures;
    }

    public void setMaxCreatures(int maxCreatures) {
        this.maxCreatures = maxCreatures;
    }

    public int getMaxCreaturesInScreen() {
        return maxCreaturesInScreen;
    }

    public boolean isRunningSpawnCD() {
        return runningSpawnCD;
    }

    public void setRunningSpawnCD(boolean runningSpawnCD) {
        this.runningSpawnCD = runningSpawnCD;
    }

    public void setSpawnCD(CountDown spawnCD) {

        this.spawnCD = spawnCD;
    }

    public CountDown getSpawnCD() {
        return spawnCD;
    }
}



