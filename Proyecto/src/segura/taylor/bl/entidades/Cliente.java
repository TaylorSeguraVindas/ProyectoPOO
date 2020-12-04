package segura.taylor.bl.entidades;

import segura.taylor.bl.enums.TipoUsuario;

import java.time.LocalDate;
import java.util.Objects;

public class Cliente extends Usuario{
    //Variables
    private LocalDate fechaNacimiento;
    private int edad;
    private Pais pais;
    private Biblioteca biblioteca;

    //Propiedades
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombrePais() {
        return this.pais.getNombre();
    }
    public Pais getPais() {
        return pais;
    }
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Biblioteca getBiblioteca() {
        return biblioteca;
    }
    public void setBiblioteca(Biblioteca biblioteca) {
        this.biblioteca = biblioteca;
    }

    //Constructores

    /**
     * Método constructor por defecto
     */
    public Cliente(){
        this.tipoUsuario = TipoUsuario.CLIENTE;
    }

    /**
     * Método constructor
     * @param correo String que define el correo
     * @param contrasenna String que define la contraseña
     * @param nombre String que define el nombre
     * @param apellidos String que define los apellidos
     * @param imagenPerfil String que define la ruta de la imagen de perfil
     * @param nombreUsuario String que define el nombre de usuario
     * @param fechaNacimiento LocalDate que define la fecha de nacimiento
     * @param edad int que define la edad
     * @param pais instancia de la clase Pais que define el pais
     * @param biblioteca instancia de la clase Biblioteca que define la biblioteca
     * @see Pais
     * @see Biblioteca
     */
    public Cliente(String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, LocalDate fechaNacimiento, int edad, Pais pais, Biblioteca biblioteca) {
        super(correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        this.tipoUsuario = TipoUsuario.CLIENTE;
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
