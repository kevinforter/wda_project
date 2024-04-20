package ch.hslu.informatik.swde.rws.util;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.GenericDAO;
import ch.hslu.informatik.swde.persister.impl.GenericDAOImpl;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import ch.hslu.informatik.swde.rws.reader.*;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Util {

    private static final GenericDAO<City> daoOrtschaft = new GenericDAOImpl<>(City.class);

    private Util() {

    }

    public static void cleanDatabase() {

        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM City e").executeUpdate();

        em.getTransaction().commit();

        em.close();

    }

    public static void createOrtschaften() {

        ApiReader proxy = new ApiReaderImpl();

        List<City> resOrt = proxy.readOrtschaften();

        for (City c : resOrt) {
            daoOrtschaft.speichern(c);
        }
    }

}
