package segura.taylor.dao;

import segura.taylor.bl.entidades.Artista;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ArtistaDAO {
    private ArrayList<Artista> artistas = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private GeneroDAO generoDAO;

    public ArtistaDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.generoDAO = new GeneroDAO(connection);
    }

    public boolean save(Artista nuevoArtista) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO artistas (nombre, apellidos, nombreArtistico, fechaNacimiento, fechaDefuncion, descripcion, idPais, idGenero) VALUES ";
            insert += "('" + nuevoArtista.getNombre() + "','";
            insert += nuevoArtista.getApellidos() + "','";
            insert += nuevoArtista.getNombreArtistico() + "','";

            insert += Date.valueOf(nuevoArtista.getFechaNacimiento()) + "','";
            insert += (nuevoArtista.getFechaNacimiento() != null) ? Date.valueOf(nuevoArtista.getFechaNacimiento()) + "','" : null;
            insert += nuevoArtista.getDescripcion() + "',";

            insert += nuevoArtista.getPaisNacimiento().getId() + ",";
            insert += nuevoArtista.getGenero().getId() + ")";
            System.out.println("Ejecuto query: " + insert);
            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Artista artistaActualizado) throws Exception {
        int indiceArtista = -1;
        int cont = 0;

        for (Artista artista : artistas) {
            if(artista.getId() == artistaActualizado.getId()) {
                indiceArtista = cont;
                break;
            }

            cont++;
        }

        if(indiceArtista != -1) {
            artistas.set(indiceArtista, artistaActualizado);
            return true;
        }

        throw new Exception("El artista que se desea actualizar no existe");
    }

    public boolean delete(int idArtista) throws Exception {
        Optional<Artista> artistaEncontrado = findByID(idArtista);

        if(artistaEncontrado.isPresent()) {
            artistas.remove(artistaEncontrado.get());
            return true;
        }

        throw new Exception("El artista que se desea eliminar no existe");
    }

    public List<Artista> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas");

        ArrayList<Artista> listaArtistas = new ArrayList<>();

        while (result.next()) {
            Artista artistaLeido = new Artista();
            artistaLeido.setId(result.getInt("idArtista"));
            artistaLeido.setNombre(result.getString("nombre"));
            artistaLeido.setApellidos(result.getString("apellidos"));
            artistaLeido.setNombreArtistico(result.getString("nombreArtistico"));
            artistaLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            //Nulleable
            Date fechaDefuncion = result.getDate("fechaDefuncion");
            artistaLeido.setFechaDefuncion((fechaDefuncion != null) ? fechaDefuncion.toLocalDate() : null);

            artistaLeido.setDescripcion(result.getString("descripcion"));

            artistaLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            artistaLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            listaArtistas.add(artistaLeido);
        }

        return Collections.unmodifiableList(listaArtistas);
    }

    public Optional<Artista> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM artistas where idArtista = " + id);

        while (result.next()) {
            Artista artistaLeido = new Artista();
            artistaLeido.setId(result.getInt("idArtista"));
            artistaLeido.setNombre(result.getString("nombre"));
            artistaLeido.setApellidos(result.getString("apellidos"));
            artistaLeido.setNombreArtistico(result.getString("nombreArtistico"));
            artistaLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            //Nulleable
            Date fechaDefuncion = result.getDate("fechaDefuncion");
            artistaLeido.setFechaDefuncion((fechaDefuncion != null) ? fechaDefuncion.toLocalDate() : null);

            artistaLeido.setDescripcion(result.getString("descripcion"));

            artistaLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            artistaLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            return Optional.of(artistaLeido);
        }

        return Optional.empty();
    }
}
