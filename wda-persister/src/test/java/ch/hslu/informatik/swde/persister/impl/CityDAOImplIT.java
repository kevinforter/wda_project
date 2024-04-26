package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CityDAOImplIT {

    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterEach
    void clearUp() {
        Util.cleanDatabase();
    }

    @Test
    void test_SavingCity_ShouldBeSameAsFoundByID() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createCityList()) {
            dao.speichern(c);
            assertEquals(c, dao.findById(c.getId()), "Objekte stimmen nicht überein");
        }

    }

    @Test
    void test_getAllCitiesFormDB_ShouldBeTheSameAsSavedList() {

        CityDAO dao = new CityDAOImpl();

        List<City> listFromUtil = Util.createCityList();
        List<City> listFromDB;

        for (City c : listFromUtil) {
            dao.speichern(c);
            assertEquals(c, dao.findById(c.getId()));
        }

        listFromDB = dao.alle();
        assertEquals(listFromUtil, listFromDB, "DB ist nicht kongruent zur Liste");
    }

    @Test
    void testFindCityByName() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createCityList()) {
            dao.speichern(c);
            assertEquals(c, dao.findCityByName(c.getName()));
        }
    }

    @Test
    void testFindEntityByField() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createCityList()) {
            dao.speichern(c);
            assertEquals(c, dao.findEntityByField("name", c.getName()));
        }
    }

    @Test
    void saveAllCities() {

        CityDAO dao = new CityDAOImpl();

        dao.saveAllCities(Util.createCityMap());
        assertEquals(3, dao.alle().size());

    }
}