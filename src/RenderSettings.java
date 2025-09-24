public class RenderSettings {
    enum RenderStyle {
        WIREFRAME,
        SOLID
    };

    RenderStyle style = RenderStyle.SOLID;
    double radius = 500;
    double xHeight, yHeight;
    int foregroundColor = 0x00ffffff;
    int backgroundColor = 0x00000000;;
    int ambientColor = 0x00323264;
    int reflectedColor = 0x00c86464;
    boolean aa = false;
    double camDist = 5.5;
    int[] ARGB;
    int width;
    int lineThickness = 2;
    boolean cullBackFace = true;

    static RenderSettings me = new RenderSettings();

    public static RenderSettings get() {
        return me;
    }
}