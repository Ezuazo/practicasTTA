package eus.ehu.tta.practica1;

import android.net.Uri;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by endika on 12/01/18.
 */

public interface ServerInterface {



    boolean authenticate (String login, String password);

    //Exercise getExercise();

    Test getTest();

    void sendFile(Uri uri);


    User getUser(String id, String password, String baseUrl) throws JSONException, IOException;
}
