package jextreme.listener;

import jextreme.algorithms.EvolutionListener;
import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

/**
 * @author vaclavnemec
 */
public class ConsoleEvolutionListener implements EvolutionListener {

    @Override
    public void reportGeneration(List<SolutionHolder> currentSolutions) {
        System.out.println(String.join("Generation: ", String.valueOf(currentSolutions)));
    }

    @Override
    public void reportNewStep(int evolutionStep) {
        System.out.println(String.join("Step: ", String.valueOf(evolutionStep)));
    }

    @Override
    public void reportBestSolution(SolutionHolder best) {
        System.out.println(String.join("Best: ", best.toString()));
    }

}
