package jextreme.algorithms.params;

public final class BlindAlgorithmParams extends OptimizationAlgorithmParams {

    private  long numberOfEvaluations;

    public long getNumberOfEvaluations() {
        return numberOfEvaluations;
    }

    public void setNumberOfEvaluations(long numberOfEvaluations) {
        this.numberOfEvaluations = numberOfEvaluations;
    }
}
