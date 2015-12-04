/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package Barathon.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;

import javax.inject.Named;

import Barathon.backend.GoogleData.GoogleBars;
import Barathon.backend.Model.Bar;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.Barathon",
                ownerName = "backend.Barathon",
                packagePath = ""
        )
)
public class MyEndpoint {

    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    @ApiMethod(name = "bars")
    public MyBean getBars(){
        MyBean response = new MyBean();
        GoogleBars gb = new GoogleBars();

        List<Bar> listBar = gb.getBars();
        int i = 0;
        String resultBars = "{ \"results\" : [";
        while(i<listBar.size()){
            System.out.println(listBar.get(i).toString());
            resultBars+=listBar.get(i).toString();
            if (i!=listBar.size()-1)
                resultBars+=", ";
            i++;
        }
        resultBars+= "] }";

        response.setData(resultBars);
        System.out.println(resultBars);
        return response;
    }
}
