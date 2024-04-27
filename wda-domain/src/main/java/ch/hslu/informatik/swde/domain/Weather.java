package ch.hslu.informatik.swde.domain;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Diese Klasse repräsentiert das Weather.
 *
 * @author Florian
 * @version 1.0
 */

@Entity
public class Weather implements Serializable {

    @Serial
    private static final long serialVersionUID = -2545723421326092280L;

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne(targetEntity = City.class, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "city_id", insertable = false, updatable = false)
    private City city;

    @Column(name = "city_id")
    private int cityId;
    private LocalDateTime DTstamp;
    private String weatherSummery;
    private String weatherDescription;
    private double currTempCelsius;
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double windDirection;

    public Weather() {
    }

    public Weather(int cityId, LocalDateTime DTstamp, String weatherSummery, String weatherDescription, double currTempCelsius, double pressure, double humidity, double windSpeed, double windDirection) {
        this.cityId = cityId;
        this.DTstamp = DTstamp;
        this.weatherSummery = weatherSummery;
        this.weatherDescription = weatherDescription;
        this.currTempCelsius = currTempCelsius;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public Weather(LocalDateTime DTstamp, String weatherSummery, String weatherDescription, double currTempCelsius, double pressure, double humidity, double windSpeed, double windDirection) {
        this.DTstamp = DTstamp;
        this.weatherSummery = weatherSummery;
        this.weatherDescription = weatherDescription;
        this.currTempCelsius = currTempCelsius;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public LocalDateTime getDTstamp() {
        return DTstamp;
    }

    public void setDTstamp(LocalDateTime DTstamp) {
        this.DTstamp = DTstamp;
    }

    public String getWeatherSummery() {
        return weatherSummery;
    }

    public void setWeatherSummery(String weatherSummery) {
        this.weatherSummery = weatherSummery;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public double getCurrTempCelsius() {
        return currTempCelsius;
    }

    public void setCurrTempCelsius(double currTempCelsius) {
        this.currTempCelsius = currTempCelsius;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        return (object instanceof Weather o)
                && (o.getId() == this.id)
                && (o.getCityId() == this.cityId)
                && Objects.equals(getCity(), this.city)
                && Objects.equals(getDTstamp(), this.DTstamp);
    }



    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", city=" + city +
                ", city_ID=" + cityId +
                ", DTstamp=" + DTstamp +
                ", weatherSummery=" + weatherSummery +
                ", weatherDescription=" + weatherDescription +
                ", currTempCelsius=" + currTempCelsius +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                ", windSpeed=" + windSpeed +
                ", windDirection=" + windDirection +
                '}';
    }
}

