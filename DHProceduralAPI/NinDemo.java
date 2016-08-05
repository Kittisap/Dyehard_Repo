import dyeHardProceduralAPI.DHProceduralAPI;
import dyeHardProceduralAPI.KeysEnum;
import dyehard.DyeHardGame;
import dyehard.World.GameState;

/**
 * 
 * 
 * @author vuochnin
 *
 */
public class NinDemo extends DHProceduralAPI
{
	int heroID;
	
	public void buildGame(){
		heroID = apiStartHero();
		apiShowScore(true);
		apiSetLivesTo(8);
		apiSetGoalDistance(1000);
		apiShowDistanceMeter();
		apiSetWinningScore(15);
		apiStartDebrisSpawner(1.5f);
		apiStartDyePackSpawner();
		apiStartEnemySpawner();
		//spawnGates();
		
		apiSpawnSingleDebris(30, 30);
		
	}
	
	
	public void updateGame(){
		
		if(apiRepeatingTimer("powerup", 5)){
			apiSpawnSinglePowerUp(apiGetWorldWidth() - 10f, apiRandomInt((int)apiGetWorldHeight() - 10));
		}
		
		apiObjectFollowTheMouse(heroID);
		
		// Fire the paint
		if(apiIsMouseLeftClicked() || apiIsKeyboardSpacePressed()){
			apiHerofirePaint();
		}
		
		if(apiIsKeyboardButtonTapped(KeysEnum.y)){			// CHECK debris subtype
			int deId = apiSpawnSingleDebris();
			apiEcho("The subtype of this debris is " + apiGetSubtype(deId));
		}
		
		// TEST Change the weapon according to the keyboard inputs
		if(apiIsKeyboardLeftPressed()){
			apiActivateSpreadFireWeapon();
		}
		if(apiIsKeyboardDownPressed()){
			apiDefaultWeapon();
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.S)){
			apiAdjustScoreBy(2);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.A)){
			apiAdjustScoreBy(-2);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.E)){
			apiSpawnSingleEnemy("Portal", 50, 20);			//TEST SpawnSingleEnemy()
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.D))
			apiSpawnSingleDyePack("blue", 50, 20); 		// TEST spawnSingleDyePack(color, x, y)

		if(apiIsKeyboardButtonTapped(KeysEnum.ESCAPE)){
			if(getState() == State.PLAYING)
				DyeHardGame.setState(State.PAUSED);
			else
				DyeHardGame.setState(State.PLAYING);
		}
		
		if(apiIsKeyboardButtonTapped(KeysEnum.X))
			apiQuitGame();
		
		if(apiUserLose()){
			apiShowLoseMenu(true);
		}
		if(apiUserWon()){
			apiShowWinMenu(true);
		}
		
		if(apiIsKeyboardRightPressed()){
			apiSpeedUp(true);
		}else{
			apiSpeedUp(false);
		}
		if(apiIsKeyboardButtonTapped(KeysEnum.G))
			apiSpawnSinglePowerUp("SlowDown", 90, 30);
		
		if(apiIsKeyboardButtonTapped(KeysEnum.b))
			apiEcho("DistanceTravelled = " + GameState.DistanceTravelled);
		if(apiIsKeyboardButtonTapped(KeysEnum.r))
			apiRestartGame();
		if(apiIsKeyboardButtonTapped(KeysEnum.c))
			apiEcho("count " + apiObjectCount());
	}
	
	
	
//	public void handleCollisions(String type1, String subtype1, int id1, String type2, String subtype2, int id2)
//	{
//		if(type1 == "Hero" && type2 == "Debris")
//		{
//			move(id2, 2, 4);
//		}
//	}
}