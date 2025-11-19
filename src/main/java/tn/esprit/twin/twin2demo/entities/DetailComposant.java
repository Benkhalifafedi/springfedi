package tn.esprit.twin.twin2demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class DetailComposant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetailComposant;

    private Float inc;
    private String typeComposant;

    // Getters et Setters
    public Long getIdDetailComposant() { return idDetailComposant; }
    public void setIdDetailComposant(Long idDetailComposant) { this.idDetailComposant = idDetailComposant; }
    public Float getInc() { return inc; }
    public void setInc(Float inc) { this.inc = inc; }
    public String getTypeComposant() { return typeComposant; }
    public void setTypeComposant(String typeComposant) { this.typeComposant = typeComposant; }
}