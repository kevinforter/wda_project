package ch.hslu.swde.wda.reader;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Station;
import ch.hslu.swde.wda.domain.Wetter;
import ch.hslu.swde.wda.persister.DAO.OrtschaftDAO;
import ch.hslu.swde.wda.persister.impl.OrtschaftDAOImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Diese Klasse stellt eine konkrete Implementierung der Schnittstelle
 * 'WdaProxy' dar.
 */

public class WdaProxyImpl implements WdaProxy {

    private static final Logger LOG = LogManager.getLogger(WdaProxyImpl.class);
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URI = "http://eee-03318.simple.eee.intern:8080/";
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String format = "application/json";

    @Override
    public List<Ortschaft> readOrtschaft() {
        try {
            URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata/cities/");
            HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", format).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            List<Ortschaft> ortListe = new LinkedList<>();
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

                        Ortschaft ort = new Ortschaft();
                        ort.setName(name);
                        ort.setZip(zip);

                        String country = parts[1].substring(8);
                        ort.setCountry(country);

                        double longitude = Double.parseDouble(parts[4].substring(10));
                        ort.setLongitude(longitude);

                        double latitude = Double.parseDouble(parts[5].substring(8));
                        ort.setLatitude(latitude);

                        int stationId = Integer.parseInt(parts[6].substring(11));
                        ort.setStationId(stationId);

                        ortListe.add(ort);

                    } else {
                        // Log-Eintrag machen
                        LOG.info("Error occurred, Status code: " + res.statusCode());
                        return new LinkedList<>();
                    }
                }

                if (ortListe.isEmpty()) {
                    // No data found in JSON response, log message and return empty List
                    LOG.info("No data found for" + uri);
                    return new LinkedList<>();
                }

