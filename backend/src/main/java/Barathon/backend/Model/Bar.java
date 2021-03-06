package Barathon.backend.Model;

import javax.persistence.*;

/**
 * Created by samyabh on 19/10/2015.
 */
@Entity
public class Bar {
    private final String URL_IMAGE_DE_BASE = "http://pngimg.com/upload/beer_PNG2362.png";
    private long id;
    private String name;
    private String address;
    private String phone;
    private String latitude;
    private String longitude;
    private String url = URL_IMAGE_DE_BASE;
    private String description;
    private static long cptforId = 0;

    public Bar() {
    }

    public Bar(long id, String name, String address, String phone, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Bar(long id, String name, String address, String phone, String latitude, String longitude, String url) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
    }


    @Id
    //@GeneratedValue(strategy=GenerationType.SEQUENCE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{\"id\":" + id +
                ", \"name\":\"" + name + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"phone\":\"" + phone + '\"' +
                ", \"latitude\":\"" + latitude + '\"' +
                ", \"longitude\":\"" + longitude + '\"' +
                ", \"url\":\"" + url + '\"' +
                '}';
    }
}