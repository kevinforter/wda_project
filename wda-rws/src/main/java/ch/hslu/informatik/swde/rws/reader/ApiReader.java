package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.*;

import java.util.*;

/**
 * Diese Schnittstelle gibt die Funktionalitäten vor, welche für die Abfrage
 * von Wetterdaten und Ortschaften benötigt werden.
 *
 * @author Kevin
 * @version 1.0
 */

public interface ApiReader {

    /**
     * Liest eine Liste von Ortschaften von einem externen Webservice.
     *
     * @return Eine Liste von Ortschaft-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    LinkedList<String> readCityNames();

    /**
     * Liest eine Liste von Ortschaften von einem externen Webservice.
     *
     * @return Eine Liste von Ortschaft-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    City readCityDetails(String cityName);

    /**
     * Liest eine Liste von Ortschaften von einem externen Webservice.
     *
     * @return Eine Liste von Ortschaft-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    LinkedHashMap<Integer, City> readCityDetailsList(LinkedList<String> cityNames);

    /**
     * Liest eine Liste von Ortschaften von einem externen Webservice.
     *
     * @return Eine Liste von Ortschaft-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    LinkedHashMap<Integer, City> readCities();
}
