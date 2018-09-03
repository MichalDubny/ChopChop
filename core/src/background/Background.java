package background;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import gameInfo.GameInfo;
import player.Player;

public class Background extends Sprite{
    private World world;
    private Body body;

    private Sprite[] bgs;

    public Background(World world) {
        super(new Texture(GameInfo.ASSETS_PREFIX_URL +"\\background\\background.png"));
        this.world = world;
        createFirstBackgroundLayer();

    }

    private void createFirstBackgroundLayer() {
        bgs = new Sprite[GameInfo.REPEAT_BG];
        for(int i = 0; i <bgs.length; i++){
            bgs[i] = new Sprite(new Texture(GameInfo.ASSETS_PREFIX_URL +"\\background\\mountains.png"));
            bgs[i].setPosition((i * bgs[i].getWidth()),0);
//            lastYPosition = Math.abs(bgs[i].getY());
        }
    }

    public void moveBackground(float x) {

    }


    public void updateBackground(OrthographicCamera mainCamera) {
        this.setPosition(mainCamera.position.x,mainCamera.position.y);
    }

    public Vector2 setPositionBackgroundLastLayer(Player player, float minCameraXPosition , float maxCameraXPosition, OrthographicCamera mainCamera ) {
        Vector2 vector2 =new Vector2();
        float x;

        if(player.isJumping()){
            mainCamera.position.y = (player.getBodyYPosition() )* GameInfo.PPM + player.getHeight() +10 ;
        }
        if(minCameraXPosition  < player.getX() ){
            if(player.getX() < maxCameraXPosition) {
                mainCamera.position.x = (player.getBodyXPosition()) * GameInfo.PPM;
                x = (mainCamera.position.x - (this.getWidth() / 2)) - 40;
            }else {
                mainCamera.position.x = (maxCameraXPosition);
                x = (mainCamera.position.x) - GameInfo.WIDTH/2 - 40;
            }
        }else {
            x = - 40;
        }
        return vector2.set(x , mainCamera.position.y - player.getHeight()*2 -20);
    }

    public void drawBackground(SpriteBatch batch, Vector2 cameraXPosition) {
            drawLastBackgroundLayer(batch, cameraXPosition);
            drawFistBackgroundLayer(batch);
    }


    /**
     * outOfCamera je roztiahnuta mimo obrazovku koly tomu aby pri pohybe nezobrazovalo prezdne miesto.
     * @param batch
     * @param cameraPosition
     */
    private void drawLastBackgroundLayer(SpriteBatch batch, Vector2 cameraPosition) {
        int outOfCamera = 50;
        batch.draw(getTexture(), cameraPosition.x ,cameraPosition.y/3,GameInfo.WIDTH + outOfCamera, GameInfo.HEIGHT + outOfCamera);
    }

    private void drawFistBackgroundLayer(SpriteBatch batch) {
        for(int i = 0; i <bgs.length; i++){
            batch.draw(bgs[i], bgs[i].getX(),0 );
        }
    }

}
