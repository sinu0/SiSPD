package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistNormal;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Runner extends SimProcess {

	private desmoj.core.dist.ContDistNormal shots;
	
	private int number;

	private Process model;

	public Runner(Model owner, String name, boolean showInTrace, int number) {
		super(owner, name, showInTrace);
		model = (Process) owner;
		this.number = number; 
		shots = new ContDistNormal(model, "shots", 5, 5, true, true);
		shots.setNonNegative(true);
	}
	
	public int getNumber() {
		return this.number;
	}

	@Override
	public void lifeCycle() {
		model.QueueRunner(this);
		hold(new TimeSpan(model.getRunnerArrivalTime().sample(), TimeUnit.SECONDS));
		model.StageRunner(this);
		System.out.println("Before passivate: " + this.number);
		passivate();
		System.out.println("After passivate: " + this.number);
		model.StageRunner(this);
	}
}
