package entity;

import java.util.List;

/**
 * Created by B on 09.10.2016.
 */
public class Video {

    private int id;
    private String name;
    private String nameRu;
    private int year;
    private Country country;
    private Person director;
    private List<Person> actors;
    private Store storeType;

    public Video() {
    }

    public Video(int id, String name, String nameRu, int year, Country country, Person director, Store storeType) {
        this.id = id;
        this.name = name;
        this.nameRu = nameRu;
        this.year = year;
        this.country = country;
        this.director = director;
        this.storeType = storeType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }

    public List<Person> getActors() {
        return actors;
    }

    public void setActors(List<Person> actors) {
        this.actors = actors;
    }

    public Store getStoreType() {
        return storeType;
    }

    public void setStoreType(Store storeType) {
        this.storeType = storeType;
    }

    @Override
    public String toString() {
        if(name==null)
            return "nameRu='" + nameRu + '\'' +
                    ", year=" + year ;
        return "nameRu='" + nameRu + '\'' +
                ", name='" + name + '\'' +
                ", year=" + year ;
    }
}
