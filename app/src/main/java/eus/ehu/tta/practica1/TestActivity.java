package eus.ehu.tta.practica1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Test test = new Test();
        TextView pregunta = (TextView)findViewById(R.id.pregunta);
        pregunta.setText(test.getPregunta());
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        int i = 0;
        for (Test.Choice choice : test.getChoices()){
            RadioButton radio = new RadioButton(this);
            radio.setText(choice.getWording());
            radio.setOnClickListener(this);
            group.addView(radio);
            if( choice.isCorrect() ){
                correct = i;
            }
            i++;
        }
    }

    public void send(View view){
        RadioGroup group = (RadioGroup)findViewById(R.id.test_choices);
        ViewGroup layout = (ViewGroup)view.getParent();
        int selected = group.getCheckedRadioButtonId()-1;
        int choices = group.getChildCount();
        for (int i = 0; i < choices; i++){
            group.getChildAt(i).setEnabled(false);
        }
        layout.removeView(findViewById(R.id.button_send_test));
        group.getChildAt(correct).setBackgroundColor(Color.GREEN);
        if (selected != correct){
            group.getChildAt(selected).setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
            findViewById(R.id.button_view_advise).setVisibility(View.VISIBLE);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Correcto",Toast.LENGTH_SHORT).show();

        }

    }

    public void viewAdvise(View view){
        ViewGroup layout = (ViewGroup)view.getParent();
        WebView web = new WebView(this);
        web.loadData("<p> Aqui insertamos el consejo <b>en modo HTML</b></br>Esto es la segunda linea</p>","text/html",null);
        web.setBackgroundColor(Color.TRANSPARENT);
        web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
        layout.addView(web);

    }

    @Override
    public void onClick(View view){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
    }

}
