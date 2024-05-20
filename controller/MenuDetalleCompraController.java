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
import org.marcobolaños.bean.Compras;
import org.marcobolaños.bean.DetalleCompra;
import org.marcobolaños.bean.Productos;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author Jessyca Martinez
 */
public class MenuDetalleCompraController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<DetalleCompra> listaDetalleCompra;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Compras> listaCompras;

    @FXML
    private Button btnRegresarDC;
    @FXML
    private TextField txtCodigoDC;
    @FXML
    private TextField txtCostoUDC;
    @FXML
    private TextField txtCantidadDC;
    @FXML
    private ComboBox cmbCodigoPROD;
    @FXML
    private ComboBox cmbNumeroDocumentoCO;
    @FXML
    private TableView tblDetalleCompra;
    @FXML
    private TableColumn colCodigoDC;
    @FXML
    private TableColumn colCostoUDC;
    @FXML
    private TableColumn colCantidadDC;
    @FXML
    private TableColumn colCodigoProductoDC;
    @FXML
    private TableColumn colNumeroDocumentoDC;
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
        cmbCodigoPROD.setItems(getProductos());
        cmbNumeroDocumentoCO.setItems(getCompras());
    }

    public void cargarDatos() {
        tblDetalleCompra.setItems(getDetalleCompra());
        colCodigoDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("codigoDetalleCompra"));
        colCostoUDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Double>("costoUnitario"));
        colCantidadDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("cantidad"));
        colCodigoProductoDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, String>("codigoProducto"));
        colNumeroDocumentoDC.setCellValueFactory(new PropertyValueFactory<DetalleCompra, Integer>("numeroDocumento"));
    }

    public void seleccionarElementos() {
        txtCodigoDC.setText(((Productos) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtCostoUDC.setText(((Productos) tblDetalleCompra.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtCantidadDC.setText(String.valueOf(((Productos) tblDetalleCompra.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        cmbCodigoPROD.getSelectionModel().select(buscarProductos(((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoProducto()));
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
                        registro.getInt("codigoProveedor")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Compras> getCompras() {
        ArrayList<Compras> listacompras = new ArrayList<>();
        try {
            PreparedStatement procedimientoCO = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCompras ()}");
            ResultSet resultadoCO = procedimientoCO.executeQuery();
            while (resultadoCO.next()) {
                listacompras.add(new Compras(resultadoCO.getInt("numeroDocumento"),
                        resultadoCO.getString("fechaDocumento"),
                        resultadoCO.getString("descripcion"),
                        resultadoCO.getDouble("totalDocumento")));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(listacompras);
    }

    public ObservableList<Productos> getProductos() {
        ArrayList<Productos> lista = new ArrayList<Productos>();
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

        return listaProductos = FXCollections.observableArrayList(lista);
    }

    public ObservableList<DetalleCompra> getDetalleCompra() {
        ArrayList<DetalleCompra> listaDC = new ArrayList<DetalleCompra>();
        try {
            PreparedStatement procedimientoDC = Conexion.getInstance().getConexion().prepareCall("{<Compras> listaCompras}");
            ResultSet resultadoDC = procedimientoDC.executeQuery();
            while (resultadoDC.next()) {
                listaDC.add(new DetalleCompra(resultadoDC.getInt("codigoDetalleCompra"),
                        resultadoDC.getDouble("costoUnitario"),
                        resultadoDC.getInt("cantidad"),
                        resultadoDC.getString("codigoProducto"),
                        resultadoDC.getInt("numeroDocumento")
                ));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }

        return listaDetalleCompra = FXCollections.observableList(listaDC);
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
        DetalleCompra registro = new DetalleCompra();
        registro.setCodigoDetalleCompra(Integer.parseInt(txtCodigoDC.getText()));
        registro.setCodigoProducto(((Productos) cmbCodigoPROD.getSelectionModel().getSelectedItem()).getCodigoProducto());
        registro.setNumeroDocumento(((Compras) cmbNumeroDocumentoCO.getSelectionModel().getSelectedItem())
                .getNumeroDocumento());
        registro.setCostoUnitario(Double.parseDouble(txtCostoUDC.getText()));
        registro.setCantidad(Integer.parseInt(txtCantidadDC.getText()));
        try {
            PreparedStatement procedimientoDC = Conexion.getInstance().getConexion().prepareCall("{CALL sp_crearDetalleCompra(?, ?, ?, ?, ?)}");
            procedimientoDC.setString(1, registro.getCodigoProducto());
            procedimientoDC.setDouble(2, registro.getCostoUnitario());
            procedimientoDC.setInt(3, registro.getCantidad());
            procedimientoDC.setString(4, registro.getCodigoProducto());
            procedimientoDC.setInt(5, registro.getNumeroDocumento());
            procedimientoDC.execute();

            listaDetalleCompra.add(registro);

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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarDetalleCompra.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarDetalleCompra.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    int respuestaprod = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar DETALLES COMPRA", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestaprod == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientoDC = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarDetalleCompra(?)}");
                            procedimientoDC.setInt(1, (((DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem()).getCodigoDetalleCompra()));
                            procedimientoDC.execute();
                            listaDetalleCompra.remove(tblDetalleCompra.getSelectionModel().getSelectedItem());
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
                if (tblDetalleCompra.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarDetalleCompra.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarDetalleCompra.png"));
                    activarControles();
                    txtCodigoDC.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un DetalleCompra para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarDetalleCompra.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarDetalleCompra.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimientoDC = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarDetalleCompra(?, ?, ?, ?, ?)}");
            DetalleCompra registroDC = (DetalleCompra) tblDetalleCompra.getSelectionModel().getSelectedItem();
            procedimientoDC.setInt(1, registroDC.getCodigoDetalleCompra());
            procedimientoDC.setDouble(2, registroDC.getCostoUnitario());
            procedimientoDC.setInt(3, registroDC.getCantidad());
            procedimientoDC.setString(4, registroDC.getCodigoProducto());
            procedimientoDC.setInt(5, registroDC.getNumeroDocumento());
            procedimientoDC.execute();
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
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarDetalleCompra.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarDetalleCompra.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoDC.setEditable(false);
        txtCostoUDC.setEditable(false);
        txtCantidadDC.setEditable(false);
        cmbCodigoPROD.setEditable(false);
        cmbNumeroDocumentoCO.setEditable(false);
    }

    public void activarControles() {
        txtCodigoDC.setEditable(true);
        txtCostoUDC.setEditable(true);
        txtCantidadDC.setEditable(true);
        cmbCodigoPROD.setEditable(true);
        cmbNumeroDocumentoCO.setEditable(true);
    }

    public void limpiarControles() {
        txtCodigoDC.clear();
        txtCostoUDC.clear();
        txtCantidadDC.clear();
        tblDetalleCompra.getSelectionModel().getSelectedItem();
        cmbCodigoPROD.getSelectionModel().getSelectedItem();
        cmbNumeroDocumentoCO.getSelectionModel().getSelectedItem();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarDC) {
            escenarioPrincipal.menuPrincipalView();
        }
    }
}
