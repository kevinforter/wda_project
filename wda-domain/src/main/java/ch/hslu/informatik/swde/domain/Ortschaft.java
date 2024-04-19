package ch.hslu.informatik.swde.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Diese Klasse repräsentiert eine Ortschaft.
 *
 * @author Rony und Florian
 * @version 1.0
 */

@Entity
public class Ortschaft {

    @Id
    @GeneratedValue
    private int id;

    private String country;

    private int zip;

    private String name;

    private double longitude;

    private double latitude;

    private int stationId;

    public Ortschaft() {
    }

    public Ortschaft(int zip, String name, String country, double longitude, double latitude, int stationId) {
        this.country = country;
        this.zip = zip;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stationId = stationId;
    }

    public Ortschaft(int zip, String name, String country) {
        this.country = country;
        this.zip = zip;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    @Override
    public String toString() {
        return "Ortschaft{" +
                "id=" + id +
                ", zip=" + zip +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", stationId=" + stationId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ortschaft ortschaft = (Ortschaft) o;
        return id == ortschaft.id && zip == ortschaft.zip && Double.compare(longitude, ortschaft.longitude) == 0 && Double.compare(latitude, ortschaft.latitude) == 0 && Double.compare(stationId, ortschaft.stationId) == 0 && Objects.equals(name, ortschaft.name) && Objects.equals(country, ortschaft.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zip, name, country, longitude, latitude, stationId);
    }
}