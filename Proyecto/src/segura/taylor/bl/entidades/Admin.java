package segura.taylor.bl.entidades;

import java.util.Objects;

public class Admin extends Usuario {
    //Variables
    private String fechaCreacion;

    //Propiedades
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    //Constructores
    public Admin(){
        this.tipoUsuario = TipoUsuario.Admin;
    }

    public Admin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaCreacion) {
        super(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        this.tipoUsuario = TipoUsuario.Admin;
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
