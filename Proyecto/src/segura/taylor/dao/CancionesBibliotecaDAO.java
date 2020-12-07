package segura.taylor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CancionesBibliotecaDAO {
    private Connection connection;

    public CancionesBibliotecaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(int idBiblioteca, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO canciones_biblioteca (idBiblioteca, idCancion) VALUES ";
            insert += "(" + idBiblioteca + ",";
            insert += idCancion + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idBiblioteca, int idCancion) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones_biblioteca WHERE idBiblioteca = " + idBiblioteca + " and idCancion = " + idCancion;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getIdCancionesBiblioteca(int idBiblioteca) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM canciones_biblioteca WHERE idBiblioteca = " + idBiblioteca);

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
