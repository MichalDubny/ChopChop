package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import gameInfo.GameInfo;
import scenes.GamePlay;
import scenes.MainMenu;

public class UIHud {
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton pauseBtn;
    private Image pausePanel;

    private FontStyle fontStyle;
    private Label resumeLabel, mainMenuLabel, quitGameLabel;


    private boolean pause;

    public UIHud(GameMain game) {
        this.game = game;

        gameViewport = new FillViewport(GameInfo.WIDTH,GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());
        setFontAndColor();

        Gdx.input.setInputProcessor(stage);

        createBtnAndAddListener();

        stage.addActor(pauseBtn);

    }

    private void createBtnAndAddListener() {
        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\buttons\\menuButtonMedium2.png"))));
        pauseBtn.setPosition(pauseBtn.getWidth()/2 +4,GameInfo.HEIGHT -4,Align.top);


        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                createPausePanel();
            }
        });

        lifeBanner();
    }

    public Stage getStage() {
        return stage;
    }

    private void createPausePanel(){
        pausePanel = new Image(new Texture(GameInfo.ASSETS_PREFIX_URL + "background\\pause.png"));
        pause = true;

        resumeLabel = new Label( "Resume",new Label.LabelStyle(fontStyle.getFont(),fontStyle.getColor())) ;
        mainMenuLabel = new Label( "Main Menu",new Label.LabelStyle(fontStyle.getFont(),fontStyle.getColor())) ;
        quitGameLabel = new Label( "Exit Game",new Label.LabelStyle(fontStyle.getFont(),fontStyle.getColor())) ;


        float positionMenuX = GameInfo.WIDTH/2f - 80;
        resumeLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f + 40,Align.left);
        mainMenuLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f,Align.left);
        quitGameLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f - 40,Align.left);

        resumeLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                removePausePanel();
            }
        });

        mainMenuLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stage.dispose();
                game.setScreen(new MainMenu(game));

            }
        });

        quitGameLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });


        stage.addActor(pausePanel);

        stage.addActor(resumeLabel);
        stage.addActor(mainMenuLabel);
        stage.addActor(quitGameLabel);

    }

    private void removePausePanel() {
        pausePanel.remove();
        resumeLabel.remove();
        mainMenuLabel.remove();
        quitGameLabel.remove();
        pause = false;
    }

    private void setFontAndColor() {
        fontStyle = new FontStyle(30);
    }

    public boolean isPause() {
        return pause;
    }
}
