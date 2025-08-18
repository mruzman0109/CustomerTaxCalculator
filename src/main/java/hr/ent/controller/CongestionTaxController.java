package hr.ent.controller;

import hr.ent.beans.VehiclePass;
import hr.ent.services.CongestionTaxService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tax")
public class CongestionTaxController {

    final CongestionTaxService congestionTaxService;

    public CongestionTaxController(CongestionTaxService congestionTaxService) {
        this.congestionTaxService = congestionTaxService;
    }

    @PostMapping("/calculate")
    public float calculateTax(@RequestBody List<VehiclePass> passes) {
        return congestionTaxService.calculateTax(passes);
    }
}
