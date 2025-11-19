package tn.esprit.twin.twin2demo.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    // pas d'@GeneratedValue: on gère l'id côté service (MAX+1)
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "nom", length = 150, nullable = false)
    private String nom;

    @Column(name = "email", length = 180, unique = true)
    private String email;

    @Column(name = "identifiant", length = 100)
    private String identifiant;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "date_premiere_vitesse")
    private Date datePremiereVitesse;

    public Client() {}

    public Client(Long idClient, String nom, String email, String identifiant, Date datePremiereVitesse) {
        this.idClient = idClient;
        this.nom = nom;
        this.email = email;
        this.identifiant = identifiant;
        this.datePremiereVitesse = datePremiereVitesse;
    }

    public Long getIdClient() { return idClient; }
    public void setIdClient(Long idClient) { this.idClient = idClient; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getIdentifiant() { return identifiant; }
    public void setIdentifiant(String identifiant) { this.identifiant = identifiant; }
    public Date getDatePremiereVitesse() { return datePremiereVitesse; }
    public void setDatePremiereVitesse(Date datePremiereVitesse) { this.datePremiereVitesse = datePremiereVitesse; }
}
