package ch.hslu.swde.wda.rws.proxy;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse implementiert die Schnittstelle ServiceProxy.
 * Sie dient als Proxy für die Kommunikation mit dem WDA-Webservice.
 *
 * @version 1.0
 */
public class ServiceProxyImpl implements ServiceProxy {

    private final HttpClient client = HttpClient.newHttpClient();
    private static final String BASE_URI = "http://localhost:8080/";
    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private final String format = "application/json";
    private static final Logger LOG = LogManager.getLogger(ServiceProxyImpl.class);

    /**
     * Konstruktor zum Erstellen eines ServiceProxyImpl Objekts.
     */
    public ServiceProxyImpl() {
    }

    /**
     * Ruft eine Liste aller Ortschaften von einem externen Webservice ab.
     *
     * @return Eine Liste von Ortschaft-Objekten.
     */
    @Override
    public List<Ortschaft> alleOrtschaften() {

        try {
            URI uri = URI.create(BASE_URI + "wda/ortschaften");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Ortschaft>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Ortschaften.", e);
        }
    }

    /**
     * Sucht eine spezifische Ortschaft anhand ihres Namens.
     *
     * @param name Der Name der Ortschaft.
     * @return Ein Ortschaft-Objekt, wenn gefunden; andernfalls null.
     */
    @Override
    public Ortschaft findCityByName(String name) {

        try {
            URI uri = URI.create(BASE_URI + "wda/ortschaften/name?name=" + name);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Ortschaft.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Ortschaft.", e);
        }
    }

    /**
     * Sucht eine spezifische Ortschaft anhand ihrer ID.
     *
     * @param id Die ID der Ortschaft.
     * @return Ein Ortschaft-Objekt, wenn gefunden; andernfalls null.
     */
    @Override
    public Ortschaft findCityById(int id) {

        try {
            URI uri = URI.create(BASE_URI + "wda/ortschaften/" + id);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Ortschaft.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Ortschaft.", e);
        }
    }

    /**
     * Fügt alle Ortschaften hinzu.
     */
    @Override
    public void addOrtschaften() {

        try {
            URI uri = URI.create(BASE_URI + "wda/ortschaften/");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                LOG.info("Ortschaften erfolgreich hinzugefügt.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen der Ortschaften.", e);
        }
    }

    /**
     * Fügt alle Stationen hinzu.
     */
    @Override
    public void addStationen() {

        try {
            URI uri = URI.create(BASE_URI + "wda/stationen/");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                LOG.info("Stationen erfolgreich hinzugefügt.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen der Stationen.", e);
        }
    }

    /**
     * Ruft eine Liste aller Benutzer von einem externen Webservice ab.
     *
     * @return Eine Liste von Benutzer-Objekten.
     */
    @Override
    public List<Benutzer> alleBenutzer() {

        try {
            URI uri = URI.create(BASE_URI + "wda/benutzer/");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Benutzer>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Benutzer.", e);
        }
    }

