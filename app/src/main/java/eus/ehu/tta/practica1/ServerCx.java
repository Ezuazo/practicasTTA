package eus.ehu.tta.practica1;

import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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
    public Test getTest(String id, String password, String baseUrl, User user) throws JSONException {

        Test test = null;
        client = new RestClient(baseUrl);
        client.setHttpBasicAuth(id, password);
        JSONObject testJSON = null;
        try {
            testJSON = client.getJson(String.format("getTest?id=%d", user.getNexttest()));
        } catch (IOException e) {
            System.out.println("excepticon");
            return null;
        }
        test = rellenarTest(testJSON);
        return test ;
    }


    @Override
    public void sendFile(Uri uri) {

    }

    @Override
    public User getUser(String id, String password, String baseUrl) throws JSONException {
        User user = null;
        client = new RestClient(baseUrl);
        client.setHttpBasicAuth(id,password);
        try {
            JSONObject userJSON = client.getJson(String.format("getStatus?dni=%s", id));
            user = rellenarUser(userJSON);
            user.setDni(id);
            user.setPassword(password);
        } catch ( IOException e){
            return null;
        }

        return user;
    }

    @Override
    public Exercise getExercise(String id, String password, String baseUrl, User user) throws JSONException {
        Exercise exercise = null;
        client = new RestClient(baseUrl);
        client.setHttpBasicAuth(id,password);
        JSONObject exerciseJSON = null;
        try {
            exerciseJSON = client.getJson(String.format("getExercise?id=%d", user.getNexttest()));
        } catch ( IOException e){
            return null;
        }
        exercise = rellenarExercise(exerciseJSON);

        return exercise;
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
            return null;
        }
        return user;
    }

    private Exercise rellenarExercise(JSONObject testJSON)  {

        Exercise exercise = new Exercise();
        try{
            exercise.setId(testJSON.getInt("id"));
            exercise.setWording(testJSON.getString("wording"));
            JSONObject beanJSON = testJSON.getJSONObject("lessonBean");
            Exercise.LessonBean bean = new Exercise.LessonBean();
            bean.setId(beanJSON.getInt("id"));
            bean.setNumber(beanJSON.getInt("number"));
            bean.setTitle(beanJSON.getString("title"));
            exercise.setBean(bean);
        } catch (JSONException e){
            return null;
        }
        return exercise;
    }

    private Test rellenarTest(JSONObject testJSON)  {
        Test test = new Test();
        JSONArray choices = new JSONArray();

        System.out.println("rellenar");

        try {
            choices = testJSON.getJSONArray("choices");
            test.setPregunta(testJSON.getString("wording"));
        } catch (JSONException e) {
            return null;
        }
        System.out.println(choices.length());
        for (int i = 0; i< choices.length();i++){
            Test.Choice choice = new Test.Choice();
            JSONObject choiceJSON = new JSONObject();
            try {
                choiceJSON = choices.getJSONObject(i);

            choice.setId(choiceJSON.getInt("id"));
            choice.setRespuesta(choiceJSON.getString("answer"));
            choice.setCorrecto(choiceJSON.getBoolean("correct"));
            choice.setRecurso(choiceJSON.optString("advise",null));
            if (!choiceJSON.isNull("resourceType")){
                JSONObject resType = choiceJSON.getJSONObject("resourceType");
                choice.setTipo(resType.getString("mime"));
            }
            else{
                choice.setTipo(null);
            }
            } catch (JSONException e) {
                System.out.println("roto");
            }

            System.out.println(choice.getCorrecto());
            //System.out.println(choice.getTipo());
            test.getChoices().add(choice);
        }

        return test;
    }
}

