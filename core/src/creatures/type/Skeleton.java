package creatures.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.Creature;
import player.Player;


public class Skeleton extends Creature {


    public Skeleton(World world, Vector2 v, Player player) {
        super(world, "skeleton", v, player);
    }
}
