package modeltrain.trains;

import java.util.List;
import java.util.ArrayList;

public class Train {

    private final int id;
    private List<RollMaterial> wagons;
    private boolean lastIsPC;

    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }

    public void add(Locomotive lo) {
        if (wagons.size() != 0 && !lo.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
        lastIsPC = false;
    }

    public void add(Coach co) {

        if (wagons.size() != 0 && !co.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
        lastIsPC = false;
    }

    public void add(PoweredCart pc) {

        if (wagons.size() != 0 && !pc.getFrontCoupling()) {
            throw new IllegalStateException("rollmaterial needs coupling in front");
        } else if (!wagons.get(wagons.size() - 1).getBackCoupling()) {
            throw new IllegalStateException("last rollmaterial needs backcoupling");
        }
        if (lastIsPC && !(pc.getModel().equals(wagons.get(wagons.size() - 1).getModel()))) {
            throw new IllegalStateException("powered car needs to be of the same model");
        }
        lastIsPC = true;
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
