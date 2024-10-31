package ma.projet.services;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CommandeService implements IDao<Commande> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(Commande o) {
        Session session = sessionFactory.getCurrentSession();
        session.save(o); // Save the Commande entity
        return true; // Return true after successful save
    }

    @Override
    @Transactional
    public Commande getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Commande.class, id); // Fetch the Commande entity by ID
    }

    @Override
    @Transactional
    public List<Commande> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Commande", Commande.class).list(); // Fetch all Commande entities
    }
}
