package combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import creatures.aiArrive.Box2dSteeringEntity;

public class CombatEntity extends Sprite {
    protected Box2dSteeringEntity steeringEntity;

    protected int healPoints;
    protected int attackDamage;
    protected boolean dead = false;

    public CombatEntity() {
    }

    public CombatEntity(Texture texture) {
        super(texture);
    }

    public Box2dSteeringEntity getSteeringEntity() {
        return steeringEntity;
    }



    public int getHealPoints() {
        return healPoints;
    }


    public void setHealPoints(int healPoints) {
        this.healPoints = healPoints;
    }


    public int getAttackDamage() {
        return attackDamage;
    }


    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}
