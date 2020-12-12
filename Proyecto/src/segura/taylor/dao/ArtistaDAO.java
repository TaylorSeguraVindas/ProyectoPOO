package segura.taylor.dao;

import segura.taylor.bl.entidades.Artista;

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
public class ArtistaDAO {
    private ArrayList<Artista> artistas = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private GeneroDAO generoDAO;
    private ArtistasAlbumDAO artistasAlbumDAO;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public ArtistaDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.generoDAO = new GeneroDAO(connection);
        this.artistasAlbumDAO = new ArtistasAlbumDAO(connection);
    }

    /**
     * Este método se usa para escribir los datos de un nuevo artista en la base de datos
     * @param nuevoArtista instancia de la clase artista que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean save(Artista nuevoArtista) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO artistas (nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, descripcion, idPais, idGenero) VALUES ";
            insert += "('" + nuevoArtista.getNombre() + "','";
            insert += nuevoArtista.getApellidos() + "','";
            insert += nuevoArtista.getNombreArtistico() + "','";

            insert += Date.valueOf(nuevoArtista.getFechaNacimiento()) + "',";
            insert += (nuevoArtista.getFechaDefuncion() != null) ? "'" + Date.valueOf(nuevoArtista.getFechaDefuncion()) + "','" : null + ",'";
            insert += nuevoArtista.getDescripcion() + "',";

            insert += nuevoArtista.getPaisNacimiento().getId() + ",";
            insert += nuevoArtista.getGenero().getId() + ")";
            System.out.println("Ejecuto query: " + insert);
            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para sobreescribir los datos de un artista en la base de datos
     * @param artistaActualizado instancia de la clase artista con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Artista artistaActualizado) throws Exception {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE artistas ";
            update += "SET nombre = '" + artistaActualizado.getNombre() + "',";
            update += "apellidos = '" + artistaActualizado.getApellidos() + "',";
            update += "nombreArtistico = '" + artistaActualizado.getNombreArtistico() + "',";
            update += "fechaDefuncion = " + ((artistaActualizado.getFechaDefuncion() != null) ? "'" + Date.valueOf(artistaActualizado.getFechaDefuncion()) + "'," : null + ",");
            update += "descripcion = '" + artistaActualizado.getDescripcion() + "'";
            update += " WHERE idArtista = " + artistaActualizado.getId();

            System.out.println("Ejecuto query: " + update);
            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para eliminar un artista de la base de datos
     * @param idArtista int que define el id del artista que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idArtista) throws Exception {
        try {
            Statement query = connection.createStatement();
            String delete = "DELETE FROM artistas WHERE idArtista = " + idArtista;

            query.execute(delete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para obtener una lista con todos los artistas guardados en la base de datos
     * @return una lista con todos los artistas guardados
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Artista> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas");

        ArrayList<Artista> listaArtistas = new ArrayList<>();

        while (result.next()) {
            Artista artistaLeido = new Artista();
            artistaLeido.setId(result.getInt("idArtista"));
            artistaLeido.setNombre(result.getString("nombre"));
            artistaLeido.setApellidos(result.getString("apellidos"));
            artistaLeido.setNombreArtistico(result.getString("nombreArtistico"));
            artistaLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            //Nulleable
            Date fechaDefuncion = result.getDate("fechaDefuncion");
            artistaLeido.setFechaDefuncion((fechaDefuncion != null) ? fechaDefuncion.toLocalDate() : null);

            artistaLeido.setDescripcion(result.getString("descripcion"));

            artistaLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            artistaLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            listaArtistas.add(artistaLeido);
        }

        return Collections.unmodifiableList(listaArtistas);
    }

    /**
     * Este método se usa para buscar un artista usando como filtro su id
     * @param id int que define el id del artista que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Artista si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Artista
     */
    public Optional<Artista> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas where idArtista = " + id);

        while (result.next()) {
            Artista artistaLeido = new Artista();
            artistaLeido.setId(result.getInt("idArtista"));
            artistaLeido.setNombre(result.getString("nombre"));
            artistaLeido.setApellidos(result.getString("apellidos"));
            artistaLeido.setNombreArtistico(result.getString("nombreArtistico"));
            artistaLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            //Nulleable
            Date fechaDefuncion = result.getDate("fechaDefuncion");
            artistaLeido.setFechaDefuncion((fechaDefuncion != null) ? fechaDefuncion.toLocalDate() : null);

            artistaLeido.setDescripcion(result.getString("descripcion"));

            artistaLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            artistaLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            return Optional.of(artistaLeido);
        }

        return Optional.empty();
    }

    public ArrayList<Artista> findArtistasAlbum(int idAlbum) throws SQLException {
        String idArtistas = artistasAlbumDAO.getIdArtistasAlbum(idAlbum);

        if(idArtistas == "") {  //No hay artistas
            return new ArrayList<>();
        }

        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas WHERE idArtista IN (" + idArtistas + ")");

        ArrayList<Artista> listaArtistas = new ArrayList<>();

        while (result.next()) {
            Artista artistaLeido = new Artista();
            artistaLeido.setId(result.getInt("idArtista"));
            artistaLeido.setNombre(result.getString("nombre"));
            artistaLeido.setApellidos(result.getString("apellidos"));
            artistaLeido.setNombreArtistico(result.getString("nombreArtistico"));
            artistaLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            //Nulleable
            Date fechaDefuncion = result.getDate("fechaDefuncion");
            artistaLeido.setFechaDefuncion((fechaDefuncion != null) ? fechaDefuncion.toLocalDate() : null);

            artistaLeido.setDescripcion(result.getString("descripcion"));

            artistaLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            artistaLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            listaArtistas.add(artistaLeido);
        }

        return listaArtistas;
    }
}
