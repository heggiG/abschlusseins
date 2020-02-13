package modeltrain.core;

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
    
    public Point getOtherPoint(Point po) {
        if(po.equals(start))
            return end;
        else if(po.equals(end))
            return start;
        else
            throw new IllegalStateException("point is not part of the track");
    }
    
    public int getLength() {
     int xLength = start.getXCord()  - end.getXCord();
     return xLength != 0 ? Math.abs(xLength) : Math.abs(start.getYCord() - end.getYCord());
    }
    
    @Override
    public boolean equals(Object o) {
        if(!(o.getClass().equals(this.getClass())))
            return false;
        if(o.toString().equals(this.toString()))
            return true;
        return false;        
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
