package eus.ehu.tta.practica1;

import android.content.Context;
import android.database.Cursor;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    public void sendFile(String id, String password, String baseUrl, User user, Uri uri, Context context) throws IOException {

        client = new RestClient(baseUrl);
        InputStream is=null;
        String fileName = null;
        if(uri.toString().startsWith("file")){
            try {
            is = new FileInputStream(uri.toString().substring(7));
            String[] pathSections = uri.toString().split("/");
            fileName = pathSections[pathSections.length-1];
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                is = context.getContentResolver().openInputStream(uri);
                fileName= dumpImageMetaData(uri,context);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(is != null && !fileName.isEmpty() && fileName!=null){
            client.setHttpBasicAuth(id, password);
            int code = client.postFile(String.format("postExercise?user%d&id=%d",user.getId(),user.getNextexercise()),is,fileName);
            System.out.println(code);
        }

    }

    public void sendChoice (String id, String password, String baseUrl, User user, int selected) throws IOException {

        client = new RestClient(baseUrl);
        JSONObject choiceJson= new JSONObject();

        client.setHttpBasicAuth(id,password);
        try {
            choiceJson.put("userId",user.getId());
            choiceJson.put("choiceId",selected);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int code = client.postJson(choiceJson,"postChoice");
        System.out.println(code);
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
            test.getChoices().add(choice);
        }

        return test;
    }

    public String dumpImageMetaData(Uri uri, Context context){

        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null);

        String displayName = null;
        try{
            if (cursor != null && cursor.moveToFirst()){
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

                int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
                String size = null;
                if (!cursor.isNull(sizeIndex)){
                    size = cursor.getString(sizeIndex);
                }
                else{
                    size = "Unknown";
                }
            }
        } finally {
            cursor.close();
            return displayName;
        }
    }
}

