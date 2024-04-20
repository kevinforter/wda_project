package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.WeatherDAO;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherDAOImpl extends GenericDAOImpl<Weather> implements WeatherDAO {
    public WeatherDAOImpl() {
        super(Weather.class);
    }

    @Override
    public Weather findLatestWeatherByCity(int ortschaftId) {

        EntityManager em = JpaUtil.createEntityManager();

        Weather objFromDb = null;

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.cityId = :ortschaftId AND w.DTstamp = (SELECT MAX(w.DTstamp) FROM Weather" +
                " w WHERE w.cityId = :ortschaftId)", Weather.class);

        tQry.setParameter("ortschaftId", ortschaftId);

        try {
            objFromDb = tQry.getSingleResult();
        } catch (Exception e) {
            // No entities found in the database
            // Handle the case where there are no entities
        }
        em.close();
        return objFromDb;
    }

    // https://chat.openai.com/share/5271fd85-cbcf-4fbc-9e5e-f28880cc1cc3

    @Override
    public Weather findWeatherFromCityByDateTime(LocalDateTime dateTime, int ortschaftId) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery(
                "SELECT w FROM Weather" + " w " +
                        "WHERE w.cityId = :ortschaftId AND w.DTstamp = :dateTime"
                , Weather.class);

        tQry.setParameter("dateTime", dateTime);
        tQry.setParameter("ortschaftId", ortschaftId);

        Weather objFromDb = tQry.getSingleResult();

        em.close();

        return objFromDb != null ? objFromDb : new Weather();
    }

    @Override
    public List<Weather> findWeatherFromCityByTimeSpan(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery(
                "SELECT w FROM Weather" + " w " +
                        "WHERE w.cityId = :ortschaftId " +
                        "AND w.DTstamp BETWEEN :von AND :bis", Weather.class);

        tQry.setParameter("ortschaftId", ortschaftId);
        tQry.setParameter("von", von);
        tQry.setParameter("bis", bis);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxTemperatureByDateTime(LocalDateTime dateTime) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.currTempCelsius = (SELECT MAX(w.currTempCelsius) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime) " +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.currTempCelsius = (SELECT MIN(w.currTempCelsius) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime)", Weather.class);

        tQry.setParameter("dateTime", dateTime);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxHumidityByDateTime(LocalDateTime dateTime) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.humidity = (SELECT MAX(w.humidity) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime)" +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.humidity = (SELECT MIN(w.humidity) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime)", Weather.class);

        tQry.setParameter("dateTime", dateTime);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxPressureByDateTime(LocalDateTime dateTime) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.pressure = (SELECT MAX(w.pressure) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime)" +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :dateTime AND w.pressure = (SELECT MIN(w.pressure) FROM Weather" +
                " w WHERE w.DTstamp = :dateTime)", Weather.class);

        tQry.setParameter("dateTime", dateTime);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public void saveAllWeather(HashMap<LocalDateTime, Weather> weatherMap) {
        EntityManager em = JpaUtil.createEntityManager();

        try {
            em.getTransaction().begin();

            for (Weather weather : weatherMap.values()) {
                // Check if the city is already in the database
                Weather existingWeather = findWeatherFromCityByDateTime(weather.getDTstamp(), weather.getCityId());

                // If the city is not in the database, persist it
                if (existingWeather == null) em.persist(weather);

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
