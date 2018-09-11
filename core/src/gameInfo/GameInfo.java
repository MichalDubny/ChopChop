package gameInfo;

public class GameInfo {
    public static final String ASSETS_PREFIX_URL = "core\\assets\\";
    public static final int WIDTH = 384 ;
    public static final int HEIGHT = 224 ;
    public static final int MULTIPLE_SIZE = 4;

    public static final int PPM = 100;
    public static final int REPEAT_BG = 5;


    /**
     * vyuziva pri filtroch na to ci sa da prejst cez alebo nie
     */
    public static final short DEFAULT = 0x0001;
    public static final short PLAYER = 0x0002;

    public static final short DESTROYED = 0x0006;
    public static final short STOP_POINT = 0x0007;
    public static final short CREATURE = 0x0016;


    public static float pixelsToMeters (int pixels) {
        return (float)pixels / PPM;
    }

    public static int metersToPixels (float meters) {
        return (int)(meters * PPM);
    }
}
