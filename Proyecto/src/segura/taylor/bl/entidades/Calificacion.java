package segura.taylor.bl.entidades;

import java.util.Objects;

public class Calificacion {
    //Variables
    private int id;
    private Usuario autor;
    private float estrellas;
    private String comentario;
    private String fechaCreacion;

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

    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
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
     * @param comentario String que define el comentario
     * @param fechaCreacion LocalDate que define la fecha de creacion
     */
    public Calificacion(Usuario autor, float estrellas, String comentario, String fechaCreacion) {
        this.id = 0;
        this.autor = autor;
        this.estrellas = estrellas;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Calificacion{" +
                "id='" + id + '\'' +
                ", autor='" + autor + '\'' +
                ", estrellas=" + estrellas +
                ", comentario='" + comentario + '\'' +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calificacion that = (Calificacion) o;
        return id == that.id &&
                Float.compare(that.estrellas, estrellas) == 0 &&
                Objects.equals(autor, that.autor) &&
                Objects.equals(comentario, that.comentario) &&
                Objects.equals(fechaCreacion, that.fechaCreacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, autor, estrellas, comentario, fechaCreacion);
    }

    /**
     * Método usado para reiniciar a los valores por defecto
     */
    public void limpiar(){
        this.estrellas = 1;
        this.comentario = "";
    }
}
