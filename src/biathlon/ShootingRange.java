package biathlon;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class ShootingRange extends SimProcess {
		
	public ShootingRange(Model owner, String arg1, boolean arg2) {
		super(owner, arg1, arg2);
		
		model = (Process) owner;
	}

	@Override
	public void lifeCycle() {
		while (true) {		
			if (model.SR_1_Queue.isEmpty()) {
				passivate();
			}
			else {
				Runner runner = model.SR_1_Queue.first();
				model.SR_1_Queue.remove(runner);
				runner.activate();
			}
		}
	}
	
	// ------------------------------------------------------------------------
	private Process model;
}
