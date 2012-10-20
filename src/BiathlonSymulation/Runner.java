package BiathlonSymulation;

import java.util.concurrent.TimeUnit;

import desmoj.core.report.TraceFileOut;
import desmoj.core.report.TraceNote;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Runner extends SimProcess {

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
	
	public int getNumber() {
		return this.number;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public double getAccuracy() {
		return this.accuracy;
	}
	
	public int getStage() {
		return this.stage;
	}
	
	public void incStage() {
		stage++;
	}
	
	public void sendMessage(String msg) {
		traceFile.receive(new TraceNote(this.model, msg, this.model.presentTime(), this, null));
	}

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
	private double speed;    // Prêdkoœæ 1-50 (m/s).
	private double accuracy; // Celnoœæ 0-100 (%).
	private int stage;
	
	private TraceFileOut traceFile;
	
	public double hold_time;
	
	boolean lock;
	
	public Run model;
}
