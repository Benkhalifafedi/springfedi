package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.twin.twin2demo.entities.Menu;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    Optional<Menu> findByLibelle(String libelle);
}
