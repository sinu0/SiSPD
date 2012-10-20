package BiathlonSymulation;

import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

public class SR extends SimProcess {

	public SR(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (Run) owner;
		SRQueue =  new desmoj.core.simulator.ProcessQueue<Runner>(model, "SR_1 Queue", false, false);
		shotDist = new ContDistUniform(model, "Shot dist.", 0.0, 100.0, false, false);
		SRDist = new ContDistUniform(model, "SR service dist.", 40.0, 60.0, false, false);
	}
	
	public void addRunner(Runner runner) {
		SRQueue.insert(runner);
		this.activate();
	}
	

	@Override
	public void lifeCycle() {
		while(true) {
			if(SRQueue.isEmpty()) {
				passivate();
			}
			else {
				Runner r = SRQueue.first();
				int penalty = 5;
				for(int i =0;i<5;++i) {
					if(shotDist.sample() < r.getAccuracy()) penalty--;
				}
				r.hold_time = (penalty * ((penaltyDistance)/(r.getSpeed()))) + SRDist.sample();
				SRQueue.remove(r);
				r.activate();
			}
		}
	}
	
	// ------------------------------------------------------------------------
	private Run model;
	private final double penaltyDistance = 150.0;
	protected desmoj.core.simulator.ProcessQueue<Runner> SRQueue;
	private desmoj.core.dist.ContDistUniform shotDist;
	private desmoj.core.dist.ContDistUniform SRDist;
}
