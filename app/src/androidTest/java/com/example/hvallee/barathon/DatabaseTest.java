package com.example.hvallee.barathon;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

import junit.framework.Assert;

import java.util.List;

/**
 * Created by yoannlt on 29/11/2015.
 */
public class DatabaseTest extends AndroidTestCase {
    private BarsDataSource dataSource;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        dataSource = new BarsDataSource(context);
        dataSource.open();
        dataSource.createBar("LE PETIT VELO", "8 place Saint Michel 35000 Rennes", "0299795886", "48.113361", "-1.681866");
        dataSource.createBar("TIO PAQUITO", "16 rue Rallier du Baty 35000 Rennes", "0299781287", "48.1128963", "-1.6812188");
        dataSource.createBar("LESQUINADE", "1 rue de Dinan 35000 Rennes", "0299311111", "48.1061346", "-1.6954336");
    }

    @Override
    public void tearDown() throws Exception {
        dataSource.close();
        super.tearDown();
    }


    //@Test
    public void testAddEntryGetAll(){
        List<Bar> bars = dataSource.getAllBars();
        Assert.assertEquals(bars.get(0).getId(), 1);
        Assert.assertEquals(bars.get(1).getId(), 2);
        Assert.assertEquals(bars.get(2).getId(), 3);

        Assert.assertEquals(bars.get(0).getName(), "LE PETIT VELO");
        Assert.assertEquals(bars.get(1).getName(), "TIO PAQUITO");
        Assert.assertEquals(bars.get(2).getName(), "LESQUINADE");
    }

    //@Test
    public void testAddEntryGetById(){
        Bar bar = dataSource.getBarById(1);
        Assert.assertEquals(bar.getId(),1);

        //Test Bar 2
        Bar bar2 = dataSource.getBarById(2);
        Assert.assertEquals(bar2.getId(), 2);
        Assert.assertEquals(bar2.getPhone(), "0299781287");

        //Test Bar 3
        Bar bar3 = dataSource.getBarById(3);
        Assert.assertEquals(bar3.getId(), 3);
        Assert.assertEquals(bar3.getPhone(), "0299311111");
    }

    //@Test
    public void testAddEntryGetByName(){
        // Test Bar 1
        Bar bar1 = dataSource.getBarByName("LE PETIT VELO");
        Assert.assertEquals(bar1.getName(), "LE PETIT VELO");
        Assert.assertEquals(bar1.getPhone(), "0299795886");

        //Test Bar 2
        Bar bar2 = dataSource.getBarByName("TIO PAQUITO");
        Assert.assertEquals(bar2.getName(), "TIO PAQUITO");
        Assert.assertEquals(bar2.getPhone(), "0299781287");

        //Test Bar 3
        Bar bar3 = dataSource.getBarByName("LESQUINADE");
        Assert.assertEquals(bar3.getName(), "LESQUINADE");
        Assert.assertEquals(bar3.getPhone(), "0299311111");
    }
}
