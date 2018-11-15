package hr.fer.zemris.optjava.rng.provimpl;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.optjava.dz11.interfaces.IGrayScaleProvider;

public class ThreadBoundGSImgProvider implements IGrayScaleProvider{

	
	@Override
	public GrayScaleImage getGSImg(int width, int height){
		if(Thread.currentThread() instanceof IGrayScaleProvider)
			return ((IGrayScaleProvider)Thread.currentThread()).getGSImg(width, height);
		else
			return new GrayScaleImage(width, height);
	}
}
