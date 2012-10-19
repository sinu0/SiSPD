package biathlon;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.ContDistExponential;
import desmoj.core.dist.ContDistNormal;
import desmoj.core.report.HTMLTraceOutput;
import desmoj.core.report.TraceFileOut;
import desmoj.core.simulator.Model;
import desmoj.core.simulator.SimProcess;
import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Runner extends SimProcess {

	private desmoj.core.dist.ContDistNormal shots;
	private final int laps = 4;

	private int currnetLap;

	private int amountOfShoots;
	
	private int number;
	
	private HTMLTraceOutput traceOutput;

	private Process model;

	public Boolean isRunnerStandOrProne() {
		if (currnetLap > 2)
			return false;
		else
			return true;
	}

	public Runner(Model owner, String name, boolean showInTrace, int number) {
		super(owner, name, showInTrace);
		model = (Process) owner;
		this.number = number; 
		currnetLap = 1;
		amountOfShoots = 5;
		shots = new ContDistNormal(model, "shots", 5, 5, true, true);
		shots.setNonNegative(true);
		
		traceOutput = new HTMLTraceOutput();
		traceOutput.open("output", "Runner_" + number);
	}

	public int doShoots() {
		return shots.sample().intValue();
	}

	@Override
	public void lifeCycle() {
		
		while (currnetLap != laps) {
			model.RunnerQueue.insert(this);
			traceOutput.writeln("Created");
			hold(new TimeSpan(model.getRunnerArrivalTime().sample(),
					TimeUnit.SECONDS));

		}
		
		traceOutput.close();
	}

}
