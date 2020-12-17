package segura.taylor.bl.entidades;

import java.util.Objects;

public class Calificacion {
    //Variables
    private int id;
    private Usuario autor;
    private float estrellas;

    //Metodos
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Usuario getAutor() {
        return autor;
    }
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public float getEstrellas() {
        return estrellas;
    }
    public void setEstrellas(float estrellas) {
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
     * @param autor instancia de la clase Usuario que define al autor
     * @param estrellas int que define la cantidad de estrellas
     */
    public Calificacion(Usuario autor, float estrellas) {
        this.id = 0;
        this.autor = autor;
        this.estrellas = estrellas;
    }

    //Metodos
    @Override
    public String toString() {
        return "Calificacion{" +
                "id='" + id + '\'' +
                ", autor='" + autor + '\'' +
                ", estrellas=" + estrellas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calificacion that = (Calificacion) o;
        return id == that.id &&
                Float.compare(that.estrellas, estrellas) == 0 &&
                Objects.equals(autor, that.autor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, autor, estrellas);
    }

    /**
     * Método usado para reiniciar a los valores por defecto
     */
    public void limpiar(){
        this.estrellas = 0;
    }
}
