package ch.hslu.informatik.swde.persister.DAO;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.domain.Weather;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Diese Schnittstelle ergänzt die generische Persister-Schnittstelle
 * mit zusätzlichen Funktionalitäten für die Persistierung von Wetterdaten.
 *
 * @author Kevin
 * @version 1.0
 */

public interface WeatherDAO extends GenericDAO<Weather> {

    /**
     * Holt das neueste Wetter für eine bestimmte Ortschaft basierend auf der Ortschafts-ID.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @return Das neueste Wetter-Objekt für die angegebene Ortschaft; null, wenn keine Daten gefunden werden.
     */
    Weather findLatestWeatherByCity(int ortschaftId);

    /**
     * Holt Wetterdaten für eine bestimmte Ortschaft zu einem bestimmten Zeitpunkt.
     *
     * @param date Der spezifische Zeitpunkt.
     * @param ortschaftId Die ID der Ortschaft.
     * @return Eine Liste von Wetter-Objekten; leer, wenn keine Daten gefunden werden.
     */
    LinkedHashMap<LocalDateTime, Weather> findWeatherFromCityByDateTime(LocalDateTime date, int ortschaftId);

    /**
     * Holt Wetterdaten für eine bestimmte Ortschaft innerhalb eines bestimmten Zeitraums.
     *
     * @param ortschaftId Die ID der Ortschaft.
     * @param von Anfangsdatum des Zeitraums.
     * @param bis Enddatum des Zeitraums.
     * @return Eine Liste von Wetter-Objekten; leer, wenn keine Daten gefunden werden.
     */
    LinkedHashMap<LocalDateTime, Weather> findWeatherFromCityByTimeSpan(int ortschaftId, LocalDateTime von, LocalDateTime bis);

    /**
     * Holt die minimale und maximale Temperatur für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Temperaturen; leer, wenn keine Daten gefunden werden.
     */
    LinkedHashMap<LocalDateTime, Weather> findMinMaxTemperatureByDateTime(LocalDateTime dateTime);

    /**
     * Holt die minimale und maximale Luftfeuchtigkeit für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Luftfeuchtigkeitswerten; leer, wenn keine Daten gefunden werden.
     */
    LinkedHashMap<LocalDateTime, Weather> findMinMaxHumidityByDateTime(LocalDateTime dateTime);

    /**
     * Holt den minimalen und maximalen Luftdruck für einen bestimmten Zeitpunkt.
     *
     * @param dateTime Der Zeitpunkt für die Abfrage.
     * @return Eine Liste von Wetter-Objekten mit minimalen und maximalen Luftdruckwerten; leer, wenn keine Daten gefunden werden.
     */
    LinkedHashMap<LocalDateTime, Weather> findMinMaxPressureByDateTime(LocalDateTime dateTime);

    /**
     * Speichert alle Städte in der Map ab
     *
     * @param weatherMao mit allen Wetterdaten
     */
    void saveAllWeather(HashMap<LocalDateTime, Weather> weatherMao);
}
