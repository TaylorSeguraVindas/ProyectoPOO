package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.util.ArrayList;
import java.util.Objects;

public class Biblioteca extends RepositorioCanciones {
    //Variables
    ArrayList<Integer> idCancionesFavoritas;

    //Propiedades
    public ArrayList<Integer> getIdCancionesFavoritas() {
        return idCancionesFavoritas;
    }
    public void setIdCancionesFavoritas(ArrayList<Integer> idCancionesFavoritas) {
        this.idCancionesFavoritas = idCancionesFavoritas;
    }

    //Contructores
    public Biblioteca() {
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
    }
    public Biblioteca(String nombre, String fechaCreacion, ArrayList<Cancion> canciones, ArrayList<Integer> idCancionesFavoritas) {
        super(nombre, fechaCreacion, canciones);
        this.tipoRepo = TipoRepositorioCanciones.BIBLIOTECA;
        this.idCancionesFavoritas = idCancionesFavoritas;
    }

    //Metodos
    @Override
    public String toString() {
        return "Biblioteca{" +
                "idCancionesFavoritas=" + idCancionesFavoritas +
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
        Biblioteca that = (Biblioteca) o;
        return Objects.equals(idCancionesFavoritas, that.idCancionesFavoritas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), idCancionesFavoritas);
    }

    public boolean agregarAFavoritos(int id){
        if(!existeEnFavoritos(id)){
            idCancionesFavoritas.add(id);
            return true;
        }
        return false;
    }

    public boolean removerDeFavoritos(int id){
        if(existeEnFavoritos(id)){
            idCancionesFavoritas.remove(id);
            return true;
        }
        return false;
    }

    public int buscarEnFavoritos(int pIdCancion){
        for (int objCancion: idCancionesFavoritas) {
            if(objCancion == pIdCancion){
                return objCancion;
            }
        }
        return -1;
    }
    public boolean existeEnFavoritos(int id){
        for (Cancion objCancion: canciones) {
            if(id == objCancion.getId()){
                return true;
            }
        }
        return false;
    }
}
