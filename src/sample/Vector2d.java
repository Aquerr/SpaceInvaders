package sample;

public final class Vector2d {
    private double X;
    private double Y;

    public Vector2d(double x, double y){
        this.X = x;
        this.Y = y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void setX(double x) {
        this.X = x;
    }

    public void setY(double y) {
        this.Y = y;
    }
}
