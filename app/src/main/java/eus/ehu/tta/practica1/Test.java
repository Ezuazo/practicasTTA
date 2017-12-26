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
            arraychoices.add(cho);
        }
        return arraychoices;
    }

    public class Choice{


        private String respuesta;
        private boolean correcto;

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
    }
}


