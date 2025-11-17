package co.edu.uniquindio.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {

    /**
     * Cambia la escena actual del {@link Stage} especificado cargando un archivo FXML.
     * <p>
     * Este metodo utiliza un {@link FXMLLoader} para cargar la interfaz ubicada en la carpeta
     * <code>/fxml/</code> dentro de los recursos del proyecto, crea una nueva {@link Scene} y la
     * asigna al {@link Stage} proporcionado.
     * </p>
     *
     * @param stage el escenario donde se mostrará la nueva escena
     * @param fxml  el nombre del archivo FXML a cargar (por ejemplo, "panelDelLogin.fxml")
     *
     * @throws NullPointerException si el archivo FXML no se encuentra en los recursos
     * @throws RuntimeException si ocurre un error al cargar el archivo FXML
     */
    public static void switchTo(Stage stage, String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource("/fxml/" + fxml));

            Parent root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
