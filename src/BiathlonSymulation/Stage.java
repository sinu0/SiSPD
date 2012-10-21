package BiathlonSymulation;

/**
 * This class represents a Stage.
 */
public class Stage {

	/**
	 * Constructor of the Stage. (it is not a process!)
	 *
	 * @param distance this stage distance in meters.
	 */
	public Stage(int distance) {
		this.distance = distance;
	}
	
	/**
	 * Returns this stage distance in meters.
	 *
	 * @return this stage distance in meters as int.
	 */
	public int getDistance() {
		return distance;
	}
	// ------------------------------------------------------------------------
	private int distance;
}
