package ch.hslu.swde.wda.rws.proxy;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Diese Schnittstelle gibt die Funktionalitäten vor, welche für die Verwaltung
 * von Wetterdaten, Ortschaften und Benutzern benötigt werden.
 *
 * @version 1.0
 */
public interface ServiceProxy {

    /**
     * Ruft eine Liste aller Ortschaften von einem externen Webservice ab.
     *
     * @return Eine Liste von Ortschaft-Objekten.
     */
    List<Ortschaft> alleOrtschaften();

    /**
     * Sucht eine spezifische Ortschaft anhand ihres Namens.
     *
     * @param name Der Name der Ortschaft.
     * @return Ein Ortschaft-Objekt, wenn gefunden; andernfalls null.
     */
    Ortschaft findCityByName(String name);

    /**
     * Sucht eine spezifische Ortschaft anhand ihrer ID.
     *
     * @param id Die ID der Ortschaft.
     * @return Ein Ortschaft-Objekt, wenn gefunden; andernfalls null.
     */
    Ortschaft findCityById(int id);

    /**
     * Fügt alle Ortschaften hinzu.
     */
    void addOrtschaften();

    /**
     * Fügt alle Stationen hinzu.
     */
    void addStationen();

    /**
     * Ruft eine Liste aller Benutzer von einem externen Webservice ab.
     *
     * @return Eine Liste von Benutzer-Objekten.
     */
    List<Benutzer> alleBenutzer();

    /**
     * Sucht einen spezifischen Benutzer anhand seines Benutzernamens.
     *
     * @param benutzername Der Benutzername.
     * @return Ein Benutzer-Objekt, wenn gefunden; andernfalls null.
     */
    Benutzer findUserByUsername(String benutzername);

    /**
     * Sucht einen spezifischen Benutzer anhand seiner ID.
     *
     * @param id Die ID des Benutzers.
     * @return Ein Benutzer-Objekt, wenn gefunden; andernfalls null.
     */
    Benutzer findBenutzerById(int id);

    /**
     * Fügt einen neuen Benutzer hinzu.
     *
     * @param benutzer Das zu hinzufügende Benutzer-Objekt.
     */
    void addBenutzer(Benutzer benutzer);

    /**
     * Löscht den übergebenen Benutzer.
     *
     * @param benutzer Das zu löschende Benutzer-Objekt.
     */
    void deleteBenutzer(Benutzer benutzer);

    /**
     * Aktualisiert den übergebenen Benutzer.
     *
     * @param benutzer Das zu aktualisierende Benutzer-Objekt.
     */
    void updateBenutzer(Benutzer benutzer);

    /**
     * Überprüft die Anmeldeinformationen eines Benutzers.
     *
     * @param benutzername Der Benutzername.
     * @param passwort     Das Passwort.
     * @return Ein Benutzer-Objekt, wenn die Anmeldeinformationen korrekt sind; andernfalls null.
     */
    Benutzer checkLogin(String benutzername, String passwort);

    /**
     * Ruft Wetterdaten für eine bestimmte Ortschaft innerhalb eines Zeitraums ab.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Eine Liste von Wetter-Objekten.
     */
    List<Wetter> getWeatherFromCityByTimeSpan(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Berechnet die Durchschnittstemperatur für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginn des Zeitraums.
     * @param bis         Ende des Zeitraums.
     * @return Die Durchschnittstemperatur.
     */
    double meanTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Berechnet den durchschnittlichen Luftdruck für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Den durchschnittlichen Luftdruck als Double.
     */
    double meanPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Berechnet die durchschnittliche Luftfeuchtigkeit für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Die durchschnittliche Luftfeuchtigkeit als Double.
     */
    double meanHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Ermittelt die höchste Temperatur für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Die höchste Temperatur als Double.
     */
    double maxTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Ermittelt den höchsten Luftdruck für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Den höchsten Luftdruck als Double.
     */
    double maxPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Ermittelt die höchste Luftfeuchtigkeit für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Die höchste Luftfeuchtigkeit als Double.
     */
    double maxHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Ermittelt die niedrigste Temperatur für eine spezifische Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Beginndatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Die niedrigste Temperatur als Double.
     */
    double minTemperature(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Berechnet den niedrigsten Luftdruck für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Startdatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Den niedrigsten Luftdruck als Double-Wert.
     */
    double minPressure(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Berechnet die niedrigste Luftfeuchtigkeit für eine bestimmte Ortschaft innerhalb eines Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von         Startdatum des Zeitraums.
     * @param bis         Enddatum des Zeitraums.
     * @return Die niedrigste Luftfeuchtigkeit als Double-Wert.
     */
    double minHumidity(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Gibt die Wetterdaten von den Ortschaften mit der Höchst- und Tiefsttemperatur zu einem spezifischen Zeitpunkt zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    List<Wetter> minMaxTemperatureByDateTimeOverall(LocalDateTime date);

    /**
     * Gibt die Wetterdaten von den Ortschaften mit dem höchsten und niedrigsten Luftdruck zu einem spezifischen Zeitpunkt zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    List<Wetter> minMaxPressureByDateTimeOverall(LocalDateTime date);

    /**
     * Gibt die Wetterdaten von den Ortschaften mit der höchsten und niedrigsten Luftfeuchtigkeit zu einem spezifischen Zeitpunkt zurück.
     *
     * @param date Das Datum, für das die Daten abgerufen werden sollen.
     * @return Eine Liste von Wetter-Objekten mit Luftfeuchtigkeitsdaten.
     */
    List<Wetter> minMaxHumidityByDateTimeOverall(LocalDateTime date);

    /**
     * Fügt Wetterdaten für eine bestimmte Ortschaft der Datenbank hinzu und gibt sie danach zurück.
     *
     * @return Eine Liste von Wetter-Objekten.
     */
    Wetter addWetter(String city);

    /**
     * Fügt Wetterdaten für eine bestimmte Stadt und ein bestimmtes Jahr hinzu.
     *
     * @param city Der Name der Stadt.
     * @param jahr Das Jahr, für das die Daten hinzugefügt werden sollen.
     */
    void addWetterJahr(String city, int jahr);

    /**
     * Fügt alle Wetterdaten hinzu.
     *
     */
    void addAllWetter();
}
