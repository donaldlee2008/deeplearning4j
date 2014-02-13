package com.ccc.deeplearning.sda;

import org.jblas.DoubleMatrix;

import com.ccc.deeplearning.nn.BaseNeuralNetwork;
import com.ccc.deeplearning.nn.NeuralNetworkGradient;
import com.ccc.deeplearning.optimize.NeuralNetworkOptimizer;

public class DenoisingAutoEncoderOptimizer extends NeuralNetworkOptimizer {

	
	private static final long serialVersionUID = 1815627091142129009L;

	public DenoisingAutoEncoderOptimizer(BaseNeuralNetwork network, double lr,
			Object[] trainingParams) {
		super(network, lr, trainingParams);
	}

	@Override
	public synchronized void getValueGradient(double[] buffer) {
		double corruptionLevel = (double) extraParams[0];
		NeuralNetworkGradient gradient = network.getGradient(new Object[]{corruptionLevel,lr});
		DoubleMatrix L_W = gradient.getwGradient();
		DoubleMatrix L_vbias_mean = gradient.getvBiasGradient();
		DoubleMatrix L_hbias_mean = gradient.gethBiasGradient();
		
		/*
		 * Treat params as linear index. Always:
		 * W
		 * Visible Bias
		 * Hidden Bias
		 */
		int idx = 0;
		for (int i = 0; i < L_W.length; i++) {
			buffer[idx++] =L_W.get(i);
		}
		for (int i = 0; i < L_vbias_mean.length; i++) {
			buffer[idx++] = L_vbias_mean.get(i);
		}
		for (int i = 0; i < L_hbias_mean.length; i++) {
			buffer[idx++] = L_hbias_mean.get(i);
		}


	}

	


}
