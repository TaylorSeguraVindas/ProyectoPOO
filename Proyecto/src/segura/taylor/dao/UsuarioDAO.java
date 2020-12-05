package segura.taylor.dao;

import segura.taylor.bl.entidades.Admin;
import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.entidades.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * La clase DAO se encarga de realizar la conexión, lectura y escritura en la base de datos
 * @author Taylor Segura Vindas
 * @version 1.0
 */
public class UsuarioDAO {
    private ArrayList<Usuario> usuarios = new ArrayList<>();

    private Connection connection;
    private PaisDAO paisDAO;
    private RepositorioCancionesDAO repoCancionesDAO;

    /**
     * Método constructor
     * @param connection instancia de la clase Connection que define la conexión con la DB
     */
    public UsuarioDAO(Connection connection) {
        this.connection = connection;
        this.paisDAO = new PaisDAO(connection);
        this.repoCancionesDAO = new RepositorioCancionesDAO(connection);
    }

    /**
     * Este método se usa para escribir los datos de un nuevo usuario en la base de datos
     * @param nuevoUsuario instancia de la clase Usuario que se desea guardar
     * @return true si el registro es exitoso, false si ocurre algún error
     */
    public boolean save(Usuario nuevoUsuario) {
        try {
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
                insert += "'" + Date.valueOf(nuevoAdmin.getFechaCreacion()) + "')";
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

    /**
     * Este método se usa para sobreescribir los datos de un usuario en la base de datos
     * @param usuarioActualizado instancia de la clase Usuario con los cambios aplicados que se desean guardar
     * @return true si la escritura es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean update(Usuario usuarioActualizado) throws Exception {
        int indiceUsuario = -1;
        int cont = 0;

        for (Usuario Usuario : usuarios) {
            if(Usuario.getId() == usuarioActualizado.getId()) {
                indiceUsuario = cont;
                break;
            }

            cont++;
        }

        if(indiceUsuario != -1) {
            usuarios.set(indiceUsuario, usuarioActualizado);
            return true;
        }

        throw new Exception("El Usuario que se desea actualizar no existe");
    }

    /**
     * Este método se usa para eliminar un usuario de la base de datos
     * @param idUsuario int que define el id del usuario que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     * @throws Exception si no se puede conectar con la DB
     */
    public boolean delete(int idUsuario) throws Exception {
        Optional<Usuario> UsuarioEncontrado = findByID(idUsuario);

        if(UsuarioEncontrado.isPresent()) {
            usuarios.remove(UsuarioEncontrado.get());
            return true;
        }

        throw new Exception("El Usuario que se desea eliminar no existe");
    }

    /**
     * Este método se usa para obtener una lista con todos los usuarios guardados en la base de datos
     * @return una lista con todos los usuarios guardados
     * @throws SQLException si no se puede conectar con la DB
     */
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

    /**
     * Este método se usa para buscar un usuario usando como filtro su id
     * @param id int que define el id del usuario que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Usuario si se encuentra una coincidencia
     * @see Optional
     * @see Usuario
     */
    public Optional<Usuario> findByID(int id) {
        for (Usuario Usuario : usuarios) {
            if(Usuario.getId() == id) {
                return Optional.of(Usuario);
            }
        }

        return Optional.empty();
    }

    /**
     * Este método se usa para buscar un usuario usando como filtro su id
     * @param pCorreo String que define el correo del usuario que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Usuario si se encuentra una coincidencia
     * @see Optional
     * @see Usuario
     */
    public Optional<Usuario> findByEmail(String pCorreo) {
        for (Usuario Usuario : usuarios) {
            if(Usuario.getCorreo().equals(pCorreo)) {
                return Optional.of(Usuario);
            }
        }

        return Optional.empty();
    }
}
