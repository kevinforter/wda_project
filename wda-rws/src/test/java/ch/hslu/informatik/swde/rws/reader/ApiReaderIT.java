package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.CityDAOImpl;
import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.rws.util.Util;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiReaderIT {
    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void cleanUp() {
        Util.cleanDatabase();
    }

    /*-----------------------------------------------CITY API REQUEST-----------------------------------------------*/

    @Nested
    class CityTest {
        @Tag("unittest")
        @Test
        void getCityNames() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedList<String> resNames = proxy.readCityNames();
            assertNotNull(resNames);
            assertEquals(40, resNames.size());
        }

        @Tag("unittest")
        @Test
        void getCityDetails() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedList<String> resNames = proxy.readCityNames();
            assertNotNull(resNames);
            assertEquals(40, resNames.size());
        }

        @Tag("unittest")
        @Test
        void getCityDetailsList() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedList<String> resNames = proxy.readCityNames();
            assertNotNull(resNames);
            assertEquals(40, resNames.size());

            LinkedHashMap<Integer, City> resDetails = proxy.readCityDetailsList(resNames);

        }

        @Tag("unittest")
        @Test
        void getCities() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedHashMap<Integer, City> resOrt = proxy.readCities();
            assertNotNull(resOrt);
            assertEquals(40, resOrt.size());
        }
    }

    /*----------------------------------------------WEATHER API REQUEST---------------------------------------------*/

    @Nested
    class WeatherTest {
        @Test
        void getCurrentWeather() {

            ApiReader proxy = new ApiReaderImpl();
            CityDAO daoCity = new CityDAOImpl();
            Util.createCities();

            List<City> cityList = daoCity.alle();

            Weather resWeather = proxy.readCurrentWeatherByCity("Davos");
            assertNotNull(resWeather);

            int cityId_weather = resWeather.getCityId();
            int cityId_city = 0;
            for (City city : cityList) {
                if (city.getName().equals("Davos")) {
                    cityId_city = city.getId();
                }
            }
            assertEquals(cityId_city, cityId_weather);
        }

        @Test
        void getWeatherByCityAndYear() {

            ApiReader proxy = new ApiReaderImpl();
            CityDAO daoCity = new CityDAOImpl();
            Util.createCities();

            LinkedHashMap<LocalDateTime, Weather> resWeather = proxy.readWeatherByCityAndYear("Davos", 2023);
            assertNotNull(resWeather);

            List<City> cityList = daoCity.alle();
            int cityId_city = 0;
            for (City city : cityList) {
                if (city.getName().equals("Davos")) {
                    cityId_city = city.getId();
                }
            }

            for (Weather weather : resWeather.values()) {
                int ortId_wetter = weather.getCityId();
                assertEquals(cityId_city, ortId_wetter);
            }
        }
    }
}