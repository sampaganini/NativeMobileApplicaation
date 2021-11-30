package com.example.fixnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private ListView chooseSpecialist;
    private String choosed;
    private Button search;
    private Button getSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        search = findViewById(R.id.button8);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        String[] specialists = {"Mechanik", "Hydraulik", "Ślusarz"};
        chooseSpecialist = findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item,specialists);
        chooseSpecialist.setAdapter(adapter);
        chooseSpecialist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                choosed = ((TextView) view).getText().toString();
            }
        });
    }
}