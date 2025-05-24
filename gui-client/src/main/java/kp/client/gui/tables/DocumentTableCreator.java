package kp.client.gui.tables;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import kp.client.model.DocumentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.stream.IntStream;

import static kp.client.Constants.DOCUMENT_FIELD_NAME_LIST;
import static kp.client.Constants.DOCUMENT_PROPERTY_LIST;

/**
 * Creator for the document table.
 * <p>
 * This class encapsulates the creation and initialization of the table
 * that displays document properties in the application's GUI.
 * </p>
 */
public class DocumentTableCreator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Creates and initializes the document table view node.
     *
     * @param tableView    the TableView instance to populate
     * @param selectedItem the ObjectProperty to bind the selected item to
     * @return the Node containing the fully initialized document table
     */
    public Node createDocumentTable(TableView<DocumentProperties> tableView,
                                    ObjectProperty<DocumentProperties> selectedItem) {

        final Label label = new Label("Query Results");
        label.getStyleClass().add("kp-table-label");
        final VBox title = new VBox(label);
        title.setAlignment(Pos.BOTTOM_CENTER);

        final VBox vBox = new VBox(10, title, createDocumentTableBox(tableView, selectedItem));
        if (logger.isDebugEnabled()) {
            logger.debug("createDocumentTable():");
        }
        return vBox;
    }

    /**
     * Creates the box containing the document table with all its columns set up.
     *
     * @param tableView    the TableView instance to configure
     * @param selectedItem the ObjectProperty to bind to the table's selected item
     * @return a VBox containing the configured TableView
     */
    private Node createDocumentTableBox(TableView<DocumentProperties> tableView,
                                        ObjectProperty<DocumentProperties> selectedItem) {

        IntStream.range(0, DOCUMENT_PROPERTY_LIST.size()).forEach(index -> {
            final TableColumn<DocumentProperties, String> tableColumn =
                    new TableColumn<>(DOCUMENT_FIELD_NAME_LIST.get(index));
            tableColumn.prefWidthProperty().bind(
                    tableView.widthProperty().multiply(index == 0 ? 0.04 : 0.24)
            );
            switch (index) {
                case 0 -> tableColumn.setCellValueFactory(features ->
                        features.getValue().id());
                case 1 -> {
                    tableColumn.setCellValueFactory(features ->
                            features.getValue().officialInformation());
                    tableColumn.getStyleClass().add("kp-column-official");
                }
                case 2 -> {
                    tableColumn.setCellValueFactory(features ->
                            features.getValue().restrictedInformation());
                    tableColumn.getStyleClass().add("kp-column-restricted");
                }
                case 3 -> {
                    tableColumn.setCellValueFactory(features ->
                            features.getValue().confidentialInformation());
                    tableColumn.getStyleClass().add("kp-column-confidential");
                }
                case 4 -> {
                    tableColumn.setCellValueFactory(features ->
                            features.getValue().secretInformation());
                    tableColumn.getStyleClass().add("kp-column-secret");
                }
                default -> {
                    // No action required for other indices
                }
            }
            tableView.getColumns().add(tableColumn);
        });
        selectedItem.bind(tableView.getSelectionModel().selectedItemProperty());
        return new VBox(tableView);
    }
}
