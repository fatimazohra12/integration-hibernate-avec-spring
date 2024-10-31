package ma.projet.services;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class CategorieService implements IDao<Categorie> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(Categorie o) {
        Session session = sessionFactory.getCurrentSession();
        session.save(o); // Save the Categorie entity
        return true; // Return true after successful save
    }

    @Override
    @Transactional
    public Categorie getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Categorie.class, id); // Fetch the Categorie entity by ID
    }

    @Override
    @Transactional
    public List<Categorie> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Categorie", Categorie.class).list(); // Fetch all Categorie entities
    }
}
