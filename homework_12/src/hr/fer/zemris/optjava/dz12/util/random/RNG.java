package hr.fer.zemris.optjava.dz12.util.random;

import java.io.IOException;
import java.util.Properties;

import hr.fer.zemris.optjava.dz12.interfaces.IRNG;
import hr.fer.zemris.optjava.dz12.interfaces.IRNGProvider;

public class RNG {

	private static IRNGProvider rngProvider;

	static {
		Properties props=new Properties();
		
			try {
				props.load(RNG.class.getClassLoader().getResourceAsStream("rng-config.properties"));
				rngProvider=(IRNGProvider)RNG.class.getClassLoader().loadClass(props.getProperty("rng-provider")).newInstance();
			} catch (IOException|InstantiationException | IllegalAccessException | ClassNotFoundException ignorable) {
				System.out.println("rng-config.properties error.");
			}

		
		
	}

	public static IRNG getRNG() {
		return rngProvider.getRNG();
	}

}
