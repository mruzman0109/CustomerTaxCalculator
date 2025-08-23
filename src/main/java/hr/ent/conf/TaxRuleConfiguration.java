package hr.ent.conf;

import hr.ent.beans.TaxRule;

import java.util.List;
import java.util.Set;

public class TaxRuleConfiguration {
    private String city;
    private int maxDailyFee;
    private List<TaxRule> taxRules;
    private Set<String> exemptVehicles;

    // getters and setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getMaxDailyFee() { return maxDailyFee; }
    public void setMaxDailyFee(int maxDailyFee) { this.maxDailyFee = maxDailyFee; }

    public List<TaxRule> getTaxRules() { return taxRules; }
    public void setTaxRules(List<TaxRule> taxRules) { this.taxRules = taxRules; }

    public Set<String> getExemptVehicles() { return exemptVehicles; }
    public void setExemptVehicles(Set<String> exemptVehicles) { this.exemptVehicles = exemptVehicles; }

}
