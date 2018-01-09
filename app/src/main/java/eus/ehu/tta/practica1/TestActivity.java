package eus.ehu.tta.practica1;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    int correct = 0;
    RadioGroup group;
    Test test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        test = new Test();
        TextView pregunta = (TextView)findViewById(R.id.pregunta);
        pregunta.setText(test.getPregunta());
        group = (RadioGroup)findViewById(R.id.test_choices);
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

        List<Test.Choice> todas = test.getChoices();
        Test.Choice elegida = todas.get(group.getCheckedRadioButtonId()-1);

        String tipo= elegida.getTipo();
        String ayuda= elegida.getRecurso();
        ViewGroup layout = (ViewGroup)view.getParent();


        if(tipo.equals("text/html")){
            if(ayuda.substring(0,10).contains("://")) {
                Uri uri = Uri.parse(ayuda);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
            else
            {
                WebView web = new WebView(this);
                web.loadData(ayuda,"text/html",null);
                web.setBackgroundColor(Color.TRANSPARENT);
                web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
                layout.addView(web);
            }
        }
        else if(tipo.equals("video")){
            showVideo(ayuda, layout);
        }
        else if(tipo.equals("audio")){
            showAudio(ayuda, layout);
        }
        else
        {
            WebView web = new WebView(this);
            web.loadData("<p> Aqui insertamos el consejo <b>por defecto</b></br>Solo si no es de los tipos definidos</p>","text/html",null);
            web.setBackgroundColor(Color.TRANSPARENT);
            web.setLayerType(WebView.LAYER_TYPE_SOFTWARE,null);
            layout.addView(web);
        }






    }

    private void showAudio(String ayuda, ViewGroup layout) {

    }


    private void showVideo(String ayuda, ViewGroup layout) {
        VideoView video = new VideoView(this);
        video.setVideoURI(Uri.parse(ayuda));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        video.setLayoutParams(params);
        MediaController controller = new MediaController(this){
            @Override
            public void hide(){
            }

            @Override
            public boolean dispatchKeyEvent(KeyEvent event){
                if ( event.getKeyCode() == KeyEvent.KEYCODE_BACK){
                    finish();
                }
                return super.dispatchKeyEvent(event);
            }
        };
        controller.setAnchorView(video);
        video.setMediaController(controller);
        layout.addView(video);
        video.start();




    }


    @Override
    public void onClick(View view){
        findViewById(R.id.button_send_test).setVisibility(View.VISIBLE);
    }

}
