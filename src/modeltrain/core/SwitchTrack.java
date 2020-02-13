package modeltrain.core;

import java.util.Set;

public class SwitchTrack extends Track {

    private Point altEnd;
    private Point currentSwitch;

    public SwitchTrack(Point start, Point end, Point altEnd, int id) {
        super(start, end, id);
        this.altEnd = altEnd;
    }

    public Point getAltEnd() {
        return altEnd;
    }

    public Point toggle() {
        if (currentSwitch.equals(end)) {
            currentSwitch = altEnd;
        } else {
            currentSwitch = end;
        }
        return currentSwitch;
    }

    @Override
    public Point getOtherPoint(Point po) {
        if (po.equals(start))
            return currentSwitch;
        else if (po.equals(currentSwitch))
            return start;
        else
            throw new IllegalStateException("Point is not part of the track/switch is in wrong position");
    }

    @Override
    public Set<Point> getPoints() {
        Set<Point> points = super.getPoints();
        points.add(altEnd);
        return points;
    }

    @Override
    public int getLength() {
        int xLength = start.getXCord() - currentSwitch.getXCord();
        int yLength = start.getYCord() - currentSwitch.getYCord();
        return (int) Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));
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
