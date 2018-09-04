package creatures.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.Creature;

public class Ghost extends Creature {

    public Ghost(World world, Vector2 v) {
        super(world, "ghost", v);
    }
}
