package segura.taylor.dao;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * La clase DAO se encarga de realizar la conexión, lectura y escritura en la base de datos
 * @author Taylor Segura Vindas
 * @version 1.0
 */
public class RepositorioCancionesDAO {
    private ArrayList<RepositorioCanciones> repoCanciones = new ArrayList<>();

    private Connection connection;
    private CancionDAO cancionDAO;
    private ArtistaDAO artistaDAO;
    private ListasReproduccionBibliotecaDAO listasBibliotecaDAO;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public RepositorioCancionesDAO(Connection connection) {
        this.connection = connection;
        this.cancionDAO = new CancionDAO(connection);
        this.artistaDAO = new ArtistaDAO(connection);
        this.listasBibliotecaDAO = new ListasReproduccionBibliotecaDAO(connection);
    }

    /**
     * Este método se usa para escribir los datos de un nuevo repositorio de canciones en la base de datos
     * @param nuevoRepositorioCanciones instancia de la clase RepositorioCanciones que se desea guardar
     * @return el id del repositorio guardado, -1 si ocurre algún error
     */
    public int save(RepositorioCanciones nuevoRepositorioCanciones) {
        String insert = "";

        if(nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.BIBLIOTECA)) {
            Biblioteca nuevaBiblioteca = (Biblioteca) nuevoRepositorioCanciones;
            insert = "INSERT INTO bibliotecas (nombre, fechaCreacion) VALUES ";
            insert += "('" + nuevaBiblioteca.getNombre() + "','";
            insert += Date.valueOf(nuevaBiblioteca.getFechaCreacion()) + "')";

        } else if (nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.ALBUM)) {
            Album nuevoAlbum = (Album) nuevoRepositorioCanciones;
            insert = "INSERT INTO albunes (nombre, fechaCreacion, fechaLanzamiento, imagen) VALUES ";
            insert += "('" + nuevoAlbum.getNombre() + "','";
            insert += Date.valueOf(nuevoAlbum.getFechaCreacion()) + "','";
            insert += Date.valueOf(nuevoAlbum.getFechaLanzamiento()) + "','";
            insert += nuevoAlbum.getImagen() + "')";

        } else if (nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.LISTA_REPRODUCCION)) {
            ListaReproduccion nuevaListaReproduccion = (ListaReproduccion) nuevoRepositorioCanciones;

            insert = "INSERT INTO listasreproduccion (nombre, fechaCreacion, imagen, descripcion) VALUES ";
            insert += "('" + nuevaListaReproduccion.getNombre() + "','";
            insert += Date.valueOf(nuevaListaReproduccion.getFechaCreacion()) + "','";
            insert += nuevaListaReproduccion.getImagen() + "','";
            insert += nuevaListaReproduccion.getDescripcion() + "')";
        }

        int key = -1;
        try {
            Statement query = connection.createStatement();
            query.execute(insert, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = query.getGeneratedKeys();

            while (generatedKeys.next()) {
                key = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    /**
     * Este método se usa para sobreescribir los datos de un repositorio de canciones en la base de datos
     * @param RepositorioCancionesActualizado instancia de la clase Repositorio con los cambios aplicados que se desean guardar
     * @return true si la sobreescritura es correcta, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(RepositorioCanciones RepositorioCancionesActualizado) throws Exception {
        String update = "";

        if (RepositorioCancionesActualizado.getTipoRepo().equals(TipoRepositorioCanciones.ALBUM)) {
            Album nuevoAlbum = (Album) RepositorioCancionesActualizado;
            update = "UPDATE albunes ";
            update += "SET nombre = '" + nuevoAlbum.getNombre() + "',";
            update += "imagen = '" + nuevoAlbum.getImagen() + "'";
            update += " WHERE idAlbum = " + nuevoAlbum.getId();

        } else if (RepositorioCancionesActualizado.getTipoRepo().equals(TipoRepositorioCanciones.LISTA_REPRODUCCION)) {
            ListaReproduccion nuevaListaReproduccion = (ListaReproduccion) RepositorioCancionesActualizado;
            update = "UPDATE listasreproduccion ";
            update += "SET nombre = '" + nuevaListaReproduccion.getNombre() + "',";
            update += "descripcion = '" + nuevaListaReproduccion.getDescripcion() + "',";
            update += "imagen = '" + nuevaListaReproduccion.getImagen() + "'";
            update += " WHERE idListaReproduccion = " + nuevaListaReproduccion.getId();
        }

        try {
            Statement query = connection.createStatement();
            query.execute(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean delete(int idRepositorioCanciones) throws SQLException {
        RepositorioCanciones repositorioCancionesEncontrado = findByID(idRepositorioCanciones).get();

        if(repositorioCancionesEncontrado != null) {
            String delete = "";
            RepositorioCanciones repoEliminar = repositorioCancionesEncontrado;

            if(repoEliminar.getTipoRepo().equals(TipoRepositorioCanciones.ALBUM)) {
                delete = "DELETE FROM albunes WHERE idAlbum = " + repoEliminar.getId();
            } else if(repoEliminar.getTipoRepo().equals(TipoRepositorioCanciones.BIBLIOTECA)) {
                delete = "DELETE FROM bibliotecas WHERE idBiblioteca = " + repoEliminar.getId();
            } else if(repoEliminar.getTipoRepo().equals(TipoRepositorioCanciones.LISTA_REPRODUCCION)) {
                delete = "DELETE FROM listasreproduccion WHERE idListaReproduccion = " + repoEliminar.getId();
            }

            try {
                Statement query = connection.createStatement();
                query.execute(delete);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }


    //General
    /**
     * Este método se usa para obtener una lista con todos los repositorios de canciones guardados en la base de datos
     * @return una lista con todos los repositorios de canciones guardados en la base de datos
     */
    public List<RepositorioCanciones> findAll() {
        return Collections.unmodifiableList(repoCanciones);
    }

    /**
     * Este método se usa para buscar un repositorio de canciones usando como filtro su id
     * @param idRepo int que define el id del repositorio de canciones que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de la clase RepositorioCanciones si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public Optional<RepositorioCanciones> findByID(int idRepo) throws SQLException {
        Optional<Album> albumEncontrado = findAlbumById(idRepo);
        if(albumEncontrado.isPresent()) {
            return Optional.of(albumEncontrado.get());
        }

        Optional<ListaReproduccion> listaEncontrada = findListaReproduccionById(idRepo);
        if(listaEncontrada.isPresent()) {
            return Optional.of(listaEncontrada.get());
        }

        Optional<Biblioteca> bibliotecaEncontrada = findBibliotecaByID(idRepo);
        if(bibliotecaEncontrada.isPresent()) {
            return Optional.of(bibliotecaEncontrada.get());
        }

        return null;
    }


    //Albunes
    /**
     * Este método se usa para obtener una lista con todos los albunes guardados en la base de datos
     * @return una lista con todos los albunes guardados en la base de datos
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Album> findAlbunes() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM albunes");

        ArrayList<Album> listaAlbunes = new ArrayList<>();

        while (result.next()) {
            Album albumLeido = new Album();
            albumLeido.setId(result.getInt("idAlbum"));
            albumLeido.setNombre(result.getString("nombre"));
            albumLeido.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            albumLeido.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            albumLeido.setImagen(result.getString("imagen"));

            albumLeido.setCanciones(buscarCancionesAlbum(albumLeido.getId()));  //Agregar canciones al album
            albumLeido.setArtistas(buscarArtistasAlbum(albumLeido.getId()));    //Agregar artistas al album
            listaAlbunes.add(albumLeido);
        }

        return Collections.unmodifiableList(listaAlbunes);
    }
    /**
     * Este método se usa para buscar un album usando como filtro su id
     * @param idAlbum int que define el id del album que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de la clase Album si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     */
    public Optional<Album> findAlbumById(int idAlbum) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM albunes WHERE idAlbum = " + idAlbum);

        while (result.next()) {
            Album albumLeido = new Album();
            albumLeido.setId(result.getInt("idAlbum"));
            albumLeido.setNombre(result.getString("nombre"));
            albumLeido.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            albumLeido.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            albumLeido.setImagen(result.getString("imagen"));

            albumLeido.setCanciones(buscarCancionesAlbum(albumLeido.getId()));  //Agregar canciones al album
            albumLeido.setArtistas(buscarArtistasAlbum(albumLeido.getId()));    //Agregar artistas al album
            return Optional.of(albumLeido);
        }

        return Optional.empty();
    }

    private ArrayList<Cancion> buscarCancionesAlbum(int pIdAlbum) {
        try {
            ArrayList<Cancion> canciones = cancionDAO.findCancionesRepo(pIdAlbum, TipoRepositorioCanciones.ALBUM);
            return canciones;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    private ArrayList<Artista> buscarArtistasAlbum(int pIdAlbum) {
        try {
            ArrayList<Artista> artistas = artistaDAO.findArtistasAlbum(pIdAlbum);
            return artistas;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }


    //Listas de reproducción
    /**
     * Este método se usa para obtener una lista con todas las listas de reproduccion guardadas en la base de datos
     * @return una lista con todas las listas de reproduccion guardadas en la base de datos
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<ListaReproduccion> findListasReproduccion() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM listasreproduccion");

        ArrayList<ListaReproduccion> listaListasReproduccion = new ArrayList<>();

        while (result.next()) {
            ListaReproduccion listaReproduccionLeida = new ListaReproduccion();
            listaReproduccionLeida.setId(result.getInt("idListaReproduccion"));
            listaReproduccionLeida.setNombre(result.getString("nombre"));
            listaReproduccionLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            listaReproduccionLeida.setImagen(result.getString("imagen"));
            listaReproduccionLeida.setDescripcion(result.getString("descripcion"));

            listaReproduccionLeida.setCanciones(buscarCancionesLista(listaReproduccionLeida.getId()));  //Agregar canciones a la lista
            listaListasReproduccion.add(listaReproduccionLeida);
        }

        return Collections.unmodifiableList(listaListasReproduccion);
    }
    /**
     * Este método se usa para buscar una lista de reproduccion usando como filtro su id
     * @param idLista int que define el id de la lista de reproduccion que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de la clase ListaReproduccion si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB o el album no existe
     */
    public Optional<ListaReproduccion> findListaReproduccionById(int idLista) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM listasreproduccion WHERE idListaReproduccion = " + idLista));

        while (result.next()) {
            ListaReproduccion listaReproduccionLeida = new ListaReproduccion();
            listaReproduccionLeida.setId(result.getInt("idListaReproduccion"));
            listaReproduccionLeida.setNombre(result.getString("nombre"));
            listaReproduccionLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            listaReproduccionLeida.setImagen(result.getString("imagen"));
            listaReproduccionLeida.setDescripcion(result.getString("descripcion"));

            listaReproduccionLeida.setCanciones(buscarCancionesLista(listaReproduccionLeida.getId()));  //Agregar canciones a la lista
            return Optional.of(listaReproduccionLeida);
        }

        return Optional.empty();
    }

    private ArrayList<Cancion> buscarCancionesLista(int pIdLista) {
        try {
            ArrayList<Cancion> canciones = cancionDAO.findCancionesRepo(pIdLista, TipoRepositorioCanciones.LISTA_REPRODUCCION);
            return canciones;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    private ArrayList<Cancion> buscarCancionesBiblioteca(int idBiblioteca) {
        try {
            ArrayList<Cancion> canciones = cancionDAO.findCancionesRepo(idBiblioteca, TipoRepositorioCanciones.BIBLIOTECA);
            return canciones;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    private ArrayList<ListaReproduccion> buscarListasReproduccionBiblioteca(int pIdBiblioteca) {
        try {
            String idListas = listasBibliotecaDAO.getIdListasReproduccionBiblioteca(pIdBiblioteca);

            if(idListas == "") { //No hay listas
                return new ArrayList<>();
            }

            Statement query = connection.createStatement();
            ResultSet result = query.executeQuery("SELECT * FROM listasreproduccion WHERE idListaReproduccion IN (" + idListas + ")");

            ArrayList<ListaReproduccion> listaListasReproduccion = new ArrayList<>();

            while (result.next()) {
                ListaReproduccion listaReproduccionLeida = new ListaReproduccion();
                listaReproduccionLeida.setId(result.getInt("idListaReproduccion"));
                listaReproduccionLeida.setNombre(result.getString("nombre"));
                listaReproduccionLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
                listaReproduccionLeida.setImagen(result.getString("imagen"));
                listaReproduccionLeida.setDescripcion(result.getString("descripcion"));

                listaReproduccionLeida.setCanciones(buscarCancionesLista(listaReproduccionLeida.getId()));  //Agregar canciones a la lista
                listaListasReproduccion.add(listaReproduccionLeida);
            }

            return listaListasReproduccion;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
    public int getIdLista(String nombre) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM listasreproduccion WHERE nombre = '" + nombre + "'"));

        while (result.next()) {
            int idLista = result.getInt("idListaReproduccion");
            return idLista;
        }

        return -1;
    }

    //Bibliotecas
    /**
     * Este método se usa para obtener una lista con todas las bibliotecas guardadas en la base de datos
     * @return una lista con todas las bibliotecas guardadas en la base de datos
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Biblioteca> findBibliotecas() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM bibliotecas");

        ArrayList<Biblioteca> listaBibliotecas = new ArrayList<>();

        while (result.next()) {
            Biblioteca bibliotecaLeida = new Biblioteca();
            bibliotecaLeida.setId(result.getInt("idBiblioteca"));
            bibliotecaLeida.setNombre(result.getString("nombre"));
            bibliotecaLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());

            bibliotecaLeida.setCanciones(buscarCancionesBiblioteca(bibliotecaLeida.getId()));  //Agregar canciones a la biblioteca
            bibliotecaLeida.setListasDeReproduccion(buscarListasReproduccionBiblioteca(bibliotecaLeida.getId()));    //Agregar listas de reproduccion a la biblioteca

            listaBibliotecas.add(bibliotecaLeida);
        }

        return Collections.unmodifiableList(listaBibliotecas);
    }
    /**
     * Este método se usa para buscar una biblioteca usando como filtro su id
     * @param idBiblioteca int que define el id de la biblioteca que se desea encontrar
     * @return objeto de tipo Optional que contiene una instancia de la clase Biblioteca si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     */
    public Optional<Biblioteca> findBibliotecaByID(int idBiblioteca) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM bibliotecas WHERE idBiblioteca = " + idBiblioteca));

        while (result.next()) {
            Biblioteca bibliotecaLeida = new Biblioteca();
            bibliotecaLeida.setId(result.getInt("idBiblioteca"));
            bibliotecaLeida.setNombre(result.getString("nombre"));
            bibliotecaLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());

            bibliotecaLeida.setCanciones(buscarCancionesBiblioteca(bibliotecaLeida.getId()));  //Agregar canciones a la biblioteca
            bibliotecaLeida.setListasDeReproduccion(buscarListasReproduccionBiblioteca(bibliotecaLeida.getId()));    //Agregar listas de reproduccion a la biblioteca
            return Optional.of(bibliotecaLeida);
        }

        return Optional.empty();
    }
}
