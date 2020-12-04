package segura.taylor.dao;

import segura.taylor.bl.entidades.Pais;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PaisDAO {
    private ArrayList<Pais> paises = new ArrayList<>();

    private Connection connection;

    public PaisDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Pais nuevoPais) {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO paises (nombre, descripcion) VALUES ";
            insert += "('" + nuevoPais.getNombre() + "','";
            insert += nuevoPais.getDescripcion() + "')";

            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Pais PaisActualizado) throws Exception {
        int indicePais = -1;
        int cont = 0;

        for (Pais Pais : paises) {
            if(Pais.getId() == PaisActualizado.getId()) {
                indicePais = cont;
                break;
            }

            cont++;
        }

        if(indicePais != -1) {
            paises.set(indicePais, PaisActualizado);
            return true;
        }

        throw new Exception("El Pais que se desea actualizar no existe");
    }

    public boolean delete(int idPais) throws Exception {
        Optional<Pais> PaisEncontrado = findByID(idPais);

        if(PaisEncontrado.isPresent()) {
            paises.remove(PaisEncontrado.get());
            return true;
        }

        throw new Exception("El Pais que se desea eliminar no existe");
    }

    public List<Pais> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM paises");

        ArrayList<Pais> listaPaises = new ArrayList<>();

        while (result.next()) {
            Pais paisLeido = new Pais();
            paisLeido.setId(result.getInt("idPais"));
            paisLeido.setNombre(result.getString("nombre"));
            paisLeido.setDescripcion(result.getString("descripcion"));

            listaPaises.add(paisLeido);
        }

        return Collections.unmodifiableList(listaPaises);
    }

    public Optional<Pais> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery(("SELECT * FROM paises WHERE idPais = " + id));

        while (result.next()) {
            Pais paisLeido = new Pais();
            paisLeido.setId(result.getInt("idPais"));
            paisLeido.setNombre(result.getString("nombre"));
            paisLeido.setDescripcion(result.getString("descripcion"));

            return Optional.of(paisLeido);
        }

        return Optional.empty();
    }
}
