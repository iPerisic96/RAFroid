package android.example.com.rafdroid.Model;

import java.io.Serializable;

public class Classroom implements Serializable {

    private String name;

    public Classroom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
