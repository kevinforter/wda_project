package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityDAOImplIT {

    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void clearUp() {
        Util.cleanDatabase();
    }

    @Test
    void testOrtschaftSpeichern() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createOrtschaftList()) {
            dao.speichern(c);
            assertEquals(c, dao.findById(c.getId()));
        }

    }

    @Test
    void testFindOrtschaftById() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createOrtschaftList()) {
            dao.speichern(c);
            assertEquals(c, dao.findById(c.getId()));
        }
    }

    @Test
    void testOrtschaftAlle() {

        CityDAO dao = new CityDAOImpl();

        List<City> listFromUtil = Util.createOrtschaftList();
        List<City> listFromDB;

        for (City c : listFromUtil) {
            dao.speichern(c);
            assertEquals(c, dao.findById(c.getId()));
        }

        listFromDB = dao.alle();
        assertEquals(listFromUtil.size(), listFromDB.size());
    }

    @Test
    void testFindCityByName() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createOrtschaftList()) {
            dao.speichern(c);
            assertEquals(c, dao.findCityByName(c.getName()));
        }
    }

    @Test
    void testFindEntityByField() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createOrtschaftList()) {
            dao.speichern(c);
            assertEquals(c, dao.findEntityByField("name", c.getName()));
        }
    }

    @Test
    void saveAllCities() {

        CityDAO dao = new CityDAOImpl();

        dao.saveAllCities(Util.createOrtschaftMap());
        assertEquals(3, dao.alle().size());

    }
}