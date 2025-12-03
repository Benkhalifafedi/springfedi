// src/main/java/tn/esprit/twin/twin2demo/entities/Commande.java
package tn.esprit.twin.twin2demo.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "commande")
public class Commande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_commande")
    private Long idCommande;

    @Column(name = "note")
    private Integer note;

    @Column(name = "total", precision = 18, scale = 3)
    private BigDecimal total;

    @Column(name = "total_remise", precision = 18, scale = 3)
    private BigDecimal totalRemise;

    // Pourcentage de remise [0..100]
    @Column(name = "remise_pourcentage", precision = 8, scale = 3)
    private BigDecimal remisePourcentage;

    @Column(name = "date_commande", nullable = false)
    private LocalDate dateCommande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu")
    private Menu menu;

    // Getters / Setters

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalRemise() {
        return totalRemise;
    }

    public void setTotalRemise(BigDecimal totalRemise) {
        this.totalRemise = totalRemise;
    }

    public BigDecimal getRemisePourcentage() {
        return remisePourcentage;
    }

    public void setRemisePourcentage(BigDecimal remisePourcentage) {
        this.remisePourcentage = remisePourcentage;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    // ✅ note en Integer partout
    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
