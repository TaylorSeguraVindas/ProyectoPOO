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

    /**
     * Método constructor por defecto
     */
    public RepositorioCanciones(){
        canciones = new ArrayList<>();
    }

    /**
     * Método constructor
     * @param nombre String que define el nombre
     * @param fechaCreacion LocalDate que define la fecha de creacion
     * @param canciones ArrayList que define las canciones que pertenecen a este repositorio
     * @see ArrayList
     * @see Cancion
     */
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

    /**
     * Método usado para agregar una cancion a este repositorio
     * @param pCancion instancia de la clase Cancion que se desea agregar
     * @return true si la agregacion es exitosa, false si la cancion ya existe
     * @see Cancion
     */
    public boolean agregarCancion(Cancion pCancion){
        if(!tieneCancion(pCancion.getId())){
            return canciones.add(pCancion);
        }
        return false;
    }

    /**
     * Método usado para remover una canción de este repositorio
     * @param pIdCancion int que hace referencia al id de la cancion que se desea remover
     * @return true si la eliminacion es exitosa, false si la cancion no existe
     */
    public boolean removerCancion(int pIdCancion){
        Optional<Cancion> cancionEncontrada = buscarCancion(pIdCancion);

        if(cancionEncontrada.isPresent()){
            return canciones.remove(cancionEncontrada.get());
        }
        return false;
    }

    /**
     * Método usado para buscar una canción en este repositorio usando como filtro su id
     * @param pIdCancion int que hace referencia al id de la cancion que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Cancion si se encuentra una coincidencia
     * @see Optional
     * @see Cancion
     */
    public Optional<Cancion> buscarCancion(int pIdCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId() == pIdCancion){
                return Optional.of(objCancion);
            }
        }
        return Optional.empty();
    }

    /**
     * Método usado para verificar si una canción pertenece a este repositorio
     * @param pIdCancion int que define el id de la canción que se desea encontrar
     * @return true si la cancion existe en este repo, false si no
     */
    public boolean tieneCancion(int pIdCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId() == pIdCancion){
                return true;
            }
        }
        return false;
    }

    /**
     * Método usado para obtener el indice o posicion en la lista de reproduccion de una cancion
     * @param idCancion int que define el id de la cancion de la que se desea conocer su posicion
     * @return int que contiene la posicion de la cancion, -1 si no existe
     */
    public int obtenerIndiceCancion(int idCancion) {
        int cont = 0;
        for (Cancion cancion : canciones) {
            if(cancion.getId() == idCancion) {
                return cont;
            }
            cont++;
        }

        return -1;
    }
}
