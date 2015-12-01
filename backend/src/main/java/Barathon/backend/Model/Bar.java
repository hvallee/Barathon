package Barathon.backend.Model;

import java.util.ArrayList;

/**
 * Created by samyabh on 19/10/2015.
 */
public class Bar {
    private long id;
    private String name;
    private String address;
    private String phone;
    private String latitude;
    private String longitude;

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


    public static ArrayList<Bar> getAListOfPersonne() {
        ArrayList<Bar> listPers = new ArrayList<Bar>();

        listPers.add(new Bar(1, "bar1", "1 rue du bar", "0202020202", "48.1125389","-1.6831857"));
        listPers.add(new Bar(2, "bar2", "2 rue du bar", "0202020201", "48.1133397","-1.68406"));

        return listPers;
    }

}