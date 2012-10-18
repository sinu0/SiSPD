package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeSpan;

public class Runner extends SimProcess {

	private final int laps=4;
	
	private int currnetLap=1;
	
	private Boolean[] shoots={false,false,false,false}; 
	
	private Process model;
	
	public Boolean isRunnerStandOrProne(){
		if(currnetLap>2)
			return false;
		else
			return true;
	}
	
	public Runner(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);
		model=(Process)owner;
	}

	@Override
	public void lifeCycle() {
		while(currnetLap!=laps)
		{
			hold(new TimeSpan(model.getRunnerArrivalTime().getLower(),TimeUnit.SECONDS));
			
			
		}
		
	}
	

	

}
