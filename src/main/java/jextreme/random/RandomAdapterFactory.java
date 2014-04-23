package jextreme.random;

import jextreme.random.ApacheCommonsRandomAdapter;
import jextreme.random.RandomAdapter;

/**
 *
 * @author Vaclav
 */
public class RandomAdapterFactory {

	private static final RandomAdapter adapter = new ApacheCommonsRandomAdapter();

    /**
     *
     * @return
     */
    public static RandomAdapter getInstance() {
		return adapter;
	}

}
