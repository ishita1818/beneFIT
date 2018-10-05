package tech.iosd.benefit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ViewGroup;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import tech.iosd.benefit.Adapters.WorkoutSaveActivityAdapter;
import tech.iosd.benefit.DashboardFragments.SaveWorkout;


public class SaveWorkoutActivity extends AppCompatActivity implements View.OnClickListener {
    Button saveActivity;TextView workoutName;
    Button happy,neutral,sad;
    private ArrayList<String> exercises = new ArrayList<>();
    private WorkoutSaveActivityAdapter adapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_save_activity);
        exercises= getIntent().getStringArrayListExtra("VIDEO_ITEM");
        recyclerView = findViewById(R.id.save_workouts_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WorkoutSaveActivityAdapter(exercises,SaveWorkoutActivity.this);
        recyclerView.setAdapter(adapter);
        saveActivity=findViewById(R.id.complete_save_activity);
        workoutName=findViewById(R.id.workout_name);
        workoutName.setText("HIGH INTENSITY INTERVAL TRAINING");
        saveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        happy=findViewById(R.id.happy);
        sad=findViewById(R.id.sad);
        neutral=findViewById(R.id.neutral);
        happy.setOnClickListener(this);
        neutral.setOnClickListener(this);
        sad.setOnClickListener(this);
    }
    boolean happySelected=false,neutralSelected=false,sadSelected=false;

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.happy: {
                happy.setBackgroundResource(R.drawable.ic_happy_on);
                neutral.setBackgroundResource(R.drawable.ic_neutral_off);
                sad.setBackgroundResource(R.drawable.ic_sad_off);
                break;
            }

            case R.id.neutral:
            {
                happy.setBackgroundResource(R.drawable.ic_happy_off);
                neutral.setBackgroundResource(R.drawable.ic_neutral_on);
                sad.setBackgroundResource(R.drawable.ic_sad_off);
                break;
            }

            case R.id.sad:
            {
                happy.setBackgroundResource(R.drawable.ic_happy_off);
                neutral.setBackgroundResource(R.drawable.ic_neutral_off);
                sad.setBackgroundResource(R.drawable.ic_sad_on);
                break;
            }
        }
    }
}
