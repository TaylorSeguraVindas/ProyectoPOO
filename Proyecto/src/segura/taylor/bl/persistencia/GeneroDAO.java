package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Genero;
import segura.taylor.bl.entidades.Pais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GeneroDAO {
    private ArrayList<Genero> generos = new ArrayList<>();

    private Connection connection;

    public GeneroDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Genero nuevoGenero) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO generos (nombre, descripcion) VALUES ";
            insert += "('" + nuevoGenero.getNombre() + "','";
            insert += nuevoGenero.getDescripcion() + "')";

            query.execute(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Genero GeneroActualizado) throws Exception {
        int indiceGenero = -1;
        int cont = 0;

        for (Genero Genero : generos) {
            if(Genero.getId() == GeneroActualizado.getId()) {
                indiceGenero = cont;
                break;
            }

            cont++;
        }

        if(indiceGenero != -1) {
            generos.set(indiceGenero, GeneroActualizado);
            return true;
        }

        throw new Exception("El Genero que se desea actualizar no existe");
    }

    public boolean delete(int idGenero) throws Exception {
        Optional<Genero> GeneroEncontrado = findByID(idGenero);

        if(GeneroEncontrado.isPresent()) {
            generos.remove(GeneroEncontrado.get());
            return true;
        }

        throw new Exception("El Genero que se desea eliminar no existe");
    }

    public List<Genero> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM generos");

        ArrayList<Genero> listaGeneros = new ArrayList<>();

        while (result.next()) {
            Genero generoLeido = new Genero();
            generoLeido.setId(result.getInt("idGenero"));
            generoLeido.setNombre(result.getString("nombre"));
            generoLeido.setDescripcion(result.getString("descripcion"));

            listaGeneros.add(generoLeido);
        }

        return Collections.unmodifiableList(listaGeneros);
    }

    public Optional<Genero> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM generos WHERE idGenero = " + id));

        while (result.next()) {
            Genero generoLeido = new Genero();
            generoLeido.setId(result.getInt("idGenero"));
            generoLeido.setNombre(result.getString("nombre"));
            generoLeido.setDescripcion(result.getString("descripcion"));

            return Optional.of(generoLeido);
        }

        return Optional.empty();
    }
}
