package modeltrain;

public class Track {
    
    protected Point start;
    protected Point end;
    protected int id;
    
    public Track(Point start, Point end, int id) {
        this.start = start;
        this.end = end;
        this.id = id;
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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("t ");
        sb.append(id);
        sb.append(" ");
        sb.append(start);
        sb.append(" -> ");
        sb.append(end);
        sb.append(" ");
        sb.append(getLength());
        return sb.toString();
    }
}
