package ch.hslu.swde.wda.persister;

import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Station;
import ch.hslu.swde.wda.domain.Wetter;
import ch.hslu.swde.wda.persister.DAO.OrtschaftDAO;
import ch.hslu.swde.wda.persister.impl.OrtschaftDAOImpl;
import ch.hslu.swde.wda.persister.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Util {

    private Util() {

    }

    public static void cleanDatabase() {

        EntityManager em = JpaUtil.createEntityManager();
        em.getTransaction().begin();

        em.createQuery("DELETE FROM Wetter e").executeUpdate();
        em.createQuery("DELETE FROM Station e").executeUpdate();
        em.createQuery("DELETE FROM Ortschaft e").executeUpdate();
        em.createQuery("DELETE FROM Benutzer e").executeUpdate();

        em.getTransaction().commit();

        em.close();

    }

    public static List<Ortschaft> createOrtschaftList() {

        List<Ortschaft> list = new ArrayList<>();

        list.add(new Ortschaft(7260, "Davos", "CH"));
        list.add(new Ortschaft(8000, "Zurich", "CH"));
        list.add(new Ortschaft(6000, "Lucerne", "CH"));

        return list;
    }

    public static List<Wetter> createWetterList() {

        OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
        List<Wetter> list = new ArrayList<>();

        for (int i = 0; i < createOrtschaftList().size(); i++) {

            Wetter wetter = new Wetter();
            Ortschaft ort = daoOrtschaft.findCityByName(createOrtschaftList().get(i).getName());
            wetter.setOrtschaftId(ort.getId());
            wetter.setDateTime(LocalDateTime.of(2023, 12, 3, 21, 30, 19));
            wetter.setDate(LocalDate.of(2023, 12, 3));
            wetter.setTime(LocalTime.of(21, 30, 19));
            wetter.setCurrTempCelsius(-6.0);
            wetter.setHumidity(91);
            wetter.setPressure(1234);
            wetter.setWindDirection(10);
            wetter.setWindSpeed(23.0);
            wetter.setWeatherDescription("good");
            wetter.setWeatherSummery("sunny");

            Wetter wetter1 = new Wetter(ort.getId(), LocalDate.of(2023, 12, 3), LocalTime.of(22, 30, 19), LocalDateTime.of(2023, 12, 3, 22, 30, 19), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);
            Wetter wetter2 = new Wetter(ort.getId(), LocalDate.of(2023, 12, 3), LocalTime.of(23, 30, 19), LocalDateTime.of(2023, 12, 3, 23, 30, 19), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);
            Wetter wetter3 = new Wetter(ort.getId(), LocalDate.now(), LocalTime.now(), LocalDateTime.now(), "foggy", "fog", 23.0, 982.0, 91.0, 43.0, 10.0);

            list.add(wetter);
            list.add(wetter1);
            list.add(wetter2);
            list.add(wetter3);
        }

        return list;
    }

    public static List<Station> createStationList() {

        OrtschaftDAO daoOrtschaft = new OrtschaftDAOImpl();
        List<Station> list = new ArrayList<>();

        List<Ortschaft> oList = daoOrtschaft.alle();

        list.add(new Station(oList.get(0).getId(), 76348, 8.3064, 47.0505));
        list.add(new Station(oList.get(1).getId(), 23452, 9.8372, 46.8043));
        list.add(new Station(oList.get(2).getId(), 32512, 8.5545, 47.3667));

        return list;
    }

    public static List<Benutzer> createBenutzerList() {

        List<Benutzer> list = new ArrayList<>();

        list.add(new Benutzer("Jovan", "Miodrag", "jovan", "jovan"));
        list.add(new Benutzer("Kevin", "Forter", "kevin", "kevin"));
        list.add(new Benutzer("Florian", "Werlen", "florian", "florian"));
        list.add(new Benutzer("Rony", "Hochstrasser", "rony", "rony"));

        return list;
    }
}