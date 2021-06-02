
package application;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class imgSpinSkin extends ButtonSkin {

    public imgSpinSkin(Button control) {
        super(control);
        
        //Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4000), ev -> {
        	final RotateTransition rotate = new RotateTransition(Duration.millis(10000));
            rotate.setNode(control);
            rotate.setCycleCount(Animation.INDEFINITE);
            rotate.setByAngle(360);
            rotate.setInterpolator(Interpolator.LINEAR);
            rotate.play();
           /* }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();*/
        
        
        
    }

}
