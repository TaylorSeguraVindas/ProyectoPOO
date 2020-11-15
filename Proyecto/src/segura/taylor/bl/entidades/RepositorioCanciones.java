package segura.taylor.bl.entidades;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RepositorioCanciones {
    //Variables
    public static int idRepoCanciones = 0;

    protected int id;
    protected String nombre;
    protected String fechaCreacion;
    protected ArrayList<Cancion> canciones;

    //Propiedades
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

    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
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
    public RepositorioCanciones(String nombre, String fechaCreacion, ArrayList<Cancion> canciones) {
        this.id = idRepoCanciones++;
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
            canciones.add(pCancion);
            return true;
        }
        return false;
    }

    public boolean removerCancion(Cancion pCancion){
        if(tieneCancion(pCancion)){
            canciones.remove(pCancion);
            return true;
        }
        return false;
    }

    public Cancion buscarCancion(int pIdCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId() == pIdCancion){
                return objCancion;
            }
        }
        return null;
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
