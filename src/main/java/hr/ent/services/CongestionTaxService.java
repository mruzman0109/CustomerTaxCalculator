package hr.ent.services;

import hr.ent.beans.TaxRule;
import hr.ent.beans.VehiclePass;
import hr.ent.conf.TaxRuleProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

@Service
public class CongestionTaxService {

    private final TaxRuleProvider taxRuleProvider;

    public CongestionTaxService(TaxRuleProvider taxRuleProvider) {
        this.taxRuleProvider = taxRuleProvider;
    }

    public float calculateTax(List<VehiclePass> vehiclePasses) {
        if (vehiclePasses.isEmpty()) return 0;
        List<LocalDateTime> sortedPasses = vehiclePasses.stream()
                .filter(pass -> !taxRuleProvider.getExemptVehicles().contains(pass.getVehicleType()))
                .flatMap(pass -> pass.getPassTime().stream())
                .filter(passTime -> !isExemptDate.test(passTime.toLocalDate()))  // koristimo Predicate lambda
                //.filter(passTime -> passTime.getYear() == 2013) // validacija datuma - da je u 2013. - ako da onda je potrebno popraviti i testove!...
                .sorted()
                .toList();
        float totalFee = 0;
        LocalDateTime windowStart = null;
        float windowMaxFee = 0;
        for (VehiclePass pass : vehiclePasses) {
            String vehicleType = pass.getVehicleType();
            if (taxRuleProvider.getExemptVehicles().contains(vehicleType)) continue;
    //      List<LocalDateTime> sortedTimes = pass.getPassTime().stream().sorted().toList();
            for (LocalDateTime passTime : sortedPasses) {
                //if (isExemptDate(passTime.toLocalDate())) continue;
                float fee = getFeeForTime(passTime.toLocalTime());
                if (windowStart == null || passTime.isAfter(windowStart.plusMinutes(60))) {
                    totalFee += windowMaxFee;
                    windowMaxFee = fee;
                    windowStart = passTime;
                } else {
                    windowMaxFee = Math.max(windowMaxFee, fee);
                }
            }

        }
        totalFee += windowMaxFee;
        return Math.min(totalFee, taxRuleProvider.getMaxDailyFee());
    }

    private final Predicate<LocalDate> isExemptDate =
            date -> date.getDayOfWeek().getValue() >= 6 || date.getMonthValue() == 7;

    private int getFeeForTime(LocalTime time) {
        for (TaxRule rule : taxRuleProvider.getRules()) {
            if (!rule.getStart().isAfter(time) && !rule.getEnd().isBefore(time)) {
                return rule.getAmount();
            }
        }
        return 0;
    }
}
