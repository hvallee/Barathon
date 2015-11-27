import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.hvallee.barathon.R;

/**
 * Created by melissa on 20/11/2015.
 */
public class ListBarActivity2 extends ListActivity {

    private String[] mStrings = {
            "AAAAAAAA", "BBBBBBBB", "CCCCCCCC"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // We'll define a custom screen layout here (the one shown above), but
        // typically, you could just use the standard ListActivity layout.
        setContentView(R.layout.activity_main);

       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mStrings);

        //setListAdapter(adapter);

    }
}
