package ch.hslu.swde.wda.persister.DAO;

import ch.hslu.swde.wda.domain.Wetter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Diese Schnittstelle ergänzt die generische Persister-Schnittstelle
 * mit zusätzlichen Funktionalitäten für die Persistierung von Wetterdaten.
 *
 * @author Jovan und Rony
 * @version 1.0
 */

public interface WetterDAO extends GenericDAO<Wetter> {

    /**
     * Holt das neueste Wetter für eine bestimmte Ortschaft basierend auf der Ortschafts-ID.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @return Das neueste Wetter-Objekt für die angegebene Ortschaft; null, wenn keine Daten gefunden werden.
     */
    Wetter latestWeatherByCity(int ortschaftId);

    /**
     * Holt Wetterdaten für eine bestimmte Ortschaft zu einem bestimmten Zeitpunkt.
     *
     * @param date Der spezifische Zeitpunkt.
     * @param ortschaftId Die ID der Ortschaft.
     * @return Eine Liste von Wetter-Objekten; leer, wenn keine Daten gefunden werden.
     */
    List<Wetter> getWeatherFromCityByDateTime(LocalDateTime date, int ortschaftId);

    /**
     * Holt Wetterdaten für eine bestimmte Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von Anfangsdatum des Zeitraums.
     * @param bis Enddatum des Zeitraums.
     * @return Eine Liste von Wetter-Objekten; leer, wenn keine Daten gefunden werden.
     */
    List<Wetter> getWeatherFromCityByTimeSpan(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Holt die minimale und maximale Temperatur für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Temperaturen; leer, wenn keine Daten gefunden werden.
     */
    List<Wetter> minMaxTemperatureByDateTime(LocalDateTime dateTime);

    /**
     * Holt die minimale und maximale Luftfeuchtigkeit für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Luftfeuchtigkeitswerten; leer, wenn keine Daten gefunden werden.
     */
    List<Wetter> minMaxHumidityByDateTime(LocalDateTime dateTime);

    /**
     * Holt den minimalen und maximalen Luftdruck für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Luftdruckwerten; leer, wenn keine Daten gefunden werden.
     */
    List<Wetter> minMaxPressureByDateTime(LocalDateTime dateTime);
}
