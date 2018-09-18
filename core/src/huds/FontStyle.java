package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import gameInfo.GameInfo;

public class FontStyle {
    private BitmapFont font;
//    ,gameNameMenu;
    private Color color;


    public FontStyle(int fontSize) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal(GameInfo.ASSETS_PREFIX_URL +"\\font\\IMMORTAL.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter parameterGameName = new FreeTypeFontGenerator.FreeTypeFontParameter();
        FreeTypeFontGenerator.FreeTypeFontParameter parameterMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameterGameName.size = fontSize;//60;

        font = generator.generateFont(parameterGameName);
//        gameNameMenu = generator.generateFont(parameterMenu);
        color = new Color(255f/255f,162f/255f,6f/255f, 1);
    }


    public BitmapFont getFont() {
        return font;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
