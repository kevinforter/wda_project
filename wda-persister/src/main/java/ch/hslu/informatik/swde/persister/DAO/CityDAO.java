package ch.hslu.informatik.swde.persister.DAO;

import ch.hslu.informatik.swde.domain.City;

/**
 * Diese Schnittstelle ergänzt die generische Persister-Schnittstelle
 * mit zusätzlichen Funktionalitäten für die Persistierung von Ortschaften.
 *
 * @author Kevin Forter
 * @version 1.0
 */

public interface CityDAO extends GenericDAO<City> {

    /**
     * Sucht und liefert eine City anhand ihres Namens.
     *
     * @param cityName Der Name der zu suchenden City.
     * @return Ein City-Objekt, falls eine City mit dem angegebenen Namen gefunden wurde
     * andernfalls leeres City-Objekt.
     */
    City findCityByName(String cityName);
}
