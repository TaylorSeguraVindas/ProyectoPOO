package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RepositorioCancionesDAO {
    private ArrayList<RepositorioCanciones> repoCanciones = new ArrayList<>();

    private Connection connection;

    public RepositorioCancionesDAO(Connection connection) {
        this.connection = connection;
    }

    //Devuelve el id de la lista creada.
    public int save(RepositorioCanciones nuevoRepositorioCanciones) {
        String insert = "";

        if(nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.BIBLIOTECA)) {
            Biblioteca nuevaBiblioteca = (Biblioteca) nuevoRepositorioCanciones;
            insert = "INSERT INTO bibliotecas (nombre, fechaCreacion) VALUES ";
            insert += "('" + nuevaBiblioteca.getNombre() + "','";
            insert += Date.valueOf(nuevaBiblioteca.getFechaCreacion()) + "')";

        } else if (nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.ALBUM)) {
            Album nuevoAlbum = (Album) nuevoRepositorioCanciones;
            insert = "INSERT INTO albunes (nombre, fechaCreacion, fechaLanzamiento, imagen) VALUES ";
            insert += "('" + nuevoAlbum.getNombre() + "','";
            insert += Date.valueOf(nuevoAlbum.getFechaCreacion()) + "','";
            insert += Date.valueOf(nuevoAlbum.getFechaLanzamiento()) + "','";
            insert += nuevoAlbum.getImagen() + "')";

        } else if (nuevoRepositorioCanciones.getTipoRepo().equals(TipoRepositorioCanciones.LISTA_REPRODUCCION)) {
            ListaReproduccion nuevaListaReproduccion = (ListaReproduccion) nuevoRepositorioCanciones;

            insert = "INSERT INTO listasreproduccion (nombre, fechaCreacion, imagen, descripcion) VALUES ";
            insert += "('" + nuevaListaReproduccion.getNombre() + "','";
            insert += Date.valueOf(nuevaListaReproduccion.getFechaCreacion()) + "','";
            insert += nuevaListaReproduccion.getImagen() + "','";
            insert += nuevaListaReproduccion.getDescripcion() + "')";
        }

        int key = -1;
        try {
            Statement query = connection.createStatement();
            query.execute(insert, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = query.getGeneratedKeys();

            while (generatedKeys.next()) {
                key = generatedKeys.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return key;
    }

    public boolean update(RepositorioCanciones RepositorioCancionesActualizado) throws Exception {
        int indiceRepositorioCanciones = -1;
        int cont = 0;

        for (RepositorioCanciones RepositorioCanciones : repoCanciones) {
            if(RepositorioCanciones.getId() == RepositorioCancionesActualizado.getId()) {
                indiceRepositorioCanciones = cont;
                break;
            }

            cont++;
        }

        if(indiceRepositorioCanciones != -1) {
            repoCanciones.set(indiceRepositorioCanciones, RepositorioCancionesActualizado);
            return true;
        }

        throw new Exception("El Repositorio de Canciones que se desea actualizar no existe");
    }
    public boolean delete(int idRepositorioCanciones) throws Exception {
        Optional<RepositorioCanciones> RepositorioCancionesEncontrado = findByID(idRepositorioCanciones);

        if(RepositorioCancionesEncontrado.isPresent()) {
            repoCanciones.remove(RepositorioCancionesEncontrado.get());
            return true;
        }

        throw new Exception("El Repositorio de Canciones que se desea eliminar no existe");
    }


    //General
    public List<RepositorioCanciones> findAll() {
        return Collections.unmodifiableList(repoCanciones);
    }
    public Optional<RepositorioCanciones> findByID(int id) {
        for (RepositorioCanciones RepositorioCanciones : repoCanciones) {
            if(RepositorioCanciones.getId() == id) {
                return Optional.of(RepositorioCanciones);
            }
        }

        return Optional.empty();
    }


    //Albunes
    public List<Album> findAlbunes() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM albunes");

        ArrayList<Album> listaAlbunes = new ArrayList<>();

        while (result.next()) {
            Album albumLeido = new Album();
            albumLeido.setId(result.getInt("idAlbum"));
            albumLeido.setNombre(result.getString("nombre"));
            albumLeido.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            albumLeido.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            albumLeido.setImagen(result.getString("imagen"));

            listaAlbunes.add(albumLeido);
        }

        return Collections.unmodifiableList(listaAlbunes);
    }
    public Optional<Album> findAlbumById(int idAlbum) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM albunes WHERE idAlbum = " + idAlbum);

        while (result.next()) {
            Album albumLeido = new Album();
            albumLeido.setId(result.getInt("idAlbum"));
            albumLeido.setNombre(result.getString("nombre"));
            albumLeido.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            albumLeido.setFechaLanzamiento(result.getDate("fechaLanzamiento").toLocalDate());
            albumLeido.setImagen(result.getString("imagen"));

            return Optional.of(albumLeido);
        }

        return Optional.empty();
    }

    //Listas de reproducción
    public List<ListaReproduccion> findListasReproduccion() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM listasreproduccion");

        ArrayList<ListaReproduccion> listaListasReproduccion = new ArrayList<>();

        while (result.next()) {
            ListaReproduccion listaReproduccionLeida = new ListaReproduccion();
            listaReproduccionLeida.setId(result.getInt("idListaReproduccion"));
            listaReproduccionLeida.setNombre(result.getString("nombre"));
            listaReproduccionLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());
            listaReproduccionLeida.setImagen(result.getString("imagen"));
            listaReproduccionLeida.setDescripcion(result.getString("descripcion"));

            listaListasReproduccion.add(listaReproduccionLeida);
        }

        return Collections.unmodifiableList(listaListasReproduccion);
    }
    public Optional<ListaReproduccion> findListaReproduccionById(int idLista) {
        return Optional.empty();
    }

    //Bibliotecas
    public List<Biblioteca> findBibliotecas() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM bibliotecas");

        ArrayList<Biblioteca> listaBibliotecas = new ArrayList<>();

        while (result.next()) {
            Biblioteca bibliotecaLeida = new Biblioteca();
            bibliotecaLeida.setId(result.getInt("idBiblioteca"));
            bibliotecaLeida.setNombre(result.getString("nombre"));
            bibliotecaLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());

            listaBibliotecas.add(bibliotecaLeida);
        }

        return Collections.unmodifiableList(listaBibliotecas);
    }
    public Optional<Biblioteca> findBibliotecaByID(int idBiblioteca) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM bibliotecas WHERE idBiblioteca = " + idBiblioteca));

        while (result.next()) {
            Biblioteca bibliotecaLeida = new Biblioteca();
            bibliotecaLeida.setId(result.getInt("idBiblioteca"));
            bibliotecaLeida.setNombre(result.getString("nombre"));
            bibliotecaLeida.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());

            return Optional.of(bibliotecaLeida);
        }

        return Optional.empty();
    }
}
