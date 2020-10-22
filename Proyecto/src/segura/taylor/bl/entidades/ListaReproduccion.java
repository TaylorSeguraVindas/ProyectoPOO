package segura.taylor.bl.entidades;

import java.util.ArrayList;
import java.util.Objects;

public class ListaReproduccion extends RepositorioCanciones {
    //Variables
    private String idCreador;
    private double calificacion;
    private String imagen;

    //Propiedades
    public String getIdCreador() {
        return idCreador;
    }
    public void setIdCreador(String idCreador) {
        this.idCreador = idCreador;
    }

    public double getCalificacion() {
        return calificacion;
    }
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    //Constructores
    public ListaReproduccion(){}
    public ListaReproduccion(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String idCreador, double calificacion, String imagen) {
        super(id, nombre, fechaCreacion, canciones);
        this.idCreador = idCreador;
        this.calificacion = calificacion;
        this.imagen = imagen;
    }

    //Metodos

    @Override
    public String toString() {
        return "ListaReproduccion{" +
                "idCreador='" + idCreador + '\'' +
                ", calificacion=" + calificacion +
                ", imagen='" + imagen + '\'' +
                ", id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", canciones=" + canciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ListaReproduccion that = (ListaReproduccion) o;
        return Double.compare(that.calificacion, calificacion) == 0 &&
                Objects.equals(idCreador, that.idCreador) &&
                Objects.equals(imagen, that.imagen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCreador, calificacion, imagen);
    }

    public Cancion cargarCancion(int posicion){
        if(posicion < canciones.size()){
            return canciones.get(posicion);
        }
        return null;
    }

    public boolean modificar(String pNombre, String pImagen){
        this.nombre = (!pNombre.equals("")) ? pNombre : this.nombre;
        this.imagen = (!pImagen.equals("")) ? pImagen : this.imagen;
        return true;
    }
}
