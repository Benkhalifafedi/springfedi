package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.twin2demo.entities.Commande;

import java.time.LocalDate;
import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByDateCommande(LocalDate dateCommande);

    List<Commande> findByDateCommandeBetweenOrderByNote(LocalDate dateCommandeAfter, LocalDate dateCommandeBefore);

    // ✅ Requête explicite : client + menu
    @Query("SELECT c FROM Commande c " +
            "WHERE c.client.identifiant = :identifiant " +
            "AND c.menu.libelle = :libelleMenu")
    List<Commande> chercherParClientEtMenu(@Param("identifiant") String identifiant,
                                           @Param("libelleMenu") String libelleMenu);
}
