package segura.taylor.bl.entidades;

import java.util.Objects;

public class Cliente extends Usuario{
    //Variables
    private String fechaNacimiento;
    private int edad;
    private String idPais;
    private Biblioteca biblioteca;

    //Propiedades
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getIdPais() {
        return idPais;
    }
    public void setIdPais(String idPais) {
        this.idPais = idPais;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    //Constructores
    public Cliente(){
        this.tipoUsuario = TipoUsuario.Normal;
    }
    public Cliente(String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String idPais, Biblioteca biblioteca) {
        super(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        this.tipoUsuario = TipoUsuario.Normal;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.idPais = idPais;
        this.biblioteca = biblioteca;
    }

    //Metodos
    @Override
    public String toString() {
        return "Cliente{" +
                "fechaNacimiento='" + fechaNacimiento + '\'' +
                ", edad=" + edad +
                ", idPais='" + idPais + '\'' +
                ", biblioteca=" + biblioteca +
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
        Cliente cliente = (Cliente) o;
        return edad == cliente.edad &&
                Objects.equals(fechaNacimiento, cliente.fechaNacimiento) &&
                Objects.equals(idPais, cliente.idPais) &&
                Objects.equals(biblioteca, cliente.biblioteca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaNacimiento, edad, idPais, biblioteca);
    }
}
