package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;
import segura.taylor.bl.interfaces.IComboBoxItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Album extends RepositorioCanciones implements IComboBoxItem {
    //Variables
    private LocalDate fechaLanzamiento;
    private String imagen;
    private ArrayList<Artista> artistas;

    //Propiedades
    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }
    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public ArrayList<Artista> getArtistas() {
        return artistas;
    }
    public void setArtistas(ArrayList<Artista> artistas) {
        this.artistas = artistas;
    }

    //Constructores
    /**
     * Método constructor por defecto
     */
    public Album() {
        this.tipoRepo = TipoRepositorioCanciones.ALBUM;
        this.artistas = new ArrayList<>();
    }

    /**
     * Método constructor
     * @param nombre String que define el nombre
     * @param fechaCreacion LocalDate que define la fecha de creacion
     * @param canciones Arraylist que define las canciones que pertenecen a este album
     * @param fechaLanzamiento LocalDate que define la fecha de lanzamiento
     * @param imagen String que define la ruta de la imagen
     * @param artistas ArrayList que define los artistas que pertenecen a este album
     */
    public Album(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, LocalDate fechaLanzamiento, String imagen, ArrayList<Artista> artistas) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.ALBUM;
        this.fechaLanzamiento = fechaLanzamiento;
        this.imagen = imagen;
        this.artistas = artistas;
    }

    //Metodos
    @Override
    public String toString() {
        return "Album{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", fechaLanzamiento='" + fechaLanzamiento + '\'' +
                ", imagen='" + imagen + '\'' +
                ", canciones=" + canciones +
                ", artistas=" + artistas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Album album = (Album) o;
        return Objects.equals(fechaLanzamiento, album.fechaLanzamiento) &&
                Objects.equals(imagen, album.imagen) &&
                Objects.equals(artistas, album.artistas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaLanzamiento, imagen, artistas);
    }

    /**
     * Método usado para agregar un artista a este album
     * @param artista instancia de la clase Artista que será almacenado
     * @return true si la agregación es exitosa, false si el artista ya ha sido agregado
     * @see Artista
     */
    public boolean agregarArtista(Artista artista){
        if(!tieneArtista(artista)){
            artistas.add(artista);
            return true;
        }
        return false;
    }

    /**
     * Método usado para remover un artista de este album
     * @param artista instancia de la clase Artista que se desea remover
     * @return true si la eliminación es exitosa, false si el artista no existe
     * @see Artista
     */
    public boolean removerArtista(Artista artista){
        if(tieneArtista(artista)){
            artistas.remove(artista);
            return true;
        }
        return false;
    }

    /**
     * Método para verificar si un artista está siendo almacenado en este album
     * @param artista instancia de la clase Artista de la que se desea verificar su existencia
     * @return true si existe, false si no
     */
    public boolean tieneArtista(Artista artista){
        for (Artista objArtista: artistas) {
            if(objArtista.equals(artista)){
                return true;
            }
        }
        return false;
    }

    /**
     * Método usado para buscar un artista en los almacenados usando como filtro su id
     * @param idArtista int que define el id del artista que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Artista si se encuentra una coincidencia
     * @see Optional
     * @see Artista
     */
    public Optional<Artista> buscarArtista(int idArtista){
        for (Artista objArtista: artistas) {
            if(objArtista.getId() == idArtista){
                return Optional.of(objArtista);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
