package segura.taylor.dao;

import segura.taylor.bl.entidades.Compositor;

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
public class CompositorDAO {
    private ArrayList<Compositor> compositores = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private GeneroDAO generoDAO;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public CompositorDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.generoDAO = new GeneroDAO(connection);
    }

    /**
     * Este método se usa para escribir los datos de un nuevo compositor en la base de datos
     * @param nuevoCompositor instancia de la clase Compositor que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean save(Compositor nuevoCompositor) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO compositores (nombre, apellidos, fechaNacimiento, idPais, idGenero) VALUES ";
            insert += "('" + nuevoCompositor.getNombre() + "','";
            insert += nuevoCompositor.getApellidos() + "','";
            insert += Date.valueOf(nuevoCompositor.getFechaNacimiento()) + "',";
            insert += nuevoCompositor.getPaisNacimiento().getId() + ",";
            insert += nuevoCompositor.getGenero().getId() + ")";
            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para sobreescribir los datos de un compositor en la base de datos
     * @param compositorActualizado instancia de la clase Compositor con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Compositor compositorActualizado) throws Exception {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE compositores ";
            update += "SET nombre = '" + compositorActualizado.getNombre() + "',";
            update += "apellidos = '" + compositorActualizado.getApellidos() + "'";
            update += " WHERE idCompositor = " + compositorActualizado.getId();

            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para eliminar un compositor de la base de datos
     * @param idCompositor int que define el id del compositor que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idCompositor) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM compositores WHERE idCompositor = " + idCompositor;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para obtener una lista con todos los compositores guardados en la base de datos
     * @return una lista con todos los compositores guardados
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Compositor> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM compositores");

        ArrayList<Compositor> listaCompositores = new ArrayList<>();

        while (result.next()) {
            Compositor compositorLeido = new Compositor();
            compositorLeido.setId(result.getInt("idCompositor"));
            compositorLeido.setNombre(result.getString("nombre"));
            compositorLeido.setApellidos(result.getString("apellidos"));
            compositorLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());
            compositorLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            compositorLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            listaCompositores.add(compositorLeido);
        }

        return Collections.unmodifiableList(listaCompositores);
    }

    /**
     * Este método se usa para buscar un compositor usando como filtro su id
     * @param id int que define el id del compositor que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Compositor si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Compositor
     */
    public Optional<Compositor> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM compositores WHERE idCompositor = " + id);

        while (result.next()) {
            Compositor compositorLeido = new Compositor();
            compositorLeido.setId(result.getInt("idCompositor"));
            compositorLeido.setNombre(result.getString("nombre"));
            compositorLeido.setApellidos(result.getString("apellidos"));
            compositorLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());
            compositorLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            compositorLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            return Optional.of(compositorLeido);
        }

        return Optional.empty();
    }
}
