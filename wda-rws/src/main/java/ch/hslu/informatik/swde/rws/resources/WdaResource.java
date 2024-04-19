/**
 * This class defines the RESTful web service resource endpoints for the Weather Data Application (WDA).
 * It provides various endpoints for retrieving and manipulating data related to weather information,
 * cities, users, and more.
 * <p>
 * The endpoints are implemented as JAX-RS resource methods using annotations like @Path, @GET, @POST, @PUT, @DELETE, etc.
 * Each method corresponds to a specific operation or query related to the WDA functionality.
 * <p>
 * This class utilizes the WdaService interface to interact with the business logic of the WDA application.
 * It handles requests, performs necessary operations, and returns appropriate responses.
 *
 * @author Kevin Forter
 * @version 1.0
 */

package ch.hslu.swde.wda.rws.ws;

import ch.hslu.swde.wda.business.WdaService;
import ch.hslu.swde.wda.business.WdaServiceImpl;
import ch.hslu.swde.wda.calculator.Calc;
import ch.hslu.swde.wda.calculator.CalcImpl;
import ch.hslu.swde.wda.domain.Benutzer;
import ch.hslu.swde.wda.domain.Ortschaft;
import ch.hslu.swde.wda.domain.Wetter;
import ch.hslu.swde.wda.reader.WdaProxy;
import ch.hslu.swde.wda.reader.WdaProxyImpl;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;


/**
 * RESTful web service resource endpoints for the Weather Data Application (WDA).
 */
@Path("wda")
public class WdaResource {

    private static final String BASE_URI = "http://localhost:8080/wda/";

    /** Proxy-Komponente */
    WdaProxy proxy = new WdaProxyImpl();

    /** Calculator-Komponente */
    Calc calc = new CalcImpl();

    private final WdaService service = new WdaServiceImpl(proxy, calc);

    /**
     * This method is used to get all cities from the WDA application.
     *
     * @return A simple string message.
     */
    @GET
    @Path("ortschaften")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alleOrtschaften() throws Exception {

        List<Ortschaft> ortschaften = service.alleOrtschaft();
        return Response.ok(ortschaften).build();
    }

