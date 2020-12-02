package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Cancion;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CancionDAO {
    private ArrayList<Cancion> canciones = new ArrayList<>();

    private Connection connection;

    public CancionDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Cancion nuevoCancion) throws Exception {
        if(!findByID(nuevoCancion.getId()).isPresent()) {
            canciones.add(nuevoCancion);
            return true;
        }

        throw new Exception("Ya existe una cancion con el id especificado");
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

    public List<Cancion> findAll() {
        return Collections.unmodifiableList(canciones);
    }

    public Optional<Cancion> findByID(int id) {
        for (Cancion Cancion : canciones) {
            if(Cancion.getId() == id) {
                return Optional.of(Cancion);
            }
        }

        return Optional.empty();
    }
}
