package eus.ehu.tta.practica1;

import java.io.Serializable;

/**
 * Created by endika on 17/01/18.
 */

public class User implements Serializable{

    int id;
    String dni;
    String name;
    String lessonnum;
    String lessontitle;
    int nexttest;
    int nextexercise;
    String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLessonnum() {
        return lessonnum;
    }

    public void setLessonnum(String lessonnum) {
        this.lessonnum = lessonnum;
    }

    public String getLessontitle() {
        return lessontitle;
    }

    public void setLessontitle(String lessontitle) {
        this.lessontitle = lessontitle;
    }

    public int getNexttest() {
        return nexttest;
    }

    public void setNexttest(int nexttest) {
        this.nexttest = nexttest;
    }

    public int getNextexercise() {
        return nextexercise;
    }

    public void setNextexercise(int nextexercise) {
        this.nextexercise = nextexercise;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
