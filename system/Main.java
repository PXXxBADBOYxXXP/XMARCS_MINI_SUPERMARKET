/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.marcobolaños.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.marcobolaños.controller.MenuCargoEmpleadoController;
import org.marcobolaños.controller.MenuClienteController;
import org.marcobolaños.controller.MenuComprasController;
import org.marcobolaños.controller.MenuDetalleCompraController;
import org.marcobolaños.controller.MenuDetalleFacturaController;
import org.marcobolaños.controller.MenuEmpleadosController;
import org.marcobolaños.controller.MenuFacturaController;
import org.marcobolaños.controller.MenuPrincipalController;
import org.marcobolaños.controller.MenuProgramadorController;
import org.marcobolaños.controller.MenuProveedoresController;
import org.marcobolaños.controller.MenuTipoProductoController;
import org.marcobolaños.controller.ProductosController;

/**
 * Documentacion Nombre completo:Marco Jose Bolaños Martinez Fecha de creacion:
 * 05/04/2024 Fecha de Modificacion: 8/04/2024 10/04/2024 11/04 17/04 23/04
 * 24/04 30/04 07/05 08/05 09/05 15/05 17/05 18/05 19/05
 *
 * @author MARCO
 */
public class Main extends Application {

    private Stage escenarioPrincipal;
    private Scene escena;

    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("XMARCS MINI SUPERMARKET");
        menuPrincipalView();
        escenarioPrincipal.getIcons().add(new Image("/org/marcobolaños/images/LOGO SUPER.jpeg"));
        //Parent root = FXMLLoader.load(getClass().getResource("/org/marcobolaños/view/MenuPrincipalView.fxml"));
        // Scene escena = new Scene(root);
        // escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();
    }

    public Initializable cambiarEscena(String fxmlName, int width, int heigth) throws Exception {
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();
        InputStream file = Main.class.getResourceAsStream("/org/marcobolaños/view/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/org/marcobolaños/view/" + fxmlName));
        escena = new Scene((AnchorPane) loader.load(file), width, heigth);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) loader.getController();
        return resultado;
    }

    public void menuPrincipalView() {
        try {
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController) cambiarEscena("MenuPrincipal.fxml", 1022, 579);
            menuPrincipalView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuClientesView() {
        try {
            MenuClienteController menuClienteView = (MenuClienteController) cambiarEscena("MenuClientes.fxml", 1022, 579);
            menuClienteView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuProgramadorView() {
        try {
            MenuProgramadorController menuProgramadorView = (MenuProgramadorController) cambiarEscena("MenuProgramador.fxml", 1022, 579);
            menuProgramadorView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuTipoProductoView() {
        try {
            MenuTipoProductoController menuTipoProductoView = (MenuTipoProductoController) cambiarEscena("MenuTipoProducto.fxml", 1022, 579);
            menuTipoProductoView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuCargoEmpleadoView() {
        try {
            MenuCargoEmpleadoController menuCargoEmpleado = (MenuCargoEmpleadoController) cambiarEscena("MenuCargoEmpleado.fxml", 1415, 800);
            menuCargoEmpleado.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void menuComprasView() {
        try {
            MenuComprasController menuComprasView = (MenuComprasController) cambiarEscena("MenuCompras.fxml", 1022, 579);
            menuComprasView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProveedoresView() {
        try {
            MenuProveedoresController menuProveedoresView = (MenuProveedoresController) cambiarEscena("MenuProveedores.fxml", 1415, 800);
            menuProveedoresView.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuProductosViiew() {
        try {
            ProductosController menuProductosViiew = (ProductosController) cambiarEscena("MenuProductos.fxml", 1415, 800);
            menuProductosViiew.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuDetalleCompraView() {
        try {
            MenuDetalleCompraController menuDetalleCompraView = (MenuDetalleCompraController) cambiarEscena("MenuDetalleCompra.fxml", 1415, 800);
            menuDetalleCompraView.setEscenarioPrincipal(this);
        } catch (Exception M) {
            M.printStackTrace();
        }
    }

    public void menuEmpleadosView() {
        try {
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController) cambiarEscena("MenuEmpleados.fxml", 1415, 800);
            menuEmpleadosView.setEscenarioPrincipal(this);
        } catch (Exception M) {
            M.printStackTrace();
        }
    }

    public void menuFacturaView() {
        try {
            MenuFacturaController menuFacturaView = (MenuFacturaController) cambiarEscena("MenuFactura.fxml", 1415, 800);
            menuFacturaView.setEscenarioPrincipal(this);
        } catch (Exception M) {
            M.printStackTrace();
        }
    }

    public void menuDetalleFacturaView() {
        try {
            MenuDetalleFacturaController menuDetalleFacturaView = (MenuDetalleFacturaController) cambiarEscena("MenuDetalleFactura.fxml", 1415, 800);
            menuDetalleFacturaView.setEscenarioPrincipal(this);
        } catch (Exception M) {
            M.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
