package application;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.skins.JFXButtonSkin;

import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class myJfxBskin extends JFXButtonSkin {

	public myJfxBskin(JFXButton arg) {
		super(arg);
		final FadeTransition fadeIn = new FadeTransition(Duration.millis(500));
        fadeIn.setNode(arg);
        fadeIn.setToValue(1);
        
        arg.setOnMouseEntered(e -> { 
        	fadeIn.playFromStart();
        	//scaleIn.playFromStart();
        });
        
       /* final ScaleTransition scaleOut = new ScaleTransition(Duration.millis(500));
        scaleOut.setNode(arg);
        scaleOut.setFromX(1.1);
        scaleOut.setToX(1);
        scaleOut.setFromY(1.1);
        scaleOut.setToY(1);*/

       final FadeTransition fadeOut = new FadeTransition(Duration.millis(500));
        fadeOut.setNode(arg);
        fadeOut.setToValue(0.85);
        
        arg.setOnMouseExited(e -> {
        	fadeOut.playFromStart();
        	//scaleOut.playFromStart();
        });

        arg.setOpacity(0.85);	}

}
