package sample;

import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.ode.sampling.StepHandler;
import org.apache.commons.math3.ode.sampling.StepInterpolator;

import java.util.ArrayList;

public class LotkaVolterraPath implements StepHandler {


    ArrayList<Double> xValues = new ArrayList<>();
    ArrayList<Double> yValues = new ArrayList<>();
    ArrayList<Double> tValues = new ArrayList<>();


    public ArrayList<Double> getxValues() {
        return xValues;
    }

    public ArrayList<Double> getyValues() {
        return yValues;
    }

    public ArrayList<Double> gettValues() {
        return tValues;
    }

    @Override
    public void init(double v, double[] doubles, double v1) {

    }

    @Override
    public void handleStep(StepInterpolator stepInterpolator, boolean b) throws MaxCountExceededException {

        double t = stepInterpolator.getCurrentTime();
        double[] x = stepInterpolator.getInterpolatedState();
        xValues.add(x[0]);
        yValues.add(x[1]);
        tValues.add(t);
    }

    public double[] ArrayList2Array(ArrayList<Double> Values) {
        double[] arr = new double[Values.size()];
        for (int i = 0; i < Values.size(); i++)
            arr[i] = Values.get(i);

        return arr;
    }
}
