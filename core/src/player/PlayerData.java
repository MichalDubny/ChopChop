package player;

import com.badlogic.gdx.math.Vector2;
import gameInfo.GameInfo;
import gameInfo.UserData;
import gameInfo.UserDataType;

public class PlayerData extends UserData{


    private final Vector2 jumpingLinearImpulse;
    private final Vector2 rightMovingLinearImpulse;
    private final Vector2 leftMovingLinearImpulse;
    private final Vector2 dampingFall;
    /**
     * maximalna rychlost
     */
    public static final float MAX_VELOCITY = 3; //2f;


    public static final float JUMP_LENGTH = 10f/GameInfo.PPM; //20f;
    public static final float MAX_JUMP_SPEED   = 7f;
    public static final float DAMP = 0.90f; //20f;




    public PlayerData(){
        super();
        jumpingLinearImpulse = new Vector2(0, 20f*4 ); //20f
        rightMovingLinearImpulse = new Vector2(8.40f, 0 );  //2.05
        leftMovingLinearImpulse = new Vector2(-8.40f, 0 ); //2.05
        dampingFall = new Vector2(0,2.7f*4);
        dataType = UserDataType.PLAYER;
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

    public Vector2 getDampingFall() {
        return dampingFall;
    }

    public float getMAX_VELOCITY() {
        return MAX_VELOCITY;
    }

}
