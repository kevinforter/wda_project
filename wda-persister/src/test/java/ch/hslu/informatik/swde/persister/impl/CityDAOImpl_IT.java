package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.util.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityDAOImpl_IT {

    @BeforeEach
    void setUp() { Util.cleanDatabase();
    }

    @AfterEach
    void tearDown() { // Util.cleanDatabase();
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
}