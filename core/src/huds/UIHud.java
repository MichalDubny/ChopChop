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
import scenes.MainMenu;
import utils.CountDown;

public class UIHud {
    // TODO prve otvorenie okna sa zobrazi vyssie ako pri zmene velkosti potom sa posunie nizsie treba vyriesit
    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;
    private int maxHealPoints;
    private int healPoints;

    private ImageButton pauseBtn;
    private Image pausePanel;
    private Image lifePanel;
    private Image lifeBlock;

    private FontStyle fontStyle;
    private FontStyle fontStyleLife;

    private Label resumeLabel, mainMenuLabel, quitGameLabel;
    private Label gameOverLabel;

    private Label lifeText;


    private CountDown gameOverCD;

    private boolean pause;
    private boolean runningGameOverCD;

    public UIHud(GameMain game) {
        this.game = game;

        gameViewport = new FillViewport(GameInfo.WIDTH,GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(gameViewport, game.getBatch());
        pausePanel = new Image(new Texture(GameInfo.ASSETS_PREFIX_URL + "background\\pause.png"));

        setFontAndColor();

        Gdx.input.setInputProcessor(stage);

        createBtnAndAddListener();

        stage.addActor(pauseBtn);
        stage.addActor(lifePanel);
        stage.addActor(lifeBlock);
        stage.addActor(lifeText);

    }

    private void createBtnAndAddListener() {
        pauseBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\buttons\\menuButtonMedium2.png"))));
//        pauseBtn.setPosition(pauseBtn.getWidth()/2 +4,GameInfo.HEIGHT - pauseBtn.getHeight() -4);
        pauseBtn.setPosition((gameViewport.getWorldWidth()/100)*97,(gameViewport.getWorldHeight()/100)*90 -5 );

        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("aaa");
                createPausePanel();
            }
        });

        lifeBanner();
    }

    private void lifeBanner() {
        lifePanel = new Image(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\life\\lifePanel.png"));
        lifePanel.setPosition(+3,GameInfo.HEIGHT - pauseBtn.getHeight() -30);

        lifeBlock = new Image(new Texture(GameInfo.ASSETS_PREFIX_URL + "\\huds\\life\\life.png")) ;
        lifeBlock.setPosition(+9,GameInfo.HEIGHT - pauseBtn.getHeight() -27);
        lifeBlock.setWidth(200);
        lifeBlock.setHeight(8);

        lifeText = new Label( maxHealPoints + "/" + healPoints,
                new Label.LabelStyle(fontStyleLife.getFont(),fontStyleLife.getColor())) ;

        lifeText.setPosition( 220,GameInfo.HEIGHT - pauseBtn.getHeight() -32);
    }



    public Stage getStage() {
        return stage;
    }

    private void createPausePanel(){
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

    public void updateLifeBar(int maxHealPoints, int healPoints){
        if(healPoints > 0) {
            this.maxHealPoints = maxHealPoints;
            this.healPoints = healPoints;
            lifeText.setText(maxHealPoints + "/" + healPoints);
            float percentHeals = healPoints / (maxHealPoints / 100);
            lifeBlock.setWidth(2 * percentHeals);
        }else {
            lifeText.setText(maxHealPoints + "/" + 0);
            lifeBlock.setWidth(0);
        }

    }

    public void gameOver(boolean isDead) {
        if(isDead){
            pause = true;
            gameOverLabel = new Label( "GAME OVER",new Label.LabelStyle(fontStyle.getFont(),fontStyle.getColor()));

            gameOverLabel.setPosition( GameInfo.WIDTH/2f - 80 ,GameInfo.HEIGHT/2f ,Align.left);

            stage.addActor(pausePanel);
            stage.addActor(gameOverLabel);
            if(!runningGameOverCD){
                gameOverCD = new CountDown(800);
                runningGameOverCD = true;
            }else {
                if (gameOverCD.isFinish()){
                    game.setScreen(new MainMenu(game));
                    pausePanel.remove();
                    gameOverLabel.remove();
                }
            }
        }
    }


    private void setFontAndColor() {
        fontStyle = new FontStyle(30);
        fontStyleLife = new FontStyle(15);
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }



}
