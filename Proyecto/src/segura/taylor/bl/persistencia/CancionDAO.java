package segura.taylor.bl.persistencia;

import javafx.scene.control.ComboBox;
import segura.taylor.bl.entidades.Cancion;
import segura.taylor.bl.entidades.Compositor;
import segura.taylor.bl.enums.TipoCancion;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CancionDAO {
    private ArrayList<Cancion> canciones = new ArrayList<>();

    private Connection connection;
    private GeneroDAO generoDAO;
    private ArtistaDAO artistaDAO;
    private CompositorDAO compositorDAO;

    public CancionDAO(Connection connection) {
        this.connection = connection;
        this.generoDAO = new GeneroDAO(connection);
        this.artistaDAO = new ArtistaDAO(connection);
        this.compositorDAO = new CompositorDAO(connection);
    }

    public boolean save(Cancion nuevaCancion) throws Exception {
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
            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Cancion CancionActualizado) throws Exception {
        int indiceCancion = -1;
        int cont = 0;

        for (Cancion Cancion : canciones) {
            if(Cancion.getId() == CancionActualizado.getId()) {
                indiceCancion = cont;
                break;
            }

            cont++;
        }

        if(indiceCancion != -1) {
            canciones.set(indiceCancion, CancionActualizado);
            return true;
        }

        throw new Exception("La cancion que se desea actualizar no existe");
    }

    public boolean delete(int idCancion) throws Exception {
        Optional<Cancion> CancionEncontrado = findByID(idCancion);

        if(CancionEncontrado.isPresent()) {
            canciones.remove(CancionEncontrado.get());
            return true;
        }

        throw new Exception("La cancion que se desea eliminar no existe");
    }

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

            listaCanciones.add(cancionLeida);
        }

        return Collections.unmodifiableList(listaCanciones);
    }

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
}
