package jextreme.algorithms.params;

public class SomaParams extends OptimizationAlgorithmParams {

    private double pathLength;
    private double step;
    private double perturbationLevel;
    private int populationSize;
    private long amountOfMigrations;

    public double getPathLength() {
        return pathLength;
    }

    public void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public double getPerturbationLevel() {
        return perturbationLevel;
    }

    public void setPerturbationLevel(double perturbationLevel) {
        this.perturbationLevel = perturbationLevel;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public long getAmountOfMigrations() {
        return amountOfMigrations;
    }

    public void setAmountOfMigrations(long amountOfMigrations) {
        this.amountOfMigrations = amountOfMigrations;
    }
}
