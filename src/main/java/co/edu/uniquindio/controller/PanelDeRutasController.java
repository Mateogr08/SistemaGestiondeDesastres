package co.edu.uniquindio.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import co.edu.uniquindio.model.AppModel;
import co.edu.uniquindio.model.Ruta;

import java.util.List;
import java.util.stream.Collectors;

public class PanelDeRutasController {

    @FXML private TableView<Ruta> tablaRutas;
    @FXML private TableColumn<Ruta, String> colOrigen;
    @FXML private TableColumn<Ruta, String> colDestino;
    @FXML private TableColumn<Ruta, Double> colDistancia;
    @FXML private TableColumn<Ruta, String> colEstado;

    @FXML private ComboBox<String> comboRutas;
    @FXML private TextField txtPersonas;

    @FXML private Button btnPlanificar;
    @FXML private Button btnEditar;
    @FXML private Button btnVolver;

    private final AppModel model = AppModel.getInstance();

    @FXML
    public void initialize() {
        cargarTabla();
        cargarComboRutas();
    }

    private void cargarTabla() {
        colOrigen.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getOrigen().getNombre()));

        colDestino.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getDestino().getNombre()));

        colDistancia.setCellValueFactory(data ->
                new SimpleDoubleProperty(data.getValue().getDistancia()).asObject());

        colEstado.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().isDisponible() ? "Disponible" : "No Disponible"));

        List<Ruta> rutas = model.getGrafoRutas().getAdyacencias().values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        tablaRutas.setItems(FXCollections.observableArrayList(rutas));
    }

    private void cargarComboRutas() {
        List<String> rutas = model.getGrafoRutas().getAdyacencias().values().stream()
                .flatMap(List::stream)
                .map(r -> r.getOrigen().getNombre() + " → " + r.getDestino().getNombre())
                .distinct()
                .collect(Collectors.toList());
        comboRutas.setItems(FXCollections.observableArrayList(rutas));
    }

    @FXML
    public void btnPlanificarAction() {
        String sel = comboRutas.getSelectionModel().getSelectedItem();
        String personas = txtPersonas.getText();

        if (sel == null || sel.isBlank()) {
            mostrar("Selecciona una ruta.");
            return;
        }

        if (personas == null || personas.isBlank()) {
            mostrar("Ingresa número de personas.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(personas);
            if (cantidad <= 0) {
                mostrar("Número de personas debe ser mayor que 0.");
                return;
            }

            mostrar("Planificación creada para " + cantidad + " personas en ruta: " + sel);

        } catch (NumberFormatException e) {
            mostrar("Ingresa un número válido de personas.");
        }
    }

    @FXML
    public void btnVolverAction() {
        Stage stage = (Stage) txtPersonas.getScene().getWindow();
        co.edu.uniquindio.util.SceneSwitcher.switchTo(stage, "panelDelEstadoGeneral.fxml");
    }

    private void mostrar(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        a.setHeaderText(null);
        a.showAndWait();
    }
}
