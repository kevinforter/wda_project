package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.DAO.WeatherDAO;
import ch.hslu.informatik.swde.persister.impl.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherDAOImplIT {

    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void clearUp() {
        //Util.cleanDatabase();
    }

    @Test
    void testWetterSpeichern() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        for (City o : Util.createOrtschaftList()) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        for (Weather w : Util.createWetterList()) {
            daoW.speichern(w);
            assertEquals(w, daoW.findById(w.getId()));
        }

    }

    @Test
    void testFindWetterById() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        for (City o : Util.createOrtschaftList()) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        for (Weather w : Util.createWetterList()) {
            daoW.speichern(w);
            assertEquals(w, daoW.findById(w.getId()));
        }
    }

    @Test
    void testWetterAlle() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        for (City o : Util.createOrtschaftList()) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        List<Weather> listFromUtil = Util.createWetterList();

        for (Weather w : listFromUtil) {
            daoW.speichern(w);
            assertEquals(w, daoW.findById(w.getId()));
        }

        List<Weather> listFromDB = daoW.alle();
        assertEquals(listFromUtil.size(), listFromDB.size());
    }

    @Test
    void testGetWeatherFromCityByDateTime() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        List<City> ortschaftList = Util.createOrtschaftList();

        for (City o : ortschaftList) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        List<Weather> wetterList = Util.createWetterList();

        for (Weather w : wetterList) {
            daoW.speichern(w);
            assertEquals(w, daoW.findById(w.getId()));
        }
    }

    @Test
    void saveAllWeather() {

        WeatherDAO dao = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        List<City> ortschaftList = Util.createOrtschaftList();

        for (City o : ortschaftList) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        dao.saveAllWeather(Util.createWeatherMap());

        assertEquals(3, dao.alle().size());
    }
}
