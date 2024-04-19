package ch.hslu.swde.wda.reader;

import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Station;
import ch.hslu.swde.wda.domain.Wetter;
import ch.hslu.swde.wda.persister.DAO.OrtschaftDAO;
import ch.hslu.swde.wda.persister.impl.OrtschaftDAOImpl;
import ch.hslu.swde.wda.reader.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WdaProxyIT {
    @BeforeEach
    void setUp() {
        Util.cleanDatabase();
    }

    @AfterAll
    static void cleanUp() {
        Util.cleanDatabase();
    }

    @Test
    void getOrtschaften() {

        WdaProxy proxy = new WdaProxyImpl();

        List<Ortschaft> resOrt = proxy.readOrtschaft();
        assertNotNull(resOrt);
        assertEquals(40, resOrt.size());
    }

    @Test
    void getStationen() {

        WdaProxy proxy = new WdaProxyImpl();
        Util.createOrtschaften();

        List<Station> resStation = proxy.readStation();
        assertNotNull(resStation);
        assertEquals(40, resStation.size());
    }

    @Test
    void getWetter() {

        WdaProxy proxy = new WdaProxyImpl();
        OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
        Util.createOrtschaften();

        List<Ortschaft> ortList = daoOrtschaft.alle();

        Wetter resWetter = proxy.readWetter("Davos");
        assertNotNull(resWetter);

        int ortId_wetter = resWetter.getOrtschaftId();
        int ortId_ortschaft = 0;
        for (Ortschaft ortschaft : ortList) {
            if (ortschaft.getName().equals("Davos")) {
                ortId_ortschaft = ortschaft.getId();
            }
        }
        assertEquals(ortId_ortschaft, ortId_wetter);
    }

    @Test
    void getWetterJahr() {

        WdaProxy proxy = new WdaProxyImpl();
        OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
        Util.createOrtschaften();

        List<Wetter> resWetter = proxy.readWetterJahr("Davos", 2023);
        assertNotNull(resWetter);

        List<Ortschaft> ortList = daoOrtschaft.alle();
        int ortId_ortschaft = 0;
        for (Ortschaft ortschaft : ortList) {
            if (ortschaft.getName().equals("Davos")) {
                ortId_ortschaft = ortschaft.getId();
            }
        }

        for (Wetter wetter : resWetter) {
            int ortId_wetter = wetter.getOrtschaftId();
            assertEquals(ortId_ortschaft, ortId_wetter);
        }
    }
}