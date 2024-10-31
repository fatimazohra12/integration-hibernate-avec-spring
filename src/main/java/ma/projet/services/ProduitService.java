package ma.projet.services;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public class ProduitService implements IDao<Produit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(Produit o) {
        Session session = sessionFactory.getCurrentSession();
        session.save(o);
        return true;
    }

    @Override
    @Transactional
    public Produit getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Produit.class, id);
    }

    @Override
    @Transactional
    public List<Produit> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Produit", Produit.class).list();
    }

    @Transactional
    public List<Produit> getProductsByCategory(int categoryId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Produit> query = session.createQuery("FROM Produit p WHERE p.categorie.id = :categoryId", Produit.class);
        query.setParameter("categoryId", categoryId);
        return query.getResultList();
    }

    @Transactional
    public List<Produit> getProductsOrderedBetweenDates(Date startDate, Date endDate) {
        Session session = sessionFactory.getCurrentSession();
        Query<Produit> query = session.createQuery(
                "SELECT lc.produit FROM LigneCommandeProduit lc JOIN lc.commande c  " +
                        "WHERE c.date BETWEEN :startDate AND :endDate", Produit.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Transactional
    public void displayProductsInOrder(int orderId) {
        Session session = sessionFactory.getCurrentSession();
        Commande commande = session.get(Commande.class, orderId);

        if (commande != null) {
            System.out.println("Commande: " + orderId);
            System.out.println("Liste des produits:");

            for (LigneCommandeProduit ligneCommande : commande.getLigneCommandeProduits()) {
                Produit produit = ligneCommande.getProduit();
                System.out.println("Référence: " + produit.getReference());
                System.out.println("Prix: " + produit.getPrix() + " DH");
                System.out.println("Date: " + commande.getDate());
                System.out.println("Quantité: " + ligneCommande.getQuantite());
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("Commande non trouvée avec l'ID: " + orderId);
        }
    }

    @Transactional
    public List<Produit> getProductsAbovePrice(Double price) {
        Session session = sessionFactory.getCurrentSession();
        Query<Produit> query = session.createNamedQuery("Produit.findByPriceGreaterThan", Produit.class);
        query.setParameter("price", price);
        return query.getResultList();
    }
}
