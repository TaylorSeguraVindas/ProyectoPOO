package segura.taylor.bl.gestor;

import java.util.ArrayList;

import segura.taylor.bl.entidades.*;

public class Gestor {
    //Variables
    private ArrayList<Album> albunes = new ArrayList<>();
    private ArrayList<Artista> artistas = new ArrayList<>();
    private ArrayList<Cancion> canciones = new ArrayList<>();
    private ArrayList<Compositor> compositores = new ArrayList<>();
    private ArrayList<Genero> generos = new ArrayList<>();
    private ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();
    private ArrayList<Pais> paises = new ArrayList<>();
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    //*******Manejo de usuarios*******
    //Admin
    public boolean crearUsuario(String fechaCreacion, String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario){
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(id, fechaCreacion, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario);
        usuarios.add(admin);
        return true;
    }

    //Cliente
    public boolean crearUsuario(String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String idPais){
        //Si no hay admin no se puede registrar usuarios.
        if(usuarios.size() == 0) return false;

        //TODO condicion para ver si el usuario ya existe.

        Cliente nuevoCliente = new Cliente(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, idPais);
        usuarios.add(nuevoCliente);
        return true;
    }
    public ArrayList<Usuario> listarUsuarios(){
        return usuarios;
    }
    public Usuario buscarUsuario(String dato){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getNombreUsuario().equals(dato) || objUsuario.getId().equals(dato)){
                return objUsuario;
            }
        }
        return null;
    }
    public boolean modificarUsuario(){
        //TODO modificar
        return false;
    }
    public boolean eliminarUsuario(Usuario usuario) {
        if(existeUsuario(usuario)){
            usuarios.remove(usuario);
            return true;
        }
        return false;
    }
    public boolean existeAdmin(){
        return (usuarios.get(0).getTipoUsuario() == Usuario.TipoUsuario.Admin);
    }
    public boolean existeUsuario(Usuario pUsuario){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.equals(pUsuario)){
                return true;
            }
        }

        return false;
    }


    //*******Manejo de albunes********
    public boolean crearAlbum(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String fechaLanzamiento, String imagen, ArrayList<Artista> artistas, Compositor compositor){
        Album nuevoAlbum = new Album(id, nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas, compositor);
        return false;
    }
    public ArrayList<Album> listarAlbunes(){
        return albunes;
    }
    public boolean modificarAlbum(Album pAlbum, String pNombre, String pImagen){
        int indice = obtenerIndiceAlbum(pAlbum);

        if(indice != -1){
            return albunes.get(indice).modificar(pNombre, pImagen);
        }

        return false;
    }
    public boolean eliminarAlbum(Album pAlbum){
        if(existeAlbum(pAlbum)){
            albunes.remove(pAlbum);
            return true;
        }
        return false;
    }

    public boolean agregarCancionEnAlbum(Cancion pCancion, Album pAlbum){
        int indice = obtenerIndiceAlbum(pAlbum);

        if(indice != -1){
            return albunes.get(indice).agregarCancion(pCancion);
        }

        return false;
    }
    public boolean removerCancionEnAlbum(Cancion pCancion, Album pAlbum){
        int indice = obtenerIndiceAlbum(pAlbum);

        if(indice != -1){
            return albunes.get(indice).removerCancion(pCancion);
        }

        return false;
    }
    public boolean agregarArtistaEnAlbum(Artista pArtista, Album pAlbum){
        int indice = obtenerIndiceAlbum(pAlbum);

        if(indice != -1){
            return albunes.get(indice).agregarArtista(pArtista);
        }

        return false;
    }
    public boolean removerArtistaEnAlbum(Artista pArtista, Album pAlbum){
        int indice = obtenerIndiceAlbum(pAlbum);

        if(indice != -1){
            return albunes.get(indice).removerArtista(pArtista);
        }

        return false;
    }

    public Album buscarAlbum(String dato){
        for (Album objAlbum: albunes) {
            if(objAlbum.getId().equals(dato) || objAlbum.getNombre().equals(dato)){
                return objAlbum;
            }
        }
        return null;
    }
    public int obtenerIndiceAlbum(Album pAlbum){
        int indice = 0;
        for (Album objAlbum: albunes) {
            if(objAlbum.equals(pAlbum)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeAlbum(Album pAlbum){
        for (Album objAlbum: albunes) {
            if(objAlbum.equals(pAlbum)){
                return true;
            }
        }
        return false;
    }


    //*********Manejo de Listas de Reproduccion***************
    public boolean crearListaReproduccion(){
        return false;
    }
    public ArrayList<ListaReproduccion> listarListasReproduccion(){
        return listasReproduccion;
    }
    public boolean agregarCancionALista(Cancion cancion, ListaReproduccion lista) {
        return false;
    }
    public boolean modificarListaReproduccion(){
        return false;
    }
    public boolean eliminarListaReproduccion(ListaReproduccion listaReproduccion){
        return false;
    }

    public ListaReproduccion buscarListaReproduccion(String dato){
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.getId().equals(dato) || objListaReproduccion.getNombre().equals(dato)){
                return objListaReproduccion;
            }
        }
        return null;
    }
    public int obtenerIndiceListaReproduccion(ListaReproduccion pListaReproduccion){
        int indice = 0;
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.equals(pListaReproduccion)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeListaReproduccion(ListaReproduccion pListaReproduccion){
        for (ListaReproduccion objListaReproduccion: listasReproduccion) {
            if(objListaReproduccion.equals(pListaReproduccion)){
                return true;
            }
        }
        return false;
    }

    //*****************Manejo de artistas*******************
    public boolean crearArtista(){
        return false;
    }
    public ArrayList<Artista> listarArtistas(){
        return artistas;
    }
    public Artista buscarArtista(String dato){
        return new Artista();
    }
    public boolean modificarArtista(){
        return false;
    }
    public boolean eliminarArtista(Artista artista){
        return false;
    }

    //*******************Manejo de canciones******************
    public boolean crearCancion(){
        return false;
    }
    public ArrayList<Cancion> listarCanciones(){
        return canciones;
    }
    public Cancion buscarCancion(String dato){
        return new Cancion();
    }
    public boolean modificarCancion(){
        return false;
    }
    public boolean eliminarCancion(Cancion cancion){
        return false;
    }

    //**************Manejo de compositores********************
    public boolean crearCompositor(){
        return false;
    }
    public ArrayList<Compositor> listarCompositores(){
        return compositores;
    }
    public Compositor buscarCompositor(String dato){
        return new Compositor();
    }
    public boolean modificarCompositor(){
        return false;
    }
    public boolean eliminarCompositor(Compositor compositor){
        return false;
    }

    //******************Manejo de Generos******************
    public boolean crearGenero(){
        return false;
    }
    public ArrayList<Genero> listarGeneros(){
        return generos;
    }
    public Genero buscarGenero(String dato) {
        return new Genero();
    }
    public boolean modificarGenero(){
        return false;
    }
    public boolean eliminarGenero(Genero genero){
        return false;
    }

    //*****************Manejo de Paises********************
    public boolean crearPais(){
        return false;
    }
    public ArrayList<Pais> listarPaises(){
        return paises;
    }
    public Pais buscarPais(String dato){
        return new Pais();
    }
    public boolean modificarPais(){
        return false;
    }
    public boolean eliminarPais(Pais pais){
        return false;
    }
}
