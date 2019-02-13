package jextreme.evolution.solution;

import jextreme.evolution.genetics.Genes;

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

    private Genes genes;

    public Double getScaledFitness() {
        return scaledFitness;
    }

    public void setScaledFitness(Double scaledFitness) {
        this.scaledFitness = scaledFitness;
    }

    public Double getProbabilityToBeParent() {
        return probabilityToBeParent;
    }

    public void setProbabilityToBeParent(Double probabilityToBeParent) {
        this.probabilityToBeParent = probabilityToBeParent;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(Double fitness) {
        this.fitness = fitness;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Genes getGenes() {
        return genes;
    }

    public void setGenes(Genes genes) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        return "SolutionHolder{" + "scaledFitness=" + scaledFitness + ", probabilityToBeParent=" + probabilityToBeParent + ", solution=" + solution + ", fitness=" + fitness + ", id=" + id + ", genotype=" + genes + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.scaledFitness);
        hash = 89 * hash + Objects.hashCode(this.probabilityToBeParent);
        hash = 89 * hash + Objects.hashCode(this.solution);
        hash = 89 * hash + Objects.hashCode(this.fitness);
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.genes);
        return hash;
    }

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
        return Objects.equals(this.genes, other.genes);
    }
}
