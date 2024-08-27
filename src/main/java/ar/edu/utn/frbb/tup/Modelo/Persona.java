package ar.edu.utn.frbb.tup.Modelo;

public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private long dni;
    private String telefono;

    public Persona(int id, String nombre, String apellido, long dni, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    //setters y getters
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public void setDni(long dni) {
        this.dni = dni;
    }

    public long getDni() {
        return dni;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefono() {
        return telefono;
    }
}
