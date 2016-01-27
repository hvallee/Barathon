package Barathon.backend.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import Barathon.backend.Model.Bar;

/**
 * Created by samyabh on 26/01/2016.
 */
public class BarDAO {
    private static String queryById = "SELECT b from Bar b "
            + " WHERE id = :barid ";
    private static String queryBar = "SELECT b from Person b";


    /**
     * Récupère un bar dans la base grace à son id
     * @param manager
     * @param personid
     * @return le bar concerné
     */
    public static Bar getBarId(EntityManager manager, int personid) {
        try {
            Bar bar = manager.createQuery(queryById, Bar.class)
                    .setParameter("personid", personid).getSingleResult();
            return bar;
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Bar> getBars(EntityManager manager) {
        try {
            List<Bar> bars = manager.createQuery(queryBar, Bar.class).getResultList();
            return bars;
        } catch (NoResultException e) {
            return null;
        }
    }
}
