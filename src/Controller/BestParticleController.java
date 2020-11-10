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
    private Sphere sphereg;

    @FXML
    private AnchorPane rootAnchorPane, innerAnchorPane;
    @FXML
    private Label ltitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //AnchorPane style
        this.rootAnchorPane.setStyle("-fx-background-color: #263238");
        this.innerAnchorPane.setStyle("-fx-background-color: #455A64");


        //Adding style to shpere:
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.web("#E91E63"));
        material.setSpecularColor(Color.BLACK);
        this.sphereg.setMaterial(material);
        animateSphere();

    }

    private void animateSphere() {

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(3));
        transition.setNode(this.sphereg);
        transition.setToX(158);
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
