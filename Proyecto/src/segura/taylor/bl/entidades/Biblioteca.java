package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Biblioteca extends RepositorioCanciones {
    //Variables
    ArrayList<Cancion> cancionesGuardadas;
    ArrayList<ListaReproduccion> listasDeReproduccion;

    //Propiedades
    public ArrayList<Cancion> getCancionesGuardadas() {
        return cancionesGuardadas;
    }
    public void setCancionesGuardadas(ArrayList<Cancion> cancionesGuardadas) {
        this.cancionesGuardadas = cancionesGuardadas;
    }

    public ArrayList<ListaReproduccion> getListasDeReproduccion() {
        return listasDeReproduccion;
    }
    public void setListasDeReproduccion(ArrayList<ListaReproduccion> listasDeReproduccion) {
        this.listasDeReproduccion = listasDeReproduccion;
    }

    //Contructores

    /**
     * Método constructor por defecto
     */
    public Biblioteca() {
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
        this.cancionesGuardadas = new ArrayList<>();
        this.listasDeReproduccion = new ArrayList<>();
    }

    /**
     * Método constructor
     * @param nombre String que define el nombre
     * @param fechaCreacion LocalDate que define la fecha de creacion
     * @param canciones ArrayList que define las canciones que pertenecen a esta biblioteca
     * @param cancionesGuardadas ArrayList que define las canciones en cola
     * @param listasDeReproduccion ArrayList que define las listas de reproduccion que pertenecen a esta biblioteca
     */
    public Biblioteca(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, ArrayList<Cancion> cancionesGuardadas, ArrayList<ListaReproduccion> listasDeReproduccion) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
        this.cancionesGuardadas = cancionesGuardadas;
        this.listasDeReproduccion = listasDeReproduccion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Biblioteca{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                ", canciones=" + canciones +
                ", cancionesGuardadas=" + cancionesGuardadas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(cancionesGuardadas, that.cancionesGuardadas) &&
                Objects.equals(listasDeReproduccion, that.listasDeReproduccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cancionesGuardadas, listasDeReproduccion);
    }

    /**
     * Método usado para agregar una cancion a la cola de reproduccion
     * @param cancion instancia de la clase Cancion que se desea agregar
     * @return true si la agregacion es exitosa, false si la cancion ya ha sido agregada
     * @see Cancion
     */
    public boolean agregarABiblioteca(Cancion cancion){
        if(!existeEnBiblioteca(cancion.getId())){
            cancionesGuardadas.add(cancion);
            return true;
        }
        return false;
    }

    /**
     * Método usado para remover una cancion de la cola de reproduccion
     * @param cancion instancia de la clase Cancion que se desea remover
     * @return true si la eliminacion es exitosa, false si la cancion no existe
     */
    public boolean removerDeBiblioteca(Cancion cancion){
        if(existeEnBiblioteca(cancion.getId())){
            cancionesGuardadas.remove(cancion);
            return true;
        }
        return false;
    }

    /**
     * Método usado para buscar una cancion en la cola de reproduccion usando como filtro su id
     * @param pIdCancion int que define el id de la cancion que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de Cancion si se encuentra una coincidencia
     */
    public Optional<Cancion> buscarEnBiblioteca(int pIdCancion){
        for (Cancion objCancion: cancionesGuardadas) {
            if(objCancion.getId() == pIdCancion){
                return Optional.of(objCancion);
            }
        }
        return Optional.empty();
    }

    /**
     * Método usado para verificar si una cancion existe en la cola de reproduccion
     * @param id int que define el id de al cancion que se desea encontrar
     * @return true si existe en la cola, false si no
     */
    public boolean existeEnBiblioteca(int id){
        for (Cancion objCancion: cancionesGuardadas) {
            if(id == objCancion.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * Método usado para agregar una lista de reproduccion a esta biblioteca
     * @param nuevaLista instancia de la clase ListaReproduccion que se desea agregar
     * @return true si la agregacion es exitosa, false si la lista ya ha sido agregada
     * @see ListaReproduccion
     */
    public boolean agregarListaReproduccion(ListaReproduccion nuevaLista) {
        return listasDeReproduccion.add(nuevaLista);
    }

    /**
     * Método usado para remover una lista de reproduccion de esta biblioteca
     * @param pIdLista int que define el id de la lista de reproduccion que se desea remover
     * @return true si la eliminacion es exitosa, false si la lista no existe
     */
    public boolean removerListaReproduccion(int pIdLista) {
        Optional<ListaReproduccion> listaEncontrada = buscarListaReproduccion(pIdLista);

        if(listaEncontrada.isPresent()) {
            return listasDeReproduccion.remove(listaEncontrada.get());
        }

        return false;
    }

    /**
     * Método usado para buscar una lista de reproduccion en esta biblioteca
     * @param pIdLista int que define el id de la lista que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de ListaReproduccion si se encuentra una coincidencia
     */
    public Optional<ListaReproduccion> buscarListaReproduccion(int pIdLista) {
        for (ListaReproduccion lista : listasDeReproduccion) {
            if(pIdLista == lista.getId()) {
                return Optional.of(lista);
            }
        }
        return Optional.empty();
    }
}
