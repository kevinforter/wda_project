package ch.hslu.informatik.swde.rws.reader;

import ch.hslu.informatik.swde.domain.City;
import ch.hslu.informatik.swde.rws.util.Util;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApiReaderIT {
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

        ApiReader proxy = new ApiReaderImpl();

        List<City> resOrt = proxy.readOrtschaft();
        assertNotNull(resOrt);
        assertEquals(40, resOrt.size());
    }
}