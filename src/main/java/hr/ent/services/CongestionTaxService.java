package hr.ent.services;

import hr.ent.beans.VehiclePass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongestionTaxService {

    public float calculateTax(List<VehiclePass> vehiclePasses) {
        return 0.0f;
    }
}
