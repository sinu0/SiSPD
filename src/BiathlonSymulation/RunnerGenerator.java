package BiathlonSymulation;

import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class RunnerGenerator extends SimProcess{
	
	private int quantity;

	public RunnerGenerator(Model arg0, String arg1, boolean arg2, int quantity) {
		super(arg0, arg1, arg2);
		this.quantity = quantity;
		
		this.speedDist = new ContDistUniform(arg0, "Runner speed dist.", 7.5, 8.0, false, false);
		this.accuracyDist = new ContDistUniform(arg0, "Runner accuracy dist.", 75.0, 90.0, false, false);
	}

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
}
