/**
 * Aug 25, 2013
 */
package jextreme.evolution.solution;

import jextreme.evolution.genetics.Genes;

import java.util.function.Function;

/**
 * @author vaclavnemec
 * 
 */
public interface FitnessFunction extends Function<Genes, Double> {

}