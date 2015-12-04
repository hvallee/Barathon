package com.example.hvallee.barathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Bar;

public class BarDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_details);

        long i = getIntent().getLongExtra("id", 0);
        // context = getApplication()
        BarsDataSource barSource = new BarsDataSource(getBaseContext());
        barSource.open();
        Bar b = barSource.getBarById((int)i);
        TextView vNom = (TextView)findViewById(R.id.NomBarDetail);
        TextView vAdresse = (TextView)findViewById(R.id.AdresseBarDetail);
        TextView vNum = (TextView)findViewById(R.id.NumBarDetail);

        vNom.setText(b.getName());
        vAdresse.setText(b.getAddress());
        vNum.setText(b.getPhone());

        barSource.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
