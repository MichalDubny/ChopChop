package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import gameInfo.GameInfo;

public class UIHud {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;


    private ImageButton pauseBtn;

    public UIHud(GameMain game) {
        this.game = game;

        gameViewport = new FillViewport(GameInfo.WIDTH,GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createBtnAndAddListener();

        stage.addActor(pauseBtn);
    }

    private void createBtnAndAddListener() {
        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\buttons\\menuButtonMedium.png"))));
        pauseBtn.setPosition(pauseBtn.getWidth()/2 +4,GameInfo.HEIGHT -4,Align.top);


        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("click menu");
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
