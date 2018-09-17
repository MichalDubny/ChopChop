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

public class Ghost extends Creature implements Creatures {

    public Ghost(World world, Vector2 v, Player player) {
        super(world, "ghost", v, player);
        setAnimations();
        setCombatData();
    }

    public void setCombatData() {
        setHealthPoints(30);
        setAttackDamage(10);
        attackDistance = 0.7f*2;
    }

    public void setAnimations() {
        Map<CreatureActivity, AnimationsParameters> arrayAnimations  = new HashMap<CreatureActivity, AnimationsParameters>();
        arrayAnimations.put(CreatureActivity.IDLE,new AnimationsParameters(
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\ghost\\GhostAnimation.atlas")
                ,0.5f));
        arrayAnimations.put(CreatureActivity.ATTACK,new AnimationsParameters(
                new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\creatures\\ghost\\GhostAnimationAttack.atlas")
                ,10f));
        setArrayAnimations(arrayAnimations);
    }
}
