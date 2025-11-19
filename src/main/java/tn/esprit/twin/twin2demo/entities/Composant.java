package tn.esprit.twin.twin2demo.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Composant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComposant;

    private String nomComposant;
    private Float prix;

    @ManyToOne
    private Menu menu;

    @OneToOne
    private DetailComposant detailComposant;

    // Getters et Setters
    public Long getIdComposant() { return idComposant; }
    public void setIdComposant(Long idComposant) { this.idComposant = idComposant; }
    public String getNomComposant() { return nomComposant; }
    public void setNomComposant(String nomComposant) { this.nomComposant = nomComposant; }
    public Float getPrix() { return prix; }
    public void setPrix(Float prix) { this.prix = prix; }
}