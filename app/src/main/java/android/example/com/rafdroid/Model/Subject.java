package android.example.com.rafdroid.Model;

import java.io.Serializable;

public class Subject implements Serializable {
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
