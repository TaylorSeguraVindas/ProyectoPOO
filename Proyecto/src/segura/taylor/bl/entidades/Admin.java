package segura.taylor.bl.entidades;

public class Admin extends Usuario {
    //Variables

    //Propiedades

    //Constructores
    public Admin(){
        tipoUsuario = TipoUsuario.Admin;
    }
    public Admin(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario) {
        super(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        tipoUsuario = TipoUsuario.Admin;
    }

    //Metodos
    @Override
    public String toString() {
        return "Admin{" +
                "id='" + id + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", correo='" + correo + '\'' +
                ", contrasenna='" + contrasenna + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", imagenPerfil='" + imagenPerfil + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                '}';
    }
}
