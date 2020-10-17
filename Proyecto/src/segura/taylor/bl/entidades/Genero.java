package segura.taylor.bl.entidades;

import java.util.Objects;

public class Genero {
    //Variables
    private String nombre;
    private String descripcion;

    //Propiedades
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores
    public Genero(){}
    public Genero(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return Objects.equals(nombre, genero.nombre) &&
                Objects.equals(descripcion, genero.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, descripcion);
    }

    public boolean modificar(String pNombre, String pDesc){
        this.nombre = pNombre;
        this.descripcion = pDesc;
        return true;
    }
}
