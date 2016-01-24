package com.example.hvallee.barathon;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcours;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by yoannlt on 04/01/2016.
 */
public class DatabaseParcourTest extends AndroidTestCase {

    private BarsDataSource datasource;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        datasource = new BarsDataSource(context);
        datasource.open();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        datasource.close();
    }

    public void testCreateParcours(){
        // test que createparcours retourne un parcours.

        String nom = "mon parcours";
        String descritption = "Description du parcours";

        Parcours parcours = datasource.createParcours(nom, descritption);

        Assert.assertTrue(parcours instanceof Parcours);
        Assert.assertEquals(nom, parcours.getName());
        Assert.assertEquals(descritption, parcours.getDescription());
    }

    public void testGetParcoursById(){
        // On insert un parcours
        String nom = "mon parcours";
        String description = "Description du parcours";
        Parcours parcour = datasource.createParcours(nom, description);
        Assert.assertEquals(parcour.getId(), 1);

        // Pour on le récupère dans la bdd
        Parcours parcour2 = datasource.getParcoursById((int) parcour.getId());

        //Puis test de l'égalité des deux parcours
        Assert.assertEquals(parcour.getName(), parcour2.getName());
        Assert.assertEquals(parcour.getDescription(), parcour2.getDescription());

        // Test false
        Assert.assertFalse(parcour2.getName().equals("TEST"));
    }

    public void testinsertBarIntoParcour(){
        // On insert un parcours
        String nom = "mon parcours";
        String description = "Description du parcours";
        datasource.createParcours(nom, description);

        // On insert des bars
        datasource.createBar("LE PETIT VELO", "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866","url");
        datasource.createBar("TIO PAQUITO", "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188","url");

        datasource.insertBarIntoParcours(1, 1);
        datasource.insertBarIntoParcours(1, 2);
    }

    public void testGetAllBarsByParcourId() {
        // On insert un parcours
        String nom = "mon parcour";
        String description = "Description du parcour";
        datasource.createParcours(nom, description);

        // On insert des bars
        String nameBar1 = "LE PETIT VELO";
        String nameBar2 = "TIO PAQUITO";
        String nameBar3 = "NAME TEST";
        datasource.createBar(nameBar1, "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866","url");
        datasource.createBar(nameBar2, "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188","url");
        datasource.createBar(nameBar3, "14 rue Test 35000 Rennes", "0299743295", "48.1128963", "-1.6812188","url");

        // Insertion des bars dans le parcours 1
        datasource.insertBarIntoParcours(1, 1);
        datasource.insertBarIntoParcours(1, 2);
        datasource.insertBarIntoParcours(1, 3);

        List<Bar> bars = datasource.getAllBarsOfParcours(1);

        // Test de la taille du
        Assert.assertTrue("Taille de la liste retournée NOK", bars.size() == 3);

        // Test du nom des 3 bars retournés
        Assert.assertEquals(nameBar1, bars.get(0).getName());
        Assert.assertEquals(nameBar2, bars.get(1).getName());
        Assert.assertEquals(nameBar3, bars.get(2).getName());
    }

    public void testDeleteBarInParcours() {
        // On insert un parcours
        String nom = "mon parcours";
        String description = "Description du parcour";
        Parcours parcours = datasource.createParcours(nom, description);

        // On insert des bars
        String nameBar1 = "LE PETIT VELO";
        String nameBar2 = "TIO PAQUITO";
        String nameBar3 = "NAME TEST";
        datasource.createBar(nameBar1, "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866","url");
        datasource.createBar(nameBar2, "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188","url");
        datasource.createBar(nameBar3, "14 rue Test 35000 Rennes", "0299743295", "48.1128963", "-1.6812188","url");

        // Insertion des bars dans le parcours 1
        datasource.insertBarIntoParcours(1, 1);
        datasource.insertBarIntoParcours(1, 2);
        datasource.insertBarIntoParcours(1, 1);

        // On supprime un bar
        //Bar bar = datasource.getBarById(2);
        //datasource.deleteBarInParcours(parcours, bar);

        // On récupère la liste complète des bars
        List<Bar> bars = datasource.getAllBarsOfParcours(1);

        // Puis on test la taille et le nom des bars
        Assert.assertEquals(2, bars.size());
        Assert.assertEquals(nameBar1, bars.get(0).getName());
        Assert.assertEquals(nameBar2, bars.get(1).getName());

        // On supprime le premier bar
        Bar bar1 = datasource.getBarById(1);
        datasource.deleteBarInParcours(parcours, bar1);

        // On récupère la liste complète des bars (maj)
        bars = datasource.getAllBarsOfParcours(1);

        // Puis test la taille et le nom du bar restant (#3)
        //Assert.assertEquals(1, bars.size()); // n'en reste plus qu'un
        //Assert.assertEquals(nameBar3, bars.get(0).getName());
    }

    public void testDeleteParcours() {
        // On insert un parcours
        String nom = "mon parcours";
        String description = "Description du parcours";
        Parcours parcours = datasource.createParcours(nom, description);

        // On insert des bars
        String nameBar1 = "LE PETIT VELO";
        String nameBar2 = "TIO PAQUITO";
        String nameBar3 = "NAME TEST";
        datasource.createBar(nameBar1, "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866","url");
        datasource.createBar(nameBar2, "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188","url");
        datasource.createBar(nameBar3, "14 rue Test 35000 Rennes", "0299743295", "48.1128963", "-1.6812188","url");

        // Insertion des bars dans le parcours 1
        datasource.insertBarIntoParcours(1, 1);
        datasource.insertBarIntoParcours(1, 2);
        datasource.insertBarIntoParcours(1, 3);

        // Puis on supprime le parcours
        datasource.deleteParcours(parcours);

        // test de recup le parcours delete
        Parcours parcoursTest = datasource.getParcoursById(1);
        Assert.assertTrue(parcoursTest == null);
    }
}