package modeltrain.trains;

import java.util.List;
import modeltrain.core.Point;
import modeltrain.core.SemanticsException;
import java.util.ArrayList;

/**
 * Class that implements a train.
 * 
 * @author Florian
 * @version 400.1
 */
public class Train {

    private final int id;
    private List<RollMaterial> wagons;
    private boolean isPC;
    private Point direction;

    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }

    /**
     * Adds a Locomotive to the train
     * 
     * @param lo The Locomotive to add
     */
    public void add(Engine lo) {
        if (wagons.isEmpty()) {
            isPC = false;
            wagons.add(lo);
        } else if (isPC) {
            throw new SemanticsException("train already contains a powered car");
        } else if (wagons.size() != 0 && !lo.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else {
            wagons.add(lo);
        }
    }

    public void add(Coach co) {
        if (wagons.isEmpty()) {
            isPC = false;
            wagons.add(co);
        } else if (isPC) {
            throw new SemanticsException("train already contains a powered car");
        } else if (wagons.size() != 0 && !co.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else {
            wagons.add(co);
        }
    }

    public void add(TrainSet pc) {
        if (wagons.isEmpty()) {
            isPC = true;
            wagons.add(pc);
        }
        if (!isPC) {
            throw new SemanticsException("train already contains a non powered car");
        } else if (wagons.size() != 0 && !pc.getFrontCoupling()) {
            throw new SemanticsException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new SemanticsException("last rollmaterial needs backcoupling");
        } else if (!wagons.get(wagons.size() - 1).getModel().equals(pc.getModel())) {
            throw new SemanticsException("model types of the powered carts don't match");
        } else {
            wagons.add(pc);
        }
    }

    public void setDirection(Point direction) {
        this.direction = direction;
    }

    public Point getDirection() {
        return direction;
    }

    public boolean isValid() {
        if (wagons.get(wagons.size() - 1).getType().equals("locomotive")
                || wagons.get(0).getType().equals("locomotive")) {
            return true;
        } else if (wagons.get(wagons.size() - 1).getType().equals("trainset")
                || wagons.get(0).getType().equals("trainset")) {
            return true;
        }
        return false;
    }

    public int getLength() {
        int length = 0;
        for (RollMaterial rm : wagons) {
            length += rm.getLength();
        }
        return length;
    }

    public int getId() {
        return id;
    }
    
    public String[] show() {
        if (wagons.size() == 0) {
            return null;
        } else {
            String[] ret = new String[wagons.get(0).show().length];
            for (RollMaterial rm : wagons) {
                for (int i = 0; i < rm.show().length; i++) {
                    if (ret[i] != null) {
                        ret[i].concat(" ");
                    }
                    ret[i].concat(rm.show()[i]);
                }
            }
        return ret;
        }        
    }
    
    public List<RollMaterial> getWagons() {
        return wagons;
    }
    
    @Override
    public int hashCode() {
        return id;
    }

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        for (RollMaterial rm : wagons) {
            sb.append(" ");
            sb.append(rm);
        }
        return sb.toString();
    }

}
