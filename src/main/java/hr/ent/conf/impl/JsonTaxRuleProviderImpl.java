package hr.ent.conf.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hr.ent.beans.TaxConfig;
import hr.ent.beans.TaxRule;
import hr.ent.conf.TaxRuleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Component
@Profile("json")
public class JsonTaxRuleProviderImpl implements TaxRuleProvider {

    private final TaxConfig config;

    public JsonTaxRuleProviderImpl(@Value("${tax.config.city}") String jsonFileName) {
        this.config = loadConfig(jsonFileName);
    }

    private TaxConfig loadConfig(String configFile) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            //tu dodam fileove koji se nalaze u resource config: /resources/config
            ClassPathResource resource = new ClassPathResource(configFile + ".json");
            return mapper.readValue(resource.getInputStream(), TaxConfig.class);
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
