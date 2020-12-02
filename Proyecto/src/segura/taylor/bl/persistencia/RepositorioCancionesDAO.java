package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.*;
import segura.taylor.bl.enums.TipoRepositorioCanciones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    public boolean save(RepositorioCanciones nuevoRepositorioCanciones) throws Exception {
        if(!findByID(nuevoRepositorioCanciones.getId()).isPresent()) {
            return repoCanciones.add(nuevoRepositorioCanciones);
        }

        throw new Exception("Ya existe un Repositorio de Canciones con el id especificado");
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
    public List<Album> findAlbunes() {
        ArrayList<Album> albunes = new ArrayList<>();

        for (RepositorioCanciones repo : repoCanciones) {
            if(repo.getTipoRepo() == TipoRepositorioCanciones.ALBUM) {
                albunes.add((Album) repo);
            }
        }
        return Collections.unmodifiableList(albunes);
    }


    //Listas de reproducción
    public List<ListaReproduccion> findListasReproduccion() {
        ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();

        for (RepositorioCanciones repo : repoCanciones) {
            if(repo.getTipoRepo() == TipoRepositorioCanciones.LISTA_REPRODUCCION) {
                listasReproduccion.add((ListaReproduccion) repo);
            }
        }
        return Collections.unmodifiableList(listasReproduccion);
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
