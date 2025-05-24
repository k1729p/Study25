package kp.client.gui.tabs;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import kp.client.model.DocumentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.stream.IntStream;

import static kp.client.Constants.DOCUMENT_FIELD_NAME_LIST;
import static kp.client.Constants.DOCUMENT_FIELD_STYLE_LIST;

/**
 * Creator for the tab presenting the details of the document.
 */
public class DetailsTabCreator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Creates and initializes the details tab.
     *
     * @param selectedItem the selected table view item
     * @return the top node
     */
    Tab createDetailsTab(ObjectProperty<DocumentProperties> selectedItem) {

        final Tab tab = new Tab();
        tab.setText("Details");
        tab.setContent(createDocumentGridPane(selectedItem));
        if (logger.isDebugEnabled()) {
            logger.debug("createDetailsTab():");
        }
        return tab;
    }

    /**
     * Creates and initializes the document grid pane.
     *
     * @param selectedItem the selected table view item
     * @return the document grid pane node
     */
    private Node createDocumentGridPane(ObjectProperty<DocumentProperties> selectedItem) {

        final List<Label> labelList = IntStream.range(0, DOCUMENT_FIELD_NAME_LIST.size())
                .mapToObj(this::createDocumentLabel)
                .toList();
        final List<TextField> fieldList = IntStream.range(0, DOCUMENT_FIELD_NAME_LIST.size())
                .mapToObj(_ -> createDocumentField())
                .toList();
        bindDocumentFields(selectedItem, fieldList);

        final GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("kp-document-grid-pane");
        IntStream.range(0, labelList.size()).forEach(index ->
                gridPane.add(labelList.get(index), 0, index));
        IntStream.range(0, fieldList.size()).forEach(index ->
                gridPane.add(fieldList.get(index), 1, index));
        List.of(15, 85).forEach(percentWidth -> {
            final ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(percentWidth);
            gridPane.getColumnConstraints().add(columnConstraints);
        });
        return gridPane;
    }

    /**
     * Creates and initializes a document label.
     *
     * @param index the index of the label
     * @return the document label
     */
    private Label createDocumentLabel(int index) {

        final Label label = new Label(DOCUMENT_FIELD_NAME_LIST.get(index));
        label.getStyleClass().add(DOCUMENT_FIELD_STYLE_LIST.get(index));
        return label;
    }

    /**
     * Creates and initializes a document text field.
     *
     * @return the document text field
     */
    private TextField createDocumentField() {

        final TextField textField = new TextField();
        textField.getStyleClass().add("kp-document-field");
        textField.setEditable(false);
        textField.setMouseTransparent(true);
        textField.setFocusTraversable(false);
        return textField;
    }

    /**
     * Binds the document fields to the provided selected item property.
     *
     * @param selectedItem the selected table view item
     * @param fieldList    the list of document fields
     */
    private void bindDocumentFields(ObjectProperty<DocumentProperties> selectedItem,
                                    List<TextField> fieldList) {

        fieldList.getFirst().textProperty()
                .bind(selectedItem.flatMap(DocumentProperties::id));
        fieldList.get(1).textProperty()
                .bind(selectedItem.flatMap(DocumentProperties::officialInformation));
        fieldList.get(2).textProperty()
                .bind(selectedItem.flatMap(DocumentProperties::restrictedInformation));
        fieldList.get(3).textProperty()
                .bind(selectedItem.flatMap(DocumentProperties::confidentialInformation));
        fieldList.get(4).textProperty()
                .bind(selectedItem.flatMap(DocumentProperties::secretInformation));
    }
}