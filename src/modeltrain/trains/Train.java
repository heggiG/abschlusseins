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
    private Boolean isPC;

    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }

    public void add(Locomotive lo) {
        if (isPC == null) {
            isPC = false;
        }
        if (isPC == true) {
            throw new IllegalStateException("train already contains a powered car");
        }
        if (wagons.size() != 0 && !lo.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
    }

    public void add(Coach co) {
        if (isPC == null) {
            isPC = false;
        }
        if (isPC == true) {
            throw new IllegalStateException("train already contains a powered car");
        }
        if (wagons.size() != 0 && !co.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
    }

    public void add(PoweredCart pc) {
        if (isPC == null) {
            isPC = true;
        }
        if (isPC == false) {
            throw new IllegalStateException("train already contains a non powered car");
        }if (wagons.size() != 0 && !pc.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
        
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

}
