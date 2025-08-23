package hr.ent.conf.impl;

import hr.ent.beans.TaxConfig;
import hr.ent.beans.TaxRule;
import hr.ent.conf.TaxRuleProvider;
import hr.ent.conf.repository.TaxConfigRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@Profile("db")
public class DBTaxRuleProviderImpl implements TaxRuleProvider {

    private final TaxConfigRepository taxConfigRepository;
    private TaxConfig config;

    @Value("${tax.config.city}")
    String city;

    public DBTaxRuleProviderImpl(TaxConfigRepository taxConfigRepository) {
        this.taxConfigRepository = taxConfigRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadConfig() {
        this.config = taxConfigRepository.findByCity(city);
        if (this.config == null) {
            throw new IllegalArgumentException("Tax configuration for city '" + city + "' not found in DB.");
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
