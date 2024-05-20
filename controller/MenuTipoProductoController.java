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
import org.marcobolaños.bean.TipoProducto;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author MARCO
 */
public class MenuTipoProductoController implements Initializable {

    private Main escenarioPrincipal;
    @FXML
    private Button btnRegresarT;
    private ObservableList<TipoProducto> listaTipoProducto;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    private TableView tblTipoProducto;
    @FXML
    private TextField txtCodicoT;
    @FXML
    private TextField txtDescripcionT;
    @FXML
    private TableColumn colCodigoT;
    @FXML
    private TableColumn colDescripcionT;
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
        tblTipoProducto.setItems(getTipoProducto());
        colCodigoT.setCellValueFactory(new PropertyValueFactory<TipoProducto, Integer>("codigoTipoProducto"));
        colDescripcionT.setCellValueFactory(new PropertyValueFactory<TipoProducto, String>("descripcion"));
    }

    public void seleccionarElemento() {
        txtCodicoT.setText(String.valueOf((((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto())));
        txtDescripcionT.setText(String.valueOf((((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getDescripcion())));
    }

    public ObservableList<TipoProducto> getTipoProducto() {
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
        return FXCollections.observableList(lista);
    }

    public void Agregar() {
        switch (tipoDeOperaciones) {
            case NINGUNO:
                activarControles();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/GUARDAR.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/ELIMINAR.png"));
                tipoDeOperaciones = operaciones.ACTUALIZAR;
            case ACTUALIZAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Actualizar");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarTipoProducto.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarTipoProducto.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        TipoProducto registro = new TipoProducto();
        registro.setCodigoTipoProducto(Integer.parseInt(txtCodicoT.getText()));
        registro.setDescripcion(txtDescripcionT.getText());
        try {
            PreparedStatement procedimientoT = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarTipoProducto(?, ?)}");
            procedimientoT.setInt(1, registro.getCodigoTipoProducto());
            procedimientoT.setString(2, registro.getDescripcion());
            procedimientoT.execute();
            listaTipoProducto.add(registro);
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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarTipoProductos.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarTipoProductos.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    int respuestaT = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro",
                            "Eliminar Tipo Producto", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestaT == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientoT = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarTipoProducto(?)}");
                            procedimientoT.setInt(1, (((TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem()).getCodigoTipoProducto()));
                            procedimientoT.execute();
                            listaTipoProducto.remove(tblTipoProducto.getSelectionModel().getSelectedItem());
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
                if (tblTipoProducto.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarTipoProductos.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarTipoProductos.png"));
                    activarControles();
                    txtCodicoT.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe de seleccionar un Tipo de producto para editar");
                }
                break;
            case ACTUALIZAR:
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarTipoProductos.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarTipoProductos.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimientoT = Conexion.getInstance().getConexion().prepareCall("{call sp_editarTipoProducto(?, ?)}");
            TipoProducto registro = (TipoProducto) tblTipoProducto.getSelectionModel().getSelectedItem();
            registro.setDescripcion(txtDescripcionT.getText());
            procedimientoT.setInt(1, registro.getCodigoTipoProducto());
            procedimientoT.setString(2, registro.getDescripcion());
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
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarClientes.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarClientes.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodicoT.setEditable(false);
        txtDescripcionT.setEditable(false);
    }

    public void activarControles() {
        txtCodicoT.setEditable(true);
        txtDescripcionT.setEditable(true);

    }

    public void limpiarControles() {
        txtCodicoT.clear();
        txtDescripcionT.clear();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarT) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
