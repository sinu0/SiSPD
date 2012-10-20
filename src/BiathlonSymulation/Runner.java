package BiathlonSymulation;

import java.util.concurrent.TimeUnit;

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
		
		System.out.println("Runner ctor $ Number[" +this.number+ "], Speed[" +this.speed+ "], Acc[" +this.accuracy+ "]");
	}
	
	public int getNumber() {
		return this.number;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public double getAccuracy() {
		return this.number;
	}
	
	public int getStage() {
		return this.stage;
	}
	
	public void incStage() {
		stage++;
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
				//System.out.println("Hold_time:" + hold_time);
				hold(new TimeSpan(hold_time, TimeUnit.SECONDS));
				hold_time = 0.0;
				lock = false;
			}
		}
	}
	
	// ------------------------------------------------------------------------
	private int number;      // numer biegacza.
	private double speed;    // Prêdkoœæ 1-50 (m/s).
	private double accuracy; // Celnoœæ 0-100 (%).
	private int stage;
	
	public double hold_time;
	
	boolean lock;
	
	public Run model;
}
