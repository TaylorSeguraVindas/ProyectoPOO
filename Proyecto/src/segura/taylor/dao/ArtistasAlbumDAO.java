package segura.taylor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArtistasAlbumDAO {
    private Connection connection;

    public ArtistasAlbumDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(int idAlbum, int idArtista) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO artistas_album (idAlbum, idArtista) VALUES ";
            insert += "(" + idAlbum + ",";
            insert += idArtista + ")";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int idAlbum, int idArtista) {
        try {
            Statement query = connection.createStatement();
            String insert = "DELETE FROM artistas_album WHERE idAlbum = " + idAlbum + " and idArtista = " + idArtista;

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public String getIdArtistasAlbum(int idAlbum) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas_album WHERE idAlbum = " + idAlbum);

        String listaArtistas = "";

        while (result.next()) {
            listaArtistas += result.getString("idArtista") + ",";
        }

        if(listaArtistas.length() > 0) {
            listaArtistas = listaArtistas.substring(0, listaArtistas.length()-1); //Quitar la última coma
        }

        return listaArtistas;
    }
}
