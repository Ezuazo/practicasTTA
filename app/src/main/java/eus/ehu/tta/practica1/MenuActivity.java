package eus.ehu.tta.practica1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "user";
    private ServerCx server;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra(EXTRA_USER);
        TextView bienvenido = (TextView)findViewById(R.id.menu_login);
        bienvenido.setText(getString(R.string.Bienvenida)+" " +user.getName());
        server = new ServerCx();
    }

    public void follow(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }

    public void test(View view){
        new ProgessTask<Test>(this){
            @Override
            protected Test work() throws Exception{

                return server.getTest(user.getDni(),user.getPassword(),getString(R.string.baseUrl),user);
            }

            @Override
            protected void onFinish(Test test) {


                if(test!= null) {
                    Intent intent = new Intent(this.context, TestActivity.class);
                    intent.putExtra(TestActivity.EXTRA_TEST, test);
                    intent.putExtra(ExerciseActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
                else{
                    System.out.println("erorr test null");
                }
            }

        }.execute();

    }

    public void exercise(View view){


        new ProgessTask<Exercise>(this){
            @Override
            protected Exercise work() throws Exception{

                return server.getExercise(user.getDni(),user.getPassword(),getString(R.string.baseUrl),user);
            }

            @Override
            protected void onFinish(Exercise exercise) {


                if(exercise!= null) {
                    Intent intent = new Intent(this.context,ExerciseActivity.class);
                    intent.putExtra(ExerciseActivity.EXTRA_EXERCISE, exercise);
                    intent.putExtra(ExerciseActivity.EXTRA_USER, user);
                    startActivity(intent);
                }
                else{
                    System.out.println("erorr exercise null");
                }
            }

        }.execute();

    }
}
