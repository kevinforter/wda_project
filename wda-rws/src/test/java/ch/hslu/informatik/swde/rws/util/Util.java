package ch.hslu.informatik.swde.rws.util;

import ch.hslu.informatik.swde.rws.reader.*;
import java.util.LinkedList;
public class Util {

    private Util() {

    }

    public static LinkedList<String> createCities() {

        ApiReader proxy = new ApiReaderImpl();

        return proxy.readCityNames();
    }
}
