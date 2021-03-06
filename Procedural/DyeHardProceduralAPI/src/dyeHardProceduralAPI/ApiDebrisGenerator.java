package dyeHardProceduralAPI;

import dyehard.Obstacles.Debris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Provides Debris spawning functionality for the API
 *
 * @author Holden
 */
public class ApiDebrisGenerator
{
	private static Random RANDOM = new Random();

	/**
	 * The interval for Debris spawns
	 */
	private static float        interval;

	/**
	 * The active state of the generator
	 */
	private static boolean      active;

	/**
	 * The set of Debris in play
	 */
	private static List<Debris> debrisList;

	static
	{
		interval = 1.0f;
		active = false;
		debrisList = new ArrayList<Debris>();
	}

	/**
	 * 	Hide the constructor to simulate a static class. Because Java doesn't have static classes.
	 */
	private ApiDebrisGenerator(){}

	/**
	 * Starts the debris generator
	 */
	public static void enable()
	{
		active = true;
	}

	/**
	 * Stops the debris generator
	 */
	public static void disable()
	{
		active = false;
	}

	/**
	 * Sets the spawn interval of debris
	 * @param seconds the time
	 */
	public static void setInterval(float seconds)
	{
		interval = seconds;
	}

	/**
	 * Reports the number of debris instantiated
	 */
	public static int debrisCount()
	{
		return debrisList.size();
	}
	
	/**
	 * Spawns a single debris
	 */
	public static int spawnDebris()
	{
		Debris d = new APIDebris(RANDOM.nextInt(3),100,100);// spawns debris at right edge of screen
		debrisList.add(d);
		
		return ApiIDManager.register(d);
	}

	/**
	 * Spawns a single debris with a specified height
	 * @param y The height at which to spawn the debris
	 */
	public static int spawnDebris(float x, float y)
	{
		// the super class constructor will set the location randomly?
		Debris d = new APIDebris(RANDOM.nextInt(3), x, x, y, y);
		d.center.set(x, y);		// so make sure the position is set as specified
		debrisList.add(d);
		return ApiIDManager.register(d);
	}

	/**
	 * Updates the debris generator
	 */
	public static void update()
	{
		if(active &&
		   ApiTimeManager.repeatingTimer("DEBRIS_GENERATION", interval))
		{
			spawnDebris();
			cleanup();
		}
	}

	/**
	 * Removes destroyed debris from game
	 */
	private static void cleanup()
	{
		// Set up queue for cleaning destroyed debris
		LinkedList<Debris> removing = new LinkedList<>();

		// Scan list for destroyed debris an add them to the queue
		for(Debris d : debrisList)
		{
			if(!d.visible)
			{
				removing.add(d);
			}
		}

		// Remove queued debris from the list
		for(Debris r : removing)
		{
			debrisList.remove(r);
		}
	}
}
