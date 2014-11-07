package com.release.bean;

import java.io.Serializable;

public class Car {

	public int level;

	public float x;
	public float y;

	// public Car() {
	// // TODO Auto-generated constructor stub
	// }
	//
	// public Car(int level, float x, float y) {
	// super();
	// this.level = level;
	// this.x = x;
	// this.y = y;
	// }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "Car [level=" + level + ", x=" + x + ", y=" + y + "]";
	}
}
