package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.DAO.WeatherDAO;
import ch.hslu.informatik.swde.persister.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherDAOImplIT {

    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterEach
    void clearUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void tearDown() {
        Util.cleanDatabase();
    }

    @Test
    void test_SaveWeather_ShouldGetCorrectWeatherByID() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        for (City o : Util.createCityList()) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        for (Weather w : Util.createWetterList()) {
            daoW.speichern(w);
            assertEquals(w, daoW.findById(w.getId()));
        }

    }

    @Test
    void test_SaveWeather_ShouldGetAllWeatherDataInOneList() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        for (City o : Util.createCityList()) {
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
    void test_GetWeatherFromCityByDateTime_ShouldReturnOneWeather() {

        WeatherDAO daoW = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        List<City> ortschaftList = Util.createCityList();

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
    void test_SaveAllWeather_ShouldSaveAllAsGetAllWeather() {

        WeatherDAO dao = new WeatherDAOImpl();
        CityDAO daoO = new CityDAOImpl();

        List<City> ortschaftList = Util.createCityList();

        for (City o : ortschaftList) {
            daoO.speichern(o);
            assertEquals(o, daoO.findById(o.getId()));
        }

        dao.saveAllWeather(Util.createWeatherMap());

        assertEquals(3, dao.alle().size());
    }
}
