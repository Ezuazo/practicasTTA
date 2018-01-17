package eus.ehu.tta.practica1;

import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by endika on 17/01/18.
 */

public class ServerCx implements ServerInterface {

    RestClient client = null;

    @Override //Creo que no lo uso
    public boolean authenticate(String login, String password) {

        return login.equals(password);
    }

    @Override
    public Test getTest() {
        return null;
    }

    @Override
    public void sendFile(Uri uri) {

    }

    @Override
    public User getUser(String id, String password, String baseUrl) throws JSONException, IOException {

        client = new RestClient(baseUrl);
        client.setHttpBasicAuth(id,password);
        JSONObject userJSON = client.getJson(String.format("getStatus?dni=%s",id));
        User user = rellenarUser(userJSON);
        return user;
    }


    public User rellenarUser ( JSONObject userJSON){
        User user = new User();
        try {
        user.setId(userJSON.getInt("id"));
        user.setLessonnum(userJSON.getString("lessonNumber"));
        user.setNextexercise(Integer.parseInt(userJSON.getString("nextExercise")));
        user.setNexttest(Integer.parseInt(userJSON.getString("nextTest")));
        user.setName(userJSON.getString("user"));
        user.setLessontitle(userJSON.getString("lessonTitle"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }
}
