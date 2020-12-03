package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Biblioteca extends RepositorioCanciones {
    //Variables
    ArrayList<Cancion> cancionesEnCola;
    ArrayList<ListaReproduccion> listasDeReproduccion;

    //Propiedades
    public ArrayList<Cancion> getIdCancionesFavoritas() {
        return cancionesEnCola;
    }
    public void setIdCancionesFavoritas(ArrayList<Cancion> cancionesEnCola) {
        this.cancionesEnCola = cancionesEnCola;
    }

    public ArrayList<ListaReproduccion> getListasDeReproduccion() {
        return listasDeReproduccion;
    }
    public void setListasDeReproduccion(ArrayList<ListaReproduccion> listasDeReproduccion) {
        this.listasDeReproduccion = listasDeReproduccion;
    }

    //Contructores
    public Biblioteca() {
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
        this.cancionesEnCola = new ArrayList<>();
        this.listasDeReproduccion = new ArrayList<>();
    }
    public Biblioteca(String nombre, LocalDate fechaCreacion, ArrayList<Cancion> canciones, ArrayList<Cancion> cancionesEnCola, ArrayList<ListaReproduccion> listasDeReproduccion) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
        this.cancionesEnCola = cancionesEnCola;
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
                ", cancionesEnCola=" + cancionesEnCola +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(cancionesEnCola, that.cancionesEnCola) &&
                Objects.equals(listasDeReproduccion, that.listasDeReproduccion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cancionesEnCola, listasDeReproduccion);
    }

    public boolean agregarACola(Cancion cancion){
        if(!existeEnCola(cancion.getId())){
            cancionesEnCola.add(cancion);
            return true;
        }
        return false;
    }

    public boolean removerDeCola(Cancion cancion){
        if(existeEnCola(cancion.getId())){
            cancionesEnCola.remove(cancion);
            return true;
        }
        return false;
    }

    public Optional<Cancion> buscarEnCola(int pIdCancion){
        for (Cancion objCancion: cancionesEnCola) {
            if(objCancion.getId() == pIdCancion){
                return Optional.of(objCancion);
            }
        }
        return Optional.empty();
    }
    public boolean existeEnCola(int id){
        for (Cancion objCancion: cancionesEnCola) {
            if(id == objCancion.getId()){
                return true;
            }
        }
        return false;
    }

    public boolean agregarListaReproduccion(ListaReproduccion nuevaLista) {
        return listasDeReproduccion.add(nuevaLista);
    }
    public boolean removerListaReproduccion(int pIdLista) {
        Optional<ListaReproduccion> listaEncontrada = buscarListaReproduccion(pIdLista);

        if(listaEncontrada.isPresent()) {
            return listasDeReproduccion.remove(listaEncontrada.get());
        }

        return false;
    }
    public Optional<ListaReproduccion> buscarListaReproduccion(int pIdLista) {
        for (ListaReproduccion lista : listasDeReproduccion) {
            if(pIdLista == lista.getId()) {
                return Optional.of(lista);
            }
        }
        return Optional.empty();
    }
}
