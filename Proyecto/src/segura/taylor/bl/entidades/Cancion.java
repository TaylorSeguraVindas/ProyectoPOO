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
    private Usuario creador;
    private String nombre;
    private String recurso;
    private double duracion;
    private Album album;
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

    public Usuario getCreador() {
        return creador;
    }
    public void setCreador(Usuario creador) {
        this.creador = creador;
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

    public Album getAlbum() {
        return album;
    }
    public void setAlbum(Album album) {
        this.album = album;
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
    public String getNombreAlbum() {
        return album.getNombre();
    }

    //Constructores
    public Cancion(){
        calificaciones = new ArrayList<Calificacion>();
    }

    public Cancion(TipoCancion tipoCancion, Usuario creador, String nombre, String recurso, double duracion, Album album, Genero genero, Artista artista, Compositor compositor, LocalDate fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio) {
        this.id = idCanciones++;
        this.creador = creador;
        this.tipoCancion = tipoCancion;
        this.nombre = nombre;
        this.recurso = recurso;
        this.duracion = duracion;
        this.album = album;
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
                ", creador='" + creador + '\'' +
                ", tipoCancion='" + tipoCancion + '\'' +
                ", nombre='" + nombre + '\'' +
                ", recurso='" + recurso + '\'' +
                ", duracion='" + recurso + '\'' +
                ", album='" + album + '\'' +
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
                Objects.equals(creador, cancion.creador) &&
                Objects.equals(nombre, cancion.nombre) &&
                Objects.equals(recurso, cancion.recurso) &&
                Objects.equals(duracion, cancion.duracion) &&
                Objects.equals(album, cancion.album) &&
                Objects.equals(genero, cancion.genero) &&
                Objects.equals(artista, cancion.artista) &&
                Objects.equals(compositor, cancion.compositor) &&
                Objects.equals(fechaLanzamiento, cancion.fechaLanzamiento) &&
                Objects.equals(calificaciones, cancion.calificaciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoCancion, id, creador, nombre, recurso, duracion, album, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
    }

    public boolean modificar(String nombre, double pPrecio){
        this.nombre = nombre;
        this.precio = pPrecio;
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

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
