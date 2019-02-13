package jextreme.random;

/**
 * @author Vaclav
 */
public interface RandomAdapter {

    /**
     * @return 0 to 1
     */
    double nextDouble();

    /**
     * @param n exclusive limit
     * @return random int from 0 to n
     */
    int nextInt(int n);

    /**
     * @param minValue the lower limit
     * @param maxValue thu upper limit
     * @return random number from the interval
     */
    double nextDouble(double minValue, double maxValue);

}
