package ch.hslu.informatik.swde.persister.impl;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
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
    void test_SavingCity_ShouldBeSameAsFoundByName() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createCityList()) {
            dao.speichern(c);
            assertEquals(c, dao.findCityByName(c.getName()), "Objekte stimmen nicht überein");
        }
    }

    @Test
    void test_SavingCity_ShouldBeSameAsFoundByFieldNameAndValue() {

        CityDAO dao = new CityDAOImpl();

        for (City c : Util.createCityList()) {
            dao.speichern(c);
            assertEquals(c, dao.findEntityByField("name", c.getName()), "Objekte stimmen nicht überein");
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
    void test_saveAllCities_ShouldMakeABulkSave() {

        CityDAO dao = new CityDAOImpl();

        LinkedHashMap<Integer, City> mapFromUtil = Util.createCityMap();

        dao.saveAllCities(mapFromUtil);
        assertEquals(mapFromUtil.size(), dao.alle().size());

    }
}