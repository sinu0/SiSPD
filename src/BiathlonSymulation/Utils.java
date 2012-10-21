package BiathlonSymulation;

import desmoj.core.simulator.TimeInstant;
import desmoj.core.simulator.TimeSpan;

public class Utils {
	public static String timeSpanFormatter(TimeSpan time) {
		StringBuilder builder = new StringBuilder();
		
		Double min = time.getTimeAsDouble();
		Double s = min;
		
		if(min > 60.0) {
			min /= 60.0;
			min = Math.floor(min);
			builder.append(" " +min.intValue()+ "m, ");
		}
		else min = 0.0;
		
		s -= min*60.0;
		builder.append("" +Utils.round2(s)+ "s");
		
		return builder.toString();
	}
	
	public static String timeInstantFormatter(TimeInstant time) {
		StringBuilder builder = new StringBuilder();
		
		Double min = time.getTimeAsDouble();
		Double s = min;
		
		if(min > 60.0) {
			min /= 60.0;
			min = Math.floor(min);
			builder.append(" " +min.intValue()+ "m, ");
		}
		else min = 0.0;
		
		s -= min*60.0;
		builder.append("" +Utils.round2(s)+ "s");
		
		return builder.toString();
	}
	
	public static double round2(double num) {
		double result = num * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
}
