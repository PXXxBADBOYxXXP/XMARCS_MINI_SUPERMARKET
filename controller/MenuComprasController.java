/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcobolaños.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.marcobolaños.bean.Compras;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author MARCO
 */
public class MenuComprasController implements Initializable {

    private Main escenarioPrincipal;
    private ObservableList<Compras> listaCompras;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML
    private DatePicker myDatePicker;

    @FXML
    private Button btnRegresarCO;
    @FXML
    private TextField txtNumeroCO;
    @FXML
    private TextField txtDescripcionCO;
    @FXML
    private TextField txtTotalCO;
    @FXML
    private TableView tblCompras;
    @FXML
    private TableColumn colNumeroCO;
    @FXML
    private TableColumn colFechaCO;
    @FXML
    private TableColumn colDescripcionCO;
    @FXML
    private TableColumn colTotalCO;
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
        tblCompras.setItems(getCompras());
        colNumeroCO.setCellValueFactory(new PropertyValueFactory<Compras, Integer>("numeroDocumento"));
        colFechaCO.setCellValueFactory(new PropertyValueFactory<Compras, String>("fechaDocumento"));
        colDescripcionCO.setCellValueFactory(new PropertyValueFactory<Compras, String>("descripcion"));
        colTotalCO.setCellValueFactory(new PropertyValueFactory<Compras, Double>("totalDocumento"));
    }

    public void seleccionarElemento() {
        txtNumeroCO.setText(String.valueOf((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento())));
        txtDescripcionCO.setText(String.valueOf((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getDescripcion())));
        txtTotalCO.setText(String.valueOf((((Compras) tblCompras.getSelectionModel().getSelectedItem()).getTotalDocumento())));
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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarCompras.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarCompras.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void guardar() {
        Compras registroCO = new Compras();
        registroCO.setNumeroDocumento(Integer.parseInt(txtNumeroCO.getText()));
        registroCO.setFechaDocumento(txtDescripcionCO.getText());
        registroCO.setTotalDocumento(Integer.parseInt(txtTotalCO.getText()));
        try {
            PreparedStatement procedimientoCO = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarCompras(?, ?, ?, ?)}");
            procedimientoCO.setInt(1, registroCO.getNumeroDocumento());
            procedimientoCO.setString(2, registroCO.getFechaDocumento());
            procedimientoCO.setString(3, registroCO.getDescripcion());
            procedimientoCO.setDouble(4, registroCO.getTotalDocumento());
            procedimientoCO.execute();
            listaCompras.add(registroCO);
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
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarComprass.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarComprass.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    int respuestaCO = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del REGISTROCO",
                            "Eliminar Compras", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestaCO == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientoCO = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarCompras(?)}");
                            procedimientoCO.setInt(1, (((Compras) tblCompras.getSelectionModel().getSelectedItem()).getNumeroDocumento()));
                            procedimientoCO.execute();
                            listaCompras.remove(tblCompras.getSelectionModel().getSelectedItem());
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
                if (tblCompras.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarComprass.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarComprass.png"));
                    activarControles();
                    txtNumeroCO.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una compra para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarCompras.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarCompras.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimientoCO = Conexion.getInstance().getConexion().prepareCall("{call sp_editarCompras(?, ?, ?, ?)}");
            Compras registro = (Compras) tblCompras.getSelectionModel().getSelectedItem();

            registro.setDescripcion(txtDescripcionCO.getText());
            procedimientoCO.setInt(1, registro.getNumeroDocumento());
            procedimientoCO.setString(2, registro.getFechaDocumento());
            procedimientoCO.setString(3, registro.getDescripcion());
            procedimientoCO.setDouble(4, registro.getTotalDocumento());
            procedimientoCO.execute();
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
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarCompras.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarComprass.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtNumeroCO.setEditable(false);
        txtDescripcionCO.setEditable(false);
        txtTotalCO.setEditable(false);
    }

    public void activarControles() {
        txtNumeroCO.setEditable(true);
        txtDescripcionCO.setEditable(true);
        txtTotalCO.setEditable(true);
    }

    public void limpiarControles() {
        txtNumeroCO.clear();
        txtDescripcionCO.clear();
        txtTotalCO.clear();
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    public void getDate(ActionEvent event) {
        LocalDate myFecha = myDatePicker.getValue();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarCO) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
