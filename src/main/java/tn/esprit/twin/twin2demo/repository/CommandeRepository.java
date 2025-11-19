package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.twin.twin2demo.entities.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
