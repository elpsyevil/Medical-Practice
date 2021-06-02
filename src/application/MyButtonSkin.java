package application;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.skin.ButtonSkin;
import javafx.util.Duration;

public class MyButtonSkin extends ButtonSkin {

    public MyButtonSkin(Button control) {
        super(control);
        
        final ScaleTransition scaleIn = new ScaleTransition(Duration.millis(500));
        scaleIn.setNode(control);
        scaleIn.setFromX(control.getScaleX());
        scaleIn.setToX(1.1);
        scaleIn.setFromY(control.getScaleY());
        scaleIn.setToY(1.1);
        
        /*final FadeTransition fadeIn = new FadeTransition(Duration.millis(500));
        fadeIn.setNode(control);
        fadeIn.setToValue(1);*/
        
        control.setOnMouseEntered(e -> { 
        	//fadeIn.playFromStart();
        	scaleIn.playFromStart();
        });
        
        final ScaleTransition scaleOut = new ScaleTransition(Duration.millis(500));
        scaleOut.setNode(control);
        scaleOut.setFromX(1.1);
        scaleOut.setToX(1);
        scaleOut.setFromY(1.1);
        scaleOut.setToY(1);

       /* final FadeTransition fadeOut = new FadeTransition(Duration.millis(500));
        fadeOut.setNode(control);
        fadeOut.setToValue(0.7);*/
        
        control.setOnMouseExited(e -> {
        	//fadeOut.playFromStart();
        	scaleOut.playFromStart();
        });

        //control.setOpacity(0.7);
        
        
        
    }

}
