package creatures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import combat.Combat;
import gameInfo.GameInfo;
import gameInfo.UserData;
import gameInfo.UserDataType;
import level.StopPoint;
import player.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CreaturesController {
    private World world;
    private Array<Creature> creatures;
    private ArrayList<StopPoint> stopPointArrayData;
    private Player player;


    public CreaturesController(World world, ArrayList<StopPoint> stopPointArrayData, Player player) {
        this.world = world;
        this.stopPointArrayData = stopPointArrayData;
        this.player = player;
        creatures = new Array<Creature>();
        generateStartingCreatures();
    }


    private void generateStartingCreatures() {
        for (StopPoint stopPointData: stopPointArrayData){
            int manyCreatures = stopPointData.getStartCreatures();
            createCreatures(manyCreatures,stopPointData);
        }
    }

    public void createCreatures(int manyCreatures,StopPoint stopPointData){

        for (int i = 0; i < manyCreatures; i++){
            Vector2 spawnPoint = stopPointData.getRandomSpawnPoint();
            String creatureTypeName = stopPointData.getRandomCreatureType();
            try {
                Constructor c = Class.forName("creatures.type." + creatureTypeName).getConstructor(World.class, Vector2.class, Player.class);
                Creature creature = (Creature) c.newInstance(world, spawnPoint, player);
                System.out.println(creatureTypeName);
                creatures.add(creature);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     *  zasiahnutu priseru
     * @return
     */
    public void setAffectedCreature(){
        for (int i = 0; i < creatures.size; i++){
            if(creatures.get(i).getFixture().getUserData() == "hit"){
                creatures.get(i).getFixture().setUserData(UserDataType.CREATURE);
                Combat<Player, Creature> combat = new Combat<Player, Creature>(player,creatures.get(i));
            }
        }
    }

    public void update(SpriteBatch batch) {
        for (Creature creature: creatures){
            creature.update();

            creature.drawAnimation(batch,creature.getArrayAnimations(),creature.getSteeringEntity().getPosition());
//            creature.getSteeringEntity().draw(batch);
        }
    }
}
