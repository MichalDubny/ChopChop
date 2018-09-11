package creatures.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.Creature;
import creatures.CreatureActivity;
import creatures.Creatures;
import gameInfo.GameInfo;
import player.Player;

import java.util.HashMap;


public class Skeleton extends Creature implements Creatures {


    public Skeleton(World world, Vector2 v, Player player) {
        super(world, "skeleton", v, player);
        setAnimations();
    }

    @Override
    public void setAnimations() {
        HashMap<CreatureActivity, TextureAtlas> arrayAnimations = new HashMap<CreatureActivity, TextureAtlas>();
        arrayAnimations.put(CreatureActivity.IDLE,
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\skeleton\\SkeletonAnimation.atlas"));
        arrayAnimations.put(CreatureActivity.WALK,
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\skeleton\\SkeletonAnimationWalk.atlas"));
        super.setArrayAnimations(arrayAnimations);
    }

}
