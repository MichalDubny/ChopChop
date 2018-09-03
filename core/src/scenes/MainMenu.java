package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;
import gameInfo.GameInfo;

public class MainMenu implements Screen {

    private GameMain game;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private Texture bg;


    public MainMenu(GameMain game) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH/2f,GameInfo.HEIGHT/2f,0);

        gameViewport = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT,mainCamera);

        bg = new Texture(GameInfo.ASSETS_PREFIX_URL +"\\background\\bgMainMenu.png");
    }



    @Override
    public void show() {

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(bg, 0,0 );
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(mainCamera.combined);

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
        bg.dispose();
    }
}
