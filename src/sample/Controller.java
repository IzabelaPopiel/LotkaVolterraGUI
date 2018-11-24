package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.EulerIntegrator;


public class Controller {

    @FXML
    private Label txtTitle;

    @FXML
    private TextField txtA;

    @FXML
    private TextField txtB;

    @FXML
    private TextField txtC;

    @FXML
    private TextField txtD;

    @FXML
    private TextField txtDrapiezniki;

    @FXML
    private TextField txtOfiary;

    @FXML
    private TextField txtCzasSymulacji;

    @FXML
    private Button btnRysuj;

    @FXML
    private Button btnCzysc;

    @FXML
    private TextArea txtWarning;

    @FXML
    private ScatterChart<Number, Number> graph;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;


    public Controller() {
        System.out.println("Calling constructor");
    }

    public void initialize() { //initialize nie mozna umiescic w konstruktorze

        System.out.println("Calling initialize");


    }

    @FXML
    void setBtnRysujPressed(ActionEvent event) {

        try {
            xAxis.setLabel("Liczebnosc populacji ofiar");
            yAxis.setLabel("Liczebnosc populacji drapieżników");

            xAxis.setAutoRanging(true);
            yAxis.setAutoRanging(true);

            xAxis.setTickUnit(1);
            yAxis.setTickUnit(1);

            XYChart.Series<Number, Number> series1 = new XYChart.Series<>();

            for (int i = 0; i < getPhaseSpace()[0].length; i++) {
                series1.getData().add(new XYChart.Data<Number, Number>(getPhaseSpace()[0][i], getPhaseSpace()[1][i]));
            }
            graph.getData().addAll(series1);
            txtWarning.clear();
            txtWarning.appendText("Wprowadzone dane są prawidłowe");
            txtWarning.setStyle("-fx-text-inner-color: green;");


        } catch (NumberFormatException e) {
            //System.out.println("Wprowadzone dane sa nieprawidlowe!");
            txtWarning.clear();
            txtWarning.appendText("Wprowadzone dane są nieprawidłowe!");
            txtWarning.setStyle("-fx-text-inner-color: red;");

        }
    }

    @FXML
    void setBtnCzyscPressed(ActionEvent event) {

        graph.getData().removeAll(graph.getData());
        txtWarning.clear();
    }

    private double[][] getPhaseSpace() {
        double a = Double.parseDouble(String.valueOf(txtA.getText()));
        double b = Double.parseDouble(String.valueOf(txtB.getText()));
        double c = Double.parseDouble(String.valueOf(txtC.getText()));
        double d = Double.parseDouble(String.valueOf(txtD.getText()));
        double ofiary = Double.parseDouble(String.valueOf(txtOfiary.getText()));
        double drapiezniki = Double.parseDouble(String.valueOf(txtDrapiezniki.getText()));
        double czas = Double.parseDouble(String.valueOf(txtCzasSymulacji.getText()));


        FirstOrderDifferentialEquations lv = new LotkaVolterra(a, b, c, d);
        FirstOrderIntegrator eulerLv = new EulerIntegrator(0.01);
        LotkaVolterraPath lotkaVolterraPath = new LotkaVolterraPath();
        eulerLv.addStepHandler(lotkaVolterraPath);

        double[] yStart = new double[]{ofiary, drapiezniki};
        double[] yStop = new double[]{0, 0};

        eulerLv.integrate(lv, 0, yStart, czas, yStop);

        double[] arrX = lotkaVolterraPath.ArrayList2Array(lotkaVolterraPath.getxValues());
        double[] arrY = lotkaVolterraPath.ArrayList2Array(lotkaVolterraPath.getyValues());
        double[] arrT = lotkaVolterraPath.ArrayList2Array(lotkaVolterraPath.gettValues());

        double[][] arr = {arrX, arrY, arrT};

        return arr;
    }
}
