package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;

public class Process extends Model {

	protected final static int NUM_SR = 4;

	protected final static int NUM_CONTENDER = 30;

	private desmoj.core.dist.ContDistUniform runnerAverageSpeed;

	private desmoj.core.dist.ContDistNormal serviceTimeProne;

	private desmoj.core.dist.ContDistNormal serviceTimeStanding;
	
	protected desmoj.core.simulator.ProcessQueue<Runner> penaltyQueue;

	protected desmoj.core.simulator.ProcessQueue<Runner> RunnerQueue;

	protected desmoj.core.simulator.ProcessQueue<ShootingRange> idleSRQueue;

	protected desmoj.core.simulator.ProcessQueue<Runner> penaltyCircuits;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Process model = new Process(null, "Biathlon simulation", true, true);

		Experiment exp = new Experiment("Biatholon_simulation", "output");
		
		model.connectToExperiment(exp);
		exp.setShowProgressBar(true);
		exp.stop(new TimeInstant(40,TimeUnit.MINUTES));
		
		exp.tracePeriod(new TimeInstant(0), new TimeInstant(40,TimeUnit.MINUTES));
		
		exp.start();
		exp.report();
		exp.finish();
	}

	public Process(Model owner, String modelName, boolean showInReport,
			boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "Symulcaja biathlonu z masowego startu";
	}

	@Override
	public void doInitialSchedules() {
		ShotingRangeGenerator sRG=new ShotingRangeGenerator(this, "Shoting range generator", false, NUM_SR);
		RunnerGenerator runnerGenerator = new RunnerGenerator(this, "Runner generator", false, NUM_CONTENDER);
		sRG.activate(new TimeSpan(0));
		runnerGenerator.activate(new TimeSpan(0));
		
	}

	@Override
	public void init() {

		serviceTimeProne = new ContDistNormal(this, "ServiceTimeStand", 30.0,
				4.0, true, false);
		serviceTimeStanding = new ContDistNormal(this, "ServiceTimeProne",
				25.0 ,3.0, true, false);
		runnerAverageSpeed = new ContDistUniform(this, "runnerAverageSpeed",
				25.0, 1.0, true, false);
		
		RunnerQueue = new ProcessQueue<Runner>(this, "Runner Queue", true, true);
		idleSRQueue = new ProcessQueue<ShootingRange>(this, "idle SR Queue",
				true, true);

	}

	public desmoj.core.dist.ContDistUniform getRunnerArrivalTime() {
		return runnerAverageSpeed;
	}

	public desmoj.core.dist.ContDistNormal getServiceTimeProne() {
		return serviceTimeProne;
	}

	public desmoj.core.dist.ContDistNormal getServiceTimeStanding() {
		return serviceTimeStanding;
	}



}