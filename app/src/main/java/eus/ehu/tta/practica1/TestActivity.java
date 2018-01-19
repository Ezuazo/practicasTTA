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

import java.io.IOException;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TEST = "test";
    public static final String EXTRA_USER = "user";
    int correct = 0;
    private int selected;
    RadioGroup group;
    Test test;
    ServerCx server;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        test = (Test)intent.getSerializableExtra(EXTRA_TEST);
        user = (User)intent.getSerializableExtra(EXTRA_USER);
        TextView pregunta = (TextView)findViewById(R.id.pregunta);
        pregunta.setText(test.getPregunta());
        group = (RadioGroup)findViewById(R.id.test_choices);
        int i = 0;
        for (Test.Choice choice : test.getChoices()){
            RadioButton radio = new RadioButton(this);
            radio.setText(choice.getRespuesta());
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

        uploadJson();

    }

    public void uploadJson(){
        new ProgessTask<Void>(this){
            @Override
            protected Void work() throws Exception{

                server = new ServerCx();
                server.sendChoice(user.getDni(),user.getPassword(),getResources().getString(R.string.baseUrl),user,selected);
                return null;
            }

            @Override
            protected void onFinish(Void user) {
                Toast.makeText(getApplicationContext(),"JSON subido",Toast.LENGTH_SHORT).show();

            }

        }.execute();
    }

    public void viewAdvise(View view) throws IOException {

        List<Test.Choice> todas = test.getChoices();
        Test.Choice elegida = todas.get(selected);

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
        else if(tipo.equals("video/mp4")){
            showVideo(ayuda, layout);
        }
        else if(tipo.equals("audio/mpeg")){
            showAudio(ayuda, view);
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

    private void showAudio(String ayuda, View view) throws IOException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AudioPlayer audio = new AudioPlayer(view,runnable);
        audio.setAudioUri(Uri.parse(ayuda));
        audio.start();
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
        selected = group.indexOfChild(view);
    }

}
