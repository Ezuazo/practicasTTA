package eus.ehu.tta.practica1;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;

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
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);

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
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(getApplicationContext(),"No tienes microfono, cafre",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
            if (intent.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(intent, AUDIO_REQUEST_CODE);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No tienes app de micro, cafre",Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void sendVideo(View view){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
        {
            Toast.makeText(getApplicationContext(),"No tienes camara, cafre",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, VIDEO_REQUEST_CODE);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"No tienes app de camara, cafre",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode !=Activity.RESULT_OK){
            return;
        }
        switch (requestCode){
            case READ_REQUEST_CODE:
                dumpImageMetaData(data.getData());
                sendArchivo(data.getData());
                break;
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
        //Codigo para subirlo al server
    }

    public void dumpImageMetaData(Uri uri){

        Cursor cursor = this.getContentResolver().query(uri, null, null, null, null, null);

        try{
            if (cursor != null && cursor.moveToFirst()){
                String displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                String size = null;
                if (!cursor.isNull(sizeIndex)){
                    size = cursor.getString(sizeIndex);
                }
                else{
                    size = "Unknown";
                }
                Toast.makeText(getApplicationContext(),"Nombre: "+displayName+" Tamaño: "+size,Toast.LENGTH_SHORT).show();

            }
        } finally {
            cursor.close();
        }
    }
}

