package jextreme.evolution.selector;

import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

/**
 * @author Vaclav
 * 
 */
public interface EvolutionSelector {

    /**
     * @param solutions the solutions to select parents from
     * @param amountOfParents the amount of parents we need
     * @return new parents
     */
    List<SolutionHolder> selectParents(List<SolutionHolder> solutions, int amountOfParents);

    /**
     * @param solutions initialize the solutions for the selection
     */
    void initSolutions(List<SolutionHolder> solutions);

}
