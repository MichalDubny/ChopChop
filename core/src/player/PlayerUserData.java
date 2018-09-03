package player;

import com.badlogic.gdx.math.Vector2;
import gameInfo.UserData;
import gameInfo.UserDataType;

public class PlayerUserData extends UserData{


    private final Vector2 jumpingLinearImpulse;
    private final Vector2 rightMovingLinearImpulse;
    private final Vector2 leftMovingLinearImpulse;
    /**
     * maximalna rychlost
     */
    private float maxVelocity = 2f;

    public PlayerUserData(){
        super();
        jumpingLinearImpulse = new Vector2(0, 20f );
        rightMovingLinearImpulse = new Vector2(2.05f, 0 );
        leftMovingLinearImpulse = new Vector2(-2.05f, 0 );
        userDataType = UserDataType.PLAYER;
    }

    public Vector2 getJumpingLinearImpulse() {
        return jumpingLinearImpulse;
    }

    public Vector2 getRightMovingLinearImpulse() {
        return rightMovingLinearImpulse;
    }

    public Vector2 getLeftMovingLinearImpulse() {
        return leftMovingLinearImpulse;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }
}
