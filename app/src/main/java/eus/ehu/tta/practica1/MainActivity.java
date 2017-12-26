package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
        Intent intent = new Intent(this,MenuActivity.class);
        String login = ((EditText)findViewById(R.id.login)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwd)).getText().toString();

        if (authenticate(login,password))
        {
            intent.putExtra(MenuActivity.EXTRA_LOGIN, login);
            startActivity(intent);
        }else {
            Toast.makeText(getApplicationContext(),R.string.errorlogin,Toast.LENGTH_SHORT).show();
        }
    }

    private boolean authenticate(String login, String password){

        //Aqui habria que checkear con el servidor
        return true;
    }
}
