package hr.ent.services;

import hr.ent.beans.TaxRule;
import hr.ent.beans.VehiclePass;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        float totalFee = 0;
        LocalDateTime windowStart = null;
        float windowMaxFee = 0;

        for (VehiclePass pass : vehiclePasses) {
            if (isExemptDate(pass.getPassTime().toLocalDate())) continue;

            float fee = getFeeForTime(pass.getPassTime().toLocalTime());
            if (windowStart == null || pass.getPassTime().isAfter(windowStart.plusMinutes(60))) {
                totalFee += windowMaxFee;
                windowMaxFee = fee;
                windowStart = pass.getPassTime();
            } else {
                windowMaxFee = Math.max(windowMaxFee, fee);
            }
        }
        totalFee += windowMaxFee;

        return Math.min(totalFee, maxDailyFee);
    }

    private List<TaxRule> loadRules() {

        return new ArrayList<>();
    }

    private boolean isExemptDate(LocalDate date) {
        return date.getDayOfWeek().getValue() >= 6 || date.getMonthValue() == 7;
    }

    private int getFeeForTime(LocalTime time) {
        for (TaxRule rule : rules) {
            if (!rule.getStartTime().isAfter(time) && !rule.getEndTime().isBefore(time)) {
                return rule.getAmount();
            }
        }
        return 0;
    }
}
