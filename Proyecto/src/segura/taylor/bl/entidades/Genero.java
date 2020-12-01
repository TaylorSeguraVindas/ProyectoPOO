package segura.taylor.bl.entidades;

import segura.taylor.bl.interfaces.IComboBoxItem;

import java.util.Objects;

public class Genero implements IComboBoxItem {
    //Variables
    public static int idGeneros = 0;
    private int id;
    private String nombre;
    private String descripcion;

    //Propiedades
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

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
        this.id = idGeneros++;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Genero{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genero genero = (Genero) o;
        return Objects.equals(id, genero.id) &&
                Objects.equals(nombre, genero.nombre) &&
                Objects.equals(descripcion, genero.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion);
    }

    public boolean modificar(String pNombre, String pDesc){
        this.nombre = (!pNombre.equals("")) ? pNombre : this.nombre;
        this.descripcion = (!pDesc.equals("")) ? pDesc : this.descripcion;
        return true;
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
