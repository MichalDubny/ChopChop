package creatures.aiArrive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import combat.Combat;
import creatures.CreatureActivity;
import utils.CountDown;

import java.util.Random;

public class AIArrive {
    private InputProcessor inputProcessor;
    private Box2dSteeringEntity target;
    private Box2dSteeringEntity character;
    private World world;
    private Arrive<Vector2> arriveSB;
    private float arriveDistance;
    private CreatureActivity creatureActivity;
    private boolean isRest;
    private boolean preparingToAttack = false;
    private boolean attacking = false;
    private CountDown preparingToAttackCountDown;
    private CountDown attackingCountDown;


    public AIArrive(World world, Box2dSteeringEntity follower, Box2dSteeringEntity target) {
        this.world = world;
        this.target = target;
        this.character = follower;
        startFollower();

        inputProcessor = new Box2dTargetInputProcessor(target);

        setArrive();
        PrioritySteering<Vector2> prioritySteering = new PrioritySteering<Vector2>(character,0.0001f)
                .add(arriveSB);
        character.setSteeringBehavior(prioritySteering);
    }

    private void setArrive() {
        arriveSB = new Arrive<Vector2>(character, target) //
            .setTimeToTarget(1f) //
            .setArrivalTolerance(0.001f) //
            .setDecelerationRadius(0);
    }

    private void startFollower() {
        character.setMaxLinearSpeed(7);  //7
        character.setMaxLinearAcceleration(8);  //8
    }

    private void stopFollower() {
        character.setMaxLinearSpeed(0);
        character.setMaxLinearAcceleration(0);
    }

    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    public void setInputProcessor(InputProcessor inputProcessor) {
        this.inputProcessor = inputProcessor;
    }

    public void update() {

        GdxAI.getTimepiece().update(Gdx.graphics.getDeltaTime());
        float deltaTime = GdxAI.getTimepiece().getDeltaTime();

        // Update box2d world
        world.step(deltaTime, 8, 3);

        setBehavior(deltaTime);

        character.update(GdxAI.getTimepiece().getDeltaTime());
    }

    private void setBehavior(float deltaTime) {
//TODO prerobit aby rozna potvori maly rozne parametre min/max dosah  rychlost utoku
        arriveDistance = getDistance(target.getPosition(),character.getPosition());
        if(!preparingToAttack) {
            setRest();
            if (arriveDistance <= 0.3f) {  //.8
                stopFollower();
                creatureActivity = CreatureActivity.IDLE;
                preparingToAttack = true;
                preparingToAttackCountDown = new CountDown(200);
            } else if (isRest) {
        //          aby jednoty nesli stale rovnakou rychlostou a nenakopili na jednu hromadu
                stopFollower();
                System.out.println("rest");
//                creatureActivity = CreatureActivity.IDLE;
           } else if (arriveDistance >= 5f) {
        //            max dosah v tomto dosahu uz neprenasleduje nepriatela
                stopFollower();
                creatureActivity = CreatureActivity.IDLE;
           } else {
                creatureActivity = CreatureActivity.WALK;
                startFollower();
           }
        }else {
            if (preparingToAttackCountDown.isFinish() && !attacking){
                creatureActivity = CreatureActivity.ATTACK; // utocna animacia
                attackingCountDown = new CountDown(50);
                attacking = true;
                if (arriveDistance <= 0.5f) {
                    System.out.println("damage");
//                    Combat combat = new Combat(character,target);
                }
            }else if(attacking){
                if (attackingCountDown.isFinish()){
                    preparingToAttack = false;
                    System.out.println("end of attack");
                    attacking = false;
                }
            }
        }
    }

    /**
     * ako casto bude spomalovat
     */
    private void setRest() {
        isRest = (new Random().nextInt(25 - 1) + 1) == 1;
    }


    private float getDistance(Vector2 a, Vector2 b) {
        return Vector2.dst(a.x,a.y,b.x,b.y);
    }

    public CreatureActivity getCreatureActivity() {
        return creatureActivity;
    }
}
