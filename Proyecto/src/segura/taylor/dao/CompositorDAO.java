package segura.taylor.dao;

import segura.taylor.bl.entidades.Compositor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompositorDAO {
    private ArrayList<Compositor> compositores = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private GeneroDAO generoDAO;

    public CompositorDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.generoDAO = new GeneroDAO(connection);
    }

    public boolean save(Compositor nuevoCompositor) throws Exception {
        try {
            Statement query = connection.createStatement();
            String insert = "INSERT INTO compositores (nombre, apellidos, fechaNacimiento, idPais, idGenero) VALUES ";
            insert += "('" + nuevoCompositor.getNombre() + "','";
            insert += nuevoCompositor.getApellidos() + "','";
            insert += Date.valueOf(nuevoCompositor.getFechaNacimiento()) + "',";
            insert += nuevoCompositor.getPaisNacimiento().getId() + ",";
            insert += nuevoCompositor.getGenero().getId() + ")";
            query.execute(insert);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(Compositor CompositorActualizado) throws Exception {
        int indiceCompositor = -1;
        int cont = 0;

        for (Compositor Compositor : compositores) {
            if(Compositor.getId() == CompositorActualizado.getId()) {
                indiceCompositor = cont;
                break;
            }

            cont++;
        }

        if(indiceCompositor != -1) {
            compositores.set(indiceCompositor, CompositorActualizado);
            return true;
        }

        throw new Exception("El Compositor que se desea actualizar no existe");
    }

    public boolean delete(int idCompositor) throws Exception {
        Optional<Compositor> CompositorEncontrado = findByID(idCompositor);

        if(CompositorEncontrado.isPresent()) {
            compositores.remove(CompositorEncontrado.get());
            return true;
        }

        throw new Exception("El Compositor que se desea eliminar no existe");
    }

    public List<Compositor> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM compositores");

        ArrayList<Compositor> listaCompositores = new ArrayList<>();

        while (result.next()) {
            Compositor compositorLeido = new Compositor();
            compositorLeido.setId(result.getInt("idCompositor"));
            compositorLeido.setNombre(result.getString("nombre"));
            compositorLeido.setApellidos(result.getString("apellidos"));
            compositorLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());
            compositorLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            compositorLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            listaCompositores.add(compositorLeido);
        }

        return Collections.unmodifiableList(listaCompositores);
    }

    public Optional<Compositor> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM compositores WHERE idCompositor = " + id);

        while (result.next()) {
            Compositor compositorLeido = new Compositor();
            compositorLeido.setId(result.getInt("idCompositor"));
            compositorLeido.setNombre(result.getString("nombre"));
            compositorLeido.setApellidos(result.getString("apellidos"));
            compositorLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());
            compositorLeido.setPaisNacimiento(paisDAO.findByID(result.getInt("idPais")).get());
            compositorLeido.setGenero(generoDAO.findByID(result.getInt("idGenero")).get());

            return Optional.of(compositorLeido);
        }

        return Optional.empty();
    }
}
