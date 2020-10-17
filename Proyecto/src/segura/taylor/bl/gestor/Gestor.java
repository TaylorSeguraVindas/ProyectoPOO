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

    //Manejo de usuarios
    public boolean crearUsuario(){
        return false;
    }
    public ArrayList<Usuario> listarUsuarios(){
        return usuarios;
    }
    public Usuario buscarUsuario(String dato){
        return new Usuario();
    }
    public boolean modificarUsuario(){
        return false;
    }
    public boolean eliminarUsuario(Usuario usuario) {
        return false;
    }

    //Manejo de albunes
    public boolean crearAlbum(){
        return false;
    }
    public ArrayList<Album> listarAlbunes(){
        return albunes;
    }
    public Album buscarAlbum(String dato){
        return new Album();
    }
    public boolean agregarCancionEnAlbum(Cancion cancion, Album album){
        return false;
    }
    public boolean limpiarAlbum(Album album){
        return false;
    }
    public boolean modificarAlbum(){
        return false;
    }
    public boolean eliminarAlbum(Album album){
        return false;
    }

    //Manejo de Listas de Reproduccion
    public boolean crearListaReproduccion(){
        return false;
    }
    public ArrayList<ListaReproduccion> listarListasReproduccion(){
        return listasReproduccion;
    }
    public ListaReproduccion buscarListaReproduccion(String dato){
        return new ListaReproduccion();
    }
    public boolean agregarCancionALista(Cancion cancion, ListaReproduccion lista){
        return false;
    }
    public boolean limpiarListaReproduccion(ListaReproduccion listaReproduccion){
        return false;
    }
    public boolean modificarListaReproduccion(){
        return false;
    }
    public boolean eliminarListaReproduccion(ListaReproduccion listaReproduccion){
        return false;
    }

    //Manejo de artistas
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

    //Manejo de canciones
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

    //Manejo de compositores
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

    //Manejo de Generos
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

    //Manejo de Paises
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
