package segura.taylor.bl.entidades;

import java.util.Objects;

public class Pais {
    //Variables
    private String nombrePais;
    private String descripcion;

    //Propiedades
    public String getNombrePais() {
        return nombrePais;
    }
    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores
    public Pais(){}
    public Pais(String nombrePais, String descripcion) {
        this.nombrePais = nombrePais;
        this.descripcion = descripcion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Pais{" +
                "nombrePais='" + nombrePais + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pais pais = (Pais) o;
        return Objects.equals(nombrePais, pais.nombrePais) &&
                Objects.equals(descripcion, pais.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombrePais, descripcion);
    }
}
