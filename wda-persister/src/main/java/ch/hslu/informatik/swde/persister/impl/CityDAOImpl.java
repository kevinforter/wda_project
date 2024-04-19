package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

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
}
