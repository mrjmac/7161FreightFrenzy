package NightfallLinearOpMode;
import android.graphics.Bitmap;
import static android.graphics.Color.red;
import static android.graphics.Color.green;
import static android.graphics.Color.blue;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.configuration.ModernRoboticsMotorControllerParams;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;
import com.vuforia.Image;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;

public class Vision extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    VuforiaLocalizer vuforia;
    LinearOpMode opMode;
    int pos = 3;

    public Vision(LinearOpMode opMode){
        this.opMode = opMode;
        VuforiaLocalizer.Parameters params = new VuforiaLocalizer.Parameters();
        params.vuforiaLicenseKey = "AQvLCbX/////AAABmTGnnsC2rUXvp1TAuiOSac0ZMvc3GKI93tFoRn4jPzB3uSMiwj75PNfUU6MaVsNZWczJYOep8LvDeM/3hf1+zO/3w31n1qJTtB2VHle8+MHWNVbNzXKLqfGSdvXK/wYAanXG2PBSKpgO1Fv5Yg27eZfIR7QOh7+J1zT1iKW/VmlsVSSaAzUSzYpfLufQDdE2wWQYrs8ObLq2kC37CeUlJ786gywyHts3Mv12fWCSdTH5oclkaEXsVC/8LxD1m+gpbRc2KC0BXnlwqwA2VqPSFU91vD8eCcD6t2WDbn0oJas31PcooBYWM6UgGm9I2plWazlIok72QG/kOYDh4yXOT4YXp1eYh864e8B7mhM3VclQ";
        params.cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(params);
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true); //enables RGB565 format for the image
        vuforia.setFrameQueueCapacity(4); //tells VuforiaLocalizer to only store one frame at a time
    }

    public Bitmap getImage() throws InterruptedException {
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        long numImages = frame.getNumImages();
        Image rgb = null;
        for (int i = 0; i < numImages; i++) {
            Image img = frame.getImage(i);
            int fmt = img.getFormat();
            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;
            }
        }
        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());
        return bm;
    }

    /*
    public String getTeamMarkerPos() throws InterruptedException {
        Bitmap rgbImage = getImage();
        ArrayList<Integer> xValues = new ArrayList<>();


        for (int x = 0; x < 70; x++) {
            int pixel = rgbImage.getPixel(x, 300);
            if (isWhite(pixel))
                xValues.add(x);
        }
        for (int x = 275; x < 330; x++) {
            int pixel = rgbImage.getPixel(x, 230);
            if (isWhite(pixel))
                xValues.add(x);
        }
        for (int x = 500; x < 550; x++) {
            int pixel = rgbImage.getPixel(x, 185);
            if (isWhite(pixel))
                xValues.add(x);
        }

        int avgX = 0;
                for (int xCoor : xValues) {
            avgX += xCoor;
        }

        if (xValues.size() != 0) {
            avgX /= xValues.size();
        }

        if (avgX < 100)
            pos = "left";
        else if (avgX > 200 && avgX < 450)
            pos = "middle";
        else
            pos = "right";

        if (xValues.size() < 10 )
            pos = "right";

        return pos;
    }

     */

    public int getPosNewMethod() throws InterruptedException {

        Bitmap rgbImage = getImage();
        int leftWhite = 0;
        int rightWhite = 0;
        int midWhite = 0;

        for (int y = 185; y < 250; y++) {
            for (int x = 0; x < 75; x++) {
                int pixel = rgbImage.getPixel(x, y);
                if (isWhite(pixel)) {
                    leftWhite += 1;
                }
            }
            for (int x = 275; x < 350; x++) {
                int pixel = rgbImage.getPixel(x, y);
                if (isWhite(pixel)) {
                    midWhite += 1;
                }
            }
            for (int x = 500; x < 575; x++) {
                int pixel = rgbImage.getPixel(x, y-50);
                if (isWhite(pixel)) {
                    rightWhite += 1;
                }
            }
        }

        int high = Math.max(Math.max(midWhite, leftWhite), rightWhite);
        if (high == rightWhite) {
            pos = 3;
        }
        else if (high == leftWhite) {
            pos= 1;
        }
        else {
            pos = 2;
        }
        /*
        opMode.telemetry.addData("leftWhite\n", leftWhite);
        opMode.telemetry.addData("MiddleWhite\n", midWhite);
        opMode.telemetry.addData("rightWhite\n", rightWhite);
        opMode.telemetry.addData("pos", pos);
        opMode.telemetry.update();

         */
        return pos;
    }

    public int getPosNewMethodBlue() throws InterruptedException {

        Bitmap rgbImage = getImage();
        int rightWhite = 0;
        int midWhite = 0;

        for (int y = 100; y < 175; y++) {
            for (int x = 0  ; x < 100; x++) {
                int pixel = rgbImage.getPixel(x, y);
                if (isWhite(pixel)) {
                    midWhite += 1;
                }
            }
            for (int x = 250; x < 350; x++) {
                int pixel = rgbImage.getPixel(x, y-50);
                if (isWhite(pixel)) {
                    rightWhite += 1;
                }
            }

        }

        int high = Math.max(midWhite, rightWhite);
        if (high < 20) {
            pos = 1;
        }
        else if (high == midWhite) {
            pos= 2;
        }
        else if (high == rightWhite){
            pos = 3;
        }
        /*
        opMode.telemetry.addData("leftWhite\n", leftWhite);
        opMode.telemetry.addData("MiddleWhite\n", midWhite);
        opMode.telemetry.addData("rightWhite\n", rightWhite);
        opMode.telemetry.addData("pos", pos);
        opMode.telemetry.update();

         */
        return pos;
    }






    public boolean isWhite(int pixel) throws InterruptedException {

        if (red(pixel) > 175 && blue(pixel) > 175 && green(pixel) > 175)
            return true;

        return false;
    }

}
