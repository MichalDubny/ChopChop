package creatures.type;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import creatures.AnimationsParameters;
import creatures.Creature;
import creatures.CreatureActivity;
import creatures.Creatures;
import gameInfo.GameInfo;
import player.Player;

import java.util.HashMap;
import java.util.Map;


public class Skeleton extends Creature implements Creatures {


    public Skeleton(World world, Vector2 v, Player player) {
        super(world, "skeleton", v, player);
        setAnimations();
    }

    @Override
    public void setAnimations() {
        Map<CreatureActivity,AnimationsParameters> arrayAnimations  = new HashMap<CreatureActivity, AnimationsParameters>();
        arrayAnimations.put(CreatureActivity.IDLE,new AnimationsParameters(
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\skeleton\\SkeletonAnimation.atlas")
                ,5f));
        arrayAnimations.put(CreatureActivity.WALK,new AnimationsParameters(
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\skeleton\\SkeletonAnimationWalk.atlas")
                ,5f));
        arrayAnimations.put(CreatureActivity.ATTACK,new AnimationsParameters(
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\skeleton\\SkeletonAnimationAttack.atlas")
                ,15f));
        setArrayAnimations(arrayAnimations);
    }

}
