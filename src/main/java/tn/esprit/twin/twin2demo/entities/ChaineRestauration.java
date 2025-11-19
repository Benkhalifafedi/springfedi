// src/main/java/tn/esprit/twin/twin2demo/entities/ChaineRestauration.java
package tn.esprit.twin.twin2demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chaine_restauration")
public class ChaineRestauration implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_chaine_restauration") // ✅ correspond exactement à la colonne de la BDD
    private Long idChaine;

    // Champ exposé à l’API (et colonne 'libelle' en BDD)
    @Column(name = "libelle", length = 150, nullable = false, unique = true)
    private String libelle;

    // Deuxième colonne existante dans la DB (remplie automatiquement)
    @Column(name = "nom", length = 150, nullable = false, unique = true)
    @JsonIgnore // on ne l’expose pas, on la pilote en interne
    private String nom;

    @OneToMany(
            mappedBy = "chaineRestauration",
            cascade = CascadeType.ALL,
            orphanRemoval = false,
            fetch = FetchType.LAZY
    )
    private Set<Restaurant> restaurants = new HashSet<>();

    // --- Sync automatiques avant insert/update ---
    @PrePersist
    @PreUpdate
    private void syncNomLibelle() {
        // Si libelle est vide mais nom existe → on copie nom → libelle
        if ((libelle == null || libelle.isBlank()) && nom != null) {
            libelle = nom;
        }
        // Si nom est vide mais libelle existe → on copie libelle → nom
        if ((nom == null || nom.isBlank()) && libelle != null) {
            nom = libelle;
        }
    }

    // --- Getters / Setters ---

    public Long getIdChaine() {
        return idChaine;
    }

    public void setIdChaine(Long idChaine) {
        this.idChaine = idChaine;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
