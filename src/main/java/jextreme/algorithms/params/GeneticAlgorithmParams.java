package jextreme.algorithms.params;

import jextreme.algorithms.EvolutionListener;
import jextreme.evolution.genetics.crossover.GeneticCrossover;

import java.util.List;

public class GeneticAlgorithmParams extends OptimizationAlgorithmParams {

    /**
     * @return the list of crossovers to be used in the optimization. Each time selected randomly, if more than 1
     */
    private List<GeneticCrossover> crossovers;

    /**
     * @return simple listener to track progress of the optimization process
     */
    private EvolutionListener evolutionListener;

    /**
     * @return the size of the population for the genetic algorithm
     */
    private int populationSize;

    /**
     * @return the number of generations to be simulated
     */
    private long numberOfGenerations;

    /**
     * @return the probability for a mutation of the solution
     */
    private double mutationProbability;

    /**
     * @return the ratio of elite solutions which is copied into new generation
     */
    private double elitismRatio;

    public List<GeneticCrossover> getCrossovers() {
        return crossovers;
    }

    public void setCrossovers(List<GeneticCrossover> crossovers) {
        this.crossovers = crossovers;
    }

    public EvolutionListener getEvolutionListener() {
        return evolutionListener;
    }

    public void setEvolutionListener(EvolutionListener evolutionListener) {
        this.evolutionListener = evolutionListener;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public long getNumberOfGenerations() {
        return numberOfGenerations;
    }

    public void setNumberOfGenerations(long numberOfGenerations) {
        this.numberOfGenerations = numberOfGenerations;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public double getElitismRatio() {
        return elitismRatio;
    }

    public void setElitismRatio(double elitismRatio) {
        this.elitismRatio = elitismRatio;
    }
}
