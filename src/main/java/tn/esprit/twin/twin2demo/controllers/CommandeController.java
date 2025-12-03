package tn.esprit.twin.twin2demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.twin.twin2demo.dto.CommandeDto;
import tn.esprit.twin.twin2demo.entities.Commande;
import tn.esprit.twin.twin2demo.service.ICommandeService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/commandes")
@Tag(name = "Commandes", description = "Services pour la gestion des commandes")
public class CommandeController {

    @Autowired
    private ICommandeService commandeService;

    // ==========
    // ⚙️ Helper DTO
    // ==========
    private CommandeDto toDto(Commande c) {
        if (c == null) return null;
        return new CommandeDto(
                c.getDateCommande(),
                c.getTotal(),
                (c.getMenu() != null ? c.getMenu().getLibelle() : null),
                (c.getClient() != null ? c.getClient().getIdentifiant() : null)
        );
    }

    // ==========
    // 🔎 READ – Lister toutes les commandes (DTO)
    // ==========

    @Operation(summary = "Lister toutes les commandes (DTO)")
    @GetMapping
    public List<CommandeDto> getAllCommandesRoot() {
        return commandeService.retrieveAllCommandes()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // (alias /all, pratique pour les tests)
    @Operation(summary = "Lister toutes les commandes (DTO, debug)")
    @GetMapping("/all")
    public List<CommandeDto> getAllCommandes() {
        return commandeService.retrieveAllCommandes()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ==========
    // 🔎 READ – Récupérer une commande par id
    // ==========

    @Operation(summary = "Récupérer une commande par son id (DTO)")
    @GetMapping("/{idCommande}")
    public CommandeDto getCommandeById(@PathVariable Long idCommande) {
        Commande commande = commandeService.retrieveCommande(idCommande);
        return toDto(commande);
    }

    // ==========
    // ➕ CREATE – Ajouter une commande
    // ==========

    @Operation(summary = "Ajouter une commande")
    @PostMapping
    public CommandeDto addCommande(@RequestBody Commande commande) {
        Commande saved = commandeService.addCommande(commande);
        return toDto(saved);
    }

    // ==========
    // ✏️ UPDATE – Modifier une commande
    // ==========

    @Operation(summary = "Mettre à jour une commande")
    @PutMapping("/{idCommande}")
    public CommandeDto updateCommande(@PathVariable Long idCommande,
                                      @RequestBody Commande commande) {
        // on force l'id pour être sûr de mettre à jour la bonne ligne
        commande.setIdCommande(idCommande);
        Commande updated = commandeService.updateCommande(commande);
        return toDto(updated);
    }

    // ==========
    // ❌ DELETE – Supprimer une commande
    // ==========

    @Operation(summary = "Supprimer une commande")
    @DeleteMapping("/{idCommande}")
    public void deleteCommande(@PathVariable Long idCommande) {
        commandeService.removeCommande(idCommande);
    }

    // ==========
    // ✅ MÉTHODE DEMANDÉE PAR LE PROF
    // ==========

    @Operation(
            summary = "Liste des commandes par client et menu",
            description = "Retourne uniquement (dateCommande, montantCommande, libelleMenu, identifiantClient) " +
                    "pour un client et un menu donnés."
    )
    @GetMapping("/client-menu/{identifiant}/{libelleMenu}")
    public List<CommandeDto> getCommandesParClientEtMenu(
            @PathVariable String identifiant,
            @PathVariable String libelleMenu) {

        return commandeService.listeCommandesParClientEtMenu(identifiant, libelleMenu);
    }
}
