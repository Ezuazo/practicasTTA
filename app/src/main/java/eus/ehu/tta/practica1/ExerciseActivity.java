package eus.ehu.tta.practica1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

    public void sendFile(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }
    public void sendPhoto(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }
    public void sendAudio(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }
    public void sendVideo(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }
}
