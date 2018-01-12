package eus.ehu.tta.practica1;

import android.net.Uri;

/**
 * Created by endika on 12/01/18.
 */

public interface ServerInterface {

    boolean authenticate (String login, String password);

    //Exercise getExercise();

    Test getTest();

    void sendFIile(Uri uri);


}
