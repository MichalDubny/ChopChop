package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import gameInfo.GameInfo;
import scenes.GamePlay;

public class MainMenuHuds {
    private GameMain game;
    private Stage stage;
    private BitmapFont gameNameFont,gameNameMenu;
    private Color orangeColor;

    private Label gameNameLabel, startGameLabel, optionsLabel, creditsLabel, quitLabel;
    public MainMenuHuds(GameMain game) {
        this.game = game;

        Viewport gameViewPort = new FillViewport(GameInfo.WIDTH,GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewPort, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createPositionUIElements();
        addListener();

        stage.addActor(gameNameLabel);
        stage.addActor(startGameLabel);
        stage.addActor(optionsLabel);
        stage.addActor(creditsLabel);
        stage.addActor(quitLabel);

    }

    private void addListener() {
        startGameLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GamePlay(game));
            }
        });
        optionsLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GamePlay(game));
            }
        });
        creditsLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GamePlay(game));
            }
        });
        quitLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }

    private void createPositionUIElements() {
        setFontAndColor();
        gameNameLabel = new Label( "Chop Chop",new Label.LabelStyle(gameNameFont,orangeColor)) ;
        startGameLabel = new Label( "Start Game",new Label.LabelStyle(gameNameMenu,orangeColor));
        optionsLabel = new Label( "Options",new Label.LabelStyle(gameNameMenu,orangeColor));
        creditsLabel = new Label( "Credits",new Label.LabelStyle(gameNameMenu,orangeColor));
        quitLabel = new Label( "Quit",new Label.LabelStyle(gameNameMenu,orangeColor));

        float positionMenuX = GameInfo.WIDTH/2f - 40;

        gameNameLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT - 20,Align.top);
        startGameLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f + 15,Align.left);
        optionsLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f,Align.left);
        creditsLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f - 15,Align.left);
        quitLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f - 30,Align.left);

    }

    private void setFontAndColor() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal(GameInfo.ASSETS_PREFIX_URL +"\\font\\IMMORTAL.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameterGameName = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterGameName.size = 30;
        FreeTypeFontGenerator.FreeTypeFontParameter parameterMenu = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameterMenu.size = 15;

        gameNameFont = generator.generateFont(parameterGameName);
        gameNameMenu = generator.generateFont(parameterMenu);
        orangeColor = new Color(255f/255f,162f/255f,6f/255f, 1);
    }

    public Stage getStage() {
        return stage;
    }
}
