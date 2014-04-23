package jextreme.listener;

import jextreme.algorithms.EvolutionListener;
import jextreme.evolution.solution.SolutionHolder;
import java.util.List;

/**
 * @author vaclavnemec
 */
public class ConsoleEvolutionListener implements EvolutionListener {

    /**
     *
     * @param currentSolutions
     */
    @Override
    public void reportGeneration(List<SolutionHolder> currentSolutions) {
        // empty
    }

    /**
     *
     * @param evolutionStep
     */
    @Override
    public void reportNewStep(int evolutionStep) {
        System.out.println(String.join("Step: ", String.valueOf(evolutionStep)));
    }

    /**
     *
     * @param selectedParents
     */
    @Override
    public void reportParents(List<SolutionHolder> selectedParents) {
        System.out.println(String.join("Parents: ", selectedParents.toString()));
    }

    /**
     *
     * @param best
     */
    @Override
    public void reportBestSolution(SolutionHolder best) {
        System.out.println(String.join("Best: ", best.toString()));
    }

}
