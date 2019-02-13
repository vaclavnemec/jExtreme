package jextreme.evolution.genetics.crossover

import jextreme.evolution.genetics.Genes
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ArithmeticCrossoverTest extends Specification {
    def "arritmetic crossover or #inputs1 and #inputs2 is #results"() {
        given:
        def c = new ArithmeticCrossover()
        def g1 = new Genes(inputs1 as double[])
        def g2 = new Genes(inputs2 as double[])
        when:
        Genes[] r = c.apply(g1, g2)
        then:
        r.length == 1
        r.first().getGenes() == results as double[]
        where:
        inputs1            | inputs2                  || results
        [1]                | [5]                      || [3]
        [1, 6]             | [5, 4]                   || [3, 5]
        [123]              | [-123]                   || [0]
        [120]              | [-68]                    || [26]
        [1, 2, 3, 4, 5, 6] | [-6, -5, -4, -3, -2, -1] || [-2.5, -1.5, -0.5, 0.5, 1.5, 2.5]
    }
}
