package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.WeatherDAO;
import ch.hslu.informatik.swde.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherDAOImpl extends GenericDAOImpl<Weather> implements WeatherDAO {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherDAOImpl.class);

    public WeatherDAOImpl() {
        super(Weather.class);
    }

    @Override
    public Weather findLatestWeatherByCity(int cityId) {

        EntityManager em = JpaUtil.createEntityManager();

        Weather objFromDb = null;

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.cityId = :cityId AND w.DTstamp = (SELECT MAX(w.DTstamp) FROM Weather" +
                " w WHERE w.cityId = :cityId)", Weather.class);

        tQry.setParameter("cityId", cityId);

        try {
            objFromDb = tQry.getSingleResult();
        } catch (Exception e) {
            // No entities found in the database
            LOG.info("No Weather found for City ID: " + cityId);
        }
        em.close();
        return objFromDb;
    }

    // https://chat.openai.com/share/5271fd85-cbcf-4fbc-9e5e-f28880cc1cc3

    @Override
    public Weather findWeatherFromCityByDateTime(LocalDateTime DTstamp, int cityId) {

        EntityManager em = JpaUtil.createEntityManager();

        Weather objFromDb = null;

        TypedQuery<Weather> tQry = em.createQuery(
                "SELECT w FROM Weather" + " w " +
                        "WHERE w.cityId = :cityId AND w.DTstamp = :DTstamp"
                , Weather.class);

        tQry.setParameter("DTstamp", DTstamp);
        tQry.setParameter("cityId", cityId);

        try {
            objFromDb = tQry.getSingleResult();
        } catch (Exception e) {
            // No entities found in the database
            LOG.info("No Weather found for City ID: " + cityId);
        }
        em.close();
        return objFromDb;
    }

    @Override
    public List<Weather> findWeatherFromCityByTimeSpan(int cityId, LocalDateTime von, LocalDateTime bis) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery(
                "SELECT w FROM Weather" + " w " +
                        "WHERE w.cityId = :cityId " +
                        "AND w.DTstamp BETWEEN :von AND :bis", Weather.class);

        tQry.setParameter("cityId", cityId);
        tQry.setParameter("von", von);
        tQry.setParameter("bis", bis);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxTemperatureByDateTime(LocalDateTime DTstamp) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.currTempCelsius = (SELECT MAX(w.currTempCelsius) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp) " +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.currTempCelsius = (SELECT MIN(w.currTempCelsius) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp)", Weather.class);

        tQry.setParameter("DTstamp", DTstamp);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxHumidityByDateTime(LocalDateTime DTstamp) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.humidity = (SELECT MAX(w.humidity) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp)" +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.humidity = (SELECT MIN(w.humidity) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp)", Weather.class);

        tQry.setParameter("DTstamp", DTstamp);

        List<Weather> objListe = tQry.getResultList();

        em.close();

        return objListe != null ? objListe : new ArrayList<>();
    }

    @Override
    public List<Weather> findMinMaxPressureByDateTime(LocalDateTime DTstamp) {

        EntityManager em = JpaUtil.createEntityManager();

        TypedQuery<Weather> tQry = em.createQuery("SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.pressure = (SELECT MAX(w.pressure) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp)" +
                "UNION " +
                "SELECT w FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp AND w.pressure = (SELECT MIN(w.pressure) FROM Weather" +
                " w WHERE w.DTstamp = :DTstamp)", Weather.class);

        tQry.setParameter("DTstamp", DTstamp);

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
