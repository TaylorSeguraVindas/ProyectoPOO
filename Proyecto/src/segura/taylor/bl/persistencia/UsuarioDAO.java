package segura.taylor.bl.persistencia;

import segura.taylor.bl.entidades.Admin;
import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.bl.entidades.Usuario;
import segura.taylor.bl.enums.TipoUsuario;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UsuarioDAO {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private RepositorioCancionesDAO repoCancionesDAO;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.repoCancionesDAO = new RepositorioCancionesDAO(connection);
    }

    public boolean save(Usuario nuevoUsuario) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Statement query = connection.createStatement();
            String insert;

            if(nuevoUsuario.esAdmin()) {
                //Registro admin
                Admin nuevoAdmin = (Admin) nuevoUsuario;
                insert = "INSERT INTO usuario_admin (tipoUsuario, correo, contrasenna, nombre, apellidos, foto, nombreUsuario, fechaCreacion) VALUES ";
                insert += "('" + nuevoAdmin.getTipoUsuario() + "',";
                insert += "'" + nuevoAdmin.getCorreo() + "',";
                insert += "'" + nuevoAdmin.getContrasenna() + "',";
                insert += "'" + nuevoAdmin.getNombre() + "',";
                insert += "'" + nuevoAdmin.getApellidos() + "',";
                insert += "'" + nuevoAdmin.getImagenPerfil() + "',";
                insert += "'" + nuevoAdmin.getNombreUsuario() + "',";
                insert += "'" + dateFormat.format(nuevoAdmin.getFechaCreacion().toString()) + "')";
            } else {
                //Registro normal
                Cliente nuevoCliente = (Cliente) nuevoUsuario;
                insert = "INSERT INTO usuarios (tipoUsuario, correo, contrasenna, nombre, apellidos, fotoPerfil, nombreUsuario, fechaNacimiento, idPais, idBiblioteca) VALUES ";
                insert += "('" + nuevoCliente.getTipoUsuario() + "',";
                insert += "'" + nuevoCliente.getCorreo() + "',";
                insert += "'" + nuevoCliente.getContrasenna() + "',";
                insert += "'" + nuevoCliente.getNombre() + "',";
                insert += "'" + nuevoCliente.getApellidos() + "',";
                insert += "'" + nuevoCliente.getImagenPerfil() + "',";
                insert += "'" + nuevoCliente.getNombreUsuario() + "',";
                insert += "'" + Date.valueOf(nuevoCliente.getFechaNacimiento()) + "',";
                insert += "" + nuevoCliente.getPais().getId() + ",";
                insert += "" + nuevoCliente.getBiblioteca().getId() + ")";
            }

            System.out.println("Ejecuto query: " + insert);
            query.execute(insert);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
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

    public List<Usuario> findAll() throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM usuario_admin");

        ArrayList<Usuario> listaUsuarios = new ArrayList<>();

        //Primero obtiene admin
        while (result.next()) {
            Admin usuarioLeido = new Admin();
            usuarioLeido.setId(result.getInt("id"));
            usuarioLeido.setCorreo(result.getString("correo"));
            usuarioLeido.setContrasenna(result.getString("contrasenna"));
            usuarioLeido.setNombre(result.getString("nombre"));
            usuarioLeido.setApellidos(result.getString("apellidos"));
            usuarioLeido.setImagenPerfil(result.getString("foto"));
            usuarioLeido.setNombreUsuario(result.getString("nombreUsuario"));
            usuarioLeido.setFechaCreacion(result.getDate("fechaCreacion").toLocalDate());

            listaUsuarios.add(usuarioLeido);
        }

        //Luego todos los demás
        result = query.executeQuery("SELECT * FROM usuarios");
        while (result.next()) {
            Cliente usuarioLeido = new Cliente();
            usuarioLeido.setId(result.getInt("idUsuario"));
            usuarioLeido.setCorreo(result.getString("correo"));
            usuarioLeido.setContrasenna(result.getString("contrasenna"));
            usuarioLeido.setNombre(result.getString("nombre"));
            usuarioLeido.setApellidos(result.getString("apellidos"));
            usuarioLeido.setImagenPerfil(result.getString("fotoPerfil"));
            usuarioLeido.setNombreUsuario(result.getString("nombreUsuario"));
            usuarioLeido.setFechaNacimiento(result.getDate("fechaNacimiento").toLocalDate());

            usuarioLeido.setPais(paisDAO.findByID(result.getInt("idPais")).get());
            usuarioLeido.setBiblioteca(repoCancionesDAO.findBibliotecaByID(result.getInt("idBiblioteca")).get());

            listaUsuarios.add(usuarioLeido);
        }

        return Collections.unmodifiableList(listaUsuarios);
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
