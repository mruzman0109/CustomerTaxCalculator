package hr.ent.beans;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VehiclePass {
    private String vehicleType;
    private List<LocalDateTime> passTime;

    public VehiclePass() {}

    public VehiclePass(String vehicleType, List<LocalDateTime> passTime) {
        this.vehicleType = vehicleType;
        this.passTime = passTime;
    }
}
