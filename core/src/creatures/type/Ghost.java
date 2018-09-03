package creatures.type;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.Creatures;

public class Ghost extends Creatures {

    public Ghost() {
    }


    public Ghost(World world, Vector2 v) {
        super(world, "ghost", v);
    }
}
