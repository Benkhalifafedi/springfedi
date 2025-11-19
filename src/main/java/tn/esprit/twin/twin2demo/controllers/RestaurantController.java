package tn.esprit.twin.twin2demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import tn.esprit.twin.twin2demo.dto.RestaurantCreateDto;
import tn.esprit.twin.twin2demo.entities.ChaineRestauration;
import tn.esprit.twin.twin2demo.entities.Restaurant;
import tn.esprit.twin.twin2demo.repository.ChaineRestaurationRepository;
import tn.esprit.twin.twin2demo.service.IRestaurantService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@Tag(name = "Restaurants", description = "Services REST pour gérer les restaurants")
public class RestaurantController {

    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private ChaineRestaurationRepository chaineRepo;

    @Operation(
            summary = "Lister tous les restaurants",
            description = "Retourne la liste complète des restaurants"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Restaurant.class))
            ))
    })
    @GetMapping
    public List<Restaurant> all() {
        return restaurantService.retrieveAllRestaurants();
    }

    @Operation(
            summary = "Récupérer un restaurant par id",
            parameters = {
                    @Parameter(name = "id", description = "Identifiant du restaurant", example = "1")
            }
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Restaurant introuvable")
    })
    @GetMapping("/{id}")
    public Restaurant one(@PathVariable Long id) {
        return restaurantService.retrieveRestaurantById(id);
    }

    @Operation(
            summary = "Créer un restaurant",
            requestBody = @RequestBody(
                    required = true,
                    description = "DTO de création de restaurant",
                    content = @Content(
                            schema = @Schema(implementation = RestaurantCreateDto.class),
                            examples = @ExampleObject(name = "Exemple création",
                                    value = """
                    {
                      "nom": "Le Gourmet",
                      "nbPlacesMax": 120,
                      "datePremiereVitesse": "2024-01-10",
                      "chaineId": 1
                    }
                    """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Créé", content = @Content(
                    schema = @Schema(implementation = Restaurant.class)
            )),
            @ApiResponse(responseCode = "400", description = "Requête invalide (nom manquant, chaine inexistante, date invalide)", content = @Content)
    })
    @PostMapping
    public Restaurant create(@org.springframework.web.bind.annotation.RequestBody RestaurantCreateDto dto) throws Exception {
        if (dto.nom == null || dto.nom.isBlank())
            throw new IllegalArgumentException("Le champ 'nom' est obligatoire.");

        Restaurant r = new Restaurant();
        r.setNom(dto.nom);
        r.setNbPlacesMax(dto.nbPlacesMax);

        if (dto.datePremiereVitesse != null && !dto.datePremiereVitesse.isBlank()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dto.datePremiereVitesse);
            r.setDatePremiereVitesse(d);
        }

        if (dto.chaineId != null) {
            ChaineRestauration ch = chaineRepo.findById(dto.chaineId)
                    .orElseThrow(() -> new IllegalArgumentException("ChaineRestauration introuvable id=" + dto.chaineId));
            r.setChaineRestauration(ch);
        }

        return restaurantService.addRestaurant(r);
    }

    @Operation(
            summary = "Mettre à jour un restaurant",
            parameters = {
                    @Parameter(name = "id", description = "Identifiant du restaurant", example = "1")
            },
            requestBody = @RequestBody(
                    required = true,
                    description = "DTO de mise à jour",
                    content = @Content(schema = @Schema(implementation = RestaurantCreateDto.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mis à jour", content = @Content(
                    schema = @Schema(implementation = Restaurant.class)
            )),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content)
    })
    @PutMapping("/{id}")
    public Restaurant update(@PathVariable Long id,
                             @org.springframework.web.bind.annotation.RequestBody RestaurantCreateDto dto) throws Exception {
        Restaurant exists = restaurantService.retrieveRestaurantById(id);
        if (exists == null) throw new IllegalArgumentException("Restaurant introuvable id=" + id);

        if (dto.nom != null) exists.setNom(dto.nom);
        if (dto.nbPlacesMax != null) exists.setNbPlacesMax(dto.nbPlacesMax);
        if (dto.datePremiereVitesse != null && !dto.datePremiereVitesse.isBlank()) {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(dto.datePremiereVitesse);
            exists.setDatePremiereVitesse(d);
        }
        if (dto.chaineId != null) {
            ChaineRestauration ch = chaineRepo.findById(dto.chaineId)
                    .orElseThrow(() -> new IllegalArgumentException("ChaineRestauration introuvable id=" + dto.chaineId));
            exists.setChaineRestauration(ch);
        }
        return restaurantService.updateRestaurant(exists);
    }

    @Operation(
            summary = "Supprimer un restaurant",
            parameters = @Parameter(name = "id", description = "Identifiant du restaurant", example = "1")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Supprimé"),
            @ApiResponse(responseCode = "404", description = "Restaurant introuvable")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        restaurantService.removeRestaurant(id);
    }

    // ---------- Recherches ----------

    @Operation(
            summary = "Rechercher par nom (contains, case-sensitive selon config DB)",
            parameters = @Parameter(name = "q", description = "Mot-clé du nom", example = "gour")
    )
    @GetMapping("/search/nom")
    public List<Restaurant> findByNom(@RequestParam String q) {
        return restaurantService.findByNomContaining(q);
    }

    @Operation(
            summary = "Rechercher par nombre de places minimum",
            parameters = @Parameter(name = "min", description = "Seuil minimum", example = "100")
    )
    @GetMapping("/search/places-min")
    public List<Restaurant> findByNbPlacesMin(@RequestParam Long min) {
        return restaurantService.findByNbPlacesMaxGreaterThan(min);
    }

    @Operation(
            summary = "Rechercher par chaîne",
            parameters = @Parameter(name = "idChaine", description = "Identifiant chaîne", example = "1")
    )
    @GetMapping("/search/chaine/{idChaine}")
    public List<Restaurant> findByChaine(@PathVariable Long idChaine) {
        return restaurantService.findByChaineRestaurationIdChaine(idChaine);
    }

    @Operation(
            summary = "Rechercher après une date (yyyy-MM-dd)",
            parameters = @Parameter(name = "date", description = "Date min", example = "2024-01-01")
    )
    @GetMapping("/search/after")
    public List<Restaurant> findAfter(@RequestParam String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return restaurantService.findByDatePremiereVitesseAfter(d);
        } catch (Exception e) {
            throw new IllegalArgumentException("Format date attendu yyyy-MM-dd");
        }
    }

    @Operation(
            summary = "Rechercher par nom exact + nbPlacesMax exact",
            parameters = {
                    @Parameter(name = "nom", description = "Nom exact", example = "Le Gourmet"),
                    @Parameter(name = "nbPlacesMax", description = "Capacité exacte", example = "150")
            }
    )
    @GetMapping("/search/nom-places")
    public List<Restaurant> findByNomAndPlaces(@RequestParam String nom,
                                               @RequestParam Long nbPlacesMax) {
        return restaurantService.findByNomAndNbPlacesMax(nom, nbPlacesMax);
    }
}