                return ortListe;

            } else {
                // Log-Eintrag machen
                LOG.info("Error occurred, Status code: " + res.statusCode());
                return new LinkedList<>();
            }

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Wetter readWetter(String name) {
        try {
            OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();

            String encodedCityName = name.replace(" ", "+");

            URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata?city=" + encodedCityName);
            HttpRequest req = HttpRequest.newBuilder(uri).GET().header("Accept", format).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            LocalDateTime formatDateTime;
            if (res.statusCode() == 200) {

                Wetter wetter = new Wetter();

                JsonNode node = mapper.readTree(res.body());
                String data = node.get("data").asText();
                String[] parts = data.split("#");

                Ortschaft ort = daoOrtschaft.findCityByName(name);
                wetter.setOrtschaftId(ort.getId());

                LocalDate date = LocalDate.parse(parts[0].substring(17, 27));
                wetter.setDate(date);

                LocalTime time = LocalTime.parse(parts[0].substring(28));
                wetter.setTime(time);

                String dateTime = parts[0].substring(17);
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                formatDateTime = LocalDateTime.parse(dateTime, format);
                wetter.setDateTime(formatDateTime);

                String summery = parts[7].substring(16);
                wetter.setWeatherSummery(summery);

                String description = parts[8].substring(20);
                wetter.setWeatherDescription(description);

                double temp = Double.parseDouble(parts[9].substring(28));
                wetter.setCurrTempCelsius(temp);

                double pressure = Double.parseDouble(parts[10].substring(9));
                wetter.setPressure(pressure);

                double humidity = Double.parseDouble(parts[11].substring(9));
                wetter.setHumidity(humidity);

                double wind = Double.parseDouble(parts[12].substring(11));
                wetter.setWindSpeed(wind);

                double direction = Double.parseDouble(parts[13].substring(15));
                wetter.setWindDirection(direction);

                return wetter;

            } else {
                // Log-Eintrag machen
                LOG.info("Error occurred, Status code: " + res.statusCode());
                return new Wetter();
            }

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Wetter> readWetterJahr(String ortschaft, int jahr) {
        try {
            OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
            String encodedCityName = ortschaft.replace(" ", "+");

            URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata/cityandyear?city=" + encodedCityName + "&year=" + jahr);
            HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", format).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            List<Wetter> wetterListe = new ArrayList<>();
            LocalDateTime formatDateTime;
            if (res.statusCode() == 200) {

                JsonNode node = mapper.readTree(res.body());
                for (JsonNode n : node) {

                    Wetter wetter = new Wetter();

                    Ortschaft ort = daoOrtschaft.findCityByName(ortschaft);
                    wetter.setOrtschaftId(ort.getId());

                    String data = n.get("data").asText();
                    String[] parts = data.split("#");

                    LocalDate date = LocalDate.parse(parts[0].substring(17, 27));
                    wetter.setDate(date);

                    LocalTime time = LocalTime.parse(parts[0].substring(28));
                    wetter.setTime(time);

                    String dateTime = parts[0].substring(17);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    formatDateTime = LocalDateTime.parse(dateTime, format);
                    wetter.setDateTime(formatDateTime);

                    String summery = parts[7].substring(16);
                    wetter.setWeatherSummery(summery);

                    String description = parts[8].substring(20);
                    wetter.setWeatherDescription(description);

                    double temp = Double.parseDouble(parts[9].substring(28));
                    wetter.setCurrTempCelsius(temp);

                    double pressure = Double.parseDouble(parts[10].substring(9));
                    wetter.setPressure(pressure);

                    double humidity = Double.parseDouble(parts[11].substring(9));
                    wetter.setHumidity(humidity);

                    double wind = Double.parseDouble(parts[12].substring(11));
                    wetter.setWindSpeed(wind);

                    double direction = Double.parseDouble(parts[13].substring(15));
                    wetter.setWindDirection(direction);

                    wetterListe.add(wetter);
                }

                if (wetterListe.isEmpty()) {
                    // No data found in JSON response, log message and return empty List
                    LOG.info("No data found for" + uri);
                    return new ArrayList<>();
                }

                return wetterListe;

            } else {
                // Log-Eintrag machen
                LOG.info("Error occurred, Status code: " + res.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Wetter> readAllWetter() {
        try {
            OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
            List<Ortschaft> ortListe = daoOrtschaft.alle();

            List<Wetter> wetterListe = new ArrayList<>();

            for (Ortschaft ortschaft : ortListe) {

                String encodedCityName = ortschaft.getName().replace(" ", "+");

                URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata/cityandyear?city=" + encodedCityName + "&year=" + Year.now().getValue());
                HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", format).build();
                HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

                LocalDateTime formatDateTime;
                if (res.statusCode() == 200) {

                    JsonNode node = mapper.readTree(res.body());
                    for (JsonNode n : node) {

                        Wetter wetter = new Wetter();

                        Ortschaft ort = daoOrtschaft.findCityByName(ortschaft.getName());
                        wetter.setOrtschaftId(ort.getId());

                        String data = n.get("data").asText();
                        String[] parts = data.split("#");

                        LocalDate date = LocalDate.parse(parts[0].substring(17, 27));
                        wetter.setDate(date);

                        LocalTime time = LocalTime.parse(parts[0].substring(28));
                        wetter.setTime(time);

                        String dateTime = parts[0].substring(17);
                        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        formatDateTime = LocalDateTime.parse(dateTime, format);
                        wetter.setDateTime(formatDateTime);

                        String summery = parts[7].substring(16);
                        wetter.setWeatherSummery(summery);

                        String description = parts[8].substring(20);
                        wetter.setWeatherDescription(description);

                        double temp = Double.parseDouble(parts[9].substring(28));
                        wetter.setCurrTempCelsius(temp);

                        double pressure = Double.parseDouble(parts[10].substring(9));
                        wetter.setPressure(pressure);

                        double humidity = Double.parseDouble(parts[11].substring(9));
                        wetter.setHumidity(humidity);

                        double wind = Double.parseDouble(parts[12].substring(11));
                        wetter.setWindSpeed(wind);

                        double direction = Double.parseDouble(parts[13].substring(15));
                        wetter.setWindDirection(direction);

                        wetterListe.add(wetter);
                    }

                    if (wetterListe.isEmpty()) {
                        // No data found in JSON response, log message and return empty List
                        LOG.info("No data found for" + uri);
                        return new ArrayList<>();
                    }

                } else {
                    // Log-Eintrag machen
                    LOG.info("Error occurred, Status code: " + res.statusCode());
                    return new ArrayList<>();
                }
            }

            return wetterListe;

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred");
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Station> readStation() {
        try {
            OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
            URI uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata/cities/");
            HttpRequest req = HttpRequest.newBuilder(uri).header("Accept", format).build();
            HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

            List<Station> stationListe = new ArrayList<>();
            if (res.statusCode() == 200) {

                JsonNode node = mapper.readTree(res.body());
                for (JsonNode n : node) {

                    String name = n.get("name").asText();
                    String encodedCityName = name.replace(" ", "+");

                    uri = URI.create(BASE_URI + "weatherdata-provider/rest/weatherdata?city=" + encodedCityName);
                    req = HttpRequest.newBuilder(uri).GET().header("Accept", format).build();
                    res = client.send(req, HttpResponse.BodyHandlers.ofString());

                    if (res.statusCode() == 200) {
                        node = mapper.readTree(res.body());
                        String data = node.get("data").asText();
                        String[] parts = data.split("#");

                        Station station = new Station();
                        Ortschaft ort = daoOrtschaft.findCityByName(name);

                        station.setOrtschaftId(ort.getId());

                        double longitude = Double.parseDouble(parts[4].substring(10));
                        station.setLongitude(longitude);

                        double latitude = Double.parseDouble(parts[5].substring(8));
                        station.setLatitude(latitude);

                        int stationId = Integer.parseInt(parts[6].substring(11));
                        station.setStationId(stationId);

                        stationListe.add(station);

                    } else {
                        // Log-Eintrag machen
                        LOG.info("Error occurred, Status code: " + res.statusCode());
                        return new ArrayList<>();
                    }
                }

                if (stationListe.isEmpty()) {
                    // No data found in JSON response, log message and return empty List
                    LOG.info("No data found for" + uri);
                    return new ArrayList<>();
                }

                return stationListe;

            } else {
                // Log-Eintrag machen
                LOG.info("Error occurred, Status code: " + res.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            // Log-Eintrag machen
            // return new ArrayList<Message>();
            LOG.error("Error occurred");
            throw new RuntimeException(e);
        }
    }
}
