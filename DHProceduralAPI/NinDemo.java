import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;
import dyehard.DyeHardGame;

/**
 * NOTE: NO NEED TO PUT ANY CODE IN THIS CLASS YET
 * (THE GAME WILL RUN FROM NinProceduralAPI.java)
 * 
 * @author vuochnin
 *
 */
public class NinDemo extends DHProceduralAPI
{
	public void buildGame(){
		startHero();
		setLivesTo(6);
		displayScore(true);
		
		
		
		startDebrisSpawner(1.5f);
		startDyePackSpawner();
		startEnemySpawner();
		echo("worldWidth " + world.getWidth());
		echo("worldHeight " + world.getHeight());
		echo("worldPositionX " + world.getPositionX());
		echo("worldPositionY " + world.getWorldPositionY());
		//spawnGates();
	}
	
	public void updateGame(){
		//for(int i = 0; i < objectCount(); i++)
		// if(getType(i) == "Hero")
		//		move(i, 0, 1);
		// else if(getType(i) == "Debris"
		// 		move(i, 
		
		heroFollowTheMouse();
		
		// TEST Change the weapon according to the keyboard inputs
		if(isKeyboardLeftPressed()){
			activateSpreadFireWeapon();
		}
		if(isKeyboardRightPressed()){
			activateLimitedAmmoWeapon();
		}
		if(isKeyboardDownPressed()){
			defaultWeapon();
		}
		if(isKeyboardButtonTapped(KeysEnum.S)){
			increaseScoreBy(2);
		}
		if(isKeyboardButtonTapped(KeysEnum.E)){
			spawnSingleEnemy("charger");			//TEST SpawnSingleEnemy()
		}
		if(isKeyboardButtonTapped(KeysEnum.D))
			spawnSingleDyePack("blue", 50, 20); 		// TEST spawnSingleDyePack(color, x, y)

		if(isKeyboardButtonTapped(KeysEnum.ESCAPE)){
			DyeHardGame.setState(State.PAUSED);
		}

		if(repeatingTimer("powerup", 4))
		{
			//spawnSinglePowerUp(100, 30);
		}

		// Fire the paint
		if(isMouseLeftClicked() || isKeyboardSpacePressed()){
			firePaint();
		}
	}
	
//	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
//	{
//		if(type1 == "Hero" && type2 == "Debris")
//		{
//			move(id2, 2, 4);
//		}
//	}
}
