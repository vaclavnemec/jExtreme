package jextreme.algorithms.params;

import jextreme.evolution.solution.FitnessFunction;
import jextreme.evolution.solution.Specimen;

public class OptimizationAlgorithmParams {

    /**
     * this is the function we optimise. We look for the best solution for this function
     */
    private FitnessFunction fitnessFunction;

    /**
     * specimen limits the space of the fitness function. It provides limits for all dimensions
     */
    private Specimen specimen;

    public Specimen getSpecimen() {
        return specimen;
    }

    public void setSpecimen(Specimen specimen) {
        this.specimen = specimen;
    }

    public FitnessFunction getFitnessFunction() {
        return fitnessFunction;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
