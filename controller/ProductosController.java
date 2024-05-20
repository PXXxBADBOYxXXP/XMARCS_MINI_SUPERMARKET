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
import org.marcobolaños.bean.Productos;
import org.marcobolaños.bean.Proveedores;
import org.marcobolaños.bean.TipoProducto;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class ProductosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Productos> listaProductos;
    private ObservableList<Proveedores> listaProveedores;
    private ObservableList<TipoProducto> listaTipoProducto;

    @FXML
    private Button btnRegresarPROD;
    @FXML
    private TextField txtCodigoPROD;
    @FXML
    private TextField txtDescPROD;
    @FXML
    private TextField txtPrecioPROD;
    @FXML
    private TextField txtPrecioDPROD;
    @FXML
    private TextField txtPrecioMPROD;
    @FXML
    private TextField txtExistenciaPROD;
    @FXML
    private ComboBox cmbCodigoTipoPRO;
    @FXML
    private ComboBox cmbCodProv;
    @FXML
    private TableView tblProductos;
    @FXML
    private TableColumn colCodProd;
    @FXML
    private TableColumn colDescProd;
    @FXML
    private TableColumn colPrecioU;
    @FXML
    private TableColumn colPrecioD;
    @FXML
    private TableColumn colPrecioM;
    @FXML
    private TableColumn colExistencia;
    @FXML
    private TableColumn colCodTipoProd;
    @FXML
    private TableColumn colCodProv;
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

    public void cargarDatos() {
        tblProductos.setItems(getProductos());
        colCodProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("codigoProducto"));
        colDescProd.setCellValueFactory(new PropertyValueFactory<Productos, String>("descripcionProducto"));
        colPrecioU.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioUnitario"));
        colPrecioD.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioDocena"));
        colPrecioM.setCellValueFactory(new PropertyValueFactory<Productos, Double>("precioMayor"));
        colExistencia.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("existencia"));
        colCodTipoProd.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoTipoProducto"));
        colCodProv.setCellValueFactory(new PropertyValueFactory<Productos, Integer>("codigoProveedor"));
    }

    public void selecionarElementos() {
        txtCodigoPROD.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto());
        txtDescPROD.setText(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getDescripcionProducto());
        txtPrecioPROD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioUnitario()));
        txtPrecioDPROD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioDocena()));
        txtPrecioMPROD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getPrecioMayor()));
        txtExistenciaPROD.setText(String.valueOf(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getExistencia()));
        cmbCodigoTipoPRO.getSelectionModel().select(buscarTipoProducto(((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));

    }

    public TipoProducto buscarTipoProducto(int codigoTipoProducto) {
        TipoProducto resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarTipoProducto(?)}");
            procedimiento.setInt(1, codigoTipoProducto);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new TipoProducto(registro.getInt("codigoTipoProducto"),
                        registro.getString("descripcion")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<Proveedores> getProveedores() {
        ArrayList<Proveedores> listaPro = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_mostrarproveedor ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaPro.add(new Proveedores(resultado.getInt("codigoProveedor"),
                        resultado.getString("nitProveedor"),
                        resultado.getString("nombreProveedor"),
                        resultado.getString("apellidosProveedor"),
                        resultado.getString("direccionProveedor"),
                        resultado.getString("razonSocial"),
                        resultado.getString("contactoPrincipal"),
                        resultado.getString("paginaWeb")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableList(listaPro);
    }

    public ObservableList<TipoProducto> getTipoP() {
        ArrayList<TipoProducto> lista = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarTipoProducto ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                lista.add(new TipoProducto(resultado.getInt("codigoTipoProducto"),
                        resultado.getString("descripcion")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaTipoProducto = FXCollections.observableList(lista);
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
        Productos registro = new Productos();
        registro.setCodigoProducto(txtCodigoPROD.getText());
        registro.setCodigoProveedor(((Proveedores) cmbCodProv.getSelectionModel().getSelectedItem()).getCodigoProveedor());
        registro.setCodigoTipoProducto(((TipoProducto) cmbCodigoTipoPRO.getSelectionModel().getSelectedItem())
                .getCodigoTipoProducto());
        registro.setDescripcionProducto(txtDescPROD.getText());
        registro.setPrecioDocena(Double.parseDouble(txtPrecioDPROD.getText()));
        registro.setPrecioUnitario(Double.parseDouble(txtPrecioPROD.getText()));
        registro.setPrecioMayor(Double.parseDouble(txtPrecioMPROD.getText()));
        registro.setExistencia(Integer.parseInt(txtExistenciaPROD.getText()));
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_agregarProducto(?, ?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setString(1, registro.getCodigoProducto());
            procedimiento.setString(2, registro.getDescripcionProducto());
            procedimiento.setDouble(3, registro.getPrecioUnitario());
            procedimiento.setDouble(4, registro.getPrecioDocena());
            procedimiento.setDouble(5, registro.getPrecioMayor());
            procedimiento.setInt(6, registro.getExistencia());
            procedimiento.setInt(7, registro.getCodigoProveedor());
            procedimiento.setInt(8, registro.getCodigoTipoProducto());
            procedimiento.execute();

            listaProductos.add(registro);

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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarProductos.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarProductos.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    int respuestaprod = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar Productos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestaprod == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientopro = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarProveedor(?)}");
                            procedimientopro.setString(1, (((Productos) tblProductos.getSelectionModel().getSelectedItem()).getCodigoProducto()));
                            procedimientopro.execute();
                            listaProductos.remove(tblProductos.getSelectionModel().getSelectedItem());
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
                if (tblProductos.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarProveedores.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarProveedores.png"));
                    activarControles();
                    txtCodigoPROD.setDisable(false);
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
            PreparedStatement procedimientoprod = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarProveedor(?, ?, ?, ?, ?, ?, ?, ?)}");
            Productos registroprod = (Productos) tblProductos.getSelectionModel().getSelectedItem();

            registroprod.setDescripcionProducto(txtDescPROD.getText());
            registroprod.setDescripcionProducto(txtDescPROD.getText());
            procedimientoprod.setString(1, registroprod.getCodigoProducto());
            procedimientoprod.setString(2, registroprod.getDescripcionProducto());
            procedimientoprod.setDouble(3, registroprod.getPrecioUnitario());
            procedimientoprod.setDouble(4, registroprod.getPrecioDocena());
            procedimientoprod.setDouble(5, registroprod.getPrecioMayor());
            procedimientoprod.setInt(6, registroprod.getExistencia());
            procedimientoprod.setInt(7, registroprod.getCodigoTipoProducto());
            procedimientoprod.setInt(8, registroprod.getCodigoProveedor());
            procedimientoprod.execute();
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
        txtCodigoPROD.setEditable(false);
        txtDescPROD.setEditable(false);
        txtPrecioPROD.setEditable(false);
        txtPrecioDPROD.setEditable(false);
        txtPrecioMPROD.setEditable(false);
        txtExistenciaPROD.setEditable(false);
        cmbCodigoTipoPRO.setDisable(true);
        cmbCodigoTipoPRO.setDisable(true);

    }

    public void activarControles() {
        txtCodigoPROD.setEditable(true);
        txtDescPROD.setEditable(true);
        txtPrecioPROD.setEditable(true);
        txtPrecioDPROD.setEditable(true);
        txtPrecioMPROD.setEditable(true);
        txtExistenciaPROD.setEditable(true);
        cmbCodigoTipoPRO.setDisable(false);
        cmbCodigoTipoPRO.setDisable(false);

    }

    public void limpiarControles() {
        txtCodigoPROD.clear();
        txtDescPROD.clear();
        txtPrecioPROD.clear();
        txtPrecioDPROD.clear();
        txtPrecioMPROD.clear();
        txtExistenciaPROD.clear();
        tblProductos.getSelectionModel().getSelectedItem();
        cmbCodigoTipoPRO.getSelectionModel().getSelectedItem();
        cmbCodigoTipoPRO.getSelectionModel().getSelectedItem();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarPROD) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
        cmbCodigoTipoPRO.setItems(getTipoP());
        cmbCodProv.setItems(getProveedores());
    }

}
