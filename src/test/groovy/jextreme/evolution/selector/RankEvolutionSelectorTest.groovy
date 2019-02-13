package jextreme.evolution.selector

import jextreme.evolution.solution.SolutionHolder
import org.junit.Assert
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class RankEvolutionSelectorTest extends Specification {

    def "get arithmetic sum for #par1 and #par2 is #result"() {
        when:
        def sum = new RankEvolutionSelector().getArithmetricSum(par1, par2)
        then:
        sum == result
        where:
        par1 | par2 || result
        1    | 2    || 3
        0    | 2    || 3
        -1   | 1    || 0
        0    | 0    || 0
        5    | 5    || 5
        -5   | 5    || 0
        3    | 5    || 12
        -5   | 2    || -12
    }

    def "the illegal argument exception is thrown"() {
        when:
        new RankEvolutionSelector().getArithmetricSum(5, 2)
        then:
        def e = thrown(IllegalArgumentException)
        e.message == "The parameter 'from' is bigger than the paramater 'to'."
    }


    /**
     * Central limit theorem test for lot of selections
     */
    def "test repeated #times times"() {
        given:
        SolutionHolder s1 = new SolutionHolder()
        s1.setFitness(2000.0)
        s1.setId(0)

        SolutionHolder s2 = new SolutionHolder()
        s2.setFitness(5.0)
        s2.setId(1)

        SolutionHolder s3 = new SolutionHolder()
        s3.setFitness(-30.0)
        s3.setId(2)

        def solutions = [s1,s2,s3]

        when:

        final int[] frequencies = new int[3];
        for (int i = 1; i < 100000; i++) {
            def selector = new RankEvolutionSelector()
            selector.initSolutions(solutions)
            final int index = selector.selectParents(solutions, 1).get(0).getId()
            frequencies[index] = frequencies[index] + 1
        }
        final double delta = 500.0
        then:
        Assert.assertEquals(frequencies[0], 100000 / 2.0, delta)
        Assert.assertEquals(frequencies[1], 100000 / 3.0, delta)
        Assert.assertEquals(frequencies[2], 100000 / 6.0, delta)
        where:
        times << (1..100)
    }
}
