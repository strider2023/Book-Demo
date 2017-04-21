package com.touchmenotapps.bookdemo;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttendanceActivity extends AppCompatActivity {

    @BindView(R.id.teacher_image) ImageView teacherImage;
    @BindView(R.id.my_attendance_header) TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        ButterKnife.bind(this);

        teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceActivity.this, MyAttendanceActivity.class);
                Pair<View, String> p1 = Pair.create((View) teacherImage, "userImage");
                Pair<View, String> p2 = Pair.create((View) header, "moduleName");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(AttendanceActivity.this, p1, p2);
                startActivity(intent, options.toBundle());
            }
        });
    }
}
