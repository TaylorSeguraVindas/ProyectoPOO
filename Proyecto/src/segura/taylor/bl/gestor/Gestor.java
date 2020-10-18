package segura.taylor.bl.gestor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import segura.taylor.bl.entidades.*;

public class Gestor {

    //Variables
    private ArrayList<Album> albunes;
    private ArrayList<Artista> artistas;
    private ArrayList<Cancion> canciones;
    private ArrayList<Compositor> compositores;
    private ArrayList<Genero> generos;
    private ArrayList<ListaReproduccion> listasReproduccion;
    private ArrayList<Pais> paises;
    private ArrayList<Usuario> usuarios;

    //Constructor
    public Gestor(){
        this.albunes = new ArrayList<>();
        this.artistas = new ArrayList<>();
        this.canciones = new ArrayList<>();
        this.compositores = new ArrayList<>();
        this.generos = new ArrayList<>();
        this.listasReproduccion = new ArrayList<>();
        this.paises = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    //Metodos

    //*******General**********
    public boolean existeAdmin(){
        if(usuarios.size() == 0){
            return false;
        }

        return (usuarios.get(0).esAdmin());
    }
    public Usuario iniciarSesion(String pCorreo, String pContrasenna){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getCorreo().equals(pCorreo)){
                if(objUsuario.getContrasenna().equals(pContrasenna)){
                    return objUsuario;
                }
            }
        }

