package tn.esprit.twin.twin2demo.service;

import tn.esprit.twin.twin2demo.dto.CommandeDto;
import tn.esprit.twin.twin2demo.entities.Commande;

import java.util.List;

public interface ICommandeService {

    List<Commande> retrieveAllCommandes();
    Commande addCommande(Commande commande);
    Commande updateCommande(Commande commande);
    Commande retrieveCommande(Long idCommande);
    void removeCommande(Long idCommande);

    // ✅ Méthode EXACTEMENT comme demandée par le prof
    List<CommandeDto> listeCommandesParClientEtMenu(String identifiant, String libelleMenu);
}
