package ch.hslu.swde.wda.reader.util;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.persister.DAO.GenericDAO;
import ch.hslu.swde.wda.persister.impl.GenericDAOImpl;
import ch.hslu.swde.wda.persister.util.JpaUtil;
import ch.hslu.swde.wda.reader.WdaProxy;
import ch.hslu.swde.wda.reader.WdaProxyImpl;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private static final GenericDAO<Ortschaft> daoOrtschaft = new GenericDAOImpl<>(Ortschaft.class);

    private Util() {

    }

    public static void cleanDatabase() {

        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Wetter e").executeUpdate();
        em.createQuery("DELETE FROM Station e").executeUpdate();
        em.createQuery("DELETE FROM Ortschaft e").executeUpdate();

        em.getTransaction().commit();

        em.close();

    }

    public static void createOrtschaften() {

        WdaProxy proxy = new WdaProxyImpl();

        List<Ortschaft> resOrt = proxy.readOrtschaft();

        for (Ortschaft o : resOrt) {
            daoOrtschaft.speichern(o);
        }
    }

}
