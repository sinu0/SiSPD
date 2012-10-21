package BiathlonSymulation;

import java.util.concurrent.TimeUnit;

import desmoj.core.report.TraceFileOut;
import desmoj.core.report.TraceNote;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

/**
 * This class represents the runner in the Run model.
 */
public class Runner extends SimProcess {

	/**
	 * Constructor of the runner process
	 * Used to create a new runner.
	 *
	 * @param owner the model this process belongs to
	 * @param name this runner's name
	 * @param showInTrace flag to indicate if this process shall produce
	 *                    output for the trace
	 * @param number this runner number.
	 * @param speed this runner average speed.
	 * @param accuracy ths runner average accuracy.
	 */
	public Runner(Model owner, String name, boolean showInTrace, int number, double speed, double accuracy) {
		super(owner, name, showInTrace);
		
		model = (Run) owner;
		this.number = number; 
		this.speed = speed;
		this.accuracy = accuracy;
		this.stage = 0;
		
		this.lock = false;
		traceFile = new TraceFileOut(2, "");
		this.traceFile.open("output", "Runner_"+this.number);
		
		String msg = "Runner number: " +this.number+ "\n</br>Avg speed: " +
					 Utils.round2(this.speed)+ " m/s\n</br>Avg Accuracy: " +Utils.round2(this.accuracy)+ " %";
		
		traceFile.receive(new TraceNote(owner, msg, owner.presentTime(), this, null));
	}
	
	/**
	 * returns this runner number.
	 * @return this runner number as int.
	 */
	public int getNumber() {
		return this.number;
	}
	
	/**
	 * returns this runner average speed.
	 * @return this runner avarage speed as double.
	 */
	public double getSpeed() {
		return this.speed;
	}
	
	/**
	 * returns this runner average accuracy.
	 * @return this runner avarage accuracy as double.
	 */
	public double getAccuracy() {
		return this.accuracy;
	}
	
	/**
	 * returns this runner current stage.
	 * @return this runner current stage as int.
	 */
	public int getStage() {
		return this.stage;
	}
	
	/**
	 * Increment this runner stage.
	 */
	public void incStage() {
		stage++;
	}
	
	/**
	 * Sends message to this runner trace file.
	 */
	public void sendMessage(String msg) {
		traceFile.receive(new TraceNote(this.model, msg, this.model.presentTime(), this, null));
	}
	
	/**
	 * Describes this runner's life cycle:
	 */
	@Override
	public void lifeCycle() {		
		while(true) {
			if(!lock) 
				model.QueueRunner(this);
			
			if(hold_time == -1.0) {break;}
			else if(hold_time == 0.0) {
				passivate();
				lock = true;
			}
			else if(hold_time != 0.0) {
				hold(new TimeSpan(hold_time, TimeUnit.SECONDS));
				hold_time = 0.0;
				lock = false;
			}
		}
		traceFile.close();
	}
	
	// ------------------------------------------------------------------------
	private int number;      // numer biegacza.
	private double speed;    // Prêdkoœæ (m/s).
	private double accuracy; // Celnoœæ (%).
	private int stage;
	
	private TraceFileOut traceFile;
	
	public double hold_time;
	
	boolean lock;
	
	public Run model;
}
