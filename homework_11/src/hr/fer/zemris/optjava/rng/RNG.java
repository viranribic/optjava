package hr.fer.zemris.optjava.rng;

import java.io.IOException;
import java.util.Properties;

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
