package biathlon;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class RunnerGenerator extends SimProcess{
	
	private int quantity;

	public RunnerGenerator(Model arg0, String arg1, boolean arg2, int quantity) {
		super(arg0, arg1, arg2);
		this.quantity = quantity;
	}

	@Override
	public void lifeCycle() {
		Process model = (Process)getModel();
		
		for (int i=1; i<quantity; ++i) {
			Runner runner = new Runner(model, "Runner", true, i);
			runner.activate(new TimeSpan(0));
		}
	}
}
