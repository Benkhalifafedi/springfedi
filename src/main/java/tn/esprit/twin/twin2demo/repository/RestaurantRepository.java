// src/main/java/tn/esprit/twin/twin2demo/repository/RestaurantRepository.java
package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.twin.twin2demo.entities.Restaurant;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // 🔹 Trouver un restaurant par son nom (champ "nom" dans l'entité Restaurant)
    Optional<Restaurant> findByNom(String nom);

    // 🔹 Recherche partielle insensible à la casse
    List<Restaurant> findByNomContainingIgnoreCase(String keyword);

    // 🔹 Nombre de places max
    List<Restaurant> findByNbPlacesMaxGreaterThan(Long nbPlacesMax);

    // 🔹 Par chaîne de restauration
    List<Restaurant> findByChaineRestauration_IdChaine(Long idChaineRestauration);

    // 🔹 Par date de première vitesse (si ce champ existe dans ton entité)
    List<Restaurant> findByDatePremiereVitesseAfter(Date date);

    // 🔹 Combinaison nom + nbPlacesMax
    List<Restaurant> findByNomAndNbPlacesMax(String nom, Long nbPlacesMax);
}
