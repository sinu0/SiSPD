package BiathlonSymulation;

import desmoj.core.report.TraceFileOut;
import desmoj.core.report.TraceNote;
import desmoj.core.simulator.*;

/**
 * This is the model class. It is the main class of a simple process-oriented
 * model of the biathlon mass start run.
 */
public class Run extends Model {
	
	/**
	 * Runs the model.
	 *
	 * @param args is an array of command-line arguments (will be ignored here)
	 */
	public static void main(String[] args) {
		Run model = new Run(null, "Biathlon simulation", true, true);

		Experiment exp = new Experiment("Biatholon_simulation", "output");
		
		model.connectToExperiment(exp);		
		
		exp.start();
		exp.report();
		exp.finish();
	}
	
	/**
	 * Queue runner to next stage.
	 *
	 * @param runner - current, which has to be queued.
	 */	
	public void QueueRunner(Runner runner) {
				
		switch(runner.getStage()) {
			case 0 : {
				ACTIVE_RUNNERS_NUM++;
				runner.incStage();
				double hold_time = (stage1.getDistance())/(runner.getSpeed());
				runner.hold_time = hold_time;
				break;
			}
			
			case 1 : {
				runner.incStage();
				SR_1.addRunner(runner);
				break;
			}
			
			case 2 : {
				String msg = "Passed first checkpoint!";		
				if(top_score_SR_1 != null) {
					msg += " +" + Utils.timeSpanFormatter(TimeOperations.diff(this.presentTime(), top_score_SR_1));
				}
				else {
					top_score_SR_1 = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score_SR_1);
				}	
				after_SR_1.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				runner.incStage();
				double hold_time = (stage2.getDistance())/(runner.getSpeed());
				runner.hold_time = hold_time;
				break;
			}
			
			case 3 : {
				runner.incStage();
				SR_2.addRunner(runner);
				break;
			}
			
			case 4 : {
				String msg = "Passed second checkpoint!";		
				if(top_score_SR_2 != null) {
					msg += " +" + Utils.timeSpanFormatter(TimeOperations.diff(this.presentTime(), top_score_SR_2));
				}
				else {
					top_score_SR_2 = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score_SR_2);
				}	
				after_SR_2.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				runner.incStage();
				double hold_time = (stage3.getDistance())/(runner.getSpeed());
				runner.hold_time = hold_time;
				break;
			}
			case 5 : {
				runner.incStage();
				SR_3.addRunner(runner);
				break;
			}
			
