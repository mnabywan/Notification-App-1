package lemury.biletomat.model.ticket;

public class Field {
    private String name;
    private boolean required;


    public Field(String name, boolean required){
        this.name = name;
        this.required = required;
    }
}
