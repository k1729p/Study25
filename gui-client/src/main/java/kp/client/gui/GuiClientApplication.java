package kp.client.gui;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Separator;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kp.client.gui.tables.DocumentTableCreator;
import kp.client.gui.tabs.TabPaneCreator;
import kp.client.gui.utils.Helper;
import kp.client.model.DocumentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static kp.client.Constants.TITLE;
import static kp.client.Constants.WINDOW_WIDTH;

/**
 * JavaFX application for the GraphQL client.
 */
public class GuiClientApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * The primary entry point for launching the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setScene(createScene());
        primaryStage.setTitle(TITLE);
        Helper.loadIcon().ifPresent(primaryStage.getIcons()::add);
        primaryStage.show();
        logger.info("start():");
    }

    /**
     * Creates and initializes the scene.
     *
     * @return the scene
     */
    private Scene createScene() {

        final Scene scene = new Scene(createRootNode(), WINDOW_WIDTH, -1);
        Helper.loadStylesheet().ifPresent(stylesheet -> {
            scene.getStylesheets().add(stylesheet);
            scene.getRoot().applyCss();
        });
        return scene;
    }

    /**
     * Creates and initializes the root node.
     *
     * @return the root node
     */
    private Parent createRootNode() {

        final TableView<DocumentProperties> tableView = new TableView<>();
        final ObjectProperty<DocumentProperties> selectedItem = new SimpleObjectProperty<>();
        final VBox vBox = new VBox(10,
                new TabPaneCreator().createTabPane(tableView, selectedItem),
                new Separator(),
                new DocumentTableCreator().createDocumentTable(tableView, selectedItem)
        );
        vBox.setPadding(new Insets(10));
        return vBox;
    }
}
