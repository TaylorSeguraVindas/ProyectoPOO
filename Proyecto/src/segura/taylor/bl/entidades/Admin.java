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
    public Admin(){
        this.tipoUsuario = TipoUsuario.ADMIN;
    }

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
