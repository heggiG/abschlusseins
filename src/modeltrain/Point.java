package modeltrain;

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
    
    public String toString() {
        return "(" + xCord + "),(" + yCord + ")";
    }
    
}
