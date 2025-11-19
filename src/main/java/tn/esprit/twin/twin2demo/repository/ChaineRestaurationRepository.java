package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.twin.twin2demo.entities.ChaineRestauration;

import java.util.Optional;

public interface ChaineRestaurationRepository extends JpaRepository<ChaineRestauration, Long> {
    Optional<ChaineRestauration> findByLibelle(String libelle);
}
