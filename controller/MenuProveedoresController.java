/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcobolaños.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.marcobolaños.bean.Proveedores;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author MARCO
 */
public class MenuProveedoresController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Proveedores> listaProveedores;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;

    @FXML
    private Button btnRegresarPRO;
    @FXML
    private TextField txtCodigoPRO;
    @FXML
    private TextField txtNitPRO;
    @FXML
    private TextField txtNombrePRO;
    @FXML
    private TextField txtApellidosPRO;
    @FXML
    private TextField txtDireccionPRO;
    @FXML
    private TextField txtRazonPRO;
    @FXML
    private TextField txtContactoPRO;
    @FXML
    private TextField txtPaginaPRO;
    @FXML
    private TableView tblProveedores;
    @FXML
    private TableColumn colCodigoPRO;
    @FXML
    private TableColumn colNitPRO;
    @FXML
    private TableColumn colNombrePRO;
    @FXML
    private TableColumn colApellidosPRO;
    @FXML
    private TableColumn colDireccionPRO;
    @FXML
    private TableColumn colRazonPRO;
    @FXML
    private TableColumn colContactoPRO;
    @FXML
    private TableColumn colPaginaPRO;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgAgregar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private ImageView imgEditar;
    @FXML
    private ImageView imgReporte;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblProveedores.setItems(getProveedores());
        colCodigoPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, Integer>("codigoProveedor"));
        colNitPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nitProveedor"));
        colNombrePRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("nombreProveedor"));
        colApellidosPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("apellidosProveedor"));
        colDireccionPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("direccionProveedor"));
        colRazonPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("razonSocial"));
        colContactoPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("contactoPrincipal"));
        colPaginaPRO.setCellValueFactory(new PropertyValueFactory<Proveedores, String>("paginaWeb"));
    }

    public void seleccionarElemento() {
        txtCodigoPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor())));
        txtNitPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNitProveedor())));
        txtNombrePRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getNombreProveedor())));
        txtApellidosPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getApellidosProveedor())));
        txtDireccionPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getDireccionProveedor())));
        txtRazonPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getRazonSocial())));
        txtContactoPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getContactoPrincipal())));
        txtPaginaPRO.setText(String.valueOf((((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getPaginaWeb())));
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaProveedores = new ArrayList<>();
        try {
            PreparedStatement procedimientoPRO = Conexion.getInstance().getConexion().prepareCall("{CALL sp_mostrarproveedor ()}");
            ResultSet resultadoPRO = procedimientoPRO.executeQuery();
            while (resultadoPRO.next()) {
                listaProveedores.add(new Proveedores(resultadoPRO.getInt("codigoProveedor"),
                        resultadoPRO.getString("nitProveedor"),
                        resultadoPRO.getString("nombreProveedor"),
                        resultadoPRO.getString("apellidosProveedor"),
                        resultadoPRO.getString("direccionProveedor"),
                        resultadoPRO.getString("razonSocial"),
                        resultadoPRO.getString("contactoPrincipal"),
                        resultadoPRO.getString("paginaWeb")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(listaProveedores);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/GUARDARPRO.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/ELIMINARPRO.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Actualizar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Proveedores registroPRO = new Proveedores();
        registroPRO.setCodigoProveedor(Integer.parseInt(txtCodigoPRO.getText()));
        registroPRO.setNitProveedor(txtNitPRO.getText());
        registroPRO.setNombreProveedor(txtNombrePRO.getText());
        registroPRO.setApellidosProveedor(txtApellidosPRO.getText());
        registroPRO.setDireccionProveedor(txtDireccionPRO.getText());
        registroPRO.setRazonSocial(txtRazonPRO.getText());
        registroPRO.setContactoPrincipal(txtContactoPRO.getText());
        registroPRO.setPaginaWeb(txtPaginaPRO.getText());
        try {
            PreparedStatement procedimientoPRO = Conexion.getInstance().getConexion().prepareCall("{call sp_crearProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimientoPRO.setInt(1, registroPRO.getCodigoProveedor());
            procedimientoPRO.setString(2, registroPRO.getNitProveedor());
            procedimientoPRO.setString(3, registroPRO.getNombreProveedor());
            procedimientoPRO.setString(4, registroPRO.getApellidosProveedor());
            procedimientoPRO.setString(5, registroPRO.getDireccionProveedor());
            procedimientoPRO.setString(6, registroPRO.getRazonSocial());
            procedimientoPRO.setString(7, registroPRO.getContactoPrincipal());
            procedimientoPRO.setString(8, registroPRO.getPaginaWeb());
            procedimientoPRO.execute();
            listaProveedores.add(registroPRO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    int respuestapro = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Proveedores", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestapro == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientopro = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedor(?)}");
                            procedimientopro.setInt(1, (((Proveedores) tblProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor()));
                            procedimientopro.execute();
                            listaProveedores.remove(tblProveedores.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblProveedores.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                    activarControles();
                    txtCodigoPRO.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Proveedor para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimientopro = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Proveedores registropro = (Proveedores) tblProveedores.getSelectionModel().getSelectedItem();

            registropro.setNitProveedor(txtNitPRO.getText());
            registropro.setNombreProveedor(txtNombrePRO.getText());
            registropro.setApellidosProveedor(txtApellidosPRO.getText());
            registropro.setDireccionProveedor(txtDireccionPRO.getText());
            registropro.setRazonSocial(txtRazonPRO.getText());
            registropro.setContactoPrincipal(txtContactoPRO.getText());
            registropro.setPaginaWeb(txtPaginaPRO.getText());
            procedimientopro.setInt(1, registropro.getCodigoProveedor());
            procedimientopro.setString(2, registropro.getNitProveedor());
            procedimientopro.setString(3, registropro.getNombreProveedor());
            procedimientopro.setString(4, registropro.getApellidosProveedor());
            procedimientopro.setString(5, registropro.getDireccionProveedor());
            procedimientopro.setString(6, registropro.getRazonSocial());
            procedimientopro.setString(7, registropro.getContactoPrincipal());
            procedimientopro.setString(8, registropro.getPaginaWeb());
            procedimientopro.execute();
            cancelar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoPRO.setEditable(false);
        txtNitPRO.setEditable(false);
        txtNombrePRO.setEditable(false);
        txtApellidosPRO.setEditable(false);
        txtDireccionPRO.setEditable(false);
        txtRazonPRO.setEditable(false);
        txtContactoPRO.setEditable(false);
        txtPaginaPRO.setDisable(false);
    }

    public void activarControles() {
        txtCodigoPRO.setEditable(true);
        txtNitPRO.setEditable(true);
        txtNombrePRO.setEditable(true);
        txtApellidosPRO.setEditable(true);
        txtDireccionPRO.setEditable(true);
        txtRazonPRO.setEditable(true);
        txtContactoPRO.setEditable(true);
        txtPaginaPRO.setDisable(true);
    }

    public void limpiarControles() {
        txtCodigoPRO.clear();
        txtNitPRO.clear();
        txtNombrePRO.clear();
        txtApellidosPRO.clear();
        txtDireccionPRO.clear();
        txtRazonPRO.clear();
        txtContactoPRO.clear();
        txtPaginaPRO.clear();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarPRO) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
