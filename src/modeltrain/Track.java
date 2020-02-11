package modeltrain;

public class Track {
    
    private Point start;
    private Point end;
    
    public Track(Point start, Point end) {
        this.start = start;
        this.end = end;
    }
    
    public Point getStart() {
        return start;
    }
    
    public Point getEnd() {
        return end;
    }
    
    public int getLength() {
     int xLength = start.getXCord()  - end.getXCord();
     return xLength != 0 ? Math.abs(xLength) : Math.abs(start.getYCord() - end.getYCord());
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append();
    }
}
