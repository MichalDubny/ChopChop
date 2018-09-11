package creatures;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import level.StopPoint;
import player.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class CreaturesController {
    World world;
    Array<Creature> creatures;
    ArrayList<StopPoint> stopPointArrayData;
    Player player;


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




    public void update(SpriteBatch batch) {
        for (Creature creature: creatures){
            creature.update();

            creature.drawAnimation(batch,creature.getArrayAnimations(),creature.getSteeringEntity().getPosition(),5f);
//            creature.getSteeringEntity().draw(batch);
        }
    }
}
