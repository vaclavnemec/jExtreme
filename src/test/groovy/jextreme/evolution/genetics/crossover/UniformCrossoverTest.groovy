package jextreme.evolution.genetics.crossover

import jextreme.evolution.genetics.Genes
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class UniformCrossoverTest extends Specification {
    def "uniform crossover or #inputs1 and #inputs2 is #results"() {
        given:
        def c = new UniformCrossover(ratio)
        def g1 = new Genes(inputs1 as double[])
        def g2 = new Genes(inputs2 as double[])
        when:
        Genes[] r = c.apply(g1, g2)
        then:
        r.length == 2
        r[0].getGenes() == result1 as double[]
        r[1].getGenes() == result2 as double[]
        where:
        ratio | inputs1         | inputs2              || result1              | result2
        1.0   | [1, 6]          | [5, 4]               || [1, 6]               | [5, 4]
        1.0   | [123]           | [-123]               || [123]                | [-123]
        1.0   | [120]           | [-68]                || [120]                | [-68]
        0.0   | [120]           | [-68]                || [-68]                | [120]
        1.0   | [1, 2, 3, 4, 5] | [-6, -5, -4, -3, -2] || [1, 2, 3, 4, 5]      | [-6, -5, -4, -3, -2]
        0.0   | [1, 2, 3, 4, 5] | [-6, -5, -4, -3, -2] || [-6, -5, -4, -3, -2] | [1, 2, 3, 4, 5]
    }
}
