package segura.taylor.bl.entidades;

import java.util.Objects;

public class Calificacion {
    //Variables
    private int id;
    private int estrellas;

    //Metodos
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEstrellas() {
        return estrellas;
    }
    public void setEstrellas(int estrellas) {
        this.estrellas = estrellas;
    }

    //Constructores

    /**
     * Método constructor por defecto
     */
    public Calificacion(){
        this.id = 0;
    }

    /**
     * Método constructor
     * @param estrellas int que define la cantidad de estrellas
     */
    public Calificacion(int estrellas) {
        this.id = 0;
        this.estrellas = estrellas;
    }

    //Metodos
    @Override
    public String toString() {
        return "Calificacion{" +
                "id='" + id + '\'' +
                ", estrellas=" + estrellas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calificacion that = (Calificacion) o;
        return id == that.id &&
                Float.compare(that.estrellas, estrellas) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, estrellas);
    }

    /**
     * Método usado para reiniciar a los valores por defecto
     */
    public void limpiar(){
        this.estrellas = 0;
    }
}
