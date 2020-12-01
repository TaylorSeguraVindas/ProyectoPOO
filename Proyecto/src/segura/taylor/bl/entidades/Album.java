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
    private Compositor compositor;

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

    public Compositor getCompositor() {
        return compositor;
    }
    public void setCompositor(Compositor compositor) {
        this.compositor = compositor;
    }

    //Tablas
    public String getNombreCompositor() {
        return compositor.getNombre();
    }

    //Constructores
    public Album() {
        this.tipoRepo = TipoRepositorioCanciones.ALBUM;
        this.artistas = new ArrayList<>();
    }

    public Album(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, LocalDate fechaLanzamiento, String imagen, ArrayList<Artista> artistas, Compositor compositor) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.ALBUM;
        this.fechaLanzamiento = fechaLanzamiento;
        this.imagen = imagen;
        this.artistas = artistas;
        this.compositor = compositor;
    }

    //Metodos
    @Override
    public String toString() {
        return "Album{" +
                "fechaLanzamiento='" + fechaLanzamiento + '\'' +
                ", imagen='" + imagen + '\'' +
                ", artistas=" + artistas +
                ", compositor=" + compositor +
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
        Album album = (Album) o;
        return Objects.equals(fechaLanzamiento, album.fechaLanzamiento) &&
                Objects.equals(imagen, album.imagen) &&
                Objects.equals(artistas, album.artistas) &&
                Objects.equals(compositor, album.compositor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaLanzamiento, imagen, artistas, compositor);
    }

    public boolean modificar(String pNombre, String pImagen){
        this.nombre = pNombre;
        this.imagen = pImagen;
        return true;
    }

    public boolean agregarArtista(Artista artista){
        if(!tieneArtista(artista)){
            artistas.add(artista);
            return true;
        }
        return false;
    }
    public boolean removerArtista(Artista artista){
        if(tieneArtista(artista)){
            artistas.remove(artista);
            return true;
        }
        return false;
    }
    public boolean tieneArtista(Artista artista){
        for (Artista objArtista: artistas) {
            if(objArtista.equals(artista)){
                return true;
            }
        }
        return false;
    }
    public Optional<Artista> buscarArtista(int datoArtista){
        for (Artista objArtista: artistas) {
            if(objArtista.getId() == datoArtista){
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
