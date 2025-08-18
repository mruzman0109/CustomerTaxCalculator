package hr.ent.controller;

import hr.ent.services.CongestionTaxService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tax")
public class CongestionTaxController {

    final CongestionTaxService congestionTaxService;

    public CongestionTaxController(CongestionTaxService congestionTaxService) {
        this.congestionTaxService = congestionTaxService;
    }
}
