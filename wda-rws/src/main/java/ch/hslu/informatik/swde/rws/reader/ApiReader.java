package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.*;

import java.util.List;

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
    List<City> readOrtschaften();
}
