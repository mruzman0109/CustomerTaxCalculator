package hr.ent.beans;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TaxRule {
    private LocalTime startTime;
    private LocalTime endTime;
    private int amount;

    public TaxRule(LocalTime startTime, LocalTime endTime, int amount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
    }
}
