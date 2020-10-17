package segura.taylor.bl.entidades;

import java.util.ArrayList;
import java.util.Objects;

public class Biblioteca extends RepositorioCanciones {
    //Variables
    ArrayList<String> idCancionesFavoritas;

    //Propiedades
    public ArrayList<String> getIdCancionesFavoritas() {
        return idCancionesFavoritas;
    }
    public void setIdCancionesFavoritas(ArrayList<String> idCancionesFavoritas) {
        this.idCancionesFavoritas = idCancionesFavoritas;
    }

    //Contructores
    public Biblioteca() {}
    public Biblioteca(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, ArrayList<String> idCancionesFavoritas) {
        super(id, nombre, fechaCreacion, canciones);
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

    public boolean agregarAFavoritos(String id){
        if(!existeEnFavoritos(id)){
            idCancionesFavoritas.add(id);
            return true;
        }
        return false;
    }

    public boolean removerDeFavoritos(String id){
        if(existeEnFavoritos(id)){
            idCancionesFavoritas.remove(id);
            return true;
        }
        return false;
    }

    public boolean existeEnFavoritos(String id){
        for (Cancion objCancion: canciones) {
            if(id.equals(objCancion.getId())){
                return true;
            }
        }
        return false;
    }
}
