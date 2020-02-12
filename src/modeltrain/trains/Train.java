package modeltrain.trains;

import java.util.List;
import java.util.ArrayList;

public class Train {
    
    private final int id;
    private List<RollMaterial> wagons;
    
    public Train(int id) {
        this.id = id;
        wagons = new ArrayList<>();
    }
    
    public void add(Locomotive lo) {
        
    }
    
    public void add(Coach w) {
        if(wagons.get(wagons.size() - 1).getBackCoupling() == false) {
           throw new IllegalStateException("last wagon needs a back coupler");
        }
    }
    
    public void add(PoweredCart pc) {
        
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Train))
            return false;
        if (o.hashCode() == this.hashCode())
            return true;
        return false;
    }
    
}
