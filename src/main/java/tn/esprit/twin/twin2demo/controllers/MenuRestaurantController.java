package tn.esprit.twin.twin2demo.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.twin.twin2demo.entities.Restaurant;
import tn.esprit.twin.twin2demo.service.IMenuRestaurantService;

@RestController
@RequestMapping("/api/menu")
public class MenuRestaurantController {

    private final IMenuRestaurantService menuRestaurantService;

    // ✅ Constructeur explicite qui initialise le champ final
    public MenuRestaurantController(IMenuRestaurantService menuRestaurantService) {
        this.menuRestaurantService = menuRestaurantService;
    }

    @PostMapping("/affecter-restaurant-chaine")
    public Restaurant affecterRestaurantAChaine(@RequestParam String nomRestaurant,
                                                @RequestParam String libelleChaine) {
        return menuRestaurantService.affecterRestaurantAChaineRestauration(nomRestaurant, libelleChaine);
    }

    // tu pourras ajouter les autres endpoints ici
}
