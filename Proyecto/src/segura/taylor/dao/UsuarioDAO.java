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
                insert = "INSERT INTO usuarios (tipoUsuario, correo, contrasenna, nombre, apellidos, fotoPerfil, nombreUsuario, fechaNacimiento, idPais, idBiblioteca, correoVerificado) VALUES ";
                insert += "('" + nuevoCliente.getTipoUsuario() + "',";
                insert += "'" + nuevoCliente.getCorreo() + "',";
                insert += "'" + nuevoCliente.getContrasenna() + "',";
                insert += "'" + nuevoCliente.getNombre() + "',";
                insert += "'" + nuevoCliente.getApellidos() + "',";
                insert += "'" + nuevoCliente.getImagenPerfil() + "',";
                insert += "'" + nuevoCliente.getNombreUsuario() + "',";
                insert += "'" + Date.valueOf(nuevoCliente.getFechaNacimiento()) + "',";
                insert += "" + nuevoCliente.getPais().getId() + ",";
                insert += "" + nuevoCliente.getBiblioteca().getId() + ",";
                insert += "" + nuevoCliente.isCorreoVerificado() + ")";
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
        try {
            Statement query = connection.createStatement();
            String update = "";

            if(usuarioActualizado.esAdmin()) {
                //Registro admin
                Admin nuevoAdmin = (Admin) usuarioActualizado;
                update = "UPDATE usuario_admin SET ";
                update += "correo = '" + nuevoAdmin.getCorreo() + "',";
                update += "nombre = '" + nuevoAdmin.getNombre() + "',";
                update += "apellidos = '" + nuevoAdmin.getApellidos() + "',";
                update += "foto = '" + nuevoAdmin.getImagenPerfil() + "',";
                update += "nombreUsuario = '" + nuevoAdmin.getNombreUsuario() + "' ";
                update += "WHERE id = " + usuarioActualizado.getId();
            } else {
                //Registro normal
                Cliente nuevoCliente = (Cliente) usuarioActualizado;
                update = "UPDATE usuarios SET ";
                update += "correo = '" + nuevoCliente.getCorreo() + "',";
                update += "nombre = '" + nuevoCliente.getNombre() + "',";
                update += "apellidos = '" + nuevoCliente.getApellidos() + "',";
                update += "fotoPerfil = '" + nuevoCliente.getImagenPerfil() + "',";
                update += "nombreUsuario = '" + nuevoCliente.getNombreUsuario() + "'";
                update += "correoVerificado = " + nuevoCliente.isCorreoVerificado() + " ";
                update += "WHERE idUsuario = " + usuarioActualizado.getId();
            }

            System.out.println("Ejecuto query: " + update);
            query.execute(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Este método se usa para eliminar un usuario de la base de datos
     * @param idUsuario int que define el id del usuario que se desea eliminar
     * @return true si la eliminación es exitosa, false si ocurre algún error
     */
    public boolean delete(int idUsuario) {
        try {
            Statement query = connection.createStatement();
            String delete = "DELETE FROM usuarios WHERE idUsuario = " + idUsuario;

            query.execute(delete);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
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
            usuarioLeido.setCorreoVerificado(result.getBoolean("correoVerificado"));

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
     * @throws SQLException si no se puede conectar con la BD
     */
    public Optional<Usuario> findByID(int id) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM usuario_admin WHERE id = " + id);

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

            return Optional.of(usuarioLeido);
        }

        //Luego todos los demás
        result = query.executeQuery("SELECT * FROM usuarios WHERE idUsuario = " + id);
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
            usuarioLeido.setCorreoVerificado(result.getBoolean("correoVerificado"));

            usuarioLeido.setPais(paisDAO.findByID(result.getInt("idPais")).get());
            usuarioLeido.setBiblioteca(repoCancionesDAO.findBibliotecaByID(result.getInt("idBiblioteca")).get());

            return Optional.of(usuarioLeido);
        }

        return Optional.empty();
    }

    /**
     * Este método se usa para buscar un usuario usando como filtro su id
     * @param pCorreo String que define el correo del usuario que se desea encontrar
     * @return un objeto de tipo Optional que contiene una instancia de Usuario si se encuentra una coincidencia
     * @see Optional
     * @see Usuario
     * @throws SQLException si no se puede conectar con la BD
     */
    public Optional<Usuario> findByEmail(String pCorreo) throws SQLException {
        Statement query = connection.createStatement();
        ResultSet result = query.executeQuery("SELECT * FROM usuario_admin WHERE correo = '" + pCorreo + "'");

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

            return Optional.of(usuarioLeido);
        }

        //Luego todos los demás
        result = query.executeQuery("SELECT * FROM usuarios WHERE correo = '" + pCorreo + "'");
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
            usuarioLeido.setCorreoVerificado(result.getBoolean("correoVerificado"));

            usuarioLeido.setPais(paisDAO.findByID(result.getInt("idPais")).get());
            usuarioLeido.setBiblioteca(repoCancionesDAO.findBibliotecaByID(result.getInt("idBiblioteca")).get());

            return Optional.of(usuarioLeido);
        }

        return Optional.empty();
    }

    /**
     * Método usado para actualizar el estado de cuando se verifica el correo de un usuario al ingresar por primera vez
     * @param usuarioActualizado el usuario con los datos actualizados
     * @return true si la modificacion es exitosa, false si ocurre algun error
     */
    public boolean updateEstadoCorreo(Cliente usuarioActualizado) {
        try {
            Statement query = connection.createStatement();
            String update;

            Cliente nuevoCliente = usuarioActualizado;
            update = "UPDATE usuarios SET ";
            update += "correoVerificado = " + nuevoCliente.isCorreoVerificado() + " ";
            update += "WHERE idUsuario = " + usuarioActualizado.getId();

            System.out.println("Ejecuto query: " + update);
            query.execute(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Método usado para actualizar la contraseña de un usuario
     * @param usuarioActualizado el usuario con los datos actualizados
     * @return true si la modificacion es exitosa, false si ocurre algun error
     */
    public boolean updateContrasenna(Usuario usuarioActualizado) {
        try {
            Statement query = connection.createStatement();
            String update = "";

            if(usuarioActualizado.esAdmin()) {
                //Registro admin
                Admin nuevoAdmin = (Admin) usuarioActualizado;
                update = "UPDATE usuario_admin SET ";
                update += "contrasenna = '" + nuevoAdmin.getContrasenna() + "' ";
                update += "WHERE id = " + usuarioActualizado.getId();
            } else {
                //Registro normal
                Cliente nuevoCliente = (Cliente) usuarioActualizado;
                update = "UPDATE usuarios SET ";
                update += "contrasenna = '" + nuevoCliente.getContrasenna() + "' ";
                update += "WHERE idUsuario = " + usuarioActualizado.getId();
            }

            System.out.println("Ejecuto query: " + update);
            query.execute(update);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
