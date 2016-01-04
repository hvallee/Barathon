package com.example.hvallee.barathon;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Parcour;

import junit.framework.Assert;

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
}
