package com.example.hvallee.barathon;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hvallee.barathon.DAO.BarsDataSource;
import com.example.hvallee.barathon.Model.Parcours;

public class CreateParcours extends AppCompatActivity {

    private BarsDataSource dataSource;
    private EditText name;
    private EditText description;
    private Button validate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parcours);

        // Masquer l'ActionBar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        dataSource = new BarsDataSource(this);
        dataSource.open();

        name = (EditText)findViewById(R.id.name_new_parcours);
        description = (EditText)findViewById(R.id.desc_new_parcours);
        validate = (Button)findViewById(R.id.button_validate);

        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parcours p = dataSource.createParcours(name.getText().toString(), description.getText().toString());

                if(p != null) {
                    dataSource.close();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Impossible de créer le parcours, un parcours portant ce nom existe déjà !", Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
