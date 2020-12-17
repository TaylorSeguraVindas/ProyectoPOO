package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.bl.interfaces.IComboBoxItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class Cancion implements IComboBoxItem {
    //Variables
    public static int idCanciones = 0;

    private TipoCancion tipoCancion;
    private int id;
    private String nombre;
    private String recurso;
    private double duracion;
    private Genero genero;
    private Artista artista;
    private Compositor compositor;
    private LocalDate fechaLanzamiento;
    private ArrayList<Calificacion> calificaciones;
    private double precio;

    //Propiedades
    public TipoCancion getTipoCancion() {
        return tipoCancion;
    }
    public void setTipoCancion(TipoCancion tipoCancion) {
        this.tipoCancion = tipoCancion;
    }

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

    public String getRecurso() {
        return recurso;
    }
    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public double getDuracion() {
        return duracion;
    }
    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Artista getArtista() {
        return artista;
    }
    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public Compositor getCompositor() {
        return compositor;
    }
    public void setCompositor(Compositor compositor) {
        this.compositor = compositor;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public ArrayList<Calificacion> getCalificaciones() {
        return calificaciones;
    }
    public void setCalificaciones(ArrayList<Calificacion> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    //Tablas
    public String getNombreGenero() {
        return genero.getNombre();
    }
    public String getNombreArtista() {
        return artista.getNombreArtistico();
    }
    public String getNombreCompositor() {
        return compositor.getNombre();
    }

    //Constructores

    /**
     * Método constructor por defecto
     */
    public Cancion(){
        calificaciones = new ArrayList<Calificacion>();
    }

    /**
     * Método constructor
     * @param tipoCancion valor de TipoCancion que define el tipo de cancion
     * @param nombre String que define el nombre
     * @param recurso String que define la ruta de la cancion
     * @param duracion double que define la duracion
     * @param genero instancia de la clase Genero que define el genero
     * @param artista instancia de la clase Artista que define al artista
     * @param compositor instancia de la clase Compositor que define al compositor
     * @param fechaLanzamiento LocalDate que define la fecha de lanzamiento
     * @param calificaciones ArrayList que define las calificaciones que pertenecen a esta cancion
     * @param precio double que define el precio
     */
    public Cancion(TipoCancion tipoCancion, String nombre, String recurso, double duracion, Genero genero, Artista artista, Compositor compositor, LocalDate fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio) {
        this.id = 0;
        this.tipoCancion = tipoCancion;
        this.nombre = nombre;
        this.recurso = recurso;
        this.duracion = duracion;
        this.genero = genero;
        this.artista = artista;
        this.compositor = compositor;
        this.fechaLanzamiento = fechaLanzamiento;
        this.calificaciones = calificaciones;
        this.precio = precio;
    }

    //Metodos
    @Override
    public String toString() {
        return "Cancion{" +
                "id='" + id + '\'' +
                ", tipoCancion='" + tipoCancion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", recurso='" + recurso + '\'' +
                ", duracion='" + recurso + '\'' +
                ", genero=" + genero +
                ", artista=" + artista +
                ", compositor=" + compositor +
                ", fechaLanzamiento='" + fechaLanzamiento + '\'' +
                ", calificaciones=" + calificaciones +
                ", precio=" + precio +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cancion cancion = (Cancion) o;
        return id == cancion.id &&
                Double.compare(cancion.precio, precio) == 0 &&
                tipoCancion == cancion.tipoCancion &&
                Objects.equals(nombre, cancion.nombre) &&
                Objects.equals(recurso, cancion.recurso) &&
                Objects.equals(duracion, cancion.duracion) &&
                Objects.equals(genero, cancion.genero) &&
                Objects.equals(artista, cancion.artista) &&
                Objects.equals(compositor, cancion.compositor) &&
                Objects.equals(fechaLanzamiento, cancion.fechaLanzamiento) &&
                Objects.equals(calificaciones, cancion.calificaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCancion, id, nombre, recurso, duracion, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
    }

    /**
     * Método usado para obtener el promedio de estrellas en de las calficaciones que pertenecen a esta cancion
     * @return calificacion promedio de esta cancion
     */
    public double obtenerPromedioEstrellas(){
        double acum = 0.0;
        double promedio = 0.0;

        for(int i = 0; i < calificaciones.size(); i++){
            acum += calificaciones.get(i).getEstrellas();
        }

        promedio = acum / calificaciones.size();

        return promedio;
    }

    /**
     * Método usado para agregar una calificacion
     * @param pCalificacion instancia de la clase calificacion que se desea agregar
     * @return true si la agregacion es exitosa, false si la calificacion ya existe
     * @see Calificacion
     */
    public boolean agregarCalificacion(Calificacion pCalificacion){
        if(!existeCalificacion(pCalificacion.getId())){
            calificaciones.add(pCalificacion);
        }
        return false;
    }

    /**
     * Método usado para eliminar una calificacion
     * @param pCalificacion instancia de la clase calificacion que se desea eliminar
     * @return true si la eliminacion es exitosa
     * @see Calificacion
     */
    public boolean eliminarCalificacion(Calificacion pCalificacion){
        if(existeCalificacion(pCalificacion.getId())){
            calificaciones.remove(pCalificacion);
        }
        return false;
    }

    /**
     * Método usado para modificar una calificacion
     * @param nuevaCalificacion instancia de la clase Calificacion con los cambios aplicados
     * @return true si la modificacion es exitosa
     * @see Calificacion
     */
    public boolean modificarCalificacion(Calificacion nuevaCalificacion){
        Calificacion viejaCalificacion = buscarCalificacion(nuevaCalificacion.getAutor().getId());

        if(viejaCalificacion != null){
            eliminarCalificacion(viejaCalificacion);
            agregarCalificacion(nuevaCalificacion);
            return true;
        }

        return false;
    }

    /**
     * Método usado para verificar si existe una calificacion
     * @param pIdCalificacion int que define el id de la calificacion de la que se desea verificar su existencia
     * @return true si existe, false si no
     */
    public boolean existeCalificacion(int pIdCalificacion){
        for (Calificacion objCalificacion: calificaciones) {
            if(objCalificacion.getId() == pIdCalificacion){
                return true;
            }
        }
        return false;
    }

    /**
     * Método usado para buscar una calificación usando como filtro el id de su autor
     * @param pIdUsuario int que define el id del autor de la calificacion
     * @return instancia de la clase Calificacion si se encuentra una coincidencia
     * @see Calificacion
     */
    public Calificacion buscarCalificacion(int pIdUsuario) {
        for (Calificacion objCalificacion: calificaciones) {
            if(pIdUsuario == objCalificacion.getAutor().getId()){
                return objCalificacion;
            }
        }
        return null;
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }

    public double getCalificacionPromedio() {
        double promedio;
        double acum = 0.0;
        int calificacionesValidas = 0;

        for (Calificacion calificacion : calificaciones) {
            acum += calificacion.getEstrellas();

            if(calificacion.getEstrellas() > 0) {   //Solo cuenta las que si tengan estrellas
                calificacionesValidas++;
            }
        }

        promedio = acum / calificacionesValidas;
        return promedio;
    }
}
