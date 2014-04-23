package jextreme.random;

import java.math.BigDecimal;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomAdaptor;
import org.apache.commons.math3.random.RandomGenerator;

/**
 *
 * @author Vaclav
 */
public class ApacheCommonsRandomAdapter implements RandomAdapter {

	private final RandomGenerator generator = new MersenneTwister();
	private final RandomAdaptor adaptor = new RandomAdaptor(this.generator);

    /**
     *
     * @return
     */
    @Override
	public double nextDouble() {
		return this.adaptor.nextDouble();
	}

    /**
     *
     * @param sigma
     * @return
     */
    @Override
	public double nextGaussian(final BigDecimal sigma) {
		final NormalDistribution dist = new NormalDistribution(this.generator, 0, sigma.doubleValue(), 1e-9);
		return dist.sample();
	}

    /**
     *
     * @param n
     * @return
     */
    @Override
	public int nextInt(final int n) {
		return this.adaptor.nextInt(n);
	}

    /**
     *
     * @param minValue
     * @param maxValue
     * @return
     */
    @Override
	public double nextDouble(final double minValue, final double maxValue) {
		if (this.adaptor.nextDouble() < 0.5) {
			return minValue + (maxValue - minValue) * (1 - this.adaptor.nextDouble());
		} else {
			return minValue + (maxValue - minValue) * (this.adaptor.nextDouble());
		}
	}

    /**
     *
     * @return
     */
    @Override
	public double nextGaussian() {
		return this.adaptor.nextGaussian();
	}

}