        return null;
    }

    //TODO terminar esto bien
    public boolean agregarCancionABibliotecaUsuario(Cliente pCliente, Cancion pCancion){
        int indice = obtenerIndiceUsuario(pCliente);

        if(indice != -1){
            Cliente clienteModificar = (Cliente) usuarios.get(indice);
            return clienteModificar.getBiblioteca().agregarCancion(pCancion);
        }
        return false;
    }
    public boolean agregarCancionAFavoritosUsuario(Cliente pCliente, Cancion pCancion){
        int indice = obtenerIndiceUsuario(pCliente);

        if(indice != -1){
            Cliente clienteModificar = (Cliente) usuarios.get(indice);
            return clienteModificar.getBiblioteca().agregarAFavoritos(pCancion.getId());
        }
        return false;
    }
    public boolean guardarCancion(Cancion pCancion){
        //TODO condicion para no repetir canciones.
        canciones.add(pCancion);
        return true;
    }

    public int cantidadAlbunes() {return albunes.size();}
    public int cantidadArtistas() {return artistas.size();}
    public int cantidadCanciones() {return canciones.size();}
    public int cantidadCompositores() {return compositores.size();}
    public int cantidadGeneros() {return generos.size();}
    public int cantidadListasReproduccion() {return listasReproduccion.size();}
    public int cantidadPaises() {return paises.size();}
    public int cantidadUsuarios(){
        return usuarios.size();
    }

    //*******Manejo de usuarios*******
    //Admin
    public boolean crearUsuarioAdmin(String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaCreacion){
        //Si ya existe un admin no se puede sobreescribir
        if(existeAdmin()) return false;

        Admin admin = new Admin(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaCreacion);
        usuarios.add(admin);
        return true;
    }
    //Cliente
    public boolean crearUsuarioCliente(String id, String correo, String contrasenna, String nombre, String apellidos, String imagenPerfil, String nombreUsuario, String fechaNacimiento, int edad, String idPais, Biblioteca biblioteca){
        //Si no hay admin no se puede registrar usuarios.
        if(usuarios.size() == 0) return false;

        if(biblioteca == null){
            biblioteca = new Biblioteca();
        }

        Cliente nuevoCliente = new Cliente(id, correo, contrasenna, nombre, apellidos, imagenPerfil, nombreUsuario, fechaNacimiento, edad, idPais, biblioteca);

        //Evitar repeticion de datos.
        if(buscarUsuario(correo) == null){
            usuarios.add(nuevoCliente);
            return true;
        }

        return false;
    }
    public boolean modificarUsuario(String pCorreo, String pNombreUsuario, String pImagenPerfil, String pContrasenna, String pNombre, String pApellidos){
        Usuario usuarioModifica = buscarUsuario(pCorreo);

        if(usuarioModifica != null){
            return usuarioModifica.modificar(pNombreUsuario, pImagenPerfil, pContrasenna, pNombre, pApellidos);
        }

        return false;
    }
    public boolean eliminarUsuario(Usuario usuario) {
        if(existeUsuario(usuario)){
            usuarios.remove(usuario);
            return true;
        }
        return false;
    }
    public ArrayList<Usuario> listarUsuarios(){
        return usuarios;
    }

    public boolean existeUsuario(Usuario pUsuario){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.equals(pUsuario)){
                return true;
            }
        }

        return false;
    }
    public Usuario buscarUsuario(String dato){
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.getId().equals(dato) || objUsuario.getCorreo().equals(dato) || objUsuario.getNombre().equals(dato)){
                return objUsuario;
            }
        }
        return null;
    }
    public int obtenerIndiceUsuario(Usuario pUsuario){
        int indice = 0;
        for (Usuario objUsuario: usuarios) {
            if(objUsuario.equals(pUsuario)){
                return indice;
            }
            indice++;
        }
        return -1;
    }

    //*******Manejo de albunes********
    public boolean crearAlbum(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String fechaLanzamiento, String imagen, ArrayList<Artista> artistas, Compositor compositor){
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }
        if(artistas == null){
            artistas = new ArrayList<Artista>();
        }

        Album nuevoAlbum = new Album(id, nombre, fechaCreacion, canciones, fechaLanzamiento, imagen, artistas, compositor);

        //Evitar repeticion de datos.
        if(!existeAlbum(nuevoAlbum)){
            albunes.add(nuevoAlbum);
            return true;
        }

        return false;
    }
    public boolean modificarAlbum(String pId, String pNombre, String pImagen, Compositor pCompositor){
        Album albumModifica = buscarAlbum(pId);

        if(albumModifica != null){
            return albumModifica.modificar(pNombre, pImagen, pCompositor);
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
    public ArrayList<Album> listarAlbunes(){
        return albunes;
    }

    //TODO terminar esto bien
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
    public boolean crearListaReproduccion(String id, String nombre, String fechaCreacion, ArrayList<Cancion> canciones, String idCreador, String imagen){
        if(canciones == null){
            canciones = new ArrayList<Cancion>();
        }

        ListaReproduccion nuevaLista = new ListaReproduccion(id, nombre, fechaCreacion, canciones, idCreador, 0.0, imagen);

        //Evitar repeticion de datos.
        if(!existeListaReproduccion(nuevaLista)){
            listasReproduccion.add(nuevaLista);
            return true;
        }

        return false;
    }
    public boolean modificarListaReproduccion(String pId, String pNombre, String pImagen){
        ListaReproduccion listaModifica = buscarListaReproduccion(pId);

        if(listaModifica != null){
            return listaModifica.modificar(pNombre, pImagen);
        }
        return false;
    }
    public boolean eliminarListaReproduccion(ListaReproduccion pListaReproduccion){
        if(existeListaReproduccion(pListaReproduccion)){
            listasReproduccion.remove(pListaReproduccion);
            return true;
        }
        return false;
    }
    public ArrayList<ListaReproduccion> listarListasReproduccion(){
        return listasReproduccion;
    }

    //TODO terminar esto bien
    public boolean agregarCancionALista(Cancion pCancion, ListaReproduccion pLista) {
        int indice = obtenerIndiceListaReproduccion(pLista);

        if(indice != -1){
            return listasReproduccion.get(indice).agregarCancion(pCancion);
        }
        return false;
    }
    public boolean removerCancionALista(Cancion pCancion, ListaReproduccion pLista) {
        int indice = obtenerIndiceListaReproduccion(pLista);

        if(indice != -1){
            return listasReproduccion.get(indice).removerCancion(pCancion);
        }
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
    public boolean crearArtista(String id, String nombre, String apellidos, String nombreArtistico, String fechaNacimiento, String fechaDefuncion, String paisNacimiento, Genero genero, int edad, String descripcion){
        Artista nuevoArtista = new Artista(id, nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, paisNacimiento, genero, edad, descripcion);

        //Evitar repeticion de datos.
        if(!existeArtista(nuevoArtista)){
            artistas.add(nuevoArtista);
            return true;
        }

        return false;
    }
    public boolean modificarArtista(String pId, String pNombre, String pApellidos, String pNomArtistico, String pFechaDefuncion){
        Artista artistaModifica = buscarArtista(pId);

        if(artistaModifica != null){
            return artistaModifica.modificar(pNombre, pApellidos, pNomArtistico, pFechaDefuncion);
        }
        return false;
    }
    public boolean eliminarArtista(Artista pArtista){
        if(existeArtista(pArtista)){
            artistas.remove(pArtista);
            return true;
        }
        return false;
    }
    public ArrayList<Artista> listarArtistas(){
        return artistas;
    }

    public Artista buscarArtista(String dato){
        for (Artista objArtista: artistas) {
            if(objArtista.getId().equals(dato) || objArtista.getNombre().equals(dato)){
                return objArtista;
            }
        }
        return null;
    }
    public int obtenerIndiceArtista(Artista pArtista){
        int indice = 0;
        for (Artista objArtista: artistas) {
            if(objArtista.equals(pArtista)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeArtista(Artista pArtista){
        for (Artista objArtista: artistas) {
            if(objArtista.equals(pArtista)){
                return true;
            }
        }
        return false;
    }


    //*******************Manejo de canciones******************
    public Cancion crearCancion(String id, String nombre, String recurso, String nombreAlbum, Genero genero, Artista artista, Compositor compositor, String fechaLanzamiento, ArrayList<Calificacion> calificaciones, double precio){
        if(calificaciones == null){
            calificaciones = new ArrayList<Calificacion>();
        }

        Cancion nuevaCancion = new Cancion(id, nombre, recurso, nombreAlbum, genero, artista, compositor, fechaLanzamiento, calificaciones, precio);
        return nuevaCancion;
    }
    public boolean modificarCancion(String pId, String pNombreAlbum, double pPrecio){
        Cancion cancionModifica = buscarCancion(pId);

        if(cancionModifica != null){
            return cancionModifica.modificar(pNombreAlbum, pPrecio);
        }
        return false;
    }
    public boolean eliminarCancion(Cancion pCancion){
        if(existeCancion(pCancion)){
            canciones.remove(pCancion);
            return true;
        }
        return false;
    }
    public ArrayList<Cancion> listarCanciones(){
        return canciones;
    }

    public Cancion buscarCancion(String dato){
        for (Cancion objCancion: canciones) {
            if(objCancion.getId().equals(dato) || objCancion.getNombre().equals(dato)){
                return objCancion;
            }
        }
        return null;
    }
    public int obtenerIndiceCancion(Cancion pCancion){
        int indice = 0;
        for (Cancion objCancion: canciones) {
            if(objCancion.equals(pCancion)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeCancion(Cancion pCancion){
        for (Cancion objCancion: canciones) {
            if(objCancion.equals(pCancion)){
                return true;
            }
        }
        return false;
    }

    
    //**************Manejo de compositores********************
    public boolean crearCompositor(String id, String nombre, String apellidos, String paisNacimiento, String fechaNacimiento, int edad){
        Compositor nuevoCompositor = new Compositor(id, nombre, apellidos, paisNacimiento, fechaNacimiento, edad);

        //Evitar repeticion de datos.
        if(!existeCompositor(nuevoCompositor)){
            compositores.add(nuevoCompositor);
            return true;
        }

        return false;
    }
    public boolean modificarCompositor(String pId, String pNombre, String pApellidos){
        Compositor compositorModifica = buscarCompositor(pId);

        if(compositorModifica != null){
            return compositorModifica.modificar(pNombre, pApellidos);
        }

        return false;
    }
    public boolean eliminarCompositor(Compositor pCompositor){
        if(existeCompositor(pCompositor)){
            compositores.remove(pCompositor);
            return true;
        }
        return false;
    }
    public ArrayList<Compositor> listarCompositores(){
        return compositores;
    }

    public Compositor buscarCompositor(String dato){
        for (Compositor objCompositor: compositores) {
            if(objCompositor.getId().equals(dato) || objCompositor.getNombre().equals(dato)){
                return objCompositor;
            }
        }
        return null;
    }
    public int obtenerIndiceCompositor(Compositor pCompositor){
        int indice = 0;
        for (Compositor objCompositor: compositores) {
            if(objCompositor.equals(pCompositor)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeCompositor(Compositor pCompositor){
        for (Compositor objCompositor: compositores) {
            if(objCompositor.equals(pCompositor)){
                return true;
            }
        }
        return false;
    }


    //******************Manejo de Generos******************
    public boolean crearGenero(String id, String nombre, String descripcion){
        Genero nuevoGenero = new Genero(id, nombre, descripcion);

        //Evitar repeticion de datos.
        if(!existeGenero(nuevoGenero)){
            generos.add(nuevoGenero);
            return true;
        }

        return false;
    }
    public boolean modificarGenero(String pId, String pNombre, String pDesc){
        Genero generoModifica = buscarGenero(pId);

        if(generoModifica != null){
            return generoModifica.modificar(pNombre, pDesc);
        }

        return false;
    }
    public boolean eliminarGenero(Genero pGenero){
        if(existeGenero(pGenero)){
            generos.remove(pGenero);
            return true;
        }

        return false;
    }
    public ArrayList<Genero> listarGeneros(){
        return generos;
    }

    public Genero buscarGenero(String dato){
        for (Genero objGenero: generos) {
            if(objGenero.getId().equals(dato) || objGenero.getNombre().equals(dato)){
                return objGenero;
            }
        }
        return null;
    }
    public int obtenerIndiceGenero(Genero pGenero){
        int indice = 0;
        for (Genero objGenero: generos) {
            if(objGenero.equals(pGenero)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existeGenero(Genero pGenero){
        for (Genero objGenero: generos) {
            if(objGenero.equals(pGenero)){
                return true;
            }
        }
        return false;
    }


    //*****************Manejo de Paises********************
    public boolean crearPais(String id, String nombrePais, String descripcion){
        Pais nuevoPais = new Pais(id, nombrePais, descripcion);

        //Evitar repeticion de datos.
        if(!existePais(nuevoPais)){
            paises.add(nuevoPais);
            return true;
        }

        return false;
    }
    public boolean modificarPais(String pId, String pNombre, String pDescripcion){
        Pais paisModifica = buscarPais(pId);

        if(paisModifica != null){
            return paisModifica.modificar(pNombre, pDescripcion);
        }

        return false;
    }
    public boolean eliminarPais(Pais pPais){
        if(existePais(pPais)){
            paises.remove(pPais);
            return true;
        }

        return false;
    }
    public ArrayList<Pais> listarPaises(){
        return paises;
    }

    public Pais buscarPais(String dato){
        for (Pais objPais: paises) {
            if(objPais.getId().equals(dato) || objPais.getNombre().equals(dato)){
                return objPais;
            }
        }
        return null;
    }
    public int obtenerIndicePais(Pais pPais){
        int indice = 0;
        for (Pais objPais: paises) {
            if(objPais.equals(pPais)){
                return indice;
            }
            indice++;
        }
        return -1;
    }
    public boolean existePais(Pais pPais){
        for (Pais objPais: paises) {
            if(objPais.equals(pPais)){
                return true;
            }
        }
        return false;
    }
}
