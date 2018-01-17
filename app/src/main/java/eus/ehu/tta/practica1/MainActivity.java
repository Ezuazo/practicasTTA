package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ServerCx server;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        server = new ServerCx();
    }

    public void login(View view){

        String login = ((EditText)findViewById(R.id.login)).getText().toString();
        String password = ((EditText)findViewById(R.id.passwd)).getText().toString();

        getInfo(login,password);

    }

    private void getInfo(final String id, final String pass){

        new ProgessTask<User>(this){
            @Override
            protected User work() throws Exception{

                return server.getUser(id,pass,this.context.getResources().getString(R.string.baseUrl));
            }

            @Override
            protected void onFinish(User user) {


                if(user!= null) {
                    Intent intent = new Intent(this.context, MenuActivity.class);
                    intent.putExtra(MenuActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
                else{
                    System.out.println("erorr user null");
                }
            }

        }.execute();

    }
}
