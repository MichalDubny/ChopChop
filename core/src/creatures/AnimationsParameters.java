package creatures;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AnimationsParameters {
    private CreatureActivity creatureActivity;
    private TextureAtlas textureAtlas;
    private float frameDuration;

    public AnimationsParameters( TextureAtlas textureAtlas, float frameDuration) {
        this.textureAtlas = textureAtlas;
        this.frameDuration = frameDuration;
    }

    public AnimationsParameters() {

    }


    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public float getFrameDuration() {
        return frameDuration;
    }
}