			case 6 : {
				String msg = "Passed 3th checkpoint!";		
				if(top_score_SR_3 != null) {
					msg += " +" + Utils.timeSpanFormatter(TimeOperations.diff(this.presentTime(), top_score_SR_3));
				}
				else {
					top_score_SR_3 = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score_SR_3);
				}	
				after_SR_3.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				runner.incStage();
				double hold_time = (stage4.getDistance())/(runner.getSpeed());
				runner.hold_time = hold_time;
				break;
			}
			
			case 7 : {
				runner.incStage();
				SR_4.addRunner(runner);
				break;
			}
			
			case 8 : {
				String msg = "Passed 4th checkpoint!";		
				if(top_score_SR_4 != null) {
					msg += " +" + Utils.timeSpanFormatter(TimeOperations.diff(this.presentTime(), top_score_SR_4));
				}
				else {
					top_score_SR_4 = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score_SR_4);
				}	
				after_SR_4.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				runner.incStage();
				double hold_time = (stage5.getDistance())/(runner.getSpeed());
				runner.hold_time = hold_time;
				break;
			}
			
			default: {
				ACTIVE_RUNNERS_NUM--;
				
				runner.hold_time = -1.0;
				
				String msg = "Finished run!";
				
				if(top_score != null) {
					runner.sendMessage(msg+ " place:" + (RUNNERS_NUM-ACTIVE_RUNNERS_NUM) + "/30");
					msg += " +" + Utils.timeSpanFormatter(TimeOperations.diff(this.presentTime(), top_score));
				}
				else {
					runner.sendMessage(msg+ " place:" + (RUNNERS_NUM-ACTIVE_RUNNERS_NUM) + "/30");
					top_score = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score);
				}
				
				results.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				if(ACTIVE_RUNNERS_NUM == 0) {
					System.out.println("Koniec symulacji.");
										
					results.close();
					after_SR_1.close();
					after_SR_2.close();
					after_SR_3.close();
					after_SR_4.close();
					
					this.getExperiment().stop();
				}	
				
				break;
			}
		}
	}
	
	/**
	 * Run constructor.
	 *
	 * Creates a new Run model via calling
	 * the constructor of the superclass.
	 *
	 * @param owner the model this model is part of (set to <tt>null</tt> when there is no such model)
	 * @param modelName this model's name
	 * @param showInReport flag to indicate if this model shall produce output to the report file
	 * @param showInTrace flag to indicate if this model shall produce output to the trace file
	 */
	public Run(Model owner, String modelName, boolean showInReport,	boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
	}

	/**
	 * returns a description of the model to be used in the report.
	 * @return model description as a string
	 */
	@Override
	public String description() {
		return "Symulcaja biathlonu z masowego startu";
	}
	
	/**
	 * activates dynamic model components (simulation processes).
	 *
	 * This method is used to place all events or processes on the
	 * internal event list of the simulator which are necessary to start
	 * the simulation.
	 *
	 * In this case, the truck generator and the van carrier(s) have to be
	 * created and activated.
	 */
	@Override
	public void doInitialSchedules() {
		stage1 = new Stage(3000);
		stage2 = new Stage(3000);
		stage3 = new Stage(3000);
		stage4 = new Stage(3000);
		stage5 = new Stage(3000);
		
		SR_1 = new SR(this, "SR 1", false);
		SR_2 = new SR(this, "SR 2", false);
		SR_3 = new SR(this, "SR 3", false);
		SR_4 = new SR(this, "SR 4", false);
		
		RunnerGenerator runnerGenerator = new RunnerGenerator(this, "Runner generator", false, RUNNERS_NUM);
		runnerGenerator.activate();
	}

	/**
	 * initialises static model components like distributions and queues.
	 */
	@Override
	public void init() {
		
		top_score = null;
		
		top_score_SR_1 = null;
		top_score_SR_2 = null;
		top_score_SR_3 = null;
		top_score_SR_4 = null;
		
		results = new TraceFileOut(2, "");
		results.open("output", "Results");
		
		after_SR_1 = new TraceFileOut(2, "");
		after_SR_1.open("output", "After_Shooting_Range_1");
		
		after_SR_2 = new TraceFileOut(2, "");
		after_SR_2.open("output", "After_Shooting_Range_2");
		
		after_SR_3 = new TraceFileOut(2, "");
		after_SR_3.open("output", "After_Shooting_Range_3");
		
		after_SR_4 = new TraceFileOut(2, "");
		after_SR_4.open("output", "After_Shooting_Range_4");
	}

	// ------------------------------------------------------------------------
	
	private TraceFileOut results;
	private TraceFileOut after_SR_1;
	private TraceFileOut after_SR_2;
	private TraceFileOut after_SR_3;
	private TraceFileOut after_SR_4;
	
	private static TimeInstant top_score; 
	private static TimeInstant top_score_SR_1;
	private static TimeInstant top_score_SR_2;
	private static TimeInstant top_score_SR_3;
	private static TimeInstant top_score_SR_4;

	private final static int RUNNERS_NUM = 30;	
	public static int ACTIVE_RUNNERS_NUM;
	
	public Stage stage1;
	public Stage stage2;
	public Stage stage3;
	public Stage stage4;
	public Stage stage5;
	
	public SR SR_1;
	public SR SR_2;
	public SR SR_3;
	public SR SR_4;	
}
