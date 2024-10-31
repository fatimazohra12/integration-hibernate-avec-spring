package ma.projet;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        // Charger le contexte de l'application
        ApplicationContext context = new AnnotationConfigApplicationContext(HibernateUtil.class);

        // Obtenir les beans DAO
        IDao<Produit> productIDao = context.getBean("produitService", IDao.class);
        IDao<Categorie> categoryIDao = context.getBean("categorieService", IDao.class);
        IDao<Commande> commandeIDao = context.getBean("commandeService", IDao.class);
        IDao<LigneCommandeProduit> ligneCommandeIDao = context.getBean("ligneCommandeProduitService", IDao.class);

        // Créer une nouvelle catégorie
        Categorie categorie = new Categorie();
        categorie.setCode("C001");
        categorie.setLibelle("Catégorie Test");

        if (categoryIDao.create(categorie)) {
            System.out.println("Catégorie créée avec succès !");
        }

        // Créer un produit associé à la catégorie
        Produit produit1 = new Produit();
        produit1.setReference("FS12");
        produit1.setPrix(120.0F);
        produit1.setCategorie(categorie);

        Produit produit2 = new Produit();
        produit2.setReference("ZR85");
        produit2.setPrix(100.0F);
        produit2.setCategorie(categorie);

        productIDao.create(produit1);
        productIDao.create(produit2);

        // Créer une commande
        Commande commande = new Commande();
        commande.setDate(new Date());

        // Créer des lignes de commande pour chaque produit et les ajouter à la commande
        LigneCommandeProduit ligneCommande1 = new LigneCommandeProduit();
        ligneCommande1.setProduit(produit1);
        ligneCommande1.setQuantite(7);
        commande.addLigneCommandeProduit(ligneCommande1); // Use the method to add line item

        LigneCommandeProduit ligneCommande2 = new LigneCommandeProduit();
        ligneCommande2.setProduit(produit2);
        ligneCommande2.setQuantite(14);
        commande.addLigneCommandeProduit(ligneCommande2); // Use the method to add line item

        // Sauvegarder la commande (avec ses lignes)
        commandeIDao.create(commande);


        StringBuilder affichage = new StringBuilder();
        affichage.append("Commande : ").append(commande.getId()).append("\n");
        affichage.append("Liste des produits :\n");

        float totalPrix = 0;
        for (LigneCommandeProduit ligne : commande.getLigneCommandeProduits()) {
            Produit produit = ligne.getProduit();
            affichage.append("Reference: ").append(produit.getReference()).append("\n");
            affichage.append("Prix: ").append(produit.getPrix()).append(" DH\n");
            totalPrix += produit.getPrix() * ligne.getQuantite();
        }

        affichage.append("Date : ").append(new SimpleDateFormat("dd MMMM yyyy").format(commande.getDate())).append("\n");
        affichage.append("Quantité et Prix:\n");

        for (LigneCommandeProduit ligne : commande.getLigneCommandeProduits()) {
            affichage.append(ligne.getQuantite()).append(" x ").append(ligne.getProduit().getPrix()).append(" DH\n");
        }

        affichage.append("Total : ").append(totalPrix).append(" DH\n");

        System.out.println(affichage.toString());
    }
}

