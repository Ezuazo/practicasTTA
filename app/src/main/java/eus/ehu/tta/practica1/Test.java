package eus.ehu.tta.practica1;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by endika on 26/12/17.
 */

public class Test implements Serializable {

    private String pregunta = "Pregunta...";
    private List<Choice> choices = new ArrayList<Choice>();

    public Test(){
        //Rellenar pregunta y respuestas para cada instancia
    }

    public String getPregunta(){
        return pregunta;
    }
    public void setPregunta(String pregunta) { this.pregunta=pregunta; }

    public void addChoice (Choice choice){
        choices.add(choice);
    }

    public List<Choice> getChoices(){

        return choices;
    }


    public void setChoices (List<Choice> choices) { this.choices = choices; }

    public static class Choice implements Serializable{

        private int id;
        private String respuesta;
        private boolean correcto;
        private String tipo;
        private String recurso;

        public Choice() {
        }



        public boolean isCorrect() {
            return correcto;
        }

        public void setRespuesta(String respuesta) {
            this.respuesta = respuesta;
        }

        public boolean getCorrecto () {return correcto;}

        public void setCorrecto(boolean correcto) {
            this.correcto = correcto;
        }

        public String getRecurso() {
            return recurso;
        }

        public void setRecurso(String recurso) {
            this.recurso = recurso;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getRespuesta() {
            return respuesta;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}


