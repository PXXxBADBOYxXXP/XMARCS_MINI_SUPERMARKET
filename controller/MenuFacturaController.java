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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.marcobolaños.bean.Clientes;
import org.marcobolaños.bean.Empleados;
import org.marcobolaños.bean.Factura;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author Jessyca Martinez
 */
public class MenuFacturaController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Factura> listaFactura;

    @FXML
    private Button btnRegresarF;
    @FXML
    private TextField txtNumeroDeFacturaF;
    @FXML
    private TextField txtEstadoF;
    @FXML
    private TextField txtTotalFacturaF;
    @FXML
    private TextField txtFechaFacturaF;
    @FXML
    private ComboBox cmbCodigoClienteF;
    @FXML
    private ComboBox cmbCodigoEmpleadoF;
    @FXML
    private TableView tblFactura;
    @FXML
    private TableColumn colnumeroDeFacturaF;
    @FXML
    private TableColumn colestadoF;
    @FXML
    private TableColumn coltotalFacturaF;
    @FXML
    private TableColumn colFechaFacturaF;
    @FXML
    private TableColumn colcodigoClienteF;
    @FXML
    private TableColumn colcodigoEmpleadoF;
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
        tblFactura.setItems(getFactura());
        colnumeroDeFacturaF.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("numeroDeFactura"));
        colestadoF.setCellValueFactory(new PropertyValueFactory<Factura, String>("estado"));
        coltotalFacturaF.setCellValueFactory(new PropertyValueFactory<Factura, Double>("totalFactura"));
        colFechaFacturaF.setCellValueFactory(new PropertyValueFactory<Factura, String>("fechaFactura"));
        colcodigoClienteF.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoCliente"));
        colcodigoEmpleadoF.setCellValueFactory(new PropertyValueFactory<Factura, Integer>("codigoEmpleado"));
    }

    public void seleccionarElementos() {
        txtNumeroDeFacturaF.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
        txtEstadoF.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getEstado()));
        txtTotalFacturaF.setText(String.valueOf(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getTotalFactura()));
        txtFechaFacturaF.setText((((Factura) tblFactura.getSelectionModel().getSelectedItem()).getFechaFactura()));
        cmbCodigoClienteF.getSelectionModel().select(buscarClientes(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoCliente()));
        cmbCodigoEmpleadoF.getSelectionModel().select(buscarEmpleados(((Factura) tblFactura.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
    }

    public Clientes buscarClientes(int clienteID) {
        Clientes resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarClientes(?)}");
            procedimiento.setInt(1, clienteID);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Clientes(registro.getInt("clienteID"),
                        registro.getString("NIT"),
                        registro.getString("nombresCliente"),
                        registro.getString("apellidosCliente"),
                        registro.getString("direccionCliente"),
                        registro.getString("telefonoCliente"),
                        registro.getString("correoCliente")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public Empleados buscarEmpleados(int codigoEmpleado) {
        Empleados resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{{call sp_buscarEmpleados(?)}}");
            procedimiento.setInt(1, codigoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Empleados(registro.getInt("codigoEmpleado"),
                        registro.getString("nombresEmpleado"),
                        registro.getString("apellidosEmpleado"),
                        registro.getDouble("sueldo"),
                        registro.getString("direccion"),
                        registro.getString("turno"),
                        registro.getInt("codigoCargoEmpleado"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public ObservableList<Clientes> getClientes() {
        ArrayList<Clientes> lista = new ArrayList<>();
        try {
            PreparedStatement procedimientoCL = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarClientes ()}");
            ResultSet resultado = procedimientoCL.executeQuery();
            while (resultado.next()) {
                lista.add(new Clientes(resultado.getInt("clienteID"),
                        resultado.getString("NIT"),
                        resultado.getString("nombresCliente"),
                        resultado.getString("apellidosCliente"),
                        resultado.getString("direccionCliente"),
                        resultado.getString("telefonoCliente"),
                        resultado.getString("correoCliente")
                ));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return FXCollections.observableList(lista);
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> lista = new ArrayList<>();
        try {
            PreparedStatement procedimientoEM = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultado = procedimientoEM.executeQuery();
            while (resultado.next()) {
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                        resultado.getString("nombresEmpleado"),
                        resultado.getString("apellidosEmpleado"),
                        resultado.getDouble("sueldo"),
                        resultado.getString("direccion"),
                        resultado.getString("turno"),
                        resultado.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return FXCollections.observableList(lista);
    }

    public ObservableList<Factura> getFactura() {
        ArrayList<Factura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarFacturas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new Factura(resultado.getInt("numeroDeFactura"),
                        resultado.getString("estado"),
                        resultado.getDouble("totalFactura"),
                        resultado.getString("fechaFactura"),
                        resultado.getInt("codigoCliente"),
                        resultado.getInt("codigoEmpleado")
                ));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(lista);
    }

    public void agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoDeOperaciones = operaciones.ACTUALIZAR;
                break;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Agregar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void guardar() {
        Factura registro = new Factura();
        registro.setNumeroDeFactura(Integer.parseInt(txtNumeroDeFacturaF.getText()));
        registro.setCodigoCliente((((Clientes) cmbCodigoClienteF.getSelectionModel().getSelectedItem()).getClienteID()));
        registro.setCodigoEmpleado((((Empleados) cmbCodigoEmpleadoF.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
        registro.setEstado(txtEstadoF.getText());
        registro.setTotalFactura(Double.parseDouble(txtTotalFacturaF.getText()));
        registro.setFechaFactura(txtFechaFacturaF.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_crearFactura(?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getNumeroDeFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(3, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
            listaFactura.add(registro);
        } catch (Exception M) {
            M.printStackTrace();
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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarProductos.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarProductos.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Factura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarFactura(?)}");
                            procedimiento.setInt(1, (((Factura) tblFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
                            procedimiento.execute();
                            listaFactura.remove(tblFactura.getSelectionModel().getSelectedItem());
                            limpiarControles();
                        } catch (Exception M) {
                            M.printStackTrace();
                        }
                    }
                }
        }
    }

    public void editar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                if (tblFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                    activarControles();
                    txtNumeroDeFacturaF.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Producto para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProductos.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProductos.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarFactura(?, ?, ?, ?, ?, ?)}");
            Factura registro = (Factura) tblFactura.getSelectionModel().getSelectedItem();
            registro.setEstado(txtEstadoF.getText());
            registro.setFechaFactura(txtFechaFacturaF.getText());
            procedimiento.setInt(1, registro.getNumeroDeFactura());
            procedimiento.setString(2, registro.getEstado());
            procedimiento.setDouble(1, registro.getTotalFactura());
            procedimiento.setString(4, registro.getFechaFactura());
            procedimiento.setInt(5, registro.getCodigoCliente());
            procedimiento.setInt(6, registro.getCodigoEmpleado());
            procedimiento.execute();
            cancelar();
        } catch (Exception M) {
            M.printStackTrace();
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
        txtNumeroDeFacturaF.setEditable(false);
        txtEstadoF.setEditable(false);
        txtTotalFacturaF.setEditable(false);
        txtFechaFacturaF.setEditable(false);
        cmbCodigoClienteF.setDisable(true);
        cmbCodigoEmpleadoF.setDisable(true);

    }

    public void activarControles() {
        txtNumeroDeFacturaF.setEditable(true);
        txtEstadoF.setEditable(true);
        txtTotalFacturaF.setEditable(true);
        txtFechaFacturaF.setEditable(true);
        cmbCodigoClienteF.setDisable(false);
        cmbCodigoEmpleadoF.setDisable(false);

    }

    public void limpiarControles() {
        txtNumeroDeFacturaF.clear();
        txtEstadoF.clear();
        txtTotalFacturaF.clear();
        txtFechaFacturaF.clear();
        tblFactura.getSelectionModel().getSelectedItem();
        cmbCodigoClienteF.getSelectionModel().getSelectedItem();
        cmbCodigoEmpleadoF.getSelectionModel().getSelectedItem();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarF) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
