package segura.taylor.dao;

import segura.taylor.bl.entidades.Cancion;
import segura.taylor.bl.enums.TipoCancion;
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
public class CancionDAO {
    private ArrayList<Cancion> canciones = new ArrayList<>();

    private Connection connection;
    private GeneroDAO generoDAO;
    private ArtistaDAO artistaDAO;
    private CompositorDAO compositorDAO;
    private CalificacionDAO calificacionDAO;

    private CancionesAlbumDAO cancionesAlbumDAO;
    private CancionesListaReproduccionDAO cancionesListaReproduccionDAO;
    private CancionesBibliotecaDAO cancionesBibliotecaDAO;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public CancionDAO(Connection connection) {
        this.connection = connection;
        this.generoDAO = new GeneroDAO(connection);
        this.artistaDAO = new ArtistaDAO(connection);
        this.compositorDAO = new CompositorDAO(connection);
        this.calificacionDAO = new CalificacionDAO(connection);

        this.cancionesAlbumDAO = new CancionesAlbumDAO(connection);
        this.cancionesListaReproduccionDAO = new CancionesListaReproduccionDAO(connection);
        this.cancionesBibliotecaDAO = new CancionesBibliotecaDAO(connection);
    }

    /**
     * Este método se usa para escribir los datos de una nueva cancion en la base de datos
     * @param nuevaCancion instancia de la clase Cancion que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public int save(Cancion nuevaCancion) throws Exception {
        int key = -1;

        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO canciones (tipoCancion, nombre, recurso, duracion, fechaLanzamiento, precio, idGenero, idArtista, idCompositor) VALUES ";
            insert += "('" + nuevaCancion.getTipoCancion().toString() + "','";
            insert += nuevaCancion.getNombre() + "','";
            insert += nuevaCancion.getRecurso() + "',";
            insert += nuevaCancion.getDuracion() + ",'";
            insert += Date.valueOf(nuevaCancion.getFechaLanzamiento()) + "',";
            insert += nuevaCancion.getPrecio() + ",";

            insert += nuevaCancion.getGenero().getId() + ",";
            insert += nuevaCancion.getArtista().getId() + ",";
            insert += nuevaCancion.getCompositor().getId() + ")";

            System.out.println("Ejecuto query: " + insert);
            query.execute(insert, Statement.RETURN_GENERATED_KEYS);

            ResultSet generatedKeys = query.getGeneratedKeys();

            while (generatedKeys.next()) {
                key = generatedKeys.getInt(1);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return key;
    }

    /**
     * Este método se usa para sobreescribir los datos de una cancion en la base de datos
     * @param cancionActualizada instancia de la clase Compositor con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Cancion cancionActualizada) throws Exception {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE canciones ";
            update += "SET nombre = '" + cancionActualizada.getNombre() + "', ";
            update += "recurso = '" + cancionActualizada.getRecurso() + "', ";
            update += "duracion = " + cancionActualizada.getDuracion() + ", ";
            update += "fechaLanzamiento = " + ( (cancionActualizada.getFechaLanzamiento() != null) ? "'" + Date.valueOf(cancionActualizada.getFechaLanzamiento()) + "'" : null) + ", ";
            update += "precio = " + cancionActualizada.getPrecio() + ", ";
            update += "idGenero = " + cancionActualizada.getGenero().getId() + ", ";
            update += "idArtista = " + cancionActualizada.getArtista().getId() + ", ";
            update += "idCompositor = " + cancionActualizada.getCompositor().getId() + "";

            update += " WHERE idCancion = " + cancionActualizada.getId();

            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para eliminar una cancion de la base de datos
     * @param idCancion int que define el id de la cancion que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idCancion) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones WHERE idCancion = " + idCancion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para obtener una lista con todas las canciones guardadas en la base de datos
     * @return una lista con todas las canciones guardadas
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Cancion> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones");

        ArrayList<Cancion> listaCanciones = new ArrayList<>();

        while (result.next()) {
            Cancion cancionLeida = new Cancion();
            cancionLeida.setId(result.getInt("idCancion"));
            cancionLeida.setTipoCancion(TipoCancion.valueOf(result.getString("tipoCancion")));

            cancionLeida.setNombre(result.getString("nombre"));
            cancionLeida.setRecurso(result.getString("recurso"));

            cancionLeida.setDuracion(result.getDouble("duracion"));
            cancionLeida.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            cancionLeida.setPrecio(result.getDouble("precio"));

            cancionLeida.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());
            cancionLeida.setArtista(artistaDAO.findByID(result.getInt("idArtista")).get());
            cancionLeida.setCompositor(compositorDAO.findByID(result.getInt("idCompositor")).get());

            cancionLeida.setCalificaciones(calificacionDAO.findByIdCancion(cancionLeida.getId()));
            listaCanciones.add(cancionLeida);
        }

        return Collections.unmodifiableList(listaCanciones);
    }

    /**
     * Este método se usa para buscar una cancion usando como filtro su id
     * @param id int que define el id de la cancion que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Cancion si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Cancion
     */
    public Optional<Cancion> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones WHERE idCancion = " + id);

