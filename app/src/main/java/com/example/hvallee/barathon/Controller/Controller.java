package com.example.hvallee.barathon.Controller;

import com.example.hvallee.barathon.Model.Bar;

import java.util.ArrayList;

/**
 * Created by samyabh on 19/10/2015.
 */
public class Controller {
    ArrayList<Bar> list_bars;


    public Controller() {
    }

    public Controller(ArrayList<Bar> list_bars) {
        this.list_bars = list_bars;
    }





    public ArrayList<Bar> getList_bars() {
        return list_bars;
    }

    public void setList_bars(ArrayList<Bar> list_bars) {
        this.list_bars = list_bars;
    }
}
