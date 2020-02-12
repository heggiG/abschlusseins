package modeltrain.core;

public class SwitchTrack extends Track {

    private Point altEnd;
    private Point currentSwitch;
    
    public SwitchTrack(Point start, Point end, Point altEnd, int id) {
        super(start, end, id);
        this.altEnd = altEnd;
    }
    
    @Override
    public Point getOtherPoint(Point po) {
        if (po.equals(super.start))
            return currentSwitch;
        else if (po.equals(currentSwitch))
            return super.start;
        else
            throw new IllegalStateException("Point is not part of the track/switch is in wrong position");
    }
    
    @Override
    public int getLength() {
        int xLength = start.getXCord()  - currentSwitch.getXCord();
        return xLength != 0 ? Math.abs(xLength) : Math.abs(start.getYCord() - currentSwitch.getYCord());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("s ");
        sb.append(id);
        sb.append(" ");
        sb.append(start);
        sb.append(" -> ");
        sb.append(end);
        sb.append(",");
        sb.append(altEnd);
        sb.append(" ");
        sb.append(getLength());
        return sb.toString();
    }
}
