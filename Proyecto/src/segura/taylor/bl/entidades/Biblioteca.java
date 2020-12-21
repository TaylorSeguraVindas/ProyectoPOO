package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Biblioteca extends RepositorioCanciones {
    //Variables
    ArrayList<ListaReproduccion> listasDeReproduccion;

    //Propiedades
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
        this.listasDeReproduccion = new ArrayList<>();
    }

    /**
     * Método constructor
     * @param nombre String que define el nombre
     * @param fechaCreacion LocalDate que define la fecha de creacion
     * @param canciones ArrayList que define las canciones que pertenecen a esta biblioteca
     * @param listasDeReproduccion ArrayList que define las listas de reproduccion que pertenecen a esta biblioteca
     */
    public Biblioteca(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, ArrayList<ListaReproduccion> listasDeReproduccion) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(listasDeReproduccion, that.listasDeReproduccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), listasDeReproduccion);
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
