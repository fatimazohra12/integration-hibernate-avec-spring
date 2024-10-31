package ma.projet.dao;

import java.util.List;

public interface IDao<T> {
    boolean create(T o);           // Method to create a new entity, returns true if successful
    T getById(int id);             // Method to get an entity by its ID
    List<T> getAll();              // Method to get all entities
}
