package ch.hslu.swde.wda.persister.impl;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.persister.DAO.OrtschaftDAO;
import ch.hslu.swde.wda.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class OrtschaftDAOImpl extends GenericDAOImpl<Ortschaft> implements OrtschaftDAO {

    public OrtschaftDAOImpl() {
        super(Ortschaft.class);
    }

    @Override
    public Ortschaft findCityByName(String cityName) {

        EntityManager em = JpaUtil.createEntityManager();

        Ortschaft objFromDb = null;

        TypedQuery<Ortschaft> tQry = em.createQuery("SELECT o FROM Ortschaft" + " o WHERE o.name = :name", Ortschaft.class);
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