    /**
     * This method is used to find a city by its name.
     *
     * @param name The name of the city to be added.
     * @return A simple string message.
     */
    @GET
    @Path("ortschaften/name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCityByName(@QueryParam("name") String name) throws Exception {

        Ortschaft ortschaftByName = service.findCityByName(name);

        if (ortschaftByName != null) {
            return Response.ok(ortschaftByName).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method is used to find a city by its id.
     *
     * @param id The id of the city to be added.
     * @return A simple string message.
     */
    @GET
    @Path("ortschaften/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findCityById(@PathParam("id") int id) throws Exception {

        Ortschaft ortschaftById = service.findCityById(id);

        if (ortschaftById != null) {
            return Response.ok(ortschaftById).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method is used to add a city to the WDA application.
     *
     * @return A simple string message.
     */
    @POST
    @Path("ortschaften")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrtschaften() throws Exception {

        service.addOrtschaft();

        return Response.ok(service.alleOrtschaft()).build();
    }

    /**
     * This method is used to add stations to the WDA application.
     *
     * @return A simple string message.
     */
    @POST
    @Path("stationen")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStationen() throws Exception {

        service.addStation();

        return Response.created(URI.create(BASE_URI + "stationen")).build();
    }

    /**
     * This method is used to find all persons from the WDA application.
     *
     * @return A simple string message.
     */
    @GET
    @Path("benutzer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response alleBenutzer() throws Exception {

        List<Benutzer> benutzer = service.alleBenutzer();
        return Response.ok(benutzer).build();
    }

    /**
     * This method is used to find a person by its username.
     *
     * @param benutzername The benutzername of the benutzer to be added.
     * @return A simple string message.
     */
    @GET
    @Path("benutzer/benutzername")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findUserByUsername(@QueryParam("benutzername") String benutzername) throws Exception {

        Benutzer benutzerByUsername = service.findByUsername(benutzername);

        if (benutzerByUsername != null) {
            return Response.ok(benutzerByUsername).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method is used to find a person by its id.
     *
     * @param id The id of the benutzer to be finde.
     * @return A simple string message.
     */
    @GET
    @Path("benutzer/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findBenutzerById(@PathParam("id") int id) throws Exception {

        Benutzer benutzerById = service.findBenutzerById(id);

        if (benutzerById != null) {
            return Response.ok(benutzerById).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method is used to add a person to the WDA application.
     *
     * @param benutzer The benutzer object to be added.
     * @return A simple string message.
     */
    @POST
    @Path("benutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBenutzer(Benutzer benutzer) throws Exception {

        service.addBenutzer(benutzer);

        return Response.created(URI.create(BASE_URI + "benutzer")).build();
    }

    /**
     * This method is used to delete a person from the WDA application.
     *
     * @param benutzer The benutzer object to be deleted.
     * @return A simple string message.
     */
    @DELETE
    @Path("benutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBenutzer(Benutzer benutzer) throws Exception {

        service.deleteBenutzer(benutzer);

        return Response.noContent().build();
    }

    /**
     * This method is used to delete a person from the WDA application.
     *
     * @param id The id of the benutzer to be deleted.
     * @return A simple string message.
     */
    @DELETE
    @Path("benutzer/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteBenutzer(@PathParam("id") int id) throws Exception {

        service.deleteBenutzerById(id);

        return Response.noContent().build();
    }

    /**
     * This method is used to update a person from the WDA application.
     *
     * @param benutzer The benutzer object to be updated.
     * @return A simple string message.
     */
    @PUT
    @Path("benutzer")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBenutzer(Benutzer benutzer) throws Exception {

        service.updateBenutzer(benutzer);

        return Response.noContent().build();
    }

    /**
     * This method is used to check the login credentials of a person.
     *
     * @param benutzername The benutzername of the benutzer to be checked.
     * @param passwort The passwort of the benutzer to be checked.
     * @return A simple string message.
     */
    @GET
    @Path("benutzer/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkLogin(@QueryParam("benutzername") String benutzername, @QueryParam("passwort") String passwort) throws Exception {

        Benutzer benutzer = service.checkLogin(benutzername, passwort);

        if (benutzer != null) {
            return Response.ok(benutzer).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * This method is used to get the weather data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/timespan")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getWeatherFromCityByTimeSpan(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);
        return Response.ok(wetterList).build();
    }

    /**
     * This method is used to get the mean temperature data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/meanTemperature")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response meanTemperature(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double meanTemperature = service.meanTemperature(wetterList);
        return Response.ok(meanTemperature).build();
    }

    /**
     * This method is used to get the mean pressure data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/meanPressure")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response meanPressure(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double meanPressure = service.meanPressure(wetterList);
        return Response.ok(meanPressure).build();
    }

    /**
     * This method is used to get the mean humidity data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/meanHumidity")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response meanHumidity(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double meanHumidity = service.meanHumidity(wetterList);
        return Response.ok(meanHumidity).build();
    }

    /**
     * This method is used to get the max temperature data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/maxTemperature")
    @Produces(MediaType.APPLICATION_JSON)
    public Response maxTemperature(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double maxTemperature = service.maxTemperature(wetterList);
        return Response.ok(maxTemperature).build();
    }

    /**
     * This method is used to get the max pressure data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/maxPressure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response maxPressure(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double maxPressure = service.maxPressure(wetterList);
        return Response.ok(maxPressure).build();
    }

    /**
     * This method is used to get the max humidity data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/maxHumidity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response maxHumidity(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double maxHumidity = service.maxHumidity(wetterList);
        return Response.ok(maxHumidity).build();
    }

    /**
     * This method is used to get the min temperature data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/minTemperature")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minTemperature(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double minTemperature = service.minTemperature(wetterList);
        return Response.ok(minTemperature).build();
    }

    /**
     * This method is used to get the min pressure data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/minPressure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minPressure(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double minPressure = service.minPressure(wetterList);
        return Response.ok(minPressure).build();
    }

    /**
     * This method is used to get the min humidity data from a specific city and time span from the WDA application.
     *
     * @param id The id of the city data to be found
     * @param von The date of the weather data to be found
     * @param bis The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/minHumidity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minHumidity(@QueryParam("id") int id, @QueryParam("von") LocalDateTime von, @QueryParam("bis") LocalDateTime bis) throws Exception {

        List<Wetter> wetterList = service.getWeatherFromCityByTimeSpan(id, von, bis);

        double minHumidity = service.minHumidity(wetterList);
        return Response.ok(minHumidity).build();
    }

    /**
     * This method is used to get the City with the highest temperature data from a specific date from the WDA application.
     *
     * @param date The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/temperature")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minMaxTemperatureByDateTimeOverall(@QueryParam("date") LocalDateTime date) throws Exception {

        List<Wetter> wetterList = service.temperature(date);
        return Response.ok(wetterList).build();
    }

    /**
     * This method is used to get the City with the highest pressure data from a specific date from the WDA application.
     *
     * @param date The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/pressure")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minMaxPressureByDateTimeOverall(@QueryParam("date") LocalDateTime date) throws Exception {


        List<Wetter> wetterList = service.pressure(date);
        return Response.ok(wetterList).build();
    }

    /**
     * This method is used to get the City with the highest humidity data from a specific date from the WDA application.
     *
     * @param date The date of the weather data to be found
     * @return A simple string message.
     */
    @GET
    @Path("wetterdaten/humidity")
    @Produces(MediaType.APPLICATION_JSON)
    public Response minMaxHumidityByDateTimeOverall(@QueryParam("date") LocalDateTime date) throws Exception {


        List<Wetter> wetterList = service.humidity(date);
        return Response.ok(wetterList).build();
    }

    /**
     * This method is used to add weather data to the WDA application.
     *
     * @param name The name of the city to be added
     */
    @POST
    @Path("wetterdaten/wetter")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addWetter(@QueryParam("name") String name) throws Exception {

        service.addWetter(name);

        return Response.ok(service.latestWeatherByCity(service.findCityByName(name).getId())).build();
    }

    /**
     * This method is used to add weather data to the WDA application.
     *
     * @param name The name of the city to be added
     * @param jahr The year of the weather data to be added
     */
    @POST
    @Path("wetterdaten/wetterjahr")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addWetterJahr(@QueryParam("name") String name, @QueryParam("jahr") int jahr) throws Exception {

        service.addWetterJahr(name, jahr);

        return Response.noContent().build();
    }

    @POST
    @Path("wetterdaten/wetter/alle")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAllWetter() throws Exception {

        service.addAllWetter();

        return Response.created(URI.create(BASE_URI + "wetterdaten/wetter/alle")).build();
    }
}