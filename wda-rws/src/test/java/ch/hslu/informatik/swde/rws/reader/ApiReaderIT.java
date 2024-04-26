package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.Weather;
import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.rws.util.Util;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.mockito.Mockito;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ApiReaderIT {

    static LinkedList<String> cities = new LinkedList<>();
    LinkedList<String> cityList = new LinkedList<>();
    @BeforeAll
    static void setUp() {
        cities = Util.createCities();
    }

   @BeforeEach
   void createCityList() {
        cityList = new LinkedList<>();
        cityList.addAll(cities);
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
        void test_GetDetailsPerCity_ShouldBe40CitiesWithCountryCodeCH() {

            ApiReader proxy = Mockito.mock(ApiReaderImpl.class);

            LinkedList<String> mockCityNames = IntStream.range(0, 40)
                    .mapToObj(i -> "City" + i)
                    .collect(Collectors.toCollection(LinkedList::new));
            when(proxy.readCityNames()).thenReturn(mockCityNames);

            City mockCity = new City();
            mockCity.setCountry("CH");
            when(proxy.readCityDetails(Mockito.anyString())).thenReturn(mockCity);

            for(String cityName : mockCityNames) {
                City city = proxy.readCityDetails(cityName);
                assertEquals("CH", city.getCountry());
            }
        }

        @Tag("unittest")
        @Test
        void test_GetCityDetailsList_ShouldBe40CitiesWithCountryCodeCH() {

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
        void test_GetCurrentWeather_ShouldReturnNewestWeather() {

            ApiReader proxy = new ApiReaderImpl();

            for(String cityName : cityList) {
                Weather resWeather = proxy.readCurrentWeatherByCity(cityName);
                assertNotNull(resWeather);
            }
        }

        @Test
        void test_GetWeatherByCityAndYear_ShouldReturnListOfWeatherDataOfGivenYear() {

            ApiReader proxy = new ApiReaderImpl();

            for(String cityName : cityList) {
                LinkedHashMap<LocalDateTime, Weather> resWeather = proxy.readWeatherByCityAndYear(cityName, 2024);
                assertNotNull(resWeather);
            }
        }
    }
}