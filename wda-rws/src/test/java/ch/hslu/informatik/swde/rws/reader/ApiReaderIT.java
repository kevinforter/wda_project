package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.CityDAOImpl;
import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.rws.util.Util;
import jakarta.ws.rs.core.Link;
import org.mockito.Mockito;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
        void test_GetCityNames_ShouldBe40Cities() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedList<String> resNames = proxy.readCityNames();
            assertNotNull(resNames);
            assertEquals(40, resNames.size());
        }

        @Tag("unittest")
        @Test
        void test_GetCityDetails_ShouldBe40CitiesWithDetails() {

            ApiReader proxy = new ApiReaderImpl();

            LinkedList<String> resNames = proxy.readCityNames();
            assertNotNull(resNames);
            assertEquals(40, resNames.size());
        }

        @Tag("unittest")
        @Test
        void test_getCityDetailsList_ShouldBe40CitiesWithCountryCodeCH() {

            ApiReader proxy = Mockito.mock(ApiReaderImpl.class);

            LinkedList<String> mockCityNames = IntStream.range(0, 40)
                    .mapToObj(i -> "City" + i)
                    .collect(Collectors.toCollection(LinkedList::new));
            when(proxy.readCityNames()).thenReturn(mockCityNames);

            LinkedHashMap<Integer, City> resDetails = proxy.readCityDetailsList(mockCityNames);

            for (City c : resDetails.values()) {
                assertEquals("CH", c.getCountry());
            }
        }

        @Tag("unittest")
        @Test
        void test_GetCitiesObjects_ShouldBe40Cities() {

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