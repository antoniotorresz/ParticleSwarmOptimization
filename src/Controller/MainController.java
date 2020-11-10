/*
 * Copyright (c) 2020. This project is created by Antonio Torres using MIT Open Source licence. You can use it just for educational purposes.
 *
 */

package Controller;

import Model.Particle;
import Utilities.TestFunctions;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Vector;


public class MainController implements Initializable {
    // Global variables
    private static int SWARM_SIZE;
    private static int ITERATIONS;
    public Particle g = new Particle();


    //Instanciar los elementos de la interface grágica. El nombre del objeto debe coincidir con su fxid.
    @FXML
    private Spinner spn_swrmsize, spn_niter;

    @FXML
    private ComboBox cmb_funct;

    @FXML
    private Label lblmsg;

    @FXML
    private TableView tvinitialpop;

    @FXML
    private TableColumn<Particle, Integer> idCol;
    @FXML
    private TableColumn<Particle, Double> fitCol;
    @FXML
    private TableColumn<Particle, Vector<Double>> posCol;
    @FXML
    private TableColumn<Particle, Vector<Double>> velCol;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Para inicializar interfaz grafica

        //---- Iniciando spinner swrmsize
        SpinnerValueFactory<Integer> swrmSize = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 200, 10);
        this.spn_swrmsize.setValueFactory(swrmSize);

        //---- Iniciando spinner numero iteraciones
        SpinnerValueFactory<Integer> iterations = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        this.spn_niter.setValueFactory(iterations);

        //---- Iniciando combobox funciones
        this.cmb_funct.getItems().addAll("Sphere", "Goldstein", "McCormick", "EggHolder", "Beale", "Matyas");
        this.cmb_funct.setPromptText("Choose one");

        //---- lblmsg conf
        lblmsg.setVisible(false);

        //---- Initial population tableview
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fitCol.setCellValueFactory(new PropertyValueFactory<>("fitness"));
        posCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        velCol.setCellValueFactory(new PropertyValueFactory<>("velocity"));

    }

    private boolean validateFields() {
        return this.cmb_funct.getValue() != null
                && Integer.parseInt(String.valueOf(this.spn_niter.getValue())) > 0
                && Integer.parseInt(String.valueOf(this.spn_swrmsize.getValue())) > 9;
    }

    public void handleCompute() {
        if (validateFields()) {
            this.lblmsg.setText("Starting procees with " + this.cmb_funct.getValue() + " function.");
            this.lblmsg.setTextFill(Color.GREEN);

            startProcess();
        } else {
            this.lblmsg.setText("Something is missing");
            this.lblmsg.setTextFill(Color.RED);
        }
        this.lblmsg.setVisible(true);
    }

    private void printPopulation(Vector<Particle> population) {
        this.tvinitialpop.setItems(FXCollections.observableList(population));
    }

    private void PSOProcess(Vector<Float> lb, Vector<Float> ub) {
        Vector<Particle> population = new Vector<>();
        int N_DIMENTIONS = 2;

        //Creating a population with N particles
        for (int i = 1; i <= this.SWARM_SIZE; i++) {
            Vector<Double> tmpPos = new Vector<>();

            tmpPos.add(TestFunctions.generateRandomDouble(lb.elementAt(0), ub.elementAt(0)));
            tmpPos.add(TestFunctions.generateRandomDouble(lb.elementAt(1), ub.elementAt(1)));
            Double fit = TestFunctions.sphereFun(tmpPos);

            population.add(new Particle(i, tmpPos, tmpPos, tmpPos, fit, fit));

        }

        g.setFitness(Double.MAX_VALUE);
        printPopulation(new Vector<>(population)); //Sending a copy of vector just to have the first population without sorting


        for (int i = 0; i < this.ITERATIONS; i++) {

            //Comparing current fitness with bestfitness
            if (population.elementAt(i).getFitness() < population.elementAt(i).getBestFitness()) {
                population.elementAt(i).setBestFitness(population.elementAt(i).getFitness());
                population.elementAt(i).setBestPosition(population.elementAt(i).getPosition());
            }

            //Comparing fitness of particles
            population.sort(Comparator.comparingDouble(Particle::getFitness));

            if (population.firstElement().getFitness() < g.getFitness())
                this.g = population.firstElement(); //Getting g particle


            for (Particle p_i : population) {
                Vector<Double> part1 = TestFunctions.componentWiseMult(TestFunctions.U(0, 2, 2),
                        TestFunctions.vecSubstract(p_i.getBestPosition(), p_i.getPosition()));

                Vector<Double> part2 = TestFunctions.componentWiseMult(TestFunctions.U(0, 2, 2),
                        TestFunctions.vecSubstract(g.getPosition(), p_i.getPosition()));

                Vector<Double> fullEquation = TestFunctions.vecSum(part1, part2);

            }
        }
    }

    private void startProcess() {
        this.SWARM_SIZE = Integer.parseInt(String.valueOf(this.spn_swrmsize.getValue()));
        this.ITERATIONS = Integer.parseInt(String.valueOf(this.spn_niter.getValue()));

        Vector<Float> lb = new Vector<>(), ub = new Vector<>(); //Lower and upper bounds

        switch (String.valueOf(this.cmb_funct.getValue())) {
            case "Sphere":
                lb.add((float) -10);
                lb.add((float) -10);
                ub.add((float) 10);
                ub.add((float) 10);
                PSOProcess(lb, ub);
                break;
            case "Goldstein":
                break;
            case "McCormick":
                break;
            case "EggHolder":
                break;
            case "Beale":
                break;
            case "Matyas":
                break;
        }
    }

    public void newSimulation() {

    }

    public void closeApp() {
        Platform.exit();
    }

    public Stage loadBPWindow() throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/FXMLViews/BestParticleSimulation.fxml"
                )
        );

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(
                new Scene(loader.load())
        );

        BestParticleController controller = loader.getController();
        controller.initData(this.g);

        stage.setResizable(false);
        stage.show();

        return stage;
    }
}
