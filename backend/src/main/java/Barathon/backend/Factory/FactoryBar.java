package Barathon.backend.Factory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Barathon.backend.Model.Bar;

/**
 * Created by samyabh on 19/10/2015.
 */
public class FactoryBar {

    private static Bar b_petit_velo = new Bar(0, "LE PETIT VELO", "8 place Saint Michel 35000 Rennes", "0299795886",  "48.113361", "-1.681866");
    private static Bar b_tio_paquito = new Bar(1, "TIO PAQUITO", "16 rue Rallier du Baty 35000 Rennes", "0299781287",  "48.1128963", "-1.6812188");
    private static Bar b_lesquinade = new Bar(2, "LESQUINADE", "1 rue de Dinan 35000 Rennes", "0299311111",  "48.1061346", "-1.6954336");

    public FactoryBar() {
    }

    public static Bar getOneBar(){
        return b_petit_velo;
    }

    public static List<Bar> getBars(){
        List<Bar> listB = new ArrayList<Bar>();
        listB.add(b_petit_velo);
        listB.add(b_tio_paquito);
        listB.add(b_lesquinade);
        return listB;
    }
}
