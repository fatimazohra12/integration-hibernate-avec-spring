package ma.projet.classes;

import javax.persistence.*;

@Entity
@NamedQuery(name = "Produit.findByPriceGreaterThan", query = "FROM Produit p WHERE p.prix > :price")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String reference;
    private float prix;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }
}

