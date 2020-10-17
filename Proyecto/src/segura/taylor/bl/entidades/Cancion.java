package segura.taylor.bl.entidades;

import java.util.ArrayList;
import java.util.Objects;

public class Cancion {
    //Variables
    private String id;
    private String nombre;
    private String recurso;
    private String nombreAlbum;
    private Genero genero;
    private Artista artista;
    private Compositor compositor;
    private String fechaLanzamiento;
    private ArrayList<Calificacion> calificaciones;
    private double precio;

    //Propiedades
    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public String getNombreAlbum() {
        return nombreAlbum;
    }
    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
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

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }
    public void setFechaLanzamiento(String fechaLanzamiento) {
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

    //Constructores
    public Cancion(){
        calificaciones = new ArrayList<Calificacion>();
    }

    public Cancion(String id, String nombre, String recurso, String nombreAlbum, Genero genero, Artista artista, Compositor compositor, String fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.recurso = recurso;
        this.nombreAlbum = nombreAlbum;
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
                ", nombre='" + nombre + '\'' +
                ", recurso='" + recurso + '\'' +
                ", nombreAlbum='" + nombreAlbum + '\'' +
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
        return Double.compare(cancion.precio, precio) == 0 &&
                Objects.equals(id, cancion.id) &&
                Objects.equals(nombre, cancion.nombre) &&
                Objects.equals(recurso, cancion.recurso) &&
                Objects.equals(nombreAlbum, cancion.nombreAlbum) &&
                Objects.equals(genero, cancion.genero) &&
                Objects.equals(artista, cancion.artista) &&
                Objects.equals(compositor, cancion.compositor) &&
                Objects.equals(fechaLanzamiento, cancion.fechaLanzamiento) &&
                Objects.equals(calificaciones, cancion.calificaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, recurso, nombreAlbum, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
    }

    public boolean modificar(String pNombreAlbum, double pPrecio){
        this.nombreAlbum = (pNombreAlbum != "") ? pNombreAlbum : this.nombreAlbum;
        this.precio = (pPrecio != precio) ? pPrecio : this.precio;
        return true;
    }

    public double obtenerPromedioEstrellas(){
        double acum = 0.0;
        double promedio = 0.0;

        for(int i = 0; i < calificaciones.size(); i++){
            acum += calificaciones.get(i).getEstrellas();
        }

        promedio = acum / calificaciones.size();

        return promedio;
    }

    public boolean agregarCalificacion(Calificacion pCalificacion){
        if(!existeCalificacion(pCalificacion)){
            calificaciones.add(pCalificacion);
        }
        return false;
    }

    public boolean eliminarCalificacion(Calificacion pCalificacion){
        if(existeCalificacion(pCalificacion)){
            calificaciones.remove(pCalificacion);
        }
        return false;
    }

    public boolean modificarCalificacion(Calificacion nuevaCalificacion){
        Calificacion viejaCalificacion = buscarCalificacion(nuevaCalificacion.getIdUsuario());

        if(viejaCalificacion != null){
            eliminarCalificacion(viejaCalificacion);
            agregarCalificacion(nuevaCalificacion);
            return true;
        }

        return false;
    }

    public boolean existeCalificacion(Calificacion pCalificacion){
        for (Calificacion objCalificacion: calificaciones) {
            if(objCalificacion.equals(pCalificacion)){
                return true;
            }
        }
        return false;
    }

    public Calificacion buscarCalificacion(String pIdUsuario) {
        for (Calificacion objCalificacion: calificaciones) {
            if(pIdUsuario.equals(objCalificacion.getIdUsuario())){
                return objCalificacion;
            }
        }
        return null;
    }
}
