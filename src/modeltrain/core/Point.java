package modeltrain.core;

public class Point {

    private int xCord;
    private int yCord;
    
    Point(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }
    
    public int getXCord() {
        return this.xCord;
    }
    
    public int getYCord() {
        return this.yCord;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        if (o.toString().equals(this.toString()))
            return true;
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + xCord + "),(" + yCord + ")";
    }
    
}
