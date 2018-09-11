package scenes;

import background.Background;
import border.EndBorder;
import border.StartBorder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import creatures.CreaturesController;
import gameInfo.UserDataType;
import ground.Ground;
import ground.GroundController;
import gameInfo.GameInfo;
import level.Level1;
import player.Player;

import java.util.logging.Level;

public class GamePlay implements Screen,ContactListener {
    private GameMain game;

    private OrthographicCamera mainCamera;
    private Vector2 cameraPosition;
    private float minCameraXPosition;
    private float maxCameraXPosition;
    private Viewport gameViewport;

    private OrthographicCamera box2DCamera;

    private Box2DDebugRenderer debugRenderer;

    private World world;
    private Level1 level;
    private Background background;
    private StartBorder startBorder;
    private EndBorder endBorder;
    private GroundController groundController;
    private Player player;
    private CreaturesController creaturesController;


    public GamePlay(GameMain game) {
        this.game = game;
        createDefaultBody();

        level = new Level1(world);

        groundController = new GroundController(world);
        background = new Background(world);

        startBorder = new StartBorder(world);
        endBorder = new EndBorder(world);

        player = new Player(world, groundController.getWidthGround());

        creaturesController = new CreaturesController(world,level.getStopPointArrayData(), player);

    }

    /**
     * nastavenia ohladom pozicii kamery, debugeru a svetu
     */
    private void createDefaultBody() {
        mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f,0);
        minCameraXPosition = mainCamera.position.x;
        maxCameraXPosition = (GameInfo.WIDTH*GameInfo.REPEAT_BG) - GameInfo.WIDTH/2;

        gameViewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,mainCamera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false , GameInfo.WIDTH/GameInfo.PPM, GameInfo.HEIGHT/GameInfo.PPM);
        box2DCamera.position.set( GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f , 0);

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0,-9.8f),true);
        world.setContactListener(this);
    }


    @Override
    public void show() {

    }

    /**
     * vstupy z klavesnice
     */
    private void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.movePlayer(player.getPlayerUserData().getLeftMovingLinearImpulse());
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                player.jump();
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.movePlayer(player.getPlayerUserData().getRightMovingLinearImpulse());
            if(Gdx.input.isKeyPressed(Input.Keys.UP)){
                player.jump();
            }
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.jump();
        }else {
            player.setWalking(false);
        }

    }

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();

        background.drawBackground(game.getBatch(), cameraPosition);
        groundController.drawGrounds(game.getBatch());
        player.drawPlayerAnimation(game.getBatch());
        creaturesController.update(game.getBatch());

        game.getBatch().end();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();
        background.updateBackground(mainCamera);

        player.updatePlayer();


        debugRenderer.render(world,box2DCamera.combined);

        world.step(Gdx.graphics.getDeltaTime(),6,2);
    }

    private void update() {
        handleInput();
        cameraPosition = background.setPositionBackgroundLastLayer(player, minCameraXPosition,maxCameraXPosition, mainCamera);
    }


    @Override
    public void resize(int width, int height) {
        gameViewport.update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        background.getTexture().dispose();
        for(Ground ground : groundController.getGround()){
            ground.getTexture().dispose();
        }
//        player.getTexture().dispose();
        debugRenderer.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if ((bodyIsInputName(a,UserDataType.PLAYER) && bodyIsInputName(b,UserDataType.GROUND)) ||
                (bodyIsInputName(a,UserDataType.GROUND) && bodyIsInputName(b,UserDataType.PLAYER))) {
            player.landed();
        }

        if (bodyIsInputName(a,UserDataType.PLAYER) && bodyIsInputName(b,UserDataType.STOP_POINT)){
            b.setUserData("turnOn");
            level.turnOnStopPoint();

        }else if(bodyIsInputName(a,UserDataType.STOP_POINT) && bodyIsInputName(b,UserDataType.PLAYER)) {
            a.setUserData("turnOn");
            level.turnOnStopPoint();
        }
    }


    private boolean bodyIsInputName(Fixture userData,Enum userDataType) {
        return userData.getUserData() != null && userData.getUserData() == userDataType;
    }


    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


}
