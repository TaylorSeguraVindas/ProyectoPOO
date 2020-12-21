package segura.taylor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionesListaReproduccionDAO {
    private Connection connection;

    public CancionesListaReproduccionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(int idListaReproduccion, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO canciones_listareproduccion (idListaReproduccion, idCancion) VALUES ";
            insert += "(" + idListaReproduccion + ",";
            insert += idCancion + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idListaReproduccion, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones_listareproduccion WHERE idListaReproduccion = " + idListaReproduccion + " and idCancion = " + idCancion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean onDeleteCancion(int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones_listareproduccion WHERE idCancion = " + idCancion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getIdCancionesListaReproduccion(int idListaReproduccion) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones_listareproduccion WHERE idListaReproduccion = " + idListaReproduccion);

        String listaCanciones = "";

        while (result.next()) {
            listaCanciones += result.getString("idCancion") + ",";
        }

        if(listaCanciones.length() > 0) {
            listaCanciones = listaCanciones.substring(0, listaCanciones.length()-1); //Quitar la última coma
        }

        return listaCanciones;
    }
}
