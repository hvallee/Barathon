package com.example.hvallee.barathon;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;
import com.example.hvallee.barathon.Model.Parcour;

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

    public void testCreateParcour(){
        // test que createparcour retourne un parcour.

        String nom = "mon parcour";
        String descritption = "Description du parcour";

        Parcour parcour = datasource.createParcour(nom, descritption);

        Assert.assertEquals(nom, parcour.getName());
        Assert.assertEquals(descritption, parcour.getDescription());
    }

    public void testGetParcourById(){

        // On insert un parcour
        String nom = "mon parcour";
        String description = "Description du parcour";
        Parcour parcour = datasource.createParcour(nom, description);
        Assert.assertEquals(parcour.getId(), 1);

        // Pour on le récupère dans la bdd
        Parcour parcour2 = datasource.getParcourById((int)parcour.getId());

        //Puis test de l'égalité des deux parcours
        Assert.assertEquals(parcour.getName(), parcour2.getName());
        Assert.assertEquals(parcour.getDescription(), parcour2.getDescription());

        // Test false
        Assert.assertFalse(parcour2.getName().equals("TEST"));
    }

    public void testinsertBarIntoParcour(){
        // On insert un parcour
        String nom = "mon parcour";
        String description = "Description du parcour";
        Parcour parcour = datasource.createParcour(nom, description);

        // On insert des bars
        datasource.createBar("LE PETIT VELO", "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866");
        datasource.createBar("TIO PAQUITO", "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188");

        datasource.insertBarintoParcour(1, 1);
        datasource.insertBarintoParcour(1, 2);
    }

    public void testGetAllBarsByParcourId() {
        // On insert un parcour
        String nom = "mon parcour";
        String description = "Description du parcour";
        Parcour parcour = datasource.createParcour(nom, description);

        // On insert des bars
        String nameBar1 = "LE PETIT VELO";
        String nameBar2 = "TIO PAQUITO";
        String nameBar3 = "NAME TEST";
        datasource.createBar(nameBar1, "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866");
        datasource.createBar(nameBar2, "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188");
        datasource.createBar(nameBar3, "14 rue Test 35000 Rennes", "0299743295", "48.1128963", "-1.6812188");

        datasource.insertBarintoParcour(1, 1);
        datasource.insertBarintoParcour(1, 2);
        datasource.insertBarintoParcour(1, 3);

        List<Bar> bars = datasource.getAllBarsOfParcour(1);

        // Test de la taille du
        Assert.assertTrue("Taille de la liste retournée NOK", bars.size() == 3);

        Assert.assertEquals(nameBar1, bars.get(0).getName());
        Assert.assertEquals(nameBar2, bars.get(1).getName());
        Assert.assertEquals(nameBar3, bars.get(2).getName());
    }
}
