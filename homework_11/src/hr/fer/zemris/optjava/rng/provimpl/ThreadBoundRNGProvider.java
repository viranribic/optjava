package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.IRNGProvider;
import hr.fer.zemris.optjava.rng.rngimpl.RNGRandomImpl;

public class ThreadBoundRNGProvider implements IRNGProvider{

	@Override
	public IRNG getRNG() {
		if(Thread.currentThread() instanceof IRNGProvider)
			return ((IRNGProvider)Thread.currentThread()).getRNG();
		else
			return new RNGRandomImpl();
	}

}
