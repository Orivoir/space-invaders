package com.codingame.game;

public class Point {

    public static Point defaultPoint = new Point(0f, 0f);

    private float x;
    private float y;

    public float getX() {
        return x;
    }
    public Point setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }
    public Point setY(float y) {
        this.y = y;
        return this;
    }

    public Point add(Point operationalPoint) {
        return new Point(this.x + operationalPoint.x, this.y + operationalPoint.y);
    }
    public Point sub(Point operationalPoint) {
        return new Point(this.x - operationalPoint.x, this.y - operationalPoint.y);
    }

    public Point() {
        this(defaultPoint.getX(), defaultPoint.getY());
    }
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
