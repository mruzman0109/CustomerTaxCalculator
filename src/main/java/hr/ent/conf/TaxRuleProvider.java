package hr.ent.conf;

import hr.ent.beans.TaxRule;

import java.util.List;
import java.util.Set;

public interface TaxRuleProvider {
    String getCity();
    int getMaxDailyFee();
    List<TaxRule> getRules();
    Set<String> getExemptVehicles();
}
