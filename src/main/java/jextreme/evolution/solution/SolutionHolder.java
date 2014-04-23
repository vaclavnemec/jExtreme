/**
 * Aug 27, 2013
 */
package jextreme.evolution.solution;

import jextreme.evolution.genetics.Genotype;
import java.util.Objects;

/**
 * @author vaclavnemec
 */
public class SolutionHolder {

    private Double scaledFitness;

    private Double probabilityToBeParent;

    private Solution solution;

    private Double fitness;

    private Integer id;

    private Genotype genotype;

    /**
     *
     * @return
     */
    public Double getScaledFitness() {
        return scaledFitness;
    }

    /**
     *
     * @param scaledFitness
     */
    public void setScaledFitness(Double scaledFitness) {
        this.scaledFitness = scaledFitness;
    }

    /**
     *
     * @return
     */
    public Double getProbabilityToBeParent() {
        return probabilityToBeParent;
    }

    /**
     *
     * @param probabilityToBeParent
     */
    public void setProbabilityToBeParent(Double probabilityToBeParent) {
        this.probabilityToBeParent = probabilityToBeParent;
    }

    /**
     *
     * @return
     */
    public Solution getSolution() {
        return solution;
    }

    /**
     *
     * @param solution
     */
    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    /**
     *
     * @return
     */
    public Double getFitness() {
        return fitness;
    }

    /**
     *
     * @param fitness
     */
    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    /**
     *
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Genotype getGenotype() {
        return genotype;
    }

    /**
     *
     * @param genotype
     */
    public void setGenotype(Genotype genotype) {
        this.genotype = genotype;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "SolutionHolder{" + "scaledFitness=" + scaledFitness + ", probabilityToBeParent=" + probabilityToBeParent + ", solution=" + solution + ", fitness=" + fitness + ", id=" + id + ", genotype=" + genotype + '}';
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.scaledFitness);
        hash = 89 * hash + Objects.hashCode(this.probabilityToBeParent);
        hash = 89 * hash + Objects.hashCode(this.solution);
        hash = 89 * hash + Objects.hashCode(this.fitness);
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.genotype);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SolutionHolder other = (SolutionHolder) obj;
        if (!Objects.equals(this.scaledFitness, other.scaledFitness)) {
            return false;
        }
        if (!Objects.equals(this.probabilityToBeParent, other.probabilityToBeParent)) {
            return false;
        }
        if (!Objects.equals(this.solution, other.solution)) {
            return false;
        }
        if (!Objects.equals(this.fitness, other.fitness)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.genotype, other.genotype)) {
            return false;
        }
        return true;
    }

    

}
