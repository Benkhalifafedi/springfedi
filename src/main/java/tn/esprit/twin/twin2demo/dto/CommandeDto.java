package tn.esprit.twin.twin2demo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO pour exposer les infos d'une commande :
 *  - date commande
 *  - montantCommande (on utilise le champ total de l'entité Commande)
 *  - libelle du menu
 *  - identifiant du client
 */
public class CommandeDto {

    private LocalDate dateCommande;
    private BigDecimal montantCommande;
    private String libelleMenu;
    private String identifiantClient;

    public CommandeDto() {
    }

    public CommandeDto(LocalDate dateCommande,
                       BigDecimal montantCommande,
                       String libelleMenu,
                       String identifiantClient) {
        this.dateCommande = dateCommande;
        this.montantCommande = montantCommande;
        this.libelleMenu = libelleMenu;
        this.identifiantClient = identifiantClient;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getMontantCommande() {
        return montantCommande;
    }

    public void setMontantCommande(BigDecimal montantCommande) {
        this.montantCommande = montantCommande;
    }

    public String getLibelleMenu() {
        return libelleMenu;
    }

    public void setLibelleMenu(String libelleMenu) {
        this.libelleMenu = libelleMenu;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }
}