        while (result.next()) {
            Cancion cancionLeida = new Cancion();
            cancionLeida.setId(result.getInt("idCancion"));
            cancionLeida.setTipoCancion(TipoCancion.valueOf(result.getString("tipoCancion")));

            cancionLeida.setNombre(result.getString("nombre"));
            cancionLeida.setRecurso(result.getString("recurso"));

            cancionLeida.setDuracion(result.getDouble("duracion"));
            cancionLeida.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            cancionLeida.setPrecio(result.getDouble("precio"));

            cancionLeida.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());
            cancionLeida.setArtista(artistaDAO.findByID(result.getInt("idArtista")).get());
            cancionLeida.setCompositor(compositorDAO.findByID(result.getInt("idCompositor")).get());

            return Optional.of(cancionLeida);
        }

        return Optional.empty();
    }


    public ArrayList<Cancion> findCancionesRepo(int idRepo, TipoRepositorioCanciones tipoRepo) throws SQLException {
        String idCanciones = "";

        if(TipoRepositorioCanciones.ALBUM.equals(tipoRepo)) {
            idCanciones = cancionesAlbumDAO.getIdCancionesAlbum(idRepo);
        } else if(TipoRepositorioCanciones.LISTA_REPRODUCCION.equals(tipoRepo)) {
            idCanciones = cancionesListaReproduccionDAO.getIdCancionesListaReproduccion(idRepo);
        } else if(TipoRepositorioCanciones.BIBLIOTECA.equals(tipoRepo)) {
            idCanciones = cancionesBibliotecaDAO.getIdCancionesBiblioteca(idRepo);
        }

        if(idCanciones == "") { //No hay canciones
            return new ArrayList<>();
        }

        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones WHERE idCancion IN (" + idCanciones + ")");

        ArrayList<Cancion> listaCanciones = new ArrayList<>();

        while (result.next()) {
            Cancion cancionLeida = new Cancion();
            cancionLeida.setId(result.getInt("idCancion"));
            cancionLeida.setTipoCancion(TipoCancion.valueOf(result.getString("tipoCancion")));

            cancionLeida.setNombre(result.getString("nombre"));
            cancionLeida.setRecurso(result.getString("recurso"));

            cancionLeida.setDuracion(result.getDouble("duracion"));
            cancionLeida.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            cancionLeida.setPrecio(result.getDouble("precio"));

            cancionLeida.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());
            cancionLeida.setArtista(artistaDAO.findByID(result.getInt("idArtista")).get());
            cancionLeida.setCompositor(compositorDAO.findByID(result.getInt("idCompositor")).get());

            listaCanciones.add(cancionLeida);
        }

        return listaCanciones;
    }
}
