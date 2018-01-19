package eus.ehu.tta.practica1;

import android.content.Context;
import android.net.Uri;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by endika on 12/01/18.
 */

public interface ServerInterface {



    boolean authenticate (String login, String password);

    //Exercise getExercise();

    
    Test getTest(String id, String password, String baseUrl, User user) throws JSONException;

    void sendFile(String id, String password, String baseUrl, User user, Uri uri, Context context) throws IOException;


    User getUser(String id, String password, String baseUrl) throws JSONException, IOException;

    Exercise getExercise(String dni, String password, String string, User user) throws JSONException;

    public void sendChoice (String id, String password, String baseUrl, User user, int selected) throws IOException;
    }
