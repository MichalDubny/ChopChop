package creatures.aiBehavior;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.PrioritySteering;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import combat.Combat;
import combat.CombatEntity;
import creatures.Creature;
import creatures.CreatureActivity;
import player.Player;
import utils.CountDown;

import java.util.Random;

public class AIBehavior<T,P> {
    private InputProcessor inputProcessor;
    private Box2dSteeringEntity target;
    private Box2dSteeringEntity character;
    private World world;
    private Arrive<Vector2> arriveSB;
    private float arriveDistance;
    private CreatureActivity animationActivity;
    private CreatureActivity activity;


    private CountDown restCountDown;
    private CountDown preparingToAttackCountDown;
    private CountDown attackingCountDown;

    private CombatEntity targetCombatEntity;
    private CombatEntity characterCombatEntity;


    public <T extends CombatEntity,P extends CombatEntity> AIBehavior(World world, T follower, P target) {
        this.world = world;
        this.targetCombatEntity = target;
        this.characterCombatEntity = follower;
        this.target = target.getSteeringEntity();
        this.character = follower.getSteeringEntity();
        startFollower();

        inputProcessor = new Box2dTargetInputProcessor(this.target);

        setArrive();
        PrioritySteering<Vector2> prioritySteering = new PrioritySteering<Vector2>(character,0.0001f)
                .add(arriveSB);
        character.setSteeringBehavior(prioritySteering);
        activity = CreatureActivity.IDLE;
    }

    private void setArrive() {
        arriveSB = new Arrive<Vector2>(character, target) //
            .setTimeToTarget(1f) //
            .setArrivalTolerance(0.001f) //
            .setDecelerationRadius(0);
    }

    private void startFollower() {
        character.setMaxLinearSpeed(260);  // 33
        character.setMaxLinearAcceleration(280);  // 35
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

        setBehavior(deltaTime);

        character.update(GdxAI.getTimepiece().getDeltaTime());
    }

    /**
     * nastavy spravanie sa potvory
     * @param deltaTime
     */
    private void setBehavior(float deltaTime) {
//      TODO prerobit aby rozna potvori maly rozne parametre min/max dosah  rychlost utoku
        arriveDistance = getDistance(target.getPosition(),character.getPosition());
        if(activity != CreatureActivity.ATTACK_PREPARE && activity != CreatureActivity.ATTACK) {
            if (arriveDistance <= characterCombatEntity.getAttackDistance() * (2f / 3f)) {  // dve tretiny dosahu utoku vtedy zacne tocit
                stopFollower();
                animationActivity = CreatureActivity.IDLE;
                activity = CreatureActivity.ATTACK_PREPARE;
                preparingToAttackCountDown = new CountDown(200);
            } else if (getRest() && (activity != CreatureActivity.REST && activity == CreatureActivity.WALK )) {
//              aby jednotky nesli stale rovnakou rychlostou a nenakopili na jednu hromadu
                setRestAction();
            }else if(activity == CreatureActivity.REST){
                setEndRestAction();
            }else if (arriveDistance >= 5f*2f) { //max dosah v tomto dosahu uz neprenasleduje nepriatela
                stopFollower();
                activity = animationActivity = CreatureActivity.IDLE;
            } else {
                startFollower();
                activity = animationActivity = CreatureActivity.WALK;
            }
        }else {
            attackAction();
        }
    }

    /**
     * v pripade ze skonci odpocet sa ukonci odpocinok potvory
     */
    private void setEndRestAction() {
        if(restCountDown.isFinish()){
//            System.out.println("rest");
            startFollower();
            activity = animationActivity = CreatureActivity.WALK;
        }
    }

    /**
     * nastavy odpocinok potvory
     */
    private void setRestAction() {
        animationActivity = CreatureActivity.IDLE;
        activity = CreatureActivity.REST;
        restCountDown = new CountDown(150);
        stopFollower();
    }


    private void attackAction() {
        if (preparingToAttackCountDown.isFinish() && activity != CreatureActivity.ATTACK){
            activity = animationActivity = CreatureActivity.ATTACK; // utocna animacia
            attackingCountDown = new CountDown(50); //50
            if (arriveDistance <= characterCombatEntity.getAttackDistance()) {
                System.out.println("damage");
                Combat<Creature, Player> combat = new Combat(characterCombatEntity,targetCombatEntity);
            }
        }else if(activity == CreatureActivity.ATTACK){
            if (attackingCountDown.isFinish()){
                System.out.println("end of attack");
                activity = CreatureActivity.IDLE;
            }
        }
    }

    /**
     * ako casto bude spomalovat
     */
    private boolean getRest() {
        return ((new Random().nextInt(250 - 1) + 1) == 1);
    }


    private float getDistance(Vector2 a, Vector2 b) {
        return Vector2.dst(a.x,a.y,b.x,b.y);
    }

    public CreatureActivity getAnimationActivity() {
        return animationActivity;
    }
}
