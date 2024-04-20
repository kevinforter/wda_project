package ch.hslu.informatik.swde.persister.impl.util;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public static LinkedHashMap<Integer, City> createOrtschaftMap() {

        LinkedHashMap<Integer, City> cityMap = new LinkedHashMap<>();

        // Create some cities
        City city1 = new City(3000, "Bern", "CH");
        City city2 = new City(8001, "Zurich", "CH");
        City city3 = new City(1201, "Geneva", "CH");

        // Put cities in the map with city name as the key
        cityMap.put(city1.getZip(), city1);
        cityMap.put(city2.getZip(), city2);
        cityMap.put(city3.getZip(), city3);

        // Retrieve a city from the map
        City retrievedCity = cityMap.get(3000);
        System.out.println(retrievedCity);

        // Iterate over the map
        for (Integer zip : cityMap.keySet()) {
            City city = cityMap.get(zip);
            System.out.println(city);
        }

        return cityMap;
    }
}