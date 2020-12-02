package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Usuario;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    private Connection connection;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Usuario nuevoUsuario) throws Exception {
        if(!findByID(nuevoUsuario.getId()).isPresent()) {
            usuarios.add(nuevoUsuario);
            return true;
        }

        throw new Exception("Ya existe un Usuario con el id especificado");
    }

    public boolean update(Usuario UsuarioActualizado) throws Exception {
        int indiceUsuario = -1;
        int cont = 0;

        for (Usuario Usuario : usuarios) {
            if(Usuario.getId() == UsuarioActualizado.getId()) {
                indiceUsuario = cont;
                break;
            }

            cont++;
        }

        if(indiceUsuario != -1) {
            usuarios.set(indiceUsuario, UsuarioActualizado);
            return true;
        }

        throw new Exception("El Usuario que se desea actualizar no existe");
    }

    public boolean delete(int idUsuario) throws Exception {
        Optional<Usuario> UsuarioEncontrado = findByID(idUsuario);

        if(UsuarioEncontrado.isPresent()) {
            usuarios.remove(UsuarioEncontrado.get());
            return true;
        }

        throw new Exception("El Usuario que se desea eliminar no existe");
    }

    public List<Usuario> findAll() {
        return Collections.unmodifiableList(usuarios);
    }

    public Optional<Usuario> findByID(int id) {
        for (Usuario Usuario : usuarios) {
            if(Usuario.getId() == id) {
                return Optional.of(Usuario);
            }
        }

        return Optional.empty();
    }

    public Optional<Usuario> findByEmail(String pCorreo) {
        for (Usuario Usuario : usuarios) {
            if(Usuario.getCorreo().equals(pCorreo)) {
                return Optional.of(Usuario);
            }
        }

        return Optional.empty();
    }
}
