package tn.esprit.twin.twin2demo.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ChefCuisinier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChefCuisinier;

    private String nom;
    private String prenom;
    private String typeChef;

    @ManyToMany
    @JoinTable(
            name = "chef_menu",
            joinColumns = @JoinColumn(name = "id_chef"),
            inverseJoinColumns = @JoinColumn(name = "id_menu")
    )
    private List<Menu> menus;



    // Getters et Setters
    public Long getIdChefCuisinier() { return idChefCuisinier; }
    public void setIdChefCuisinier(Long idChefCuisinier) { this.idChefCuisinier = idChefCuisinier; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getTypeChef() { return typeChef; }
    public void setTypeChef(String typeChef) { this.typeChef = typeChef; }
}