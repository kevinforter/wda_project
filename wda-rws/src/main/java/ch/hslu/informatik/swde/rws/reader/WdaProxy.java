package ch.hslu.swde.wda.reader;

import ch.hslu.swde.wda.domain.*;

import java.util.List;

/**
 * Diese Schnittstelle gibt die Funktionalitäten vor, welche für die Abfrage
 * von Wetterdaten und Ortschaften benötigt werden.
 *
 * @author Kevin
 * @version 1.0
 */

public interface WdaProxy {

    /**
     * Liest eine Liste von Wetterstationen von einem externen Webservice.
     *
     * @return Eine Liste von Station-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    List<Station> readStation();

    /**
     * Liest eine Liste von Ortschaften von einem externen Webservice.
     *
     * @return Eine Liste von Ortschaft-Objekten, die aus der Antwort des Webservice extrahiert wurden.
     */
    List<Ortschaft> readOrtschaft();

    /**
     * Liest Wetterdaten für eine spezifische Ortschaft von einem externen Webservice.
     *
     * @param name Der Name der Ortschaft.
     * @return Ein Wetter-Objekt mit den Wetterdaten der angegebenen Ortschaft.
     */
    Wetter readWetter(String name);

    /**
     * Liest alle Wetterdaten von einem externen Webservice.
     *
     * @return Eine Liste von Wetter-Objekten, die alle Wetterdaten enthalten.
     */
    List<Wetter> readAllWetter();

    /**
     * Liest Wetterdaten für eine spezifische Ortschaft und ein bestimmtes Jahr von einem externen Webservice.
     *
     * @param ortschaft Der Name der Ortschaft.
     * @param jahr Das spezifizierte Jahr.
     * @return Eine Liste von Wetter-Objekten, die den Wetterdaten für das angegebene Jahr und die Ortschaft entsprechen.
     */
    List<Wetter> readWetterJahr(String ortschaft, int jahr);
}
