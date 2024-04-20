package ch.hslu.informatik.swde.persister.impl.util;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.CityDAOImpl;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        em.createQuery("DELETE FROM Weather e").executeUpdate();
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

    public static List<Weather> createWetterList() {

        CityDAO daoO = new CityDAOImpl();
        List<Weather> list = new ArrayList<>();

        for (int i = 0; i < createOrtschaftList().size(); i++) {

            Weather wetter = new Weather();
            City ort = daoO.findCityByName(createOrtschaftList().get(i).getName());
            wetter.setCityId(ort.getId());
            wetter.setDTstamp(LocalDateTime.of(2023, 12, 3, 21, 30, 19));
            wetter.setCurrTempCelsius(-6.0);
            wetter.setHumidity(91);
            wetter.setPressure(1234);
            wetter.setWindDirection(10);
            wetter.setWindSpeed(23.0);
            wetter.setWeatherDescription("good");
            wetter.setWeatherSummery("sunny");

            Weather wetter1 = new Weather(ort.getId(), LocalDateTime.of(2023, 12, 3, 22, 30, 19), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);
            Weather wetter2 = new Weather(ort.getId(), LocalDateTime.of(2023, 12, 3, 23, 30, 19), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);
            Weather wetter3 = new Weather(ort.getId(), LocalDateTime.now(), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);

            list.add(wetter);
            list.add(wetter1);
            list.add(wetter2);
            list.add(wetter3);
        }

        return list;
    }
}