package tn.esprit.twin.twin2demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.twin.twin2demo.entities.*;
import tn.esprit.twin.twin2demo.service.IMenuAdvancedService;

import java.util.Set;

@RestController
@RequestMapping("/api/advanced")
@Tag(name = "Advanced Services", description = "Services avancés Menu-Restaurant")
public class MenuAdvancedController {

    private final IMenuAdvancedService advancedService;

    @Autowired
    public MenuAdvancedController(IMenuAdvancedService advancedService) {
        this.advancedService = advancedService;
    }

    @Operation(summary = "Affecter un restaurant à une chaîne")
    @PostMapping("/affecter-restaurant-a-chaine")
    public Restaurant affecterRestaurantAChaine(
            @RequestParam String nomRestaurant,
            @RequestParam String libelleChaine) {
        return advancedService.affecterRestaurantAChaineRestauration(nomRestaurant, libelleChaine);
    }

    @Operation(summary = "Ajouter un restaurant avec ses menus (prix menus initialisé à 0)")
    @PostMapping("/ajout-restaurant-et-menus")
    public Restaurant ajoutRestaurantEtMenus(@RequestBody Restaurant restaurant) {
        return advancedService.ajoutRestaurantEtMenuAssocies(restaurant);
    }

    @Operation(summary = "Affecter un chef cuisinier à un menu")
    @PostMapping("/affecter-chef-a-menu")
    public ChefCuisinier affecterChefAMenu(@RequestParam Long idChefCuisinier, @RequestParam Long idMenu) {
        return advancedService.affecterChefCuisinierAMenu(idChefCuisinier, idMenu);
    }

    @Operation(summary = "Désaffecter un chef du menu")
    @PostMapping("/desaffecter-chef-du-menu")
    public ChefCuisinier desaffecterChefDuMenu(@RequestParam Long idMenu, @RequestParam Long idChefCuisinier) {
        return advancedService.desaffecterChefCuisinierDuMenu(idMenu, idChefCuisinier);
    }

    @Operation(summary = "Ajouter des composants au menu et mettre à jour le prix (cap 20.000)")
    @PostMapping("/ajout-composants-menu/{idMenu}")
    public Menu ajoutComposantsEtMajPrix(@PathVariable Long idMenu, @RequestBody Set<Composant> composants) {
        return advancedService.ajoutComposantsEtMiseAjourPrixMenu(composants, idMenu);
    }

    @Operation(summary = "Ajouter une commande et l’associer au client et au menu")
    @PostMapping("/ajout-commande")
    public void ajouterCommande(
            @RequestBody Commande commande,
            @RequestParam String identifiantClient,
            @RequestParam String libelleMenu) {
        advancedService.ajouterCommandeEtAffecterAClientEtMenu(commande, identifiantClient, libelleMenu);
    }
}
