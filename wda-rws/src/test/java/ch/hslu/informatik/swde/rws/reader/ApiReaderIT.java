package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.CityDAOImpl;
import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.rws.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ApiReaderIT {

    CityDAO dao = new CityDAOImpl();

    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void cleanUp() {
        Util.cleanDatabase();
    }

    @Test
    void getCityNames() {

        ApiReader proxy = new ApiReaderImpl();

        LinkedList<String> resNames = proxy.readCityNames();
        assertNotNull(resNames);
        assertEquals(40, resNames.size());

        for (String s : resNames) {
            System.out.println(s);
        }
    }

    @Test
    void getCityDetails() {

        ApiReader proxy = new ApiReaderImpl();

        LinkedList<String> resNames = proxy.readCityNames();
        assertNotNull(resNames);
        assertEquals(40, resNames.size());

        for (String s : resNames) {
            City city = proxy.readCityDetails(s);
            System.out.println(city);
        }
    }

    @Test
    void getCityDetailsList() {

        ApiReader proxy = new ApiReaderImpl();

        LinkedList<String> resNames = proxy.readCityNames();
        assertNotNull(resNames);
        assertEquals(40, resNames.size());

        LinkedHashMap<Integer, City> resDetails = proxy.readCityDetailsList(resNames);

        for (City c : resDetails.values()) {
            System.out.println(c);
        }
    }

    @Test
    void getCities() {

        ApiReader proxy = new ApiReaderImpl();

        LinkedHashMap<Integer, City> resOrt = proxy.readCities();
        assertNotNull(resOrt);
        assertEquals(40, resOrt.size());

        for (City c : resOrt.values()) {
            System.out.println(c);
        }

        //dao.saveAllCities(resOrt);
    }
}