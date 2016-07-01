package dyeHardProcedrualAPI;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Random;

import Engine.BaseCode;
import dyehard.DyeHardGame;
import dyehard.UpdateManager;
import dyehard.Collision.CollisionManager;
import dyehard.Resources.ConfigurationFileParser;
import dyehard.World.GameState;
import dyehard.Player.Hero;
import dyehard.Weapons.*;
//import dyehard.Updateable;

/**
 * @author vuochnin
 *
 */
public class DHProcedrualAPI extends DyeHardGame{
	
	/**
     * Used in the wrapper API function isKeyboardButtonDown, this array 
     * maps a shorter abbreviation like 'RIGHT' to the virtual key event 'KeyEvent.VK_RIGHT'
     * (Source: SpaceSmasherFunctionalAPI/SpaceSmasherProceduralAPI)
     */
	public static final int[] keyEventMap = {
		KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, 
		KeyEvent.VK_SPACE, KeyEvent.VK_ENTER, KeyEvent.VK_ESCAPE, KeyEvent.VK_SHIFT, 
		KeyEvent.VK_LESS, KeyEvent.VK_GREATER,
		KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4,
        KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, 
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F,
        KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R,
        KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
        KeyEvent.VK_Y, KeyEvent.VK_Z,
        KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F,
        KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L,
        KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R,
        KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X,
        KeyEvent.VK_Y, KeyEvent.VK_Z
	};
	
	private Hero hero;
	
	/**
	 * @Override 
	 * Must override the initialize() method from the abstract super class, DyeHardGame
	 */
	public void initialize(){
		requestFocusInWindow();
		setGoalDistance();
		
		buildGame();
	}
	
	public void buildGame(){
		startHero();
	}
	
	/**
	 * @Override 
	 * Must override the update() method from the abstract super class, DyeHardGame
	 */
	public void update(){
		// following the DyeHardUser code
		//keyboardUpdate();
		
		runUpdateManager();
		handleCollisions();
		
		updateGame();
		
	}
	
	public void updateGame()
	{
		heroFollowTheMouse();
		
		if(isMouseLeftClicked()){
			firePaint();
		}
		
	}
	
//----------------------SOME POSSIBLE PROCEDURAL FUNCTIONS -----------------------------------
	/**
	 * Fire the current weapon
	 */
	public void firePaint(){					// Fire the paint
		hero.currentWeapon.fire();
	}
	
	/**
	 * Create new Hero object and set it to hero instance and 
	 * set the cursor to the center of that hero
	 */
	public void startHero(){					// Create new Hero
		hero = new Hero();
		setCursorToCenterOfHero();	// move the cursor to the center of the hero
	}
	
	public void moveHero(float x, float y){
		hero.moveTo(x, y);
	}
	
	// Make hero follow the mouse movement
	public void heroFollowTheMouse(){
		moveHero(mouse.getWorldX(), mouse.getWorldY());
	}
	
	/**
	 * Get the x-coordinate position of the mouse
	 * @return x-coordinate position of the mouse
	 */
	public float mousePositionX(){
		return mouse.getWorldX();
	}
	
	/**
	 * Get the y-coordinate position of the mouse
	 * @return y-coordinate position of the mouse
	 */
	public float mousePositionY(){
		return mouse.getWorldY();
	}
	
	/**
	 * Check if the MOUSE LEFT button is clicked
	 * @return	true/false
	 */
	public boolean isMouseLeftClicked(){
		return (mouse.isButtonDown(1));
	}
	
	/**
     * API utility method
     * Check if the key we're investigating is currently being pressed
     * (Source: SpaceSmasherFunctionalAPI/SpaceSmasherProceduralAPI)
     * @param key - the key we're investigating
     * @return - true if the key we're investigating is currently being pressed
     */
	public boolean isKeyboardButtonDown(KeysEnum key) {
        return keyboard.isButtonDown(keyEventMap[key.ordinal()]);  //ordinal is like indexOf for enums->ints
    }
	
	/**
	 * Check if the LEFT arrow on the keyboard is pressed
	 * @return true if the left arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardLeftPressed(){
		return isKeyboardButtonDown(KeysEnum.LEFT);
	}
	
	/**
	 * Check if the RIGHT arrow on the keyboard is pressed
	 * @return true if the right arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardRightPressed(){
		return isKeyboardButtonDown(KeysEnum.RIGHT);
	}
	
	/**
	 * Check if the UP arrow on the keyboard is pressed
	 * @return true if the up arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardUpPressed(){
		return isKeyboardButtonDown(KeysEnum.UP);
	}
	
	/**
	 * Check if the DOWN arrow on the keyboard is pressed
	 * @return true if the down arrow key on the keyboard is pressed
	 */
	public boolean isKeyboardDownPressed(){
		return isKeyboardButtonDown(KeysEnum.DOWN);
	}
	
	
	// ---------- Utilities functions ------------
	
	/**
	 * 
	 */
	private void requestFocusInWindow(){
		window.requestFocusInWindow();
	}
	
	/**
	 * Sets the distance the player must travel to beat the game to a default value
	 */
	public void setGoalDistance(){
		GameState.TargetDistance = ConfigurationFileParser.getInstance().getWorldData().getWorldMapLength();
	}
	
	/**
	 * Sets the distance the player must travel to beat the game 
	 * @param distance The new distance required to beat the game
	 */
	public void setGoalDistance(int distance){
		GameState.TargetDistance = distance;
	}
	
	
	private void setCursorToCenterOfHero(){
		try{
			Robot robot = new Robot();
			
			robot.mouseMove(window.getLocationOnScreen().x + (int)(hero.center.getX() 
								* window.getWidth() / BaseCode.world.getWidth()), 
							window.getLocationOnScreen().y + window.getHeight() - (int)(hero.center.getX() 
								* window.getWidth() / BaseCode.world.getWidth()));
		} catch(AWTException e){
			e.printStackTrace();
		}
	}
	
	private void handleCollisions(){					// Handle collisions
		CollisionManager.getInstance().update();
	}
	
	private void runUpdateManager(){			// Handle UpdateManager, called in initialize function
		UpdateManager.getInstance().update();
	}
	
	// ---------- Utilities functions end ------------
	
	
	
	//-------------- WEAPONS -----------------------------------
	public void addSpreadFireWeapon(){
		hero.registerWeapon(new SpreadFireWeapon(hero));
	}
	
	public void addOverHeatWeapon(){
		hero.registerWeapon(new OverHeatWeapon(hero));
	}
	
	public void addLimitedAmmoWeapon(){
		hero.registerWeapon(new LimitedAmmoWeapon(hero));
	}
	//-------------- WEAPONS end-----------------------------------
}
