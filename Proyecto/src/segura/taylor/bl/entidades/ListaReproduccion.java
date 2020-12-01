package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;
import segura.taylor.bl.interfaces.IComboBoxItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ListaReproduccion extends RepositorioCanciones implements IComboBoxItem {
    //Variables
    private Usuario creador;
    private double calificacion;
    private String imagen;
    private String descripcion;

    //Propiedades
    public Usuario getCreador() {
        return creador;
    }
    public void setCreador(Usuario Creador) {
        this.creador = Creador;
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

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores
    public ListaReproduccion(){
        this.tipoRepo = TipoRepositorioCanciones.LISTA_REPRODUCCION;
    }
    public ListaReproduccion(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, Usuario creador, double calificacion, String imagen, String descripcion) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.LISTA_REPRODUCCION;
        this.creador = creador;
        this.calificacion = calificacion;
        this.imagen = imagen;
        this.descripcion = descripcion;
    }

    //Metodos

    @Override
    public String toString() {
        return "ListaReproduccion{" +
                "ID='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", creador='" + creador + '\'' +
                ", calificacion=" + calificacion +
                ", imagen='" + imagen + '\'' +
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
                Objects.equals(creador, that.creador) &&
                Objects.equals(imagen, that.imagen) &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), creador, calificacion, imagen, descripcion);
    }

    public Cancion cargarCancion(int posicion){
        if(posicion < canciones.size()){
            return canciones.get(posicion);
        }
        return null;
    }

    public boolean modificar(String pNombre, String pImagen, String pDescripcion){
        this.nombre = pNombre;
        this.imagen = pImagen;
        this.descripcion = pDescripcion;
        return true;
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
