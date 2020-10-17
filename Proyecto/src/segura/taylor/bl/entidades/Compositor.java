package segura.taylor.bl.entidades;

import java.util.Objects;

public class Compositor {
    //Variables
    private String nombre;
    private String apellidos;
    private String paisNacimiento;
    private String fechaNacimiento;
    private int edad;

    //Propiedades
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }
    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    //Constructores
    public Compositor(){}
    public Compositor(String nombre, String apellidos, String paisNacimiento, String fechaNacimiento, int edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.paisNacimiento = paisNacimiento;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }

    //Metodos
    @Override
    public String toString() {
        return "Compositor{" +
                "nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", edad=" + edad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compositor that = (Compositor) o;
        return edad == that.edad &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellidos, that.apellidos) &&
                Objects.equals(paisNacimiento, that.paisNacimiento) &&
                Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos, paisNacimiento, fechaNacimiento, edad);
    }
}
