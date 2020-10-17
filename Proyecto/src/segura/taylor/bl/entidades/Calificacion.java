package segura.taylor.bl.entidades;

import java.util.Objects;

public class Calificacion {
    //Variables
    private String idUsuario;
    private float estrellas;
    private String comentario;
    private String fechaCreacion;

    //Metodos
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
    public Calificacion(){}
    public Calificacion(String idUsuario, float estrellas, String comentario, String fechaCreacion) {
        this.idUsuario = idUsuario;
        this.estrellas = estrellas;
        this.comentario = comentario;
        this.fechaCreacion = fechaCreacion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Calificacion{" +
                "idUsuario='" + idUsuario + '\'' +
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
        return Float.compare(that.estrellas, estrellas) == 0 &&
                Objects.equals(idUsuario, that.idUsuario) &&
                Objects.equals(comentario, that.comentario) &&
                Objects.equals(fechaCreacion, that.fechaCreacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, estrellas, comentario, fechaCreacion);
    }

    public boolean modificar(int estrellas, String comentario){
        this.estrellas = estrellas;
        this.comentario = comentario;
        return true;
    }

    public void limpiar(){
        this.estrellas = 1;
        this.comentario = "";
    }
}
