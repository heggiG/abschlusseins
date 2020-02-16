package modeltrain.trains;

import java.util.List;
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

    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }

    /**
     * Adds a Locomotive to the train
     * 
     * @param lo The Locomotive to add
     * @throws IllegalStateException if the train contains a Powered Cart, the Locomotive to add dosen't have a front
     *          coupler, or the last train dosen't have a back coupler
     */
    public void add(Locomotive lo) {
        if (wagons.size() == 0) {
            isPC = false;
        }
        if (isPC) {
            throw new IllegalStateException("train already contains a powered car");
        }
        if (wagons.size() != 0 && !lo.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
    }

    public void add(Coach co) {
        if (wagons.size() == 0) {
            isPC = false;
        }
        if (isPC) {
            throw new IllegalStateException("train already contains a powered car");
        }
        if (wagons.size() != 0 && !co.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
    }

    public void add(PoweredCart pc) {
        if (wagons.size() == 0) {
            isPC = true;
        }
        if (!isPC) {
            throw new IllegalStateException("train already contains a non powered car");
        }
        if (wagons.size() != 0 && !pc.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
        
    }
    
    public int getLength() {
        int length = 0;
        for(RollMaterial rm : wagons) {
            length += rm.getLength();
        }
        return length;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o.getClass().equals(this.getClass())))
            return false;
        if (o.hashCode() == this.hashCode())
            return true;
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
    
}
