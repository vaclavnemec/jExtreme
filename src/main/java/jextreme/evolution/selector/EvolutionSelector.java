/**
 * Aug 9, 2013
 */
package jextreme.evolution.selector;

import jextreme.evolution.solution.SolutionHolder;

import java.util.List;

/**
 * @author Vaclav
 * 
 */
public interface EvolutionSelector {

    /**
     *
     * @param solutions
     * @param amountOfParents
     * @return
     */
    List<SolutionHolder> selectParents(List<SolutionHolder> solutions, int amountOfParents);

    /**
     *
     * @param solutions
     */
    void initSolutions(List<SolutionHolder> solutions);

}
