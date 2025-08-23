package hr.ent.beans;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "tax_rules")
@Data
public class TaxRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")  // valid identifier
    private LocalTime start;

    @Column(name = "end_time")    // valid identifier
    private LocalTime end;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "tax_config_id")
    private TaxConfig taxConfig;

    public TaxRule() {}

    public TaxRule(LocalTime start, LocalTime end, int amount) {
        this.start = start;
        this.end = end;
        this.amount = amount;
    }
}
