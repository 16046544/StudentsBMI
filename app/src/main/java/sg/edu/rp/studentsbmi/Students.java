package sg.edu.rp.studentsbmi;

public class Students {

    private int id;
    private Double height, weight, bmi;
    private String firstname, lastname, classroom;

    public Students(int id, Double height, Double weight, Double bmi, String firstname, String lastname, String classroom) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.bmi = bmi;
        this.firstname = firstname;
        this.lastname = lastname;
        this.classroom = classroom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
