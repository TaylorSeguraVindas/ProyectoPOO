package segura.taylor.bl.entidades;

import java.util.Objects;

public class Artista {
    //Variables
    private String id;
    private String nombre;
    private String apellidos;
    private String nombreArtistico;
    private String fechaNacimiento;
    private String fechaDefuncion;
    private String paisNacimiento;
    private Genero genero;
    private int edad;
    private String descripcion;

    //Propiedades
    public String getId() {
        return id;
    }
    public void setId(String id) {
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

    public String getNombreArtistico() {
        return nombreArtistico;
    }
    public void setNombreArtistico(String nombreArtistico) {
        this.nombreArtistico = nombreArtistico;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaDefuncion() {
        return fechaDefuncion;
    }
    public void setFechaDefuncion(String fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }
    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public Genero getGenero() {
        return genero;
    }
    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Constructores
    public Artista(){}
    public Artista(String id, String nombre, String apellidos, String nombreArtistico, String fechaNacimiento, String fechaDefuncion, String paisNacimiento, Genero genero, int edad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreArtistico = nombreArtistico;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaDefuncion = fechaDefuncion;
        this.paisNacimiento = paisNacimiento;
        this.genero = genero;
        this.edad = edad;
        this.descripcion = descripcion;
    }

    //Metodos


    @Override
    public String toString() {
        return "Artista{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nombreArtistico='" + nombreArtistico + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", fechaDefuncion='" + fechaDefuncion + '\'' +
                ", paisNacimiento='" + paisNacimiento + '\'' +
                ", genero=" + genero +
                ", edad=" + edad +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artista artista = (Artista) o;
        return edad == artista.edad &&
                Objects.equals(id, artista.id) &&
                Objects.equals(nombre, artista.nombre) &&
                Objects.equals(apellidos, artista.apellidos) &&
                Objects.equals(nombreArtistico, artista.nombreArtistico) &&
                Objects.equals(fechaNacimiento, artista.fechaNacimiento) &&
                Objects.equals(fechaDefuncion, artista.fechaDefuncion) &&
                Objects.equals(paisNacimiento, artista.paisNacimiento) &&
                Objects.equals(genero, artista.genero) &&
                Objects.equals(descripcion, artista.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);
    }

    public boolean modificar(String pNombre, String pApellidos, String pNomArtistico, String pFechaDefuncion){
        this.nombre = (!pNombre.equals("")) ? pNombre : this.nombre;
        this.apellidos = (!pApellidos.equals("")) ? pApellidos : this.apellidos;
        this.nombreArtistico = (!pNomArtistico.equals("")) ? pNomArtistico : this.nombreArtistico;
        this.fechaDefuncion = (!pFechaDefuncion.equals("")) ? pFechaDefuncion : this.fechaDefuncion;
        return true;
    }
}
