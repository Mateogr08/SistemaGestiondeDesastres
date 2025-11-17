package co.edu.uniquindio.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import co.edu.uniquindio.util.SceneSwitcher;
import co.edu.uniquindio.model.AppModel;
import co.edu.uniquindio.model.Ubicacion;
import co.edu.uniquindio.model.Recurso;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PanelDelEstadoGeneralController {

    @FXML
    private TableView<Ubicacion> tablaZonas;
    @FXML private TableColumn<Ubicacion, String> colZona;
    @FXML private TableColumn<Ubicacion, String> colEstadoZona;
    @FXML private TableColumn<Ubicacion, Integer> colPersonasAfectadas;

    @FXML private TableView<Recurso> tablaRecursos;
    @FXML private TableColumn<Recurso, String> colRecurso;
    @FXML private TableColumn<Recurso, Integer> colCantidadRecurso;
    @FXML private TableColumn<Recurso, String> colUbicacionRecurso;

    @FXML
    public void initialize() {
        // Configuración de columnas
        colZona.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colEstadoZona.setCellValueFactory(cell -> new SimpleStringProperty("Urgencia: " + cell.getValue().getNivelUrgencia()));
        colPersonasAfectadas.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getPersonasAfectadas()).asObject());

        colRecurso.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colCantidadRecurso.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getCantidadDisponible()).asObject());
        colUbicacionRecurso.setCellValueFactory(cell -> new SimpleStringProperty("Global"));

        cargarDatosEnTablas();
    }

    private void cargarDatosEnTablas() {
        ObservableList<Ubicacion> zonas = FXCollections.observableArrayList(
                AppModel.getInstance().getGrafoRutas().getAdyacencias().keySet()
        );
        tablaZonas.setItems(zonas);

        ObservableList<Recurso> recursos = FXCollections.observableArrayList(
                AppModel.getInstance().getGestorRecursos().getInventarioGlobal()
        );
        tablaRecursos.setItems(recursos);
    }

    // Metodo auxiliar para obtener el Stage actual
    private Stage getStage() {
        if (tablaZonas != null && tablaZonas.getScene() != null) {
            return (Stage) tablaZonas.getScene().getWindow();
        }
        return null;
    }

    // navegación
    @FXML
    public void irPanelAdministracion() {
        Stage stage = getStage();
        if(stage != null) SceneSwitcher.switchTo(stage, "panelDeAdministracion.fxml");
    }

    @FXML
    public void irPanelRutas() {
        Stage stage = getStage();
        if(stage != null) SceneSwitcher.switchTo(stage, "panelDeRutas.fxml");
    }

    @FXML
    public void irPanelEstadisticas() {
        Stage stage = getStage();
        if(stage != null) SceneSwitcher.switchTo(stage, "panelDeEstadisticas.fxml");
    }

    @FXML
    public void irPanelMapaInteractivo() {
        Stage stage = getStage();
        if(stage != null) SceneSwitcher.switchTo(stage, "panelDeMapaInteractivo.fxml");
    }
}
