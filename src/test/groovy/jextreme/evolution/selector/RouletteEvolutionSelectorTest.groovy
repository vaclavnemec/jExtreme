package jextreme.evolution.selector

import jextreme.evolution.solution.SolutionHolder
import jextreme.random.ApacheCommonsRandomAdapter
import jextreme.random.RandomAdapter
import org.junit.Assert
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RouletteEvolutionSelectorTest extends Specification {

    def "the sum of probability to be a parent over all solutions 1"() {
        given:
        final RandomAdapter random = new ApacheCommonsRandomAdapter()

        final List<SolutionHolder> solutions = (0..20).collect {
            final SolutionHolder holder = new SolutionHolder()
            holder.setId(it)
            holder.setFitness(random.nextDouble() * 300 - 150)
            return holder
        }.toList()

        when:
        new RouletteEvolutionSelector().normalize(0, 1, solutions)
        def sum = solutions.sum {it.getProbabilityToBeParent()}

        then:
        Assert.assertEquals(1.0, sum, 0.00000000001)
    }

    /**
     * Central limit theorem test for lot of selections
     */
    def "test repeated #times times"() {

        given:
        def s1 = new SolutionHolder()
        s1.setId(0)
        s1.setFitness(2 / 3.0)

        def s2 = new SolutionHolder()
        s2.setId(1)
        s2.setFitness(1 / 3.0)

        def s3 = new SolutionHolder()
        s3.setId(2)
        s3.setFitness(0 / 3.0)

        def solutions = [s1, s2, s3]
        def selector = new RouletteEvolutionSelector()

        when:

        final int[] frequencies = new int[3]
        for (int i = 0; i < 99999; i++) {
            selector.initSolutions(solutions)
            def parents = selector.selectParents(solutions, 1)
            final int index = parents.get(0).getId()
            frequencies[index] = frequencies[index] + 1
        }
        final double delta = 500.0

        then:
        Assert.assertEquals(frequencies[0], 66666, delta)
        Assert.assertEquals(frequencies[1], 33333, delta)
        Assert.assertEquals(frequencies[2], 0, delta)

        where:
        times << (1..100)
    }
}
