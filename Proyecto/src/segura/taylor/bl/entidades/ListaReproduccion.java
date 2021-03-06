package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoListaReproduccion;
import segura.taylor.bl.enums.TipoRepositorioCanciones;
import segura.taylor.bl.interfaces.IComboBoxItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ListaReproduccion extends RepositorioCanciones implements IComboBoxItem {
    //Variables
    private TipoListaReproduccion tipoLista;
    private double calificacion;
    private String imagen;
    private String descripcion;

    //Propiedades
    public TipoListaReproduccion getTipoLista() {
        return tipoLista;
    }
    public void setTipoLista(TipoListaReproduccion tipoLista) {
        this.tipoLista = tipoLista;
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

    /**
     * Método constructor por defecto
     */
    public ListaReproduccion(){
        this.tipoRepo = TipoRepositorioCanciones.LISTA_REPRODUCCION;
    }

    /**
     * Método constructor
     * @param tipo Enum que define el tipo de lista de reproduccion TIENDA o USUARIO
     * @param nombre String que define el nombre
     * @param fechaCreacion LocalDate que define la fecha de creacion
     * @param canciones ArrayList que define las canciones que pertenecen a esta lista de reproduccion
     * @param calificacion int que define la calificacion promedio
     * @param imagen String que define la ruta de la imagen
     * @param descripcion Stirng que define la calificacion
     */
    public ListaReproduccion(TipoListaReproduccion tipo, String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, double calificacion, String imagen, String descripcion) {
        super(nombre, fechaCreacion, canciones);
        this.tipoLista = tipo;
        this.tipoRepo = TipoRepositorioCanciones.LISTA_REPRODUCCION;
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
                Objects.equals(imagen, that.imagen) &&
                Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), calificacion, imagen, descripcion);
    }

    /**
     * Método usado par obtener una canción ubicada en X posicion
     * @param posicion int que define el indice de la cancion que se desea obtener
     * @return instancia de la clase Cancion que se encuentra en la posicion especificada
     */
    public Cancion cargarCancion(int posicion){
        if(posicion < canciones.size()){
            return canciones.get(posicion);
        }
        return null;
    }

    public double calcularCalificacion() {
        double promedio;
        double acum = 0.0;
        int calificacionesValidas = 0;

        for (Cancion cancion : canciones) {
            System.out.println("Cal cancion: " + cancion.getCalificacionPromedio());
            acum += cancion.getCalificacionPromedio();
            calificacionesValidas++;
        }

        System.out.println("Acumulado: " + acum);
        System.out.println("Canciones: " + calificacionesValidas);

        promedio = acum / calificacionesValidas;
        calificacion = promedio;
        return calificacion;
    }
    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
