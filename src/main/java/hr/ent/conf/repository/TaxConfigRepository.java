package hr.ent.conf.repository;

import hr.ent.beans.TaxConfig;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxConfigRepository extends JpaRepository<TaxConfig, Long> {
    @EntityGraph(attributePaths = {"taxRules", "exemptVehicles"})
    TaxConfig findByCity(String city);
}

