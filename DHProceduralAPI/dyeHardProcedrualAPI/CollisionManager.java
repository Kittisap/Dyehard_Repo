package dyeHardProcedrualAPI;


import dyehard.Collectibles.*;
import dyehard.Collectibles.PowerUp;
import dyehard.Collision.CollidableGameObject;
import dyehard.Enemies.Enemy;
import dyehard.Enemies.EnemyManager;
import dyehard.Enums.ManagerStateEnum;
import dyehard.Obstacles.Debris;
import dyehard.Player.Hero;

public class CollisionManager {

	private static dyehard.Collision.CollisionManager instance;
	static
	{
		instance = dyehard.Collision.CollisionManager.getInstance();
	}
	public void update(){
		CollidableGameObject[] objects =
		(CollidableGameObject[]) instance.getCollidables().toArray();
		
		int count = objects.length;
		
		
		// ACTORS: Hero, Enemies
		// CollidableGameObjects: DyePacks, PowerUps, Bullets, and Debris
		for(int i = 0; i < count; i++)
		{
			if(objects[i].collideState() != ManagerStateEnum.ACTIVE)
				continue;
			
			for(int j = i+1; j < count; j++)
			{
				if(objects[j].collideState() != ManagerStateEnum.ACTIVE)
					continue;
				
				if(objects[i].collided(objects[j]))
				{
					if(objects[i] instanceof Hero)
					{
						handleHeroCollisions(objects[i], objects[j]);
					}
					else if (objects[j] instanceof Hero)
					{
						handleHeroCollisions(objects[j], objects[i]);
					}
					else
					{
						objects[i].handleCollision(objects[j]);
						objects[j].handleCollision(objects[i]);
					}
				}
				
			}
		}
		
		instance.updateSet();
	}
	
	private void handleHeroCollisions(CollidableGameObject hero, CollidableGameObject other)
	{
		//if other instanceof Debris
		if(other instanceof Debris){
			int id = DebrisGenerator.getID((Debris)other);
			//handleDebrisCollision(id);
		}
		//handle enemy(id, type)
		else if(other instanceof Enemy){
			String type = other.toString();
			int id = EnemyManager.getInstance().getId((Enemy) other);
			//handleEnemyCollision(id, type);
		}
		
		// powerup(id, type)
		else if(other instanceof PowerUp)
		{
			String type = getPowerUpType(other);
			
			//handlePowerUpCollision(id, type);
		}
		
		// dyePack(id, color)
		else if(other instanceof DyePack){
			
		}
	}
	
	/**
	 * Get the type of the PowerUp
	 * @param obj the collidableGameObject PowerUp
	 * @return the name of the PowerUp as a String 
	 */
	private String getPowerUpType(CollidableGameObject obj){
		String type = "";
		if(obj instanceof Ghost)
		{
			type = "Ghost";
		}
		else if(obj instanceof Gravity){
			type = "Gravity";
		}
		else if(obj instanceof Invincibility){
			type = "Invincibility";
		}
		else if(obj instanceof Magnetism){
			type = "Magnetism";
		}
		else if(obj instanceof Overload)
		{
			type = "Overload";
		}
		else if(obj instanceof Repel){
			type = "Repel";
		}
		else if(obj instanceof SlowDown){
			type = "SlowDown";
		}
		else if(obj instanceof SpeedUp){
			type = "SpeedUp";
		}
		else if(obj instanceof Unarmed){
			type = "Unarmed";
		}
		return type;
	}
	
}