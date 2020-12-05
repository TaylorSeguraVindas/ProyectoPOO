package segura.taylor.dao;

import segura.taylor.bl.entidades.Pais;

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
public class PaisDAO {
    private ArrayList<Pais> paises = new ArrayList<>();

    private Connection connection;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public PaisDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Este método se usa para escribir los datos de un nuevo pais en la base de datos
     * @param nuevoPais instancia de la clase Pais que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean save(Pais nuevoPais) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO paises (nombre, descripcion) VALUES ";
            insert += "('" + nuevoPais.getNombre() + "','";
            insert += nuevoPais.getDescripcion() + "')";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para sobreescribir los datos de un pais en la base de datos
     * @param paisActualizado instancia de la clase Pais con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Pais paisActualizado) throws Exception {
        int indicePais = -1;
        int cont = 0;

        for (Pais Pais : paises) {
            if(Pais.getId() == paisActualizado.getId()) {
                indicePais = cont;
                break;
            }

            cont++;
        }

        if(indicePais != -1) {
            paises.set(indicePais, paisActualizado);
            return true;
        }

        throw new Exception("El Pais que se desea actualizar no existe");
    }

    /**
     * Este método se usa para eliminar un pais de la base de datos
     * @param idPais int que define el id del pais que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idPais) throws Exception {
        Optional<Pais> PaisEncontrado = findByID(idPais);

        if(PaisEncontrado.isPresent()) {
            paises.remove(PaisEncontrado.get());
            return true;
        }

        throw new Exception("El Pais que se desea eliminar no existe");
    }

    /**
     * Este método se usa para obtener una lista con todos los paises guardados en la base de datos
     * @return una lista con todos los paises guardados
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Pais> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM paises");

        ArrayList<Pais> listaPaises = new ArrayList<>();

        while (result.next()) {
            Pais paisLeido = new Pais();
            paisLeido.setId(result.getInt("idPais"));
            paisLeido.setNombre(result.getString("nombre"));
            paisLeido.setDescripcion(result.getString("descripcion"));

            listaPaises.add(paisLeido);
        }

        return Collections.unmodifiableList(listaPaises);
    }

    /**
     * Este método se usa para buscar un pais usando como filtro su id
     * @param id int que define el id del pais que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Pais si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Pais
     */
    public Optional<Pais> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM paises WHERE idPais = " + id));

        while (result.next()) {
            Pais paisLeido = new Pais();
            paisLeido.setId(result.getInt("idPais"));
            paisLeido.setNombre(result.getString("nombre"));
            paisLeido.setDescripcion(result.getString("descripcion"));

            return Optional.of(paisLeido);
        }

        return Optional.empty();
    }
}
