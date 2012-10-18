package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;

public class Process extends Model {

	protected static int NUM_SR = 4;

	protected static int NUM_CONTENDER = 30;

	private desmoj.core.dist.ContDistUniform runnerArrivalTime;

	private desmoj.core.dist.ContDistNormal serviceTimeProne;

	private desmoj.core.dist.ContDistNormal serviceTimeStanding;

	private desmoj.core.dist.ContDistNormal penaltyTime;
	
	protected desmoj.core.simulator.ProcessQueue<Runner> penaltyQueue;

	protected desmoj.core.simulator.ProcessQueue<Runner> RunnerQueue;

	protected desmoj.core.simulator.ProcessQueue<ShootingRange> idleSRQueue;

	protected desmoj.core.simulator.ProcessQueue<Runner> penaltyCircuits;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Process model = new Process(null, "Biathlon simulation", true, true);
		// null as first parameter because it is the main model and has no
		// mastermodel

		Experiment exp = new Experiment("Biatholon_simulation!",
				TimeUnit.MILLISECONDS, TimeUnit.SECONDS, null);
		model.connectToExperiment(exp);
		exp.setShowProgressBar(true);
		exp.stop(new TimeInstant(40,TimeUnit.MINUTES));
		//exp.tracePeriod(, arg1)

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
		for (int i = 1; i < NUM_SR; i++) {
			ShootingRange range = new ShootingRange(this, "Shooting range ",
					true);
			range.activate(new TimeSpan(0));
		}
		for (int i = 1; i < NUM_CONTENDER; i++) {
			RunnerGenerator runner = new RunnerGenerator(this, "Runner", true);
			runner.activate(new TimeSpan(0));
		}

	}

	@Override
	public void init() {

		serviceTimeProne = new ContDistNormal(this, "ServiceTimeStream", 28.0,
				32.0, true, false);
		serviceTimeStanding = new ContDistNormal(this, "ServiceTimeStream",
				22.0, 26.0, true, false);
		runnerArrivalTime = new ContDistUniform(this, "runnerArrivalTime",
				6.0 * 60, 7.0 * 60, true, false);
		penaltyTime = new ContDistNormal(this, "penaltyTime", 21.0, 23.0, true,
				false);
		RunnerQueue = new ProcessQueue<Runner>(this, "Runner Queue", true, true);
		idleSRQueue = new ProcessQueue<ShootingRange>(this, "idle SR Queue",
				true, true);

	}

	public desmoj.core.dist.ContDistUniform getRunnerArrivalTime() {
		return runnerArrivalTime;
	}

	public desmoj.core.dist.ContDistNormal getServiceTimeProne() {
		return serviceTimeProne;
	}

	public desmoj.core.dist.ContDistNormal getServiceTimeStanding() {
		return serviceTimeStanding;
	}

	public desmoj.core.dist.ContDistNormal getPenaltyTime() {
		return penaltyTime;
	}

}
