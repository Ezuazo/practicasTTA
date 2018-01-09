package eus.ehu.tta.practica1;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by endika on 26/12/17.
 */

public class Test {

    private String pregunta = "Pregunta...";
    private String[] resp= {"A","B","C","D"};
    private boolean[] corr= {true,false,false,false};
    private String[] mime= {"no advice","text/html","video","audio"};
    private String[] res= {"no resource","http://www.athletic-club.eus/prehome.html","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4","http://u017633.ehu.eus:28080/static/ServidorTta/AndroidManifest.mp4"};

    public Test(){
        //Rellenar pregunta y respuestas para cada instancia
    }

    public String getPregunta(){
        return pregunta;
    }
    public List<Choice> getChoices(){
        List<Choice> arraychoices = new ArrayList<Choice>();
        for(int i = 0 ; i< resp.length ; i++) {
            Choice cho = new Choice();
            cho.setRespuesta(resp[i]);
            cho.setCorrecto(corr[i]);
            cho.setRecurso(res[i]);
            cho.setTipo(mime[i]);
            arraychoices.add(cho);
        }
        return arraychoices;
    }

    public class Choice{


        private String respuesta;
        private boolean correcto;
        private String tipo;
        private String recurso;

        public Choice() {
        }

        public String getWording(){
            return respuesta;
        }

        public boolean isCorrect() {
            return correcto;
        }

        public void setRespuesta(String respuesta) {
            this.respuesta = respuesta;
        }


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
    }
}


