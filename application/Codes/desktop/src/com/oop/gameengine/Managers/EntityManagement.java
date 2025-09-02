package com.oop.gameengine.Managers;

import java.util.ArrayList;
import java.util.List;
import com.oop.gameengine.Entities.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Iterator;
public class EntityManagement {

	private List<Entity> entityList;

	public EntityManagement(){
		entityList = new ArrayList<Entity> ();
	}
	public void createEntity(Entity e) {
		entityList.add(e);
	}
	
	public void removeEntity(Entity e) {
		entityList.remove(e);
	}
	public List<Entity> getEntityList(){
		return entityList;
	}
	public void draw(SpriteBatch batch, ShapeRenderer shape)
	{
		for(int i = 0; i < entityList.size(); i++)
		{
			batch.begin();
			entityList.get(i).draw(batch);
			batch.end();

			shape.begin(ShapeRenderer.ShapeType.Filled);
			entityList.get(i).draw(shape);
			shape.end();
		}
	}
	public void update() {

		for (int i = 0; i < entityList.size(); i++)
		{
			entityList.get(i).update();
		}

	}
	public void clearEntities() {
		entityList.clear();
	}
	
	public void clearItemsOfType(Class<? extends Entity> entityType) {
	    Iterator<Entity> iterator = entityList.iterator();
	    while (iterator.hasNext()) {
	        Entity entity = iterator.next();
	        if (entityType.isInstance(entity)) {
	            iterator.remove();
	        }
	    }
	}

}


