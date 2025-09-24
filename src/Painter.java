import java.awt.*;

public class Painter {

    public static void drawLine(double startX, double startY, double endX, double endY, int color, int thickness,
            Graphics g) {
        double gradient = 0.0;
        int x, y, tempVar, dY;
        double rise, run, yInc, xInc;
        double fgR = (color >> 16) & 255;
        double fgG = (color >> 8) & 255;
        double fgB = (color) & 255;
        boolean aa = RenderSettings.get().aa;
        int[] ARGB = RenderSettings.get().ARGB;
        int width = RenderSettings.get().width;
        double radius = RenderSettings.get().radius;
        int startPointX = (int) ((startX * radius) + RenderSettings.get().xHeight);
        int startPointY = (int) ((startY * radius) + RenderSettings.get().yHeight);
        int endPointX = (int) ((endX * radius) + RenderSettings.get().xHeight);
        int endPointY = (int) ((endY * radius) + RenderSettings.get().yHeight);

        if (endPointX < startPointX) {
            tempVar = startPointX;
            startPointX = endPointX;
            endPointX = tempVar;
            tempVar = startPointY;
            startPointY = endPointY;
            endPointY = tempVar;
        }
        run = endPointX - startPointX;
        rise = endPointY - startPointY;
        x = startPointX;
        y = startPointY;
        yInc = 0;
        xInc = 0;
        dY = 1;
        if (rise < 0) { // change in y is positive
            dY = -1;
            rise = -rise;
        }
        if (rise < run) { // steps in x
            while (x != endPointX) {
                if (aa) {
                    gradient = yInc / run;
                    // -----
                    int bgColor = ARGB[(y + dY) * width + x];

                    int pixelColor = ((int) (gradient * (double) fgR) << 16) |
                            ((int) (gradient * (double) fgG) << 8) |
                            ((int) (gradient * (double) fgB));
                    ARGB[(y + dY) * width + x] = pixelColor;

                    pixelColor = (color);
                    ARGB[y * width + x] = pixelColor;

                    pixelColor = ((int) ((1.0 - gradient) * (double) fgR) << 16) |
                            ((int) ((1.0 - gradient) * (double) fgG) << 8) |
                            ((int) ((1.0 - gradient) * (double) fgB));
                    ARGB[(y - dY) * width + x] = pixelColor;
                } else {
                    ARGB[(y) * width + x] = color;
                }
                x++;
                yInc += rise;
                if (yInc >= run) {
                    yInc -= run;
                    y += dY;
                }
            }
        } else { // steps in y
            while (y != endPointY) {
                if (aa) {
                    gradient = xInc / rise;
                    int pixelColor = ((int) (gradient * (double) fgR) << 16) |
                            ((int) (gradient * (double) fgG) << 8) |
                            ((int) (gradient * (double) fgB));
                    ARGB[y * width + (x + 1)] = pixelColor;

                    pixelColor = (color);
                    ARGB[y * width + (x)] = pixelColor;

                    pixelColor = ((int) ((1.0 - gradient) * (double) fgR) << 16) |
                            ((int) ((1.0 - gradient) * (double) fgG) << 8) |
                            ((int) ((1.0 - gradient) * (double) fgB));
                    ARGB[(y) * width + (x - 1)] = pixelColor;
                } else {
                    ARGB[(y) * width + (x)] = color;
                }

                y += dY;
                xInc += run;
                if (xInc >= rise) {
                    xInc -= rise;
                    x++;
                }
            }
        }
    }
}