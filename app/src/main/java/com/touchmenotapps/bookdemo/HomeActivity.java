package com.touchmenotapps.bookdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_select_interactivity_list)
    ListViewCompat optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position) {
                    case 0:
                        intent = new Intent(HomeActivity.this, FlipbookActivity.class);
                        break;
                    case 1:
                        intent = new Intent(HomeActivity.this, InteractiveBookActivity.class);
                        break;
                    case 2:
                        intent = new Intent(HomeActivity.this, FeesActivity.class);
                        break;
                    case 3:
                        intent = new Intent(HomeActivity.this, AttendanceActivity.class);
                        break;
                }
                if(intent != null) {
                    startActivity(intent);
                }
            }
        });
    }
}
