package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.twin.twin2demo.entities.Composant;

public interface ComposantRepository extends JpaRepository<Composant, Long> {
}
