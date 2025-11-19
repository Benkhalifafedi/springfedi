package tn.esprit.twin.twin2demo.service;

import tn.esprit.twin.twin2demo.entities.*;

import java.util.Set;

public interface IMenuAdvancedService {
    // 1) Affecter restaurant à une chaîne
    Restaurant affecterRestaurantAChaineRestauration(String nomRestaurant, String libelleChaine);

    // 2) Ajouter restaurant + menus associés (prix menu initialisé à 0)
    Restaurant ajoutRestaurantEtMenuAssocies(Restaurant restaurant);

    // 3) Affecter chef à menu
    ChefCuisinier affecterChefCuisinierAMenu(Long idChefCuisinier, Long idMenu);

    // 4) Désaffecter chef du menu
    ChefCuisinier desaffecterChefCuisinierDuMenu(Long idMenu, Long idChefCuisinier);

    // 5) Ajouter composants au menu et MAJ prix (cap 20.000)
    Menu ajoutComposantsEtMiseAjourPrixMenu(Set<Composant> composants, Long idMenu);

    // 6) Ajouter commande et l’affecter à client + menu (calculs totaux)
    void ajouterCommandeEtAffecterAClientEtMenu(Commande commande, String identifiantClient, String libelleMenu);
}
