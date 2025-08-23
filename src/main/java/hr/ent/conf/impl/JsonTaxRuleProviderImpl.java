package hr.ent.conf.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.ent.beans.TaxRule;
import hr.ent.conf.TaxRuleConfiguration;
import hr.ent.conf.TaxRuleProvider;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class JsonTaxRuleProviderImpl implements TaxRuleProvider {

    private final TaxRuleConfiguration config;

    public JsonTaxRuleProviderImpl(TaxRuleConfiguration config) {
        this.config = config;
    }

    private TaxRuleConfiguration loadConfig(String cofigJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            //tu dodam fileove koji se nalaze u resource config: /resources/config
            return mapper.readValue(new File(cofigJson), TaxRuleConfiguration.class);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load tax rules JSON config", e);
        }
    }

    @Override
    public String getCity() {
        return config.getCity();
    }

    @Override
    public int getMaxDailyFee() {
        return config.getMaxDailyFee();
    }

    @Override
    public List<TaxRule> getRules() {
        return config.getTaxRules();
    }

    @Override
    public Set<String> getExemptVehicles() {
        return config.getExemptVehicles();
    }
}
