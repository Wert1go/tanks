package com.itdoesnotmatter;

import java.util.Random;

import com.itdoesnotmatter.GameObject.MoveDirection;

public class Milo {
	
	public void update(World world) {
		for (int i = 0; i < world.getObjects().size(); i ++ ) {
			if (i <= 4) {
				Tank tank = (Tank) world.getObjects().get(i);
				if (!tank.move) {
					Random rand = new Random();
					int direction;
					do {
						direction = rand.nextInt(4);
					} while (direction == tank.direction.ordinal());
					tank.changeDirection(MoveDirection.get(direction));
					tank.moving(true);
					
					
				}
				if (tank.getBulletCount() > 0) {
					tank.shoot();
				}
			}
		}
	}
}
