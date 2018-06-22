package com.itdoesnotmatter;

import android.util.Log;

import com.itdoesnotmatter.GameObject.MoveDirection;

public class CollisionDetection {
	
	private GameMap mGameMap;

	public ActiveObject[] mMovingObjects;
	
	public CollisionDetection(GameMap map) {
		this.setGameMap(map);

	}
	
	public void update(World world) {
		for (ActiveObject object : world.getObjects()) {
			moveObject(object, object.direction);
		}
	}
	
	/*
	 * Система определения столкновений
	 * Берем объект, в зависимости от направления движения определяем будущие (по завершению движения)
	 *  координаты его углов на карте
	 * Проверяем можно ли передвигаться по тайлам лежащим по этим координатам
	 * Если можно - двигаемся, если нет, то необходимо подвинуть объект к границе тайла
	 * Для этого пока используется метод, который мне не очень нравится:
	 * Вычисляем разницу между координатами границы тайла, к которому идет движения и коорд. границы объекта
	 * Если разница больше определенной величины scale, то нужно подвинуть объект к границе тайла + величина scale
	 * Пока не айс
	 */
	
	private void calculateCorners(ActiveObject object, float x, float y) {
		
		//Определение положения углов объекта
		object.upCellY = (int) Math.floor((GameMap.getOriginY() - object.currentTopBorder(y))/GameMap.tileSize());
		object.downCellY = (int) Math.floor((GameMap.getOriginY() - object.currentBottomBorder(y))/GameMap.tileSize());
		
		object.leftCellX = (int) Math.floor((object.currentLeftBorder(x) - GameMap.getOriginX())/GameMap.tileSize());
		object.rightCellX = (int) Math.floor((object.currentRightBorder(x) - GameMap.getOriginX())/GameMap.tileSize());
		if (Global.DEBUG) {
			Log.d("up/down", "" + object.upCellY + "/" + object.downCellY);
			Log.d("left/right", "" + object.leftCellX + "/" + object.rightCellX);
		}
		//Проверка на возможность движения по объекту
		object.upLeft = !getGameMap().getTile(object.upCellY, object.leftCellX).isSolid();
		object.upRight = !getGameMap().getTile(object.upCellY, object.rightCellX).isSolid();
		
		object.downLeft = !getGameMap().getTile(object.downCellY, object.leftCellX).isSolid();
		object.downRight = !getGameMap().getTile(object.downCellY, object.rightCellX).isSolid();
	}
	
	public void moveObject(ActiveObject object, MoveDirection direction) {
		float moveX;
		float moveY;
		if (object.move) {
			//Log.d("moveObject", object.velocity + "");
			switch(direction) {
			case TOP:
				
				moveY = object.moveY + object.velocity;

				calculateCorners(object, object.moveX, moveY);
				if (object.upLeft && object.upRight) {
					object.moveY += object.velocity;
				} else {
					int x = object.getColumn();
					int y = object.getRow();
					
					Tile tile = getGameMap().tile_map[y-1][x];
					float tileBorderCoords = tile.currentBottomBorder();
					float objectBorderCoords = object.currentTopBorder();
					float delta = tileBorderCoords - objectBorderCoords;
					if (delta > object.getSize() * object.scale) {
						object.moveY += (delta - object.getSize() * object.scale);
					}
					object.collision();
				}
				
				break;
			case DOWN:
				moveY = object.moveY - object.velocity;

				calculateCorners(object, object.moveX, moveY);
				if (object.downLeft && object.downRight) {
					object.moveY -= object.velocity;
				} else {
					int x = object.getColumn();
					int y = object.getRow();
					
					Tile tile = getGameMap().tile_map[y+1][x];
					float tileBorderCoords = tile.currentTopBorder();
					float objectBorderCoords = object.currentBottomBorder();
					float delta = objectBorderCoords - tileBorderCoords;
					if (delta > object.getSize() * object.scale) {
						object.moveY -= (delta - object.getSize() * object.scale);
					}
					object.collision();
				}
				break;
			case LEFT:

				moveX = object.moveX - object.velocity;
				calculateCorners(object, moveX, object.moveY);
				if (object.upLeft && object.downLeft) {
					object.moveX -= object.velocity;
				} else {
					int x = object.getColumn();
					int y = object.getRow();
					
					Tile tile = getGameMap().tile_map[y][x-1];
					float tileBorderCoords = tile.currentRightBorder();
					float objectBorderCoords = object.currentLeftBorder();
					float delta = objectBorderCoords - tileBorderCoords;
					if (delta > object.getSize() * object.scale) {
						object.moveX -= (delta - object.getSize() * object.scale);
						
					}
					object.collision();
				}
				break;
			case RIGHT:

				moveX = object.moveX + object.velocity;

				calculateCorners(object, moveX, object.moveY);
				if (object.upRight && object.downRight) {
					object.moveX += object.velocity;
				} else {
					int x = object.getColumn();
					int y = object.getRow();
					
					Tile tile = getGameMap().tile_map[y][x+1];
					float tileBorderCoords = tile.currentLeftBorder();
					float objectBorderCoords = object.currentRightBorder();
					float delta = tileBorderCoords - objectBorderCoords;
					if (delta > object.getSize() * object.scale) {
						object.moveX += (delta - object.getSize() * object.scale);
					}
					object.collision();
				}
				break;
			}
			
			object.calculateTile();
		}
	}
	
/************************** Getters / Setters *******************************/	
	
	public GameMap getGameMap() {
		return mGameMap;
	}

	public void setGameMap(GameMap mGameMap) {
		this.mGameMap = mGameMap;
	}

}
