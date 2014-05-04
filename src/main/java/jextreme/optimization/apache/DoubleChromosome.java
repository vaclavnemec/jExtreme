package jextreme.optimization.apache;

import java.util.List;

import org.apache.commons.math3.exception.util.LocalizedFormats;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

import jextreme.evolution.solution.Solution;
import jextreme.evolution.solution.SolutionFactory;
import jextreme.evolution.genetics.Genotype;

/**
 *
 * @author Vaclav
 */
public class DoubleChromosome extends AbstractListChromosome<Double> {

    private final SolutionFactory solutionFactory;

    /**
     *
     * @param chromosomeRepresentation
     * @param solutionFactory
     */
    public DoubleChromosome(final List<Double> chromosomeRepresentation, final SolutionFactory solutionFactory) {
        super(chromosomeRepresentation);
        this.solutionFactory = solutionFactory;
    }

    @Override
    public double fitness() {
        final Solution createSolution = this.solutionFactory.createSolution(new Genotype(this.getRepresentation()));
        return createSolution.getFitness();
    }

    @Override
    protected void checkValidity(final List<Double> chromosomeRepresentation) throws InvalidRepresentationException {
        if (chromosomeRepresentation.size() < 1) {
            throw new InvalidRepresentationException(LocalizedFormats.INVALID_FIXED_LENGTH_CHROMOSOME, chromosomeRepresentation.size());
        }
    }

    @Override
    public List<Double> getRepresentation() {
        return super.getRepresentation();
    }

    @Override
    public AbstractListChromosome<Double> newFixedLengthChromosome(final List<Double> chromosomeRepresentation) {
        return new DoubleChromosome(chromosomeRepresentation, this.solutionFactory);
    }

}
