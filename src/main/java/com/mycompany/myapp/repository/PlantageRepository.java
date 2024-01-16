package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Plantage;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Plantage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantageRepository extends JpaRepository<Plantage, Long> {
    @Query("SELECT p.planteLibelle.planteLibelle FROM Plantage p WHERE p.id = :PlantageId")
    String findFermeParParcelleId(@Param("PlantageId") Long plantageId);

    @Query("SELECT p.parcelleLibelle.parcelleLibelle FROM Plantage p WHERE p.id = :PlantageId")
    String findParcellePlantageId(@Param("PlantageId") Long plantageId);
}
