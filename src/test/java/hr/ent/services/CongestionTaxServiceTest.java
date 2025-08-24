package hr.ent.services;

import hr.ent.beans.TaxRule;
import hr.ent.beans.VehiclePass;
import hr.ent.conf.TaxRuleProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CongestionTaxServiceTest {

    private TaxRuleProvider taxRuleProvider;
    private CongestionTaxService congestionTaxService;

    @BeforeEach
    void setUp() {
        taxRuleProvider = Mockito.mock(TaxRuleProvider.class);
        congestionTaxService = new CongestionTaxService(taxRuleProvider);
    }

    @Test
    void testCalculateTax_NoVehiclePasses_ReturnsZero() {
        float tax = congestionTaxService.calculateTax(Collections.emptyList());
        assertEquals(0, tax);
    }

    @Test
    void testCalculateTax_ExemptVehicle_ReturnsZero() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Motorbike");
        pass.setPassTime(List.of(LocalDateTime.of(2025, 8, 25, 8, 0)));

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of("Motorbike"));

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(0, tax);
    }

    @Test
    void testCalculateTax_ExemptDate_ReturnsZero() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Car");
        // Saturday, which is exempt
        pass.setPassTime(List.of(LocalDateTime.of(2025, 8, 24, 8, 0)));

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of());
        when(taxRuleProvider.getRules()).thenReturn(List.of(new TaxRule(LocalTime.of(7, 0), LocalTime.of(9, 0), 10)));
        when(taxRuleProvider.getMaxDailyFee()).thenReturn(60);

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(0, tax);
    }

    @Test
    void testCalculateTax_SinglePassWithinFeeWindow() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Car");
        pass.setPassTime(List.of(LocalDateTime.of(2025, 8, 25, 8, 0)));

        TaxRule rule = new TaxRule(LocalTime.of(7, 0), LocalTime.of(9, 0), 10);

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of());
        when(taxRuleProvider.getRules()).thenReturn(List.of(rule));
        when(taxRuleProvider.getMaxDailyFee()).thenReturn(60);

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(10, tax);
    }

    @Test
    void testCalculateTax_MultiplePassesWithinSameHour() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Car");
        pass.setPassTime(Arrays.asList(
                LocalDateTime.of(2025, 8, 25, 8, 0),
                LocalDateTime.of(2025, 8, 25, 8, 30)
        ));

        TaxRule rule = new TaxRule(LocalTime.of(7, 0), LocalTime.of(9, 0), 10);

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of());
        when(taxRuleProvider.getRules()).thenReturn(List.of(rule));
        when(taxRuleProvider.getMaxDailyFee()).thenReturn(60);

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(10, tax); // only max in window counts
    }

    @Test
    void testCalculateTax_MultiplePassesDifferentHours() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Car");
        pass.setPassTime(Arrays.asList(
                LocalDateTime.of(2025, 8, 25, 8, 0),
                LocalDateTime.of(2025, 8, 25, 9, 30)
        ));

        TaxRule rule = new TaxRule(LocalTime.of(7, 0), LocalTime.of(10, 0), 10);

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of());
        when(taxRuleProvider.getRules()).thenReturn(List.of(rule));
        when(taxRuleProvider.getMaxDailyFee()).thenReturn(60);

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(20, tax); // sum of two separate windows
    }

    @Test
    void testCalculateTax_MaxDailyFeeLimit() {
        VehiclePass pass = new VehiclePass();
        pass.setVehicleType("Car");
        // 10 passes, each 10 fee
        List<LocalDateTime> passTimes = Arrays.asList(
                LocalDateTime.of(2025, 8, 25, 7, 0),
                LocalDateTime.of(2025, 8, 25, 8, 1),
                LocalDateTime.of(2025, 8, 25, 9, 2),
                LocalDateTime.of(2025, 8, 25, 10, 3),
                LocalDateTime.of(2025, 8, 25, 13, 4)
        );
        pass.setPassTime(passTimes);

        TaxRule rule = new TaxRule(LocalTime.of(7, 0), LocalTime.of(12, 0), 20);

        when(taxRuleProvider.getExemptVehicles()).thenReturn(Set.of());
        when(taxRuleProvider.getRules()).thenReturn(List.of(rule));
        when(taxRuleProvider.getMaxDailyFee()).thenReturn(60);

        float tax = congestionTaxService.calculateTax(List.of(pass));
        assertEquals(60, tax); // should cap at maxDailyFee
    }
}
