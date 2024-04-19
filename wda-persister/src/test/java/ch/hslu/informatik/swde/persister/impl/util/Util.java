package ch.hslu.informatik.swde.persister.impl.util;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private Util() {

    }

    public static void cleanDatabase() {

        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM City e").executeUpdate();

        em.getTransaction().commit();

        em.close();

    }

    public static List<City> createOrtschaftList() {

        List<City> list = new ArrayList<>();

        list.add(new City(7260, "Davos", "CH"));
        list.add(new City(8000, "Zurich", "CH"));
        list.add(new City(6000, "Lucerne", "CH"));

        return list;
    }
}