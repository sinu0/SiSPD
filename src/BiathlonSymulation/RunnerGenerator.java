package BiathlonSymulation;

import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

/**
 * This class represents a process source, which generates an amount of runners.
 */
public class RunnerGenerator extends SimProcess{

	/**
	 * RunnerGenerator constructor.
	 * @param owner the model this truck generator belongs to
	 * @param name this runners generator's name
	 * @param showInTrace flag to indicate if this process shall produce output for the trace
	 * @param quantity describes quantity of runners to generate.
	 */
	public RunnerGenerator(Model arg0, String arg1, boolean arg2, int quantity) {
		super(arg0, arg1, arg2);
		this.quantity = quantity;
		
		this.speedDist = new ContDistUniform(arg0, "Runner speed dist.", 7.5, 8.0, false, false);
		this.accuracyDist = new ContDistUniform(arg0, "Runner accuracy dist.", 75.0, 90.0, false, false);
		
		speedDist.reset(System.currentTimeMillis());
		accuracyDist.reset(System.currentTimeMillis());
	}

	/**
	 * describes this process's life cycle: generate an amount of trucks.
	 */
	@Override
	public void lifeCycle() {
		Run model = (Run)getModel();
		
		for (int i=1; i<=quantity; ++i) {
			Runner runner = new Runner(model, "Runner", true, i, speedDist.sample(), accuracyDist.sample());
			runner.activate();
		}
	}
	
	// ------------------------------------------------------------------------
	private desmoj.core.dist.ContDistUniform speedDist;
	private desmoj.core.dist.ContDistUniform accuracyDist;
	
	private int quantity;
}
