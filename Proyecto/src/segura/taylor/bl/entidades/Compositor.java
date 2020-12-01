package segura.taylor.bl.entidades;

import segura.taylor.bl.interfaces.IComboBoxItem;

import java.time.LocalDate;
import java.util.Objects;

public class Compositor implements IComboBoxItem {
    //Variables
    public static int idCompositores = 0;

    private int id;
    private String nombre;
    private String apellidos;
    private Pais paisNacimiento;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private int edad;

    //Propiedades
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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

    public Pais getPaisNacimiento() {
        return paisNacimiento;
    }
    public void setPaisNacimiento(Pais paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

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

    //Tablas
    public String getNombrePais() {
        return paisNacimiento.getNombre();
    }
    public String getNombreGenero() {
        return genero.getNombre();
    }

    //Constructores
    public Compositor(){}
    public Compositor(String nombre, String apellidos, Pais paisNacimiento, Genero genero, LocalDate fechaNacimiento, int edad) {
        this.id = idCompositores++;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.paisNacimiento = paisNacimiento;
        this.genero = genero;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
    }

    //Metodos
    @Override
    public String toString() {
        return "Compositor{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", edad=" + edad +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compositor that = (Compositor) o;
        return id == that.id &&
                edad == that.edad &&
                Objects.equals(nombre, that.nombre) &&
                Objects.equals(apellidos, that.apellidos) &&
                Objects.equals(paisNacimiento, that.paisNacimiento) &&
                Objects.equals(genero, that.genero) &&
                Objects.equals(fechaNacimiento, that.fechaNacimiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, paisNacimiento, genero, fechaNacimiento, edad);
    }

    public boolean modificar(String pNombre, String pApellidos){
        this.nombre = (!pNombre.equals("")) ? pNombre : this.nombre;
        this.apellidos = (!pApellidos.equals("")) ? pApellidos : this.nombre;
        return true;
    }

    @Override
    public String toComboBoxItem() {
        return id + "-" + nombre;
    }
}
