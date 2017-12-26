package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_LOGIN = "login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        String logintext = intent.getStringExtra(EXTRA_LOGIN);
        TextView bienvenido = (TextView)findViewById(R.id.menu_login);
        bienvenido.setText(getString(R.string.Bienvenida)+" " +logintext);
    }

    public void follow(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }

    public void test(View view){
        Intent intent = new Intent(this,TestActivity.class);
        startActivity(intent);

    }

    public void exercise(View view){
        Intent intent = new Intent(this,ExerciseActivity.class);
        startActivity(intent);

    }
}
