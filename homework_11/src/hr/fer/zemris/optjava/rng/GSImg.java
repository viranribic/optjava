package hr.fer.zemris.optjava.rng;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.interfaces.IGrayScaleProvider;
import hr.fer.zemris.optjava.rng.provimpl.ThreadBoundGSImgProvider;

public class GSImg {

	private static IGrayScaleProvider gsimgProvider;
	
	static {
		gsimgProvider=new ThreadBoundGSImgProvider();
	}
	public static GrayScaleImage getGSImg(int width, int height){
		return gsimgProvider.getGSImg(width, height);
	}
}
