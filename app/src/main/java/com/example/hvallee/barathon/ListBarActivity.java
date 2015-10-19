package com.example.hvallee.barathon;

import android.app.ListActivity;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListAdapter;

public class ListBarActivity extends ListActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);

            // We'll define a custom screen layout here (the one shown above), but
            // typically, you could just use the standard ListActivity layout.
            setContentView(R.layout.activity_list_bar);



            // Bind to our new adapter.
            //setListAdapter();
        }
    }
