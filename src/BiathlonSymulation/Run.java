package BiathlonSymulation;

import desmoj.core.report.TraceFileOut;
import desmoj.core.report.TraceNote;
import desmoj.core.simulator.*;

public class Run extends Model {

	public static void main(String[] args) {
		Run model = new Run(null, "Biathlon simulation", true, true);

		Experiment exp = new Experiment("Biatholon_simulation", "output");
		
		model.connectToExperiment(exp);		
		
		exp.start();
		exp.report();
		exp.finish();
	}
	
	
	public void QueueRunner(Runner runner) {
		//System.out.println("Runner:" + runner.getNumber() + " entered stage:" + runner.getStage());
		
		
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
					msg += " +" + Utils.timeInstantFormatter(TimeOperations.diff(this.presentTime(), top_score));
				}
				else {
					top_score = this.presentTime();
					msg += " "  +Utils.timeInstantFormatter(top_score);
				}
				
				results.receive(new TraceNote(this, msg, this.presentTime(), runner, null));
				
				if(ACTIVE_RUNNERS_NUM == 0) {
					System.out.println("Koniec");
					results.close();
					this.getExperiment().stop();
				}	
				
				break;
			}
		}
	}

	public Run(Model owner, String modelName, boolean showInReport,	boolean showInTrace) {
		super(owner, modelName, showInReport, showInTrace);
	}

	@Override
	public String description() {
		return "Symulcaja biathlonu z masowego startu";
	}

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

	@Override
	public void init() {
		
		top_score = null;
		
		results = new TraceFileOut(2, "");
		results.open("output", "Results");
	}

	// ------------------------------------------------------------------------
	
	private TraceFileOut results;
	private static TimeInstant top_score; 

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
