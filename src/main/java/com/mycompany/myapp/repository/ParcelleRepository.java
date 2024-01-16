package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Parcelle;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Parcelle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ParcelleRepository extends JpaRepository<Parcelle, Long> {
    @Query("SELECT p.fermeLibelle.fermeLibelle FROM Parcelle p WHERE p.id = :ParcelleId")
    String findFermeParParcelleId(@Param("ParcelleId") Long parcelleId);
}
