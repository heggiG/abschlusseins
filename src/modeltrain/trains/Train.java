package modeltrain.trains;

import java.util.List;
import modeltrain.core.Point;
import modeltrain.core.SemanticsException;
import java.util.ArrayList;

/**
 * Class that implements a train.
 * 
 * @author Florian
 * @version 1.1
 */
public class Train implements Comparable<Train> {

    private final int id;
    private List<RollingStock> wagons;
    private boolean isTrainSet;
    private boolean isValid;
    private Point direction;

    /**
     * Constructs the train
     * @param id The trains id
     */
    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }

    /**
     * Adds an engine to the train
     * 
     * @param engine The engine to add
     * @throws SemanticsException 
     */
    public void add(Engine engine) throws SemanticsException {
        if (wagons.isEmpty()) {
            isTrainSet = false;
            isValid = true;
            wagons.add(engine);
        } else if (isTrainSet) {
            throw new SemanticsException("train already contains a powered car");
        } else if (wagons.size() != 0 && !engine.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else {
            wagons.add(engine);
        }
    }

    /**
     * Adds a coach to the train
     * @param co The coach to add
     * @throws SemanticsException if the parameters dont work
     */ 
    public void add(Coach co) throws SemanticsException {
        if (wagons.isEmpty()) {
            isTrainSet = false;
            wagons.add(co);
        } else if (isTrainSet) {
            throw new SemanticsException("train already contains a powered car");
        } else if (wagons.size() != 0 && !co.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else {
            wagons.add(co);
        }
    }

    /**
     * Adds a trainset to the train
     * @param trainSet The trainset to add
     * @throws SemanticsException if the parameters dont work
     */
    public void add(TrainSet trainSet) throws SemanticsException {
        if (wagons.isEmpty()) {
            isTrainSet = true;
            isValid = true;
            wagons.add(trainSet);
        }
        if (!isTrainSet) {
            throw new SemanticsException("train already contains a non powered car");
        } else if (wagons.size() != 0 && !trainSet.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else if (!wagons.get(wagons.size() - 1).getModelSeries().equals(trainSet.getModelSeries())) {
            throw new SemanticsException("model types of the powered carts don't match");
        } else {
            wagons.add(trainSet);
        }
    }

    /**
     * Sets the direction and reduces it to length 1
     * @param direction The trains direction
     */
    public void setDirection(Point direction) {
        this.direction = direction.reduce();
    }

    /**
     * 
     * @return the trains direction
     */
    public Point getDirection() {
        return direction;
    }

    /**
     * 
     * @return Whether the train is valid
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * 
     * @return The trains length as a sum of its rolling stock
     */
    public int getLength() {
        int length = 0;
        for (RollingStock rm : wagons) {
            length += rm.getLength();
        }
        return length;
    }

    /**
     * 
     * @return The trains id
     */
    public int getId() {
        return id;
    }

    /**
     * Constructs the trains ascii art representation
     * @return a string array containing the ascii art representation
     */
    public String[] show() {
        if (wagons.size() == 0) {
            return null;
        } else {
            StringBuilder[] sb = new StringBuilder[wagons.get(0).show().length];
            for (RollingStock rm : wagons) {
                for (int i = 0; i < rm.show().length; i++) {
                    if (sb[i] == null) {
                        sb[i] = new StringBuilder();
                    }
                    if (sb[i].length() != 0) {
                        sb[i].append(" ");
                    }
                    sb[i].append(rm.show()[i]);
                }
            }
            String ret[] = new String[sb.length];
            for (int i = 0; i < ret.length; i++) {
                ret[i] = sb[i].toString();
            }
            return ret;
        }
    }

    /**
     * 
     * @return the trains rolling stock
     */
    public List<RollingStock> getRollingStock() {
        return wagons;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Train o) {
        return this.id - o.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() != this.getClass())
            return false;
        Train t = (Train) o;
        if (t.id == this.id)
            return true;
        else if (this.wagons.containsAll(t.wagons))
            return true;
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        for (RollingStock rm : wagons) {
            sb.append(" ");
            sb.append(rm.getId());
        }
        return sb.toString();
    }

}
