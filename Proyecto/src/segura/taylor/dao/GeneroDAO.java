package segura.taylor.dao;

import segura.taylor.bl.entidades.Genero;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * La clase DAO se encarga de realizar la conexión, lectura y escritura en la base de datos
 * @author Taylor Segura Vindas
 * @version 1.0
 */
public class GeneroDAO {
    private ArrayList<Genero> generos = new ArrayList<>();

    private Connection connection;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public GeneroDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Este método se usa para escribir los datos de un nuevo genero en la base de datos
     * @param nuevoGenero instancia de la clase Genero que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean save(Genero nuevoGenero) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO generos (nombre, descripcion) VALUES ";
            insert += "('" + nuevoGenero.getNombre() + "','";
            insert += nuevoGenero.getDescripcion() + "')";

            query.execute(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Este método se usa para sobreescribir los datos de un genero en la base de datos
     * @param generoActualizado instancia de la clase Genero con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Genero generoActualizado) throws Exception {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE generos ";
            update += "SET nombre = '" + generoActualizado.getNombre() + "',";
            update += "descripcion = '" + generoActualizado.getDescripcion() + "'";
            update += " WHERE idGenero = " + generoActualizado.getId();

            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para eliminar un genero de la base de datos
     * @param idGenero int que define el id del genero que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idGenero) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM generos WHERE idGenero = " + idGenero;

            query.execute(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Este método se usa para obtener una lista con todos los generos guardados en la base de datos
     * @return una lista con todos los generos guardados
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Genero> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM generos");

        ArrayList<Genero> listaGeneros = new ArrayList<>();

        while (result.next()) {
            Genero generoLeido = new Genero();
            generoLeido.setId(result.getInt("idGenero"));
            generoLeido.setNombre(result.getString("nombre"));
            generoLeido.setDescripcion(result.getString("descripcion"));

            listaGeneros.add(generoLeido);
        }

        return Collections.unmodifiableList(listaGeneros);
    }

    /**
     * Este método se usa para buscar un genero usando como filtro su id
     * @param id int que define el id del genero que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Genero si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Genero
     */
    public Optional<Genero> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM generos WHERE idGenero = " + id));

        while (result.next()) {
            Genero generoLeido = new Genero();
            generoLeido.setId(result.getInt("idGenero"));
            generoLeido.setNombre(result.getString("nombre"));
            generoLeido.setDescripcion(result.getString("descripcion"));

            return Optional.of(generoLeido);
        }

        return Optional.empty();
    }

    /**
     * Este método se usa para buscar un genero usando como filtro su id
     * @param nombre String que define el nombre del genero que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Genero si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Genero
     */
    public Optional<Genero> findByNombre(String nombre) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM generos WHERE nombre = '" + nombre + "'"));

        while (result.next()) {
            Genero generoLeido = new Genero();
            generoLeido.setId(result.getInt("idGenero"));
            generoLeido.setNombre(result.getString("nombre"));
            generoLeido.setDescripcion(result.getString("descripcion"));

            return Optional.of(generoLeido);
        }

        return Optional.empty();
    }
}
