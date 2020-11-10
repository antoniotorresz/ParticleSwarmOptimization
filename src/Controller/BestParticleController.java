/*
 * Copyright (c) 2020. This project is created by Antonio Torres using MIT Open Source licence. You can use it just for educational purposes.
 *
 */

package Controller;


import Model.Particle;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

public class BestParticleController implements Initializable {

    @FXML
    private Sphere sphereg, sphere2, sphere3;

    @FXML
    private AnchorPane rootAnchorPane, innerAnchorPane, secAnchorPane;
    @FXML
    private Label ltitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //AnchorPane style
        //this.rootAnchorPane.setStyle("-fx-background-color: #263238");
        this.innerAnchorPane.setStyle("-fx-background-color: #CFD8DC");
        this.secAnchorPane.setStyle("-fx-background-color: #CFD8DC");



        //Adding style to shpere:

        this.sphereg.setMaterial(new PhongMaterial(Color.web("#E91E63")));
        this.sphere2.setMaterial(new PhongMaterial(Color.BLUE));
        this.sphere3.setMaterial(new PhongMaterial(Color.GREEN));

        animateSphere(this.sphereg, 158, 0,3 );
        animateSphere(this.sphere2, 60, 60, 1);
        animateSphere(this.sphere3, -60,-60,  1);
    }

    private void animateSphere(Sphere sphereToAnimate, int xMovement,int yMovement, int secs) {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(secs));
        transition.setNode(sphereToAnimate);
        transition.setToX(xMovement);
        transition.setToY(yMovement);
        transition.setAutoReverse(true);
        transition.setCycleCount(Timeline.INDEFINITE);
        transition.play();
    }

    public void initData(Particle particle) {

        // ---- Label title configuration
        if (particle != null) {
            this.ltitle.setText("Best particle Id: " + particle.getId());
        } else {
            this.ltitle.setText("There is no g particle.");
        }
    }
}
