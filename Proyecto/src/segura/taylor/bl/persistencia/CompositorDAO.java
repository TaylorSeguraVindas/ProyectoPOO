package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Compositor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CompositorDAO {
    private ArrayList<Compositor> compositores = new ArrayList<>();

    private Connection connection;

    public CompositorDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Compositor nuevoCompositor) throws Exception {
        if(!findByID(nuevoCompositor.getId()).isPresent()) {
            compositores.add(nuevoCompositor);
            return true;
        }

        throw new Exception("Ya existe un Compositor con el id especificado");
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

    public List<Compositor> findAll() {
        return Collections.unmodifiableList(compositores);
    }

    public Optional<Compositor> findByID(int id) {
        for (Compositor Compositor : compositores) {
            if(Compositor.getId() == id) {
                return Optional.of(Compositor);
            }
        }

        return Optional.empty();
    }
}
