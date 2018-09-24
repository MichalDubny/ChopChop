package combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import creatures.aiBehavior.Box2dSteeringEntity;

public class CombatEntity extends Sprite {
    protected Box2dSteeringEntity steeringEntity;

    protected int maxHealPoints;
    protected int healPoints;
    protected int attackDamage;
    protected float attackDistance;
    protected boolean dead = false;



    public CombatEntity() {
    }

    public CombatEntity(Texture texture) {
        super(texture);
    }

    public Box2dSteeringEntity getSteeringEntity() {
        return steeringEntity;
    }

    public int getMaxHealPoints() {
        return maxHealPoints;
    }

    public int getHealPoints() {
        return healPoints;
    }


    public void setHealthPoints(int healPoints) {
        this.healPoints = healPoints;
    }


    public int getAttackDamage() {
        return attackDamage;
    }


    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getAttackDistance() {
        return attackDistance;
    }

    public void setAttackDistance(float attackDistance) {
        this.attackDistance = attackDistance;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

}
