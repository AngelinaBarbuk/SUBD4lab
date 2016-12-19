package entity;

/**
 * Created by B on 09.10.2016.
 */
public class Person {

    private int id;
    private String name;
    private String nameRu;

    public Person() {
    }

    public Person(int id, String name, String nameRu) {
        this.id = id;
        this.name = name;
        this.nameRu = nameRu;
    }

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

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    @Override
    public String toString() {
        String str ="nameRu: " + nameRu;
        if(name==null)
            return str;
        else
            return str+", name: " + name;
    }
}
