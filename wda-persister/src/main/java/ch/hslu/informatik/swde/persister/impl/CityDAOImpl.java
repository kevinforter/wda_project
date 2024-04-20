package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CityDAOImpl extends GenericDAOImpl<City> implements CityDAO {

    public CityDAOImpl() {
        super(City.class);
    }

    @Override
    public City findCityByName(String cityName) {

        EntityManager em = JpaUtil.createEntityManager();

        City objFromDb = null;

        TypedQuery<City> tQry = em.createQuery("SELECT o FROM City" + " o WHERE o.name = :name", City.class);
        tQry.setParameter("name", cityName);

        try {
            objFromDb = tQry.getSingleResult();
        } catch (Exception e) {
            // No entity found in the database
        } finally {
            em.close();
            return objFromDb;
        }
    }

    @Override
    public void saveAllCities(LinkedHashMap<Integer, City> cityMap) {
        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();

            for (City city : cityMap.values()) {
                // Check if the city is already in the database
                City existingCity = findEntityByField("name", city.getName());

                // If the city is not in the database, persist it
                if (existingCity == null) em.persist(city);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            em.close();
        }
    }
}
