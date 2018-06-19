package android.example.com.rafdroid.Model;

import java.io.Serializable;

public class Professor implements Serializable {

    private String name;

    public Professor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
