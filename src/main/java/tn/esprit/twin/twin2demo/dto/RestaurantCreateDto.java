package tn.esprit.twin.twin2demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(name = "RestaurantCreateDto", description = "Payload de création/mise à jour d’un restaurant")
public class RestaurantCreateDto {

    @Schema(
            description = "Nom du restaurant",
            example = "Le Gourmet",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 150, message = "Le nom ne doit pas dépasser 150 caractères")
    public String nom;

    @Schema(
            description = "Nombre de places maximum",
            example = "120"
    )
    @Positive(message = "Le nombre de places doit être strictement positif")
    public Long nbPlacesMax;

    @Schema(
            description = "Date d’ouverture au format yyyy-MM-dd",
            example = "2024-01-10"
    )
    @Pattern(
            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
            message = "Le format de la date doit être yyyy-MM-dd"
    )
    public String datePremiereVitesse;

    @Schema(
            description = "Identifiant de la chaîne à laquelle rattacher le restaurant",
            example = "1"
    )
    public Long chaineId; // optionnel
}
