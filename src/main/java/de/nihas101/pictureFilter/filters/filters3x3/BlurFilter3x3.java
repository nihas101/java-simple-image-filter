package de.nihas101.pictureFilter.filters.filters3x3;

import de.nihas101.pictureFilter.filters.Filter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import static java.lang.Math.*;

public class BlurFilter3x3 extends PixelIterator3x3 implements Filter {
    @Override
    public void applyFilter(Image image, WritableImage writableImage) {
        setup(image, writableImage);

        // source: http://lodev.org/cgtutor/filtering.html
        rowWise(imageHeight, imageWidth,
                (int x1, int y1, int x2, int y2, int x3, int y3,
                 int x4, int y4, int x,  int y,  int x5, int y5,
                 int x6, int y6, int x7, int y7, int x8, int y8) ->{
                    /* Get colors of neighbours for blur calculations */
                    Color color1 = pixelReader.getColor(x,y);
                    Color color2 = pixelReader.getColor(x4,y4);
                    Color color3 = pixelReader.getColor(x2,y2);
                    Color color4 = pixelReader.getColor(x5,y5);
                    Color color5 = pixelReader.getColor(x7,y7);

                   /* Set new color */
                    Color color = averageOfColors(color1,color2, color3, color4, color5);
                    pixelWriter.setColor(x,y,color);
                });
    }

    @Override
    public String getFilterName() { return "blur3x3"; }

    private Color averageOfColors(Color... colors){
        double red = 0;
        double blue = 0;
        double green = 0;

        for (Color color : colors) {
            red   += color.getRed();
            blue  += color.getBlue();
            green += color.getGreen();
        }

        int middle = (int) ceil(colors.length/((double)2));
        red = min(max(red*.2,0),1);
        green = min(max(green*.2,0),1);
        blue = min(max(blue*.2,0),1);

        return new Color(red, green, blue, colors[middle].getOpacity());
    }
}
