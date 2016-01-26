package Barathon.backend;

/**
 * Created by samyabh on 20/11/2015.
 */

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;

import Barathon.backend.Controller.Controller;
import Barathon.backend.Factory.FactoryBar;
import Barathon.backend.GoogleData.GoogleBars;

import Barathon.backend.Model.Bar;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "apiBar",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.Barathon",
                ownerName = "backend.Barathon",
                packagePath = "bar"
        )
)
public class BarEndPoint {
    @ApiMethod(name = "bar")
    public Bar getBar(){
        return FactoryBar.getOneBar();
    }

    @ApiMethod(name = "bars")
    public List<Bar> getBars(){
        Controller c = new Controller();
        return c.getBars();
    }

    @ApiMethod(name = "majbase")
    public void miseAjour(){
        Controller c = new Controller();
        c.miseAjour();
    }
}
