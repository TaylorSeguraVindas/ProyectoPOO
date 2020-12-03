package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.time.LocalDate;
import java.util.*;

public abstract class RepositorioCanciones {
    //Variables
    public static int idRepoCanciones = 0;

    protected int id;
    protected TipoRepositorioCanciones tipoRepo;
    protected String nombre;
    protected LocalDate fechaCreacion;
    protected ArrayList<Cancion> canciones;

    //Propiedades
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TipoRepositorioCanciones getTipoRepo() {return this.tipoRepo; }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<Cancion> getCanciones() {
        return Collections.unmodifiableList(this.canciones);
    }
    public void setCanciones(ArrayList<Cancion> canciones) {
        this.canciones = canciones;
    }

    //Constructores
    public RepositorioCanciones(){
        canciones = new ArrayList<>();
    }
    public RepositorioCanciones(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones) {
        this.id = 0;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.canciones = canciones;
    }

    //Metodos

    @Override
    public String toString() {
        return "RepositorioCanciones{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", canciones=" + canciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RepositorioCanciones that = (RepositorioCanciones) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(fechaCreacion, that.fechaCreacion) &&
                Objects.equals(canciones, that.canciones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, fechaCreacion, canciones);
    }

    public boolean agregarCancion(Cancion pCancion){
        if(!tieneCancion(pCancion)){
            return canciones.add(pCancion);
        }
        return false;
    }

    public boolean removerCancion(int pIdCancion){
        Optional<Cancion> cancionEncontrada = buscarCancion(pIdCancion);

        if(cancionEncontrada.isPresent()){
            return canciones.remove(cancionEncontrada.get());
        }
        return false;
    }

    public Optional<Cancion> buscarCancion(int pIdCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId() == pIdCancion){
                return Optional.of(objCancion);
            }
        }
        return Optional.empty();
    }

    public boolean tieneCancion(Cancion pCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.equals(pCancion)){
                return true;
            }
        }
        return false;
    }
}
