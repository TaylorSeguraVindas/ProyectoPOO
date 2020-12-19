package segura.taylor.dao;

import segura.taylor.bl.entidades.Calificacion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CalificacionDAO {
    private ArrayList<Calificacion> calificaciones = new ArrayList<>();

    private Connection connection;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public CalificacionDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Este método se usa para escribir los datos de un nuevo calificacion en la base de datos
     * @param nuevaCalificacion instancia de la clase calificacion que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean save(Calificacion nuevaCalificacion, int idCancion, int idAutor) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO calificaciones (estrellas, idAutor, idCancion) VALUES ";
            insert += "(" + nuevaCalificacion.getEstrellas() + ",";
            insert += idAutor + ",";
            insert += idCancion + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para sobreescribir los datos de un calificacion en la base de datos
     * @param calificacionActualizado instancia de la clase calificacion con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Calificacion calificacionActualizado) throws Exception {
        try {
            Statement query = connection.createStatement();
            String update = "UPDATE calificaciones ";
            update += "SET estrellas = " + calificacionActualizado.getEstrellas() + "";
            update += " WHERE idCalificacion = " + calificacionActualizado.getId();

            query.execute(update);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para eliminar un calificacion de la base de datos
     * @param idcalificacion int que define el id del calificacion que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idcalificacion) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM calificaciones WHERE idCalificacion = " + idcalificacion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Este método se usa para obtener una lista con todos los calificaciones guardados en la base de datos
     * @return una lista con todos los calificaciones guardados
     * @throws SQLException si no se puede conectar con la DB
     */
    public List<Calificacion> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM calificaciones");

        ArrayList<Calificacion> listacalificaciones = new ArrayList<>();

        while (result.next()) {
            Calificacion calificacionLeida = new Calificacion();
            calificacionLeida.setId(result.getInt("idCalificacion"));
            calificacionLeida.setEstrellas(result.getInt("estrellas"));

            listacalificaciones.add(calificacionLeida);
        }

        return Collections.unmodifiableList(listacalificaciones);
    }

    /**
     * Este método se usa para buscar un calificacion usando como filtro su id
     * @param id int que define el id del calificacion que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de calificacion si se encuentra una coincidencia
     * @throws SQLException si no se puede conectar con la DB
     * @see Optional
     * @see Calificacion
     */
    public Optional<Calificacion> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM calificaciones WHERE idCalificacion = " + id));

        while (result.next()) {
            Calificacion calificacionLeida = new Calificacion();
            calificacionLeida.setId(result.getInt("idCalificacion"));
            calificacionLeida.setEstrellas(result.getInt("estrellas"));

            return Optional.of(calificacionLeida);
        }

        return Optional.empty();
    }

    public ArrayList<Calificacion> findByIdCancion(int idCancion) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM calificaciones WHERE idCancion = " + idCancion));

        ArrayList<Calificacion> listacalificaciones = new ArrayList<>();

        while (result.next()) {
            Calificacion calificacionLeida = new Calificacion();
            calificacionLeida.setId(result.getInt("idCalificacion"));
            calificacionLeida.setEstrellas(result.getInt("estrellas"));

            listacalificaciones.add(calificacionLeida);
        }

        return listacalificaciones;
    }

    public Optional<Calificacion> findByCancionYUsuario(int idCancion, int idUsuario) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM calificaciones WHERE idCancion = " + idCancion + " AND idAutor = " + idUsuario));

        while (result.next()) {
            Calificacion calificacionLeida = new Calificacion();
            calificacionLeida.setId(result.getInt("idCalificacion"));
            calificacionLeida.setEstrellas(result.getInt("estrellas"));

            return Optional.of(calificacionLeida);
        }

        return Optional.empty();
    }
}
