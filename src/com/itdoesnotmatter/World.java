package com.itdoesnotmatter;

import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class World {
	private Vector<GameObject> staticObjects;
	private Vector<ActiveObject> objects;
	private Vector<GameObject> objectsToDelete;
	private static Vector<ActiveObject> objectsToAdd;
	
	private CollisionDetection collisionManager;
	private Milo milo;
	public World(int width, int height)  {
		
		objects = new Vector<ActiveObject>();
		objectsToDelete = new Vector<GameObject>();
		staticObjects = new Vector<GameObject>();
		objectsToAdd = new Vector<ActiveObject>();
		GameMap map = new GameMap(width, height, false);
		collisionManager = new CollisionDetection(map);
		milo = new Milo();
		staticObjects.add(map);
	}
	
	public void addObject(ActiveObject object) {
		objects.add(object);
	}
	
	public static void addObjectToQueue(ActiveObject object) {
		if (objectsToAdd == null) {
			objectsToAdd = new Vector<ActiveObject>();
		}
		objectsToAdd.add(object);
	}
	
	public void render(GL10 gl, float interpolation) {
		for (GameObject object : staticObjects) {
			object.drawObject(gl, interpolation);
		}
		for (GameObject object : objects) {
			object.drawObject(gl, interpolation);
		}
	}
	
	public void tick() {
		
		if (objectsToAdd != null) {
			for (ActiveObject object : objectsToAdd) {
				this.addObject(object);
			}
		}
		objectsToAdd = null;
		
		milo.update(this);
		collisionManager.update(this);
		
		objectsToDelete = new Vector<GameObject>();
		
		for (GameObject object : this.getObjects()) {
			if (!object.isAlive()) {
				objectsToDelete.add(object);
			}
		}
		
		for (GameObject object : objectsToDelete) {
			getObjects().remove(object);
		}
		
		objectsToDelete = null;
	}
	
	public Vector<ActiveObject> getObjects() {
		return this.objects;
	}
	
	public void placeObject(ActiveObject object, int row, int column) {
		float originX = GameMap.getOriginX();
		float originY = GameMap.getOriginY();
		float size = GameMap.tileSize()*2 - (GameMap.tileSize()*2) * object.scale;
		//Предпологается, что все объекты квадратные
		float deltaSize = (GameMap.tileSize() - size)/2;
		//float deltaSize = 0;
		float[] vertices = new float[] {
				(float) (originX + (GameMap.tileSize() * column) + deltaSize), (float) (originY - (GameMap.tileSize()*row)  - GameMap.tileSize() + deltaSize), 0.0f,
				(float) (originX + (GameMap.tileSize() * column) + GameMap.tileSize() - deltaSize), (float) (originY - (GameMap.tileSize()*row) - GameMap.tileSize() + deltaSize), 0.0f,
				(float) (originX + (GameMap.tileSize() * column) + deltaSize), (float) (originY - (GameMap.tileSize()*row) - deltaSize), 0.0f, 
				(float) (originX + (GameMap.tileSize() * column) + GameMap.tileSize() - deltaSize), (float) (originY - (GameMap.tileSize()*row) - deltaSize), 0.0f
		};

		object.setOnTile(vertices, size);
	}
}
