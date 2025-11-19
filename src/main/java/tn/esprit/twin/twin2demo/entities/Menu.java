// src/main/java/tn/esprit/twin/twin2demo/entities/Menu.java
package tn.esprit.twin.twin2demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long idMenu;

    @Column(length = 150, nullable = false)
    private String libelle;

    @Column(name = "prix_total", precision = 18, scale = 3)
    private BigDecimal prixTotal;

    // ✅ le champ attendu par la méthode du repository
    @Enumerated(EnumType.STRING)
    @Column(name = "type_menu", length = 30, nullable = false)
    private TypeMenu typeMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurant")
    @JsonIgnore
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chef")
    private ChefCuisinier chefCuisinier;

    // Composants associés
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "menu_composant",
            joinColumns = @JoinColumn(name = "id_menu"),
            inverseJoinColumns = @JoinColumn(name = "id_composant")
    )
    private Set<Composant> composants = new HashSet<>();

    // --- getters/setters ---
    public Long getIdMenu() { return idMenu; }
    public void setIdMenu(Long idMenu) { this.idMenu = idMenu; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getPrixTotal() { return prixTotal; }
    public void setPrixTotal(BigDecimal prixTotal) { this.prixTotal = prixTotal; }

    public TypeMenu getTypeMenu() { return typeMenu; }
    public void setTypeMenu(TypeMenu typeMenu) { this.typeMenu = typeMenu; }

    public Restaurant getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }

    public ChefCuisinier getChefCuisinier() { return chefCuisinier; }
    public void setChefCuisinier(ChefCuisinier chefCuisinier) { this.chefCuisinier = chefCuisinier; }

    public Set<Composant> getComposants() { return composants; }
    public void setComposants(Set<Composant> composants) { this.composants = composants; }
}
