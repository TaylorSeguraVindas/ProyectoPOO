package segura.taylor.bl.entidades;

import segura.taylor.bl.interfaces.IComboBoxItem;

import java.util.Objects;

public class Pais implements IComboBoxItem {
    //Variables
    public static int idPaises = 0;

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
    public void setNombre(String nombrePais) {
        this.nombre = nombrePais;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores

    /**
     * Método constructor por defecto
     */
    public Pais(){}

    /**
     * Método constructor
     * @param nombrePais String que define el nombre
     * @param descripcion String que define la descripcion
     */
    public Pais(String nombrePais, String descripcion) {
        this.id = 0;
        this.nombre = nombrePais;
        this.descripcion = descripcion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Pais{" +
                "id='" + id + '\'' +
                ", nombrePais='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return Objects.equals(id, pais.id) &&
                Objects.equals(nombre, pais.nombre) &&
                Objects.equals(descripcion, pais.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion);
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
