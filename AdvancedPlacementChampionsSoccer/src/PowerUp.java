/**
 * Represents the power up that a Tekkist has
 * @author Mira Khosla
 * @version 5/15/18
 *
 */
public class PowerUp {
	public static final String[] powerUps= new String[]{"shrink goal","grow", "slow motion"};
	public int whichPower;
	
	public PowerUp(int whichPower)
	{
		this.whichPower = whichPower;
	}
}
