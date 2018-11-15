package hr.fer.zemris.optjava.dz12.util.random;

import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.interfaces.IRNGProvider;

public class ThreadBoundRNGProvider implements IRNGProvider{

	@Override
	public IRNG getRNG() {
		if(Thread.currentThread() instanceof IRNGProvider)
			return ((IRNGProvider)Thread.currentThread()).getRNG();
		else
			return new RNGRandomImpl();
	}

}
