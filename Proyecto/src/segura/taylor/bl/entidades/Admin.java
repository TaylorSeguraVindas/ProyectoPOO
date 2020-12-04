package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoUsuario;

import java.time.LocalDate;
import java.util.Objects;

public class Admin extends Usuario {
    //Variables
    private LocalDate fechaCreacion;

    //Propiedades
    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    //Constructores
    /**
     * Método constructor por defecto
     */
    public Admin(){
        this.tipoUsuario = TipoUsuario.ADMIN;
    }

    /**
     * Método constructor
     * @param correo String que define el correo
     * @param contrasenna String que define la contraseña
     * @param nombre String que define el nombre
     * @param apellidos String que define los apellidos
     * @param imagenPerfil String que define la ruta de la imagen de perfil
     * @param nombreUsuario String que define el nombre de usuario
     * @param fechaCreacion LocalDate que define la fecha de creacion
     */
    public Admin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaCreacion) {
        super(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        this.tipoUsuario = TipoUsuario.ADMIN;
        this.fechaCreacion = fechaCreacion;
    }

    //Metodos
    @Override
    public String toString() {
        return "Admin{" +
                "fechaCreacion='" + fechaCreacion + '\'' +
                ", id='" + id + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", correo='" + correo + '\'' +
                ", contrasenna='" + contrasenna + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", imagenPerfil='" + imagenPerfil + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Admin admin = (Admin) o;
        return Objects.equals(fechaCreacion, admin.fechaCreacion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaCreacion);
    }
}
