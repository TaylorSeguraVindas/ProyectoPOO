package segura.taylor.bl.entidades;

import java.util.Objects;

public class Usuario {
    //Enums
    enum TipoUsuario{
        Admin,
        Normal
    }

    //Variables
    protected String id;
    protected TipoUsuario tipoUsuario;
    protected String correo;
    protected String contrasenna;
    protected String nombre;
    protected String apellidos;
    protected String imagenPerfil;
    protected String nombreUsuario;

    //Propiedades
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenna() {
        return contrasenna;
    }
    public void setContrasenna(String contrasenna) {
        this.contrasenna = contrasenna;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }
    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    //Constructores
    public Usuario(){}

    public Usuario(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario) {
        this.correo = correo;
        this.contrasenna = contrasenna;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.imagenPerfil = imagenPerfil;
        this.nombreUsuario = nombreUsuario;
    }

    //Metodos
    @Override
    public String toString() {
        return "Usuario{" +
                "tipoUsuario=" + tipoUsuario +
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
        Usuario usuario = (Usuario) o;
        return  tipoUsuario == usuario.tipoUsuario &&
                Objects.equals(correo, usuario.correo) &&
                Objects.equals(contrasenna, usuario.contrasenna) &&
                Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(apellidos, usuario.apellidos) &&
                Objects.equals(imagenPerfil, usuario.imagenPerfil) &&
                Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoUsuario, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
    }

    public boolean modificar(String pNombreUsuario, String pImagenPerfil, String pContrasenna, String pNombre, String pApellidos){
        this.nombreUsuario = (pNombreUsuario != "") ? pNombreUsuario : this.nombreUsuario;
        this.imagenPerfil = (pImagenPerfil != "") ? pImagenPerfil : imagenPerfil;
        this.contrasenna = (pContrasenna != "") ? pContrasenna : this.contrasenna;
        this.nombre = (pNombre != "") ? pNombre : this.nombre;
        this.apellidos = (pApellidos != "") ? pApellidos : this.apellidos;
        return true;
    }
}
