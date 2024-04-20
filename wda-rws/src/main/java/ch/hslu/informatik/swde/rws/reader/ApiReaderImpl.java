package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.persister.DAO.CityDAO;
import ch.hslu.informatik.swde.persister.impl.CityDAOImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse stellt eine konkrete Implementierung der Schnittstelle
 * 'ApiReader' dar.
 */

public class ApiReaderImpl implements ApiReader {

    private static final Logger LOG = LoggerFactory.getLogger(ApiReaderImpl.class);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URI = "http://eee-03318.simple.eee.intern:8080/";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String format = "application/json";

    @Override
    public List<City> readOrtschaften() {
        try {
            URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata/cities/");
            HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", format).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            List<City> cityList = new ArrayList<>();
            if (res.statusCode() == 200) {

                JsonNode node = mapper.readTree(res.body());
                for (JsonNode n : node) {

                    String name = n.get("name").asText();
                    String encodedCityName = name.replace(" ", "+");
                    int zip = n.get("zip").asInt();

                    uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata?city=" + encodedCityName);
                    req = HttpRequest.newBuilder(uri).GET().header("Accept", format).build();
                    res = client.send(req, HttpResponse.BodyHandlers.ofString());

                    if (res.statusCode() == 200) {
                        node = mapper.readTree(res.body());
                        String data = node.get("data").asText();
                        String[] parts = data.split("#");

                        City city = new City();
                        city.setName(name);
                        city.setZip(zip);

                        String country = parts[1].substring(8);
                        city.setCountry(country);

                        cityList.add(city);

                    } else {
                        // Log-Eintrag machen
                        LOG.info("Error occurred, Status code: " + res.statusCode());
                        return new ArrayList<>();
                    }
                }

                if (cityList.isEmpty()) {
                    // No data found in JSON response, log message and return empty List
                    LOG.info("No data found for" + uri);
                    return new ArrayList<>();
                }

                return cityList;

            } else {
                // Log-Eintrag machen
                LOG.info("Error occurred, Status code: " + res.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred: " + e);
            throw new RuntimeException(e);
        }
    }
}