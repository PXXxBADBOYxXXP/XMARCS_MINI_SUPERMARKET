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
import org.marcobolaños.bean.DetalleFactura;
import org.marcobolaños.bean.Factura;
import org.marcobolaños.bean.Productos;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author Jessyca Martinez
 */
public class MenuDetalleFacturaController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleFactura> listaDetalleFactura;
    private ObservableList<Factura> listaFactura;
    private ObservableList<Productos> listaProductos;

    @FXML
    private Button btnRegresarDF;
    @FXML
    private TextField txtCodigoDetalleFacturaDF;
    @FXML
    private TextField txtPrecioUnitarioDF;
    @FXML
    private TextField txtCantidadDF;
    @FXML
    private ComboBox cmbNumeroDeFacturaDF;
    @FXML
    private ComboBox cmbCodigoProductoDF;
    @FXML
    private TableView tblDetalleFactura;
    @FXML
    private TableColumn colCodigoDetalleFacturaDF;
    @FXML
    private TableColumn colPrecioUnitarioDF;
    @FXML
    private TableColumn colCantidadDF;
    @FXML
    private TableColumn colNumeroDeFacturaDF;
    @FXML
    private TableColumn colCodigoProductoDF;
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
        tblDetalleFactura.setItems(getDetalleFactura());
        colCodigoDetalleFacturaDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("codigoDetalleFactura"));
        colPrecioUnitarioDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Double>("precioUnitario"));
        colCantidadDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("cantidad"));
        colNumeroDeFacturaDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, Integer>("numeroDeFactura"));
        colCodigoProductoDF.setCellValueFactory(new PropertyValueFactory<DetalleFactura, String>("codigoProducto"));
    }

    public void seleccionarElementos() {
        txtCodigoDetalleFacturaDF.setText(String.valueOf((((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura())));
        txtPrecioUnitarioDF.setText(String.valueOf((((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getPrecioUnitario())));
        txtCantidadDF.setText(String.valueOf((((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCantidad())));
        cmbNumeroDeFacturaDF.getSelectionModel().select(buscarFactura(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
        cmbCodigoProductoDF.getSelectionModel().select(buscarProductos(((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoProducto()));
    }

    public Factura buscarFactura(int numeroDeFactura) {
        Factura resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarFactura(?)}");
            procedimiento.setInt(1, numeroDeFactura);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Factura(registro.getInt("numeroDeFactura"),
                        registro.getString("estado"),
                        registro.getDouble("totalFactura"),
                        registro.getString("fechaFactura"),
                        registro.getInt("codigoCliente"),
                        registro.getInt("codigoEmpleado"));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return resultado;
    }

    public Productos buscarProductos(String codigoProducto) {
        Productos resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarProductos(?)}");
            procedimiento.setString(1, codigoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new Productos(registro.getString("codigoProducto"),
                        registro.getString("descripcionProducto"),
                        registro.getDouble("precioUnitario"),
                        registro.getDouble("precioDocena"),
                        registro.getDouble("precioMayor"),
                        registro.getInt("existencia"),
                        registro.getInt("codigoTipoProducto"),
                        registro.getInt("codigoProveedor"));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return resultado;
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
                        resultado.getInt("codigoEmpleado")));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return listaFactura = FXCollections.observableList(lista);
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<>();
        try {
            PreparedStatement procedimientoPROD = Conexion.getInstance().getConexion().prepareCall("{call sp_mostrarProductos()}");
            ResultSet resultadoPROD = procedimientoPROD.executeQuery();
            while (resultadoPROD.next()) {
                lista.add(new Productos(resultadoPROD.getString("Productos"),
                        resultadoPROD.getString("descripcionProducto"),
                        resultadoPROD.getDouble("precioUnitario"),
                        resultadoPROD.getDouble("precioDocena"),
                        resultadoPROD.getDouble("precioMayor"),
                        resultadoPROD.getInt("existencia"),
                        resultadoPROD.getInt("codigoTipoProducto"),
                        resultadoPROD.getInt("codigoProveedor")
                ));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return listaProductos = FXCollections.observableList(lista);
    }

    public ObservableList<DetalleFactura> getDetalleFactura() {
        ArrayList<DetalleFactura> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_listarDetallesFactura()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new DetalleFactura(resultado.getInt("codigoDetalleFactura"),
                        resultado.getDouble("precioUnitario"),
                        resultado.getInt("cantidad"),
                        resultado.getInt("numeroDeFactura"),
                        resultado.getString("codigoProducto")));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return listaDetalleFactura = FXCollections.observableList(lista);
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
        DetalleFactura registro = new DetalleFactura();
        registro.setCodigoDetalleFactura(Integer.parseInt(txtCodigoDetalleFacturaDF.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioUnitarioDF.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidadDF.getText()));
        registro.setNumeroDeFactura((((Factura) cmbNumeroDeFacturaDF.getSelectionModel().getSelectedItem()).getNumeroDeFactura()));
        registro.setCodigoProducto((((Productos) cmbCodigoProductoDF.getSelectionModel().getSelectedItem()).getCodigoProducto()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_crearDetalleFactura(?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroDeFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
            procedimiento.execute();
            listaDetalleFactura.add(registro);
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
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    int respuesta = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar DetalleFactura", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleFactura(?)}");
                            procedimiento.setInt(1, (((DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem()).getCodigoDetalleFactura()));
                            procedimiento.execute();
                            listaDetalleFactura.remove(tblDetalleFactura.getSelectionModel().getSelectedItem());
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
                if (tblDetalleFactura.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                    activarControles();
                    txtCodigoDetalleFacturaDF.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un DetalleFactura para editar");
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
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleFactura(?, ?, ?, ?, ?)}");
            DetalleFactura registro = (DetalleFactura) tblDetalleFactura.getSelectionModel().getSelectedItem();
            procedimiento.setInt(1, registro.getCodigoDetalleFactura());
            procedimiento.setDouble(2, registro.getPrecioUnitario());
            procedimiento.setInt(3, registro.getCantidad());
            procedimiento.setInt(4, registro.getNumeroDeFactura());
            procedimiento.setString(5, registro.getCodigoProducto());
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
        txtCodigoDetalleFacturaDF.setEditable(false);
        txtPrecioUnitarioDF.setEditable(false);
        txtCantidadDF.setEditable(false);
        cmbNumeroDeFacturaDF.setDisable(true);
        cmbCodigoProductoDF.setDisable(true);

    }

    public void activarControles() {
        txtCodigoDetalleFacturaDF.setEditable(true);
        txtPrecioUnitarioDF.setEditable(true);
        txtCantidadDF.setEditable(true);
        cmbNumeroDeFacturaDF.setDisable(false);
        cmbCodigoProductoDF.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoDetalleFacturaDF.clear();
        txtPrecioUnitarioDF.clear();
        txtCantidadDF.clear();
        tblDetalleFactura.getSelectionModel().getSelectedItem();
        cmbNumeroDeFacturaDF.getSelectionModel().getSelectedItem();
        cmbCodigoProductoDF.getSelectionModel().getSelectedItem();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarDF) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
