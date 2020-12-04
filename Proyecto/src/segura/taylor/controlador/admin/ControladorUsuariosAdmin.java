package segura.taylor.controlador.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import segura.taylor.bl.entidades.Cliente;
import segura.taylor.bl.entidades.Genero;
import segura.taylor.bl.entidades.Pais;
import segura.taylor.bl.entidades.Usuario;
import segura.taylor.controlador.ControladorGeneral;
import segura.taylor.ui.dialogos.AlertDialog;
import segura.taylor.ui.dialogos.YesNoDialog;

import java.util.List;

public class ControladorUsuariosAdmin {
    public TableView tblUsuarios;
    public VBox ventanaPrincipal;

    public void initialize() {
        inicializarTabla();
        mostrarDatos();
    }

    public void inicializarTabla() {
        //ID
        TableColumn<Cliente, String> columnaId = new TableColumn("ID");
        columnaId.setMinWidth(100);
        columnaId.setCellValueFactory(new PropertyValueFactory<>("id"));

        //TipoUsuario
        TableColumn<Cliente, String> columnaTipoUsuario = new TableColumn("Tipo Usuario");
        columnaTipoUsuario.setMinWidth(100);
        columnaTipoUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoUsuario"));

        //Correo
        TableColumn<Cliente, String> columnaCorreo = new TableColumn("Correo");
        columnaCorreo.setMinWidth(100);
        columnaCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));

        //Contraseña
        TableColumn<Cliente, String> columnaContrasenna = new TableColumn("Contraseña");
        columnaContrasenna.setMinWidth(100);
        columnaContrasenna.setCellValueFactory(new PropertyValueFactory<>("contrasenna"));
        
        //Nombre
        TableColumn<Cliente, String> columnaNombre = new TableColumn("Nombre");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        //Apellidos
        TableColumn<Cliente, String> columnaApellidos = new TableColumn("Apellidos");
        columnaApellidos.setMinWidth(100);
        columnaApellidos.setCellValueFactory(new PropertyValueFactory<>("apellidos"));

        //Nombre usuario
        TableColumn<Cliente, String> columnaNombreUsuario = new TableColumn("Nombre de usuario");
        columnaNombre.setMinWidth(100);
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));

        //FechaNacimiento
        TableColumn<Cliente, String> columnaFechaNacimiento = new TableColumn("Fecha Nacimiento");
        columnaFechaNacimiento.setMinWidth(100);

        columnaFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));

        //Edad
        TableColumn<Cliente, String> columnaEdad = new TableColumn("Edad");
        columnaEdad.setMinWidth(100);
        columnaEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        //Pais
        TableColumn<Cliente, String> columnaPais = new TableColumn("Pais");
        columnaPais.setMinWidth(100);
        columnaPais.setCellValueFactory(new PropertyValueFactory<>("nombrePais"));
        
        tblUsuarios.getColumns().addAll(columnaId, columnaTipoUsuario, columnaCorreo, columnaContrasenna, columnaNombre, columnaApellidos, columnaNombreUsuario, columnaFechaNacimiento, columnaEdad, columnaPais);
    }
    private void mostrarDatos() {
        tblUsuarios.getItems().clear();
        tblUsuarios.setItems(obtenerClientes());
    }

    public ObservableList<Cliente> obtenerClientes() {
        List<Usuario> usuarios = ControladorGeneral.instancia.getGestor().listarUsuarios();

        ObservableList<Cliente> clientesFinal = FXCollections.observableArrayList();

        for(Usuario usuario : usuarios) {
            if(!usuario.esAdmin()) {    //Se excluye al admin
                clientesFinal.add((Cliente) usuario);
            }
        }

        return clientesFinal;
    }

    public void modificarUsuario() {
        try {
            //Referencias para el controlador
            Cliente ClienteSeleccionado = (Cliente) tblUsuarios.getSelectionModel().getSelectedItem();

            if (ClienteSeleccionado == null) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No hay ningún Usuario seleccionado");
                return;
            }

            Stage ventanaRegistroCliente = new Stage();
            //This locks previous window interacivity until this one is closed.
            ventanaRegistroCliente.initModality(Modality.APPLICATION_MODAL);

            VBox root = FXMLLoader.load(getClass().getResource("../../ui/ventanas/VentanaRegistroCliente.fxml"));

            //Referencia a los campos

            //Actualizar campos

            //Desactivar campos inmodificables

            Scene escena = new Scene(root, 580, 440);

            ventanaRegistroCliente.setScene(escena);
            ventanaRegistroCliente.setTitle("Modificación de Cliente");
            ventanaRegistroCliente.setResizable(false);
            ventanaRegistroCliente.showAndWait();

            mostrarDatos(); //Actualizar tabla
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminarUsuario() {
        YesNoDialog yesNoDialog = new YesNoDialog();
        boolean resultado = yesNoDialog.mostrar("Aviso", "Realmente quiere eliminar al Cliente seleccionado?");

        if (resultado) {
            Cliente ClienteSeleccionado = (Cliente) tblUsuarios.getSelectionModel().getSelectedItem();

            if (ClienteSeleccionado == null) {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.mostrar("Error", "No hay ningún Cliente seleccionado");
                return;
            }

            int idCliente = ClienteSeleccionado.getId();
            try {
                resultado = ControladorGeneral.instancia.getGestor().eliminarUsuario(idCliente);
                if (resultado) {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Exito", "Cliente eliminado correctamente");
                    mostrarDatos();
                } else {
                    AlertDialog alertDialog = new AlertDialog();
                    alertDialog.mostrar("Error", "No se pudo eliminar el Cliente");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
