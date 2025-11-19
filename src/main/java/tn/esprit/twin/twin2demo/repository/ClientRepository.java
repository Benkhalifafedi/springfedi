package tn.esprit.twin.twin2demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.twin.twin2demo.entities.Client;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    // 🔹 Génère le prochain id = MAX(idClient) + 1 (si tu l'utilises dans ClientService)
    @Query("select coalesce(max(c.idClient), 0) + 1 from Client c")
    Long nextId();

    // 🔹 Utilisée dans MenuRestaurantServiceImpl (ajouterCommandeEtAffecterAClientEtMenu)
    Optional<Client> findByIdentifiant(String identifiant);
    // ⚠️ Ton entité Client doit avoir un champ "identifiant"
    // ex: private String identifiant;

    // 🔹 Utilisée dans ClientService.searchByNom(...)
    List<Client> findByNomContainingIgnoreCase(String keyword);
    // ⚠️ Si ton champ s'appelle "nomClient", renomme en:
    // List<Client> findByNomClientContainingIgnoreCase(String keyword);

    // 🔹 Utilisée dans ClientService.searchRegisteredAfter(...)
   // @Query("select c from Client c where c.dateInscription > :d")
    //List<Client> findRegisteredAfter(@Param("d") Date d);
    // ⚠️ Adapte 'dateInscription' au vrai nom de ton champ (dateInscription, dateCreation, etc.)

    // 🔹 Utilisée dans ClientService.searchByEmailDomain(...)
    List<Client> findByEmailLike(String domainLike);
    // ex: domainLike = "%@gmail.com"
}
