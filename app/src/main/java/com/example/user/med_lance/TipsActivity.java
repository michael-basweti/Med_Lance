package com.example.user.med_lance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TipsActivity extends AppCompatActivity {

    ListView lv;
    String[] charactersDC={"Cholera","Malaria","Typhoid","Accident","Tuberculosis","Epilepsy","Asthma","Head-Injury","Diabetes","Cardiac Arrest","Labour"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        lv=(ListView)findViewById(R.id.ListView);
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,charactersDC);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    Intent cholera = new Intent(TipsActivity.this, cholera.class);
                    startActivity(cholera);
                } else if (i == 1) {

                    Intent Malaria = new Intent(TipsActivity.this, Malaria.class);
                    startActivity(Malaria);

                } else if (i == 2) {

                    Intent Typhoid = new Intent(TipsActivity.this, Typhoid.class);
                    startActivity(Typhoid);

                }
                else if (i == 3) {

                    Intent Accident = new Intent(TipsActivity.this, Accident.class);
                    startActivity(Accident);

                }
                else if (i == 4) {

                    Intent Tuberculosis = new Intent(TipsActivity.this, Tuberculosis.class);
                    startActivity(Tuberculosis);

                }

                else if (i == 5) {

                    Intent Epilepsy = new Intent(TipsActivity.this, Epilepsy.class);
                    startActivity(Epilepsy);

                }
                else if (i == 6) {

                    Intent Asthma = new Intent(TipsActivity.this, Asthma.class);
                    startActivity(Asthma);

                }
                else if (i == 7) {

                    Intent Head_Injury = new Intent(TipsActivity.this, Head_Injury.class);
                    startActivity(Head_Injury);

                }
                else if (i == 8) {

                    Intent Diabetes = new Intent(TipsActivity.this, Diabetes.class);
                    startActivity(Diabetes);

                }
                else if (i == 9) {

                    Intent Cardiac_Arrest = new Intent(TipsActivity.this, Cardiac_Arrest.class);
                    startActivity(Cardiac_Arrest);

                }
                else if (i == 10) {

                    Intent Labour = new Intent(TipsActivity.this, Labour.class);
                    startActivity(Labour);

                }
            }




        });
    }
    }