    /**
     * Sucht einen spezifischen Benutzer anhand seines Benutzernamens.
     *
     * @param benutzername Der Benutzername.
     * @return Ein Benutzer-Objekt, wenn gefunden; andernfalls null.
     */
    @Override
    public Benutzer findUserByUsername(String benutzername) {

        try {
            URI uri = URI.create(BASE_URI + "wda/benutzer/benutzername?benutzername=" + benutzername);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Benutzer.class);

            } else if (response.statusCode() == 404) {
                LOG.error("USER NOT FOUND");
                return null;
            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen des Benutzers.", e);
        }
    }

    /**
     * Sucht einen spezifischen Benutzer anhand seiner ID.
     *
     * @param id Die ID des Benutzers.
     * @return Ein Benutzer-Objekt, wenn gefunden; andernfalls null.
     */
    @Override
    public Benutzer findBenutzerById(int id) {

        try {
            URI uri = URI.create(BASE_URI + "wda/benutzer/" + id);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Benutzer.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen des Benutzers.", e);
        }
    }

    /**
     * Fügt einen neuen Benutzer hinzu.
     *
     * @param benutzer = Benutzer Objekt
     */
    @Override
    public void addBenutzer(Benutzer benutzer) {

        try {
            String jsonData = mapper.writeValueAsString(benutzer);

            URI uri = URI.create(BASE_URI + "wda/benutzer");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                LOG.info("Benutzer erfolgreich hinzugefügt.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen des Benutzers.", e);
        }
    }

    /**
     * Löscht den übergebenen Benutzer.
     *
     * @param benutzer = Benutzer Objekt
     */
    @Override
    public void deleteBenutzer(Benutzer benutzer) {

        try {
            URI uri = URI.create(BASE_URI + "wda/benutzer/" + benutzer.getId());
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .DELETE()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                LOG.info("Benutzer erfolgreich gelöscht.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Löschen des Benutzers.", e);
        }
    }

    /**
     * Aktualisiert den übergebenen Benutzer.
     *
     * @param benutzer = Benutzer Objekt
     */
    @Override
    public void updateBenutzer(Benutzer benutzer) {

        try {
            String jsonData = mapper.writeValueAsString(benutzer);

            URI uri = URI.create(BASE_URI + "wda/benutzer");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .PUT(HttpRequest.BodyPublishers.ofString(jsonData))
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                LOG.info("Benutzer erfolgreich aktualisiert.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Aktualisieren des Benutzers.", e);
        }
    }

    /**
     * Überprüft die Anmeldeinformationen eines Benutzers.
     *
     * @param benutzername Der Benutzername.
     * @param passwort     Das Passwort.
     * @return Ein Benutzer-Objekt, wenn die Anmeldeinformationen korrekt sind; andernfalls null.
     */
    @Override
    public Benutzer checkLogin(String benutzername, String passwort) {

        try {
            URI uri = URI.create(BASE_URI + "wda/benutzer/login?benutzername=" + benutzername + "&passwort=" + passwort);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Benutzer.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Prüfen des Logins.", e);
        }
    }

    /**
     * Ruft Wetterdaten für eine bestimmte Ortschaft innerhalb eines Zeitraums ab.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Eine Liste von Wetter-Objekten.
     */
    @Override
    public List<Wetter> getWeatherFromCityByTimeSpan(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/timespan?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Wetter>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Wetterdaten.", e);
        }
    }

    /**
     * Berechnet die Durchschnittstemperatur für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Die Durchschnittstemperatur.
     */
    @Override
    public double meanTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/meanTemperature?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Berechnen der Durchschnittstemperatur.", e);
        }
    }

    /**
     * Berechnet den durchschnittlichen Luftdruck für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Der Durchschnittsdruck.
     */
    @Override
    public double meanPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/meanPressure?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Berechnen des durchschnittlichen Luftdrucks.", e);
        }
    }

    /**
     * Berechnet die durchschnittliche Luftfeuchtigkeit für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Die Durchschnittsfeuchtigkeit.
     */
    @Override
    public double meanHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/meanHumidity?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Berechnen der durchschnittlichen Luftfeuchtigkeit.", e);
        }
    }

    /**
     * Ermittelt die höchste Temperatur für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Die Höchsttemperatur.
     */
    @Override
    public double maxTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/maxTemperature?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln der Höchsttemperatur.", e);
        }
    }

    /**
     * Ermittelt den höchsten Luftdruck für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Der Höchstdruck.
     */
    @Override
    public double maxPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/maxPressure?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln des höchsten Luftdrucks.", e);
        }
    }

    /**
     * Ermittelt die höchste Luftfeuchtigkeit für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Der höchste Feuchtigkeitswert.
     */
    @Override
    public double maxHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/maxHumidity?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln der höchsten Luftfeuchtigkeit.", e);
        }
    }

    /**
     * Ermittelt die Tiefsttemperatur für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Die tiefste Temperatur.
     */
    @Override
    public double minTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/minTemperature?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln der Tiefsttemperatur.", e);
        }
    }

    /**
     * Ermittelt den niedrigsten Luftdruck für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Der tiefste Druck.
     */
    @Override
    public double minPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/minPressure?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln des niedrigsten Luftdrucks.", e);
        }
    }

    /**
     * Ermittelt die niedrigste Luftfeuchtigkeit für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Der tiefste Feuchtigkeitswert.
     */
    @Override
    public double minHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/minHumidity?id=" + ortschaftId + "&von=" + von + "&bis=" + bis);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), Double.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return 0;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Ermitteln der niedrigsten Luftfeuchtigkeit.", e);
        }
    }

    /**
     * Gibt die Wetterdaten von den Ortschaften mit der Höchst- und Tiefsttemperatur zu einem spezifischen Zeitpunkt zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    public List<Wetter> minMaxTemperatureByDateTimeOverall(LocalDateTime date) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/temperature?date=" + date);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Wetter>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Höchst- und Tiefstwerten.", e);
        }
    }

    /**
     * Gibt die Wetterdaten von den Ortschaften mit dem höchsten und tiefsten Druck zu einem spezifischen Zeitpunkt zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    @Override
    public List<Wetter> minMaxPressureByDateTimeOverall(LocalDateTime date) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/pressure?date=" + date);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Wetter>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Höchst- und Tiefstwerten.", e);
        }
    }

    /**
     * Gibt die Wetterdaten von den Ortschaften mit der höchsten und tiefsten Feuchtigkeit zu einem spezifischen Zeitpunkt zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    @Override
    public List<Wetter> minMaxHumidityByDateTimeOverall(LocalDateTime date) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/humidity?date=" + date);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .header("Accept", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new TypeReference<List<Wetter>>() {
                });

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return new ArrayList<>();
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Abrufen der Höchst- und Tiefstwerten.", e);
        }
    }

    /**
     * Fügt Wetterdaten für eine bestimmte Ortschaft der Datenbank hinzu und gibt sie danach zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    @Override
    public Wetter addWetter(String city) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/wetter?name=" + city);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                LOG.info("Wetterdaten erfolgreich hinzugefügt.");
                return mapper.readValue(response.body(), Wetter.class);

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen der Wetterdaten.", e);
        }
    }

    /**
     * Fügt Wetterdaten für eine bestimmte Stadt und ein bestimmtes Jahr hinzu.
     *
     * @param city Der Name der Stadt.
     * @param jahr Das Jahr, für das die Daten hinzugefügt werden sollen.
     */
    @Override
    public void addWetterJahr(String city, int jahr) {

        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/wetterjahr?name=" + city + "&jahr=" + jahr);
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 204) {
                LOG.info("Wetterdaten erfolgreich hinzugefügt.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen der Wetterdaten.", e);
        }
    }

    /**
     * Fügt alle Wetterdaten hinzu.
     *
     */
    @Override
    public void addAllWetter() {
        try {
            URI uri = URI.create(BASE_URI + "wda/wetterdaten/wetter/alle/");
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .headers("Content-Type", format)
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                LOG.info("Wetterdaten erfolgreich hinzugefügt.");

            } else {
                LOG.error("Error occurred, status code: " + response.statusCode());
            }

        } catch (Exception e) {
            LOG.error("Error occurred: " + e.getMessage());
            throw new RuntimeException("Fehler beim Hinzufügen der Wetterdaten.", e);
        }
    }
}