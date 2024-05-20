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
import org.marcobolaños.bean.CargoEmpleado;
import org.marcobolaños.bean.Empleados;
import org.marcobolaños.db.Conexion;
import org.marcobolaños.system.Main;

/**
 * FXML Controller class
 *
 * @author Jessyca Martinez
 */
public class MenuEmpleadosController implements Initializable {

    private Main escenarioPrincipal;

    private enum operaciones {
        AGREGAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO
    }
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<CargoEmpleado> listaCargoEmpleado;

    @FXML
    private Button btnRegresarEM;
    /**
     * Initializes the controller class.
     */

    @FXML
    private TextField txtCodigoEmpleadoEM;
    @FXML
    private TextField txtNombresEmpleadoEM;
    @FXML
    private TextField txtApellidosEmpleadoEM;
    @FXML
    private TextField txtSueldoEM;
    @FXML
    private TextField txtDireccionEM;
    @FXML
    private TextField txtTurnoEM;
    @FXML
    private ComboBox cmbCodigoCargoEmpleadoEM;
    @FXML
    private TableView tblEmpleados;
    @FXML
    private TableColumn colCodigoEmpleadoEM;
    @FXML
    private TableColumn colNombresEmpleadoEM;
    @FXML
    private TableColumn colApellidosEmpleadoEM;
    @FXML
    private TableColumn colSueldoEM;
    @FXML
    private TableColumn colDireccionEM;
    @FXML
    private TableColumn colTurnoEM;
    @FXML
    private TableColumn colCodigoCargoEmpleadoEM;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblEmpleados.setItems(getEmpleados());
        colCodigoEmpleadoEM.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoEmpleado"));
        colNombresEmpleadoEM.setCellValueFactory(new PropertyValueFactory<Empleados, String>("nombresEmpleado"));
        colApellidosEmpleadoEM.setCellValueFactory(new PropertyValueFactory<Empleados, String>("apellidosEmpleado"));
        colSueldoEM.setCellValueFactory(new PropertyValueFactory<Empleados, Double>("sueldo"));
        colDireccionEM.setCellValueFactory(new PropertyValueFactory<Empleados, String>("direccion"));
        colTurnoEM.setCellValueFactory(new PropertyValueFactory<Empleados, String>("turno"));
        colCodigoCargoEmpleadoEM.setCellValueFactory(new PropertyValueFactory<Empleados, Integer>("codigoCargoEmpleado"));
    }

    public void seleccionarElementos() {
        txtCodigoEmpleadoEM.setText(String.valueOf((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado())));
        txtNombresEmpleadoEM.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getNombresEmpleado()));
        txtApellidosEmpleadoEM.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getApellidosEmpleado()));
        txtSueldoEM.setText(String.valueOf((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo())));
        txtDireccionEM.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getDireccion()));
        txtTurnoEM.setText((((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getTurno()));
        cmbCodigoCargoEmpleadoEM.getSelectionModel().select(buscarCargoEmpleado(((Empleados) tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
    }

    public CargoEmpleado buscarCargoEmpleado(int codigoCargoEmpleado) {
        CargoEmpleado resultado = null;
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_buscarCargoEmpleado(?)}");
            procedimiento.setInt(1, codigoCargoEmpleado);
            ResultSet registro = procedimiento.executeQuery();
            while (registro.next()) {
                resultado = new CargoEmpleado(registro.getInt("codigoCargoEmpleado"),
                        registro.getString("nombreCargo"),
                        registro.getString("descripcionCargo"));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }

        return resultado;
    }

    public ObservableList<CargoEmpleado> getCargoEmpleado() {
        ArrayList<CargoEmpleado> listaCE = new ArrayList<>();
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarCargoEmpleado ()}");
            ResultSet resultado = procedimiento.executeQuery();
            while (resultado.next()) {
                listaCE.add(new CargoEmpleado(resultado.getInt("codigoCargoEmpleado"),
                        resultado.getString("nombreCargo"),
                        resultado.getString("descripcionCargo")));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }

        return listaCargoEmpleado = FXCollections.observableList(listaCE);
    }

    public ObservableList<Empleados> getEmpleados() {
        ArrayList<Empleados> listaEM = new ArrayList<Empleados>();
        try {
            PreparedStatement procedimientoEM = Conexion.getInstance().getConexion().prepareCall("{call sp_listarEmpleados()}");
            ResultSet resultadoEM = procedimientoEM.executeQuery();
            while (resultadoEM.next()) {
                listaEM.add(new Empleados(resultadoEM.getInt("codigoEmpleado"),
                        resultadoEM.getString("nombresEmpleado"),
                        resultadoEM.getString("apellidosEmpleado"),
                        resultadoEM.getDouble("sueldo"),
                        resultadoEM.getString("direccion"),
                        resultadoEM.getString("turno"),
                        resultadoEM.getInt("codigoCargoEmpleado")));
            }
        } catch (Exception M) {
            M.printStackTrace();
        }
        return listaEmpleados = FXCollections.observableArrayList(listaEM);
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
        }
    }

    public void guardar() {
        Empleados registro = new Empleados();
        registro.setCodigoEmpleado(Integer.parseInt(txtCodigoEmpleadoEM.getText()));
        registro.setCodigoCargoEmpleado((((CargoEmpleado) cmbCodigoCargoEmpleadoEM.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        registro.setCodigoCargoEmpleado((((CargoEmpleado) cmbCodigoCargoEmpleadoEM.getSelectionModel().getSelectedItem()).getCodigoCargoEmpleado()));
        registro.setNombresEmpleado(txtNombresEmpleadoEM.getText());
        registro.setApellidosEmpleado(txtApellidosEmpleadoEM.getText());
        registro.setSueldo(Double.parseDouble(txtSueldoEM.getText()));
        registro.setDireccion(txtDireccionEM.getText());
        registro.setTurno(txtTurnoEM.getText());
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_crearEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            procedimiento.setInt(1, registro.getCodigoCargoEmpleado());
            procedimiento.setString(2, registro.getNombresEmpleado());
            procedimiento.setString(3, registro.getApellidosEmpleado());
            procedimiento.setDouble(4, registro.getSueldo());
            procedimiento.setString(5, registro.getDireccion());
            procedimiento.setString(6, registro.getTurno());
            procedimiento.setInt(7, registro.getCodigoCargoEmpleado());
            procedimiento.execute();
            listaEmpleados.add(registro);
        } catch (Exception M) {
            M.printStackTrace();
        }
    }

    public void eliminar() {
        switch (tipoDeOperaciones) {
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnAgregar.setText("Eliminar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgAgregar.setImage(new Image("/org/marcobolaños/images/agregarEmpleados.png"));
                imgEliminar.setImage(new Image("/org/marcobolaños/images/eliminarEmpleados.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
            default:
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    int respuestaEM = JOptionPane.showConfirmDialog(null, "Confirmar la eliminacion del registro", "Eliminar EMPLEADOS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuestaEM == JOptionPane.YES_NO_OPTION) {
                        try {
                            PreparedStatement procedimientoEM = Conexion.getInstance().getConexion().prepareCall("{call sp_eliminarEmpleado(?)}");
                            procedimientoEM.execute();
                            listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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
                if (tblEmpleados.getSelectionModel().getSelectedItem() != null) {
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    imgEditar.setImage(new Image("/org/marcobolaños/images/agregarEmpleados.png"));
                    imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarEmpleados.png"));
                    activarControles();
                    txtCodigoEmpleadoEM.setDisable(false);
                    tipoDeOperaciones = operaciones.ACTUALIZAR;
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un Empleado para editar");
                }
                break;
            case ACTUALIZAR:
                actualizar();
                btnEditar.setText("Actualizar");
                btnReporte.setText("Cancelar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarEmpleados.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarEmpleados.png"));
                desactivarControles();
                limpiarControles();
                tipoDeOperaciones = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }

    public void actualizar() {
        try {
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_actualizarEmpleado(?, ?, ?, ?, ?, ?, ?)}");
            Empleados registroEM = (Empleados) tblEmpleados.getSelectionModel().getSelectedItem();
            registroEM.setNombresEmpleado(txtNombresEmpleadoEM.getText());
            registroEM.setApellidosEmpleado(txtApellidosEmpleadoEM.getText());
            registroEM.setDireccion(txtDireccionEM.getText());
            registroEM.setTurno(txtTurnoEM.getText());
            procedimiento.setInt(1, registroEM.getCodigoEmpleado());
            procedimiento.setString(2, registroEM.getNombresEmpleado());
            procedimiento.setString(3, registroEM.getApellidosEmpleado());
            procedimiento.setDouble(4, registroEM.getSueldo());
            procedimiento.setString(5, registroEM.getDireccion());
            procedimiento.setString(6, registroEM.getTurno());
            procedimiento.setInt(7, registroEM.getCodigoCargoEmpleado());
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
                imgEditar.setImage(new Image("/org/marcobolaños/images/agregarEmpleados.png"));
                imgReporte.setImage(new Image("/org/marcobolaños/images/eliminarEmpleados.png"));
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }

    public void desactivarControles() {
        txtCodigoEmpleadoEM.setEditable(false);
        txtNombresEmpleadoEM.setEditable(false);
        txtApellidosEmpleadoEM.setEditable(false);
        txtSueldoEM.setEditable(false);
        txtDireccionEM.setEditable(false);
        txtTurnoEM.setEditable(false);
        cmbCodigoCargoEmpleadoEM.setDisable(true);
    }

    public void activarControles() {
        txtCodigoEmpleadoEM.setEditable(true);
        txtNombresEmpleadoEM.setEditable(true);
        txtApellidosEmpleadoEM.setEditable(true);
        txtSueldoEM.setEditable(true);
        txtDireccionEM.setEditable(true);
        txtTurnoEM.setEditable(true);
        cmbCodigoCargoEmpleadoEM.setDisable(false);
    }

    public void limpiarControles() {
        txtCodigoEmpleadoEM.clear();
        txtNombresEmpleadoEM.clear();
        txtApellidosEmpleadoEM.clear();
        txtSueldoEM.clear();
        txtDireccionEM.clear();
        txtTurnoEM.clear();
        tblEmpleados.getSelectionModel().getSelectedItem();
        cmbCodigoCargoEmpleadoEM.getSelectionModel().getSelectedItem();

    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnRegresarEM) {
            escenarioPrincipal.menuPrincipalView();
        }
    }

}
