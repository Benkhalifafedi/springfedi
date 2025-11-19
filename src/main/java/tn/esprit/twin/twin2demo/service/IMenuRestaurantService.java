package tn.esprit.twin.twin2demo.service;

import tn.esprit.twin.twin2demo.entities.*;

import java.util.Set;

public interface IMenuRestaurantService {

    // 1) Affecter un restaurant à une chaîne
    Restaurant affecterRestaurantAChaineRestauration(String nomRestaurant, String libelleChaine);

    // 2) Ajouter un restaurant avec ses menus associés
    Restaurant ajoutRestaurantEtMenuAssocies(Restaurant restaurant);

    // 3) Affecter un chef cuisinier à un menu
    ChefCuisinier affecterChefCuisinierAMenu(Long idChefCuisinier, Long idMenu);

    // 4) Désaffecter un chef cuisinier d’un menu
    ChefCuisinier desaffecterChefCuisinierDuMenu(Long idMenu, Long idChefCuisinier);

    // 5) Ajouter composants + mise à jour du prix du menu
    Menu ajoutComposantsEtMiseAjourPrixMenu(Set<Composant> composants, Long idMenu);

    // 6) Ajouter commande + affecter à client et menu
    void ajouterCommandeEtAffecterAClientEtMenu(Commande commande, String identifiant, String libelleMenu);
}
