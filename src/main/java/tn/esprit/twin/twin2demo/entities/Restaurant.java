package tn.esprit.twin.twin2demo.entities;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
public class Restaurant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_restaurant")
    private Long idRestaurant;

    @Column(length = 150, nullable = false)
    private String nom;

    @Column(name = "nb_places_max")
    private Long nbPlacesMax;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_premiere_vitesse")
    private Date datePremiereVitesse;

    // ✅ UNE SEULE association vers la chaîne, une seule colonne id_chaine
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chaine", foreignKey = @ForeignKey(name = "fk_restaurant_chaine"))
    private ChaineRestauration chaineRestauration;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<Menu> menus = new HashSet<>();

    // getters/setters ...
    public Long getIdRestaurant() { return idRestaurant; }
    public void setIdRestaurant(Long idRestaurant) { this.idRestaurant = idRestaurant; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public Long getNbPlacesMax() { return nbPlacesMax; }
    public void setNbPlacesMax(Long nbPlacesMax) { this.nbPlacesMax = nbPlacesMax; }
    public Date getDatePremiereVitesse() { return datePremiereVitesse; }
    public void setDatePremiereVitesse(Date datePremiereVitesse) { this.datePremiereVitesse = datePremiereVitesse; }
    public ChaineRestauration getChaineRestauration() { return chaineRestauration; }
    public void setChaineRestauration(ChaineRestauration chaineRestauration) { this.chaineRestauration = chaineRestauration; }
    public Set<Menu> getMenus() { return menus; }
    public void setMenus(Set<Menu> menus) { this.menus = menus; }
}
