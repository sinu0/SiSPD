package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;

public class Process extends Model {

	private desmoj.core.dist.ContDistUniform runnerAverageSpeed;
	private desmoj.core.dist.ContDistNormal serviceTimeProne;
	private desmoj.core.dist.ContDistNormal serviceTimeStanding;

	public static void main(String[] args) {
		Process model = new Process(null, "Biathlon simulation", true, true);

		Experiment exp = new Experiment("Biatholon_simulation", "output");
		
		model.connectToExperiment(exp);		
		
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
		
		RunnerGenerator runnerGenerator = new RunnerGenerator(this, "Runner generator", false, RUNNERS_NUM);
		runnerGenerator.activate();
	}

	@Override
	public void init() {

		serviceTimeProne = new ContDistNormal(this, "ServiceTimeStand", 30.0, 4.0, true, false);
		serviceTimeStanding = new ContDistNormal(this, "ServiceTimeProne", 25.0 ,3.0, true, false);
		runnerAverageSpeed = new ContDistUniform(this, "runnerAverageSpeed", 25.0, 1.0, true, false);
		
		Stage_1_Queue = new ProcessQueue<Runner>(this, "Stage 1 queue", true, true);
		Stage_2_Queue = new ProcessQueue<Runner>(this, "Stage 1 queue", true, true);
		Stage_3_Queue = new ProcessQueue<Runner>(this, "Stage 1 queue", true, true);
		Stage_4_Queue = new ProcessQueue<Runner>(this, "Stage 1 queue", true, true);
		
		SR_1_Queue = new ProcessQueue<Runner>(this, "SR 1 queue", true, true);
		SR_2_Queue = new ProcessQueue<Runner>(this, "SR 1 queue", true, true);
		SR_3_Queue = new ProcessQueue<Runner>(this, "SR 1 queue", true, true);
		SR_4_Queue = new ProcessQueue<Runner>(this, "SR 1 queue", true, true);
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

//	public void checkEnd() {
//		if(ACTIVE_RUNNERS_NUM == 0) {
//			System.out.println("Koniec");
//			this.getExperiment().stop();
//		}
//		else {
//			System.out.println("Pozosta³o:" + ACTIVE_RUNNERS_NUM);
//		}
//	}
	
	public void QueueRunner(Runner runner) {
		Stage_1_Queue.insert(runner);
		System.out.println("Queue runner:" +runner.getNumber());
		ACTIVE_RUNNERS_NUM++;
	}
	
	public void StageRunner(Runner runner) {
		if(Stage_1_Queue.contains(runner)) {
			Stage_1_Queue.remove(runner);
			SR_1_Queue.insert(runner);
			
			
			return;
		}
		else if(Stage_2_Queue.contains(runner)) {
			Stage_2_Queue.remove(runner);
			ACTIVE_RUNNERS_NUM--;
		}
		
		if(ACTIVE_RUNNERS_NUM == 0) {
			System.out.println("Koniec");
			this.getExperiment().stop();
		}
	}
	
	// ------------------------------------------------------------------------

	protected final static int RUNNERS_NUM = 30;	
	protected static int ACTIVE_RUNNERS_NUM;
	
	protected final static int STAGE_1_DIST = 3000;
	protected final static int STAGE_2_DIST = 4000;
	protected final static int STAGE_3_DIST = 4000;
	protected final static int STAGE_4_DIST = 4000;
	
	protected desmoj.core.simulator.ProcessQueue<Runner> Stage_1_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> Stage_2_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> Stage_3_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> Stage_4_Queue;
	
	protected desmoj.core.simulator.ProcessQueue<Runner> SR_1_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> SR_2_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> SR_3_Queue;
	protected desmoj.core.simulator.ProcessQueue<Runner> SR_4_Queue;
}
