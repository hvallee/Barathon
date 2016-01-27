package Barathon.backend.Controller;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import Barathon.backend.GoogleData.GoogleBars;
import Barathon.backend.Model.Bar;
import Barathon.backend.dao.BarDAO;

/**
 * Created by samyabh on 26/01/2016.
 */
public class Controller {


    public Controller(){

    }

    public List<Bar> getBars(){

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("Backend");
        EntityManager manager = factory.createEntityManager();
        return BarDAO.getBars(manager);
    }

    public void miseAjour(){
        GoogleBars gb = new GoogleBars();
        List<Bar> bars = gb.getBars();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Backend");
        EntityManager manager = factory.createEntityManager();

        //View vue = new View(manager);

        EntityTransaction tx = manager.getTransaction();
        tx.begin();

        manager.persist(bars);


        tx.commit();

        System.out.println(".. done");


    }
}
