package ch.hslu.informatik.swde.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Diese Klasse repräsentiert eine City.
 *
 * @author Kevin Forter
 * @version 1.0
 */

@Entity
public class City {

    @Id
    @GeneratedValue
    private int id;

    private String country;

    private int zip;

    private String name;

    public City() {
    }

    public City(int zip, String name, String country) {
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

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", zip=" + zip +
                ", name=" + name +
                ", country=" + country +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        return (object instanceof City o)
                && (o.getName().equals(this.name))
                && (o.getCountry().equals(this.country))
                && (o.getZip() == this.zip)
                && (o.getId() == this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zip, name, country);
    }
}