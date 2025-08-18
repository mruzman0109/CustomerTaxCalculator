package hr.ent.services;

import hr.ent.beans.TaxRule;
import hr.ent.beans.VehiclePass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class CongestionTaxService {

    private final List<TaxRule> rules = loadRules();
    private final int maxDailyFee = 60;
    private final Set<String> exemptVehicles = Set.of("Emergency", "Bus", "Diplomat", "Motorcycle", "Military", "Foreign");

    public float calculateTax(List<VehiclePass> vehiclePasses) {
        if (vehiclePasses.isEmpty()) return 0;

        vehiclePasses.sort(Comparator.comparing(VehiclePass::getPassTime));

        String vehicleType = vehiclePasses.get(0).getVehicleType();
        if (exemptVehicles.contains(vehicleType)) return 0;
        return 0.0f;
    }

    private List<TaxRule> loadRules() {
        return new ArrayList<>();
    }
}
