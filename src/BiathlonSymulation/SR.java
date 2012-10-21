package BiathlonSymulation;

import desmoj.core.dist.ContDistUniform;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;

/**
 * This class represents a Shooting range in the Run model.
 * The SR waits until a runner requests his service.
 * If there is another runner waiting it starts serving this
 * runner. Otherwise it waits again for the next runner to arrive.
 */
public class SR extends SimProcess {

	/**
	 * Constructor of the Shooting Range process
	 *
	 * Used to create a new SR to serve runners.
	 *
	 * @param owner the model this process belongs to
	 * @param name this SR's name
	 * @param showInTrace flag to indicate if this process shall produce output for the trace
	 */
	public SR(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		
		model = (Run) owner;
		SRQueue =  new desmoj.core.simulator.ProcessQueue<Runner>(model, "SR_1 Queue", false, false);
		shotDist = new ContDistUniform(model, "Shot dist.", 0.0, 100.0, false, false);
		SRDist = new ContDistUniform(model, "SR service dist.", 40.0, 60.0, false, false);
		
		shotDist.reset( System.currentTimeMillis());
		SRDist.reset( System.currentTimeMillis());
	}
	
	/**
	 * Adds runner to this shooting range's queue.
	 * After that, activates this shooting range life cycle.
	 * @param runner, runner to serve.
	 */	
	public void addRunner(Runner runner) {
		SRQueue.insert(runner);
		this.activate();
	}
	
	/**
	 * Describes this Shooting Range's life cycle.
	 */
	@Override
	public void lifeCycle() {
		while(true) {
			if(SRQueue.isEmpty()) {
				passivate();
			}
			else {
				Runner r = SRQueue.first();
				r.sendMessage("Entered Shooting range ["+ this.getName() +"]");
				int penalty = 5;
				for(int i =0;i<5;++i) {
					double s = shotDist.sample() ;
					if(s <= r.getAccuracy()) penalty--;
				}
				r.sendMessage("Shooting range ["+ this.getName() +"] good shoots:" + (5-penalty) + "/5");
				if(penalty != 0) r.sendMessage("Shooting range ["+ this.getName() +"] penalty:" + penalty + " circuts");
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
