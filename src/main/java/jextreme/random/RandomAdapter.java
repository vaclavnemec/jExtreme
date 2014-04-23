/**
 * Aug 22, 2013
 */
package jextreme.random;

import java.math.BigDecimal;

/**
 * @author Vaclav
 */
public interface RandomAdapter {

    /**
     *
     * @return
     */
    double nextDouble();

    /**
     *
     * @param n
     * @return
     */
    int nextInt(int n);

    /**
     *
     * @return
     */
    double nextGaussian();

    /**
     *
     * @param divide
     * @return
     */
    double nextGaussian(BigDecimal divide);

    /**
     *
     * @param minValue
     * @param maxValue
     * @return
     */
    double nextDouble(double minValue, double maxValue);

}
