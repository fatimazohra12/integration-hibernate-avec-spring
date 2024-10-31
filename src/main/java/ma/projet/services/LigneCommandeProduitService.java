package ma.projet.services;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class LigneCommandeProduitService implements IDao<LigneCommandeProduit> {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public boolean create(LigneCommandeProduit ligneCommandeProduit) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ligneCommandeProduit);
        return true;
    }

    @Override
    @Transactional
    public LigneCommandeProduit getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(LigneCommandeProduit.class, id);
    }

    @Override
    @Transactional
    public List<LigneCommandeProduit> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM LigneCommandeProduit", LigneCommandeProduit.class).list();
    }
}
