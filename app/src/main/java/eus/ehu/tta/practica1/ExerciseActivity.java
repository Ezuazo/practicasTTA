package eus.ehu.tta.practica1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class ExerciseActivity extends AppCompatActivity {

    public final static int PICTURE_REQUEST_CODE = 1;
    public final static int VIDEO_REQUEST_CODE = 2;
    public final static int AUDIO_REQUEST_CODE = 3;
    public final static int READ_REQUEST_CODE = 4;

    Uri pictureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }

    public void sendFile(View view){

        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();

    }
    public void sendPhoto(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(getApplicationContext(),"No tienes camara, cafre",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null){
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile("tta", ".jpg", dir);
                    pictureUri = Uri.fromFile(file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                    startActivityForResult(intent, PICTURE_REQUEST_CODE);
                } catch (IOException e){}
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No tienes app de camara, cafre",Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void sendAudio(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }
    public void sendVideo(View view){
        Toast.makeText(getApplicationContext(),R.string.nofunction,Toast.LENGTH_SHORT).show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode !=Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case READ_REQUEST_CODE:
            case VIDEO_REQUEST_CODE:
            case AUDIO_REQUEST_CODE:
                sendArchivo(data.getData());
                break;
            case PICTURE_REQUEST_CODE:
                sendArchivo(pictureUri);
                break;
        }
    }
    public void sendArchivo(Uri uri){

    }
}
