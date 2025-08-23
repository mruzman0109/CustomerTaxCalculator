package hr.ent.beans;

import hr.ent.conf.TaxRuleProvider;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tax_config")
@Data
public class TaxConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private int maxDailyFee;

    // Assuming relationship with TaxRule
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tax_config_id")
    private List<TaxRule> taxRules;

    @ElementCollection
    @CollectionTable(name = "exempt_vehicles", joinColumns = @JoinColumn(name = "tax_config_id"))
    @Column(name = "name")
    private Set<String> exemptVehicles;
}
