package creatures.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.Creatures;


public class Skeleton extends Creatures {

    public Skeleton() {
    }

    public Skeleton(World world, Vector2 v) {
        super(world, "skeleton", v);
    }
}
