package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
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

    private String menuPosition = "";
    private float elapsedTime;

    private Label gameNameLabel, startGameLabel, optionsLabel, creditsLabel, quitLabel;
    float positionMenuX;
    private TextureAtlas signAtlas;
    private Sprite sign;

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

        signAtlas = new TextureAtlas(GameInfo.ASSETS_PREFIX_URL + "\\huds\\sign\\SignAnimation.atlas");
    }

    private void addListener() {
        startGameLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GamePlay(game));
                stage.dispose();
            }
        });
        startGameLabel.addListener(new FocusListener(){
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved") && menuPosition != "startGame") {
                    sign.setPosition(positionMenuX - 40,GameInfo.HEIGHT/2f + 13);
                    menuPosition = "startGame";
                    return false;
                }

                return true;
            }
        });
        optionsLabel.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("click");
            }
        });
        optionsLabel.addListener(new FocusListener(){
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved") && menuPosition != "options") {
                    sign.setPosition(positionMenuX - 40,GameInfo.HEIGHT/2f -22);
                    return false;
                }
                return true;
            }
        });
        creditsLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GamePlay(game));
            }
        });
        creditsLabel.addListener(new FocusListener(){
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved") && menuPosition != "credits") {
                    sign.setPosition(positionMenuX - 40,GameInfo.HEIGHT/2f -55);
                    menuPosition = "credits";
                    return false;
                }

                return true;
            }
        });
        quitLabel.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        quitLabel.addListener(new FocusListener(){
            @Override
            public boolean handle(Event event) {
                if (event.toString().equals("mouseMoved") && menuPosition != "quit") {
                    sign.setPosition(positionMenuX - 40,GameInfo.HEIGHT/2f -92);
                    menuPosition = "quit";
                    return false;
                }

                return true;
            }
        });
    }

    private void createPositionUIElements() {
        setUIElements();
        setPositionUIElements();
    }

    private void setPositionUIElements() {
        positionMenuX = GameInfo.WIDTH/2f - 80;

        gameNameLabel.setPosition(GameInfo.WIDTH/2f,GameInfo.HEIGHT - 40,Align.top);
        startGameLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f + 35,Align.left);
        optionsLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f,Align.left);
        creditsLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f - 35,Align.left);
        quitLabel.setPosition(positionMenuX,GameInfo.HEIGHT/2f - 70,Align.left);

        sign.setPosition(positionMenuX - 40,GameInfo.HEIGHT/2f + 13);
    }

    private void setUIElements() {
        setFontAndColor();

        gameNameLabel = new Label( "Chop Chop",new Label.LabelStyle(gameNameFont,orangeColor)) ;
        startGameLabel = new Label( "Start Game",new Label.LabelStyle(gameNameMenu,orangeColor));
        optionsLabel = new Label( "Options",new Label.LabelStyle(gameNameMenu,orangeColor));
        creditsLabel = new Label( "Credits",new Label.LabelStyle(gameNameMenu,orangeColor));
        quitLabel = new Label( "Quit",new Label.LabelStyle(gameNameMenu,orangeColor));

        sign = new Sprite(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\sign\\sign1.png"));
    }

    private void setFontAndColor() {
        FontStyle nameFontStyle = new FontStyle(60);
        FontStyle menuFontStyle = new FontStyle(30);

        gameNameFont = nameFontStyle.getFont();
        gameNameMenu = menuFontStyle.getFont();

        orangeColor = nameFontStyle.getColor();
    }

    public Stage getStage() {
        return stage;
    }


    public void drawSignAnimation(SpriteBatch batch){
        float frameDuration = 1f / 15f;
        elapsedTime += Gdx.graphics.getDeltaTime();
        Array<TextureAtlas.AtlasRegion> frames = signAtlas.getRegions();

        Animation<TextureAtlas.AtlasRegion> animation = new Animation<TextureAtlas.AtlasRegion>(frameDuration, signAtlas.getRegions());
        batch.draw(animation.getKeyFrame(elapsedTime, true),
                sign.getX() - sign.getWidth() / 2f +20, sign.getY() - (sign.getHeight() / 2f -20)  );
    }

}
