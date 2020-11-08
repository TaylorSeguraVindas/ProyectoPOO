package segura.taylor.bl.entidades;

import java.util.Objects;

public class Cliente extends Usuario{
    //Variables
    private String fechaNacimiento;
    private int edad;
    private String pais;
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

    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {
        this.pais = pais;
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
    public Cliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String pais, Biblioteca biblioteca) {
        super(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        this.tipoUsuario = TipoUsuario.Normal;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.pais = pais;
        this.biblioteca = biblioteca;
    }

    //Metodos
    @Override
    public String toString() {
        return "Cliente{" +
                "fechaNacimiento='" + fechaNacimiento + '\'' +
                ", edad=" + edad +
                ", id='" + id + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                ", correo='" + correo + '\'' +
                ", contrasenna='" + contrasenna + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", imagenPerfil='" + imagenPerfil + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", pais='" + pais + '\'' +
                ", biblioteca=" + biblioteca +
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
                Objects.equals(pais, cliente.pais) &&
                Objects.equals(biblioteca, cliente.biblioteca);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fechaNacimiento, edad, pais, biblioteca);
    }
}
