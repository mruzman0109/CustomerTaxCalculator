package hr.ent.conf.impl;

import hr.ent.beans.TaxRule;
import hr.ent.conf.TaxRuleProvider;

import java.util.List;
import java.util.Set;


public class DBTaxRuleProviderImpl implements TaxRuleProvider {
    @Override
    public String getCity() {
        return "";
    }

    @Override
    public int getMaxDailyFee() {
        return 0;
    }

    @Override
    public List<TaxRule> getRules() {
        return List.of();
    }

    @Override
    public Set<String> getExemptVehicles() {
        return Set.of();
    }
}
