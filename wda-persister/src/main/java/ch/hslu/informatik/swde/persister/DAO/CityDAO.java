package ch.hslu.swde.wda.persister.DAO;

import ch.hslu.swde.wda.domain.Ortschaft;

/**
 * Diese Schnittstelle ergänzt die generische Persister-Schnittstelle
 * mit zusätzlichen Funktionalitäten für die Persistierung von Ortschaften.
 *
 * @author Jovan und Rony
 * @version 1.0
 */

public interface OrtschaftDAO extends GenericDAO<Ortschaft> {

    /**
     * Sucht und liefert eine Ortschaft anhand ihres Namens.
     *
     * @param cityName Der Name der zu suchenden Ortschaft.
     * @return Eine Ortschaft-Objekt, falls eine Ortschaft mit dem angegebenen Namen gefunden wurde; andernfalls null.
     */
    Ortschaft findCityByName(String cityName);
}
