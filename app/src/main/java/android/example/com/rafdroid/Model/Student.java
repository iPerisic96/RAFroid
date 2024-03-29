package android.example.com.rafdroid.Model;

import java.io.Serializable;

public class Student implements Serializable {

    private String name;
    private String index;

    public Student(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
