package segura.taylor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionesAlbumDAO {

    private Connection connection;

    public CancionesAlbumDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(int idAlbum, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO canciones_album (idAlbum, idCancion) VALUES ";
            insert += "(" + idAlbum + ",";
            insert += idCancion + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idAlbum, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones_album WHERE idAlbum = " + idAlbum + " and idCancion = " + idCancion;

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
            String insert = "DELETE FROM canciones_album WHERE idCancion = " + idCancion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getIdCancionesAlbum(int idAlbum) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones_album WHERE idAlbum = " + idAlbum);

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
