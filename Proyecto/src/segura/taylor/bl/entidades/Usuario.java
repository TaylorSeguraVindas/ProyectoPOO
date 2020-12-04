package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoCancion;
import segura.taylor.bl.enums.TipoUsuario;

import java.util.Objects;

public abstract class Usuario {
    //Variables
    public static int idUsuarios = 0;

    protected int id;
    protected TipoUsuario tipoUsuario;
    protected String correo;
    protected String contrasenna;
    protected String nombre;
    protected String apellidos;
    protected String imagenPerfil;
    protected String nombreUsuario;

    //Propiedades
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public TipoUsuario getTipoUsuario() {
        return this.tipoUsuario;
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

    /**
     * Método constructor por defecto
     */
    public Usuario(){}

    /**
     * Método constructor
     * @param correo String que define el correo
     * @param contrasenna String que define la contraseña
     * @param nombre String que define el nombre
     * @param apellidos String que define los apellidos
     * @param imagenPerfil String que define la ruta de la imagen de perfil
     * @param nombreUsuario String que define el nombre de usuario
     */
    public Usuario(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario) {
        this.id = 0;
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
                "id='" + id + '\'' +
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
        return Objects.equals(id, usuario.id) &&
                tipoUsuario == usuario.tipoUsuario &&
                Objects.equals(correo, usuario.correo) &&
                Objects.equals(contrasenna, usuario.contrasenna) &&
                Objects.equals(nombre, usuario.nombre) &&
                Objects.equals(apellidos, usuario.apellidos) &&
                Objects.equals(imagenPerfil, usuario.imagenPerfil) &&
                Objects.equals(nombreUsuario, usuario.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoUsuario, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
    }

    /**
     * Método usado para saber si esta instancia es un usuario administrador
     * @return true si es administrador, false si no
     */
    public boolean esAdmin(){
        return tipoUsuario == TipoUsuario.ADMIN;
    }

    /**
     * Método usado para saber si esta instancia es un usuario creador
     * @return true si es creador, false si no
     */
    public boolean esCreador() {
        return tipoUsuario == TipoUsuario.CREADOR;
    }
}
