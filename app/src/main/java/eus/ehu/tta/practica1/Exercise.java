package eus.ehu.tta.practica1;

import java.io.Serializable;

/**
 * Created by endika on 19/01/18.
 */

public class Exercise implements Serializable {

    int id;
    String wording;
    LessonBean bean;

    public Exercise() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWording() {
        return wording;
    }

    public void setWording(String wording) {
        this.wording = wording;
    }

    public LessonBean getBean() {
        return bean;
    }

    public void setBean(LessonBean bean) {
        this.bean = bean;
    }


    public static class LessonBean implements Serializable {

        private int id;
        private String title;
        private int number;

        public LessonBean() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}