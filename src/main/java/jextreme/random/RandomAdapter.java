package jextreme.random;

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
     * @param minValue
     * @param maxValue
     * @return
     */
    double nextDouble(double minValue, double maxValue);

}
