package segura.taylor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ListasReproduccionBibliotecaDAO {
    private Connection connection;

    public ListasReproduccionBibliotecaDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(int idBiblioteca, int idLista) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO canciones_album (idBiblioteca, idLista) VALUES ";
            insert += "(" + idBiblioteca + ",";
            insert += idLista + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idBiblioteca, int idLista) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM canciones_album WHERE idBiblioteca = " + idBiblioteca + " and idLista = " + idLista;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getIdListasReproduccionBiblioteca(int idBiblioteca) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM listas_biblioteca WHERE idBiblioteca = " + idBiblioteca);

        String listaCanciones = "";

        while (result.next()) {
            listaCanciones += result.getString("idLista") + ",";
        }

        if(listaCanciones.length() > 0) {
            listaCanciones = listaCanciones.substring(0, listaCanciones.length()-1); //Quitar la última coma
        }

        return listaCanciones;
    }
}
