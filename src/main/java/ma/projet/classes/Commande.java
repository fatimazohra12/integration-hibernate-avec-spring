package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> ligneCommandeProduits = new ArrayList<>();

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<LigneCommandeProduit> getLigneCommandeProduits() {
        return ligneCommandeProduits;
    }

    public void addLigneCommandeProduit(LigneCommandeProduit ligneCommandeProduit) {
        ligneCommandeProduits.add(ligneCommandeProduit);
        ligneCommandeProduit.setCommande(this); // Set the reverse relationship
    }
}
