package kp.client.gui.buttons;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import kp.client.gui.utils.Helper;
import kp.client.model.DocumentProperties;
import kp.client.services.DocumentMutationService;
import kp.client.services.DocumentQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import static kp.client.Constants.*;

/**
 * Creator for action buttons related to document operations.
 */
public class ActionButtonsCreator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());
    private final DocumentMutationService documentMutationService = new DocumentMutationService();
    private final DocumentQueryService documentQueryService = new DocumentQueryService();
    private final TableView<DocumentProperties> tableView;
    private final ToggleGroup endpointsToggleGroup;
    private final ToggleGroup usersToggleGroup;

    /**
     * Parameterized constructor.
     *
     * @param tableView            the table view to be updated
     * @param endpointsToggleGroup the toggle group for endpoints
     * @param usersToggleGroup     the toggle group for users
     */
    public ActionButtonsCreator(TableView<DocumentProperties> tableView,
                                ToggleGroup endpointsToggleGroup,
                                ToggleGroup usersToggleGroup) {
        this.tableView = tableView;
        this.endpointsToggleGroup = endpointsToggleGroup;
        this.usersToggleGroup = usersToggleGroup;
    }

    /**
     * Creates and initializes the button for the GraphQL mutation action.
     *
     * @return the mutation button node
     */
    public Node createMutateButton() {

        final Button button = new Button(MUTATE_DOCUMENTS_LABEL, Helper.createCircle(Color.YELLOW));
        button.getStyleClass().add(BUTTON_CSS);
        button.setOnAction(_ -> mutateDocument());
        return button;
    }

    /**
     * Creates and initializes the button for the GraphQL delete action.
     *
     * @return the delete button node
     */
    public Node createDeleteButton() {

        final Button button = new Button(DELETE_ALL_DOCUMENTS_LABEL, Helper.createCircle(Color.RED));
        button.getStyleClass().add(BUTTON_CSS);
        button.setOnAction(_ -> deleteAllDocuments());
        return button;
    }

    /**
     * Creates and initializes the button for the GraphQL query action.
     *
     * @return the query button node
     */
    public Node createQueryButton() {

        final Button button = new Button(QUERY_DOCUMENTS_LABEL, Helper.createCircle(Color.GREEN));
        button.getStyleClass().add(BUTTON_CSS);
        button.setOnAction(_ -> queryDocuments());
        return button;
    }

    /**
     * Executes the document mutation action.
     */
    private void mutateDocument() {

        final String endpoint = getSelectedUserData(endpointsToggleGroup);
        final String user = getSelectedUserData(usersToggleGroup);
        try {
            documentMutationService.mutateDocuments(endpoint, user);
        } catch (Exception e) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(TITLE);
            alert.setHeaderText("Documents mutation failure.");
            alert.setContentText(ALERT_EXCEPTION_FMT.formatted(endpoint, user, e.getMessage()));
            alert.showAndWait();
            logger.error("mutateDocument(): exception[{}], endpoint[{}], user[{}]",
                    e.getMessage(), endpoint, user);
            return;
        }
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TITLE);
        alert.setHeaderText("Documents mutation success.");
        alert.setContentText(ALERT_SUCCESS_FMT.formatted(endpoint, user));
        alert.showAndWait();
        logger.info("mutateDocument(): endpoint[{}], user[{}]", endpoint, user);
    }

    /**
     * Executes the action to delete all documents.
     */
    private void deleteAllDocuments() {

        final String endpoint = getSelectedUserData(endpointsToggleGroup);
        final String user = getSelectedUserData(usersToggleGroup);
        if (!USERS.getFirst().equals(user)) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(TITLE);
            alert.setHeaderText("Required classification level 'Secret'");
            alert.setContentText("""
                    To delete all documents,
                    the user must have the classification level 'Secret'.""");
            alert.showAndWait();
            logger.error("deleteAllDocuments(): user must have classification level 'Secret', user[{}]", user);
            return;
        }
        try {
            documentMutationService.deleteAllDocuments(endpoint, user);
        } catch (Exception e) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(TITLE);
            alert.setHeaderText("Documents deletion failure.");
            alert.setContentText(ALERT_EXCEPTION_FMT.formatted(endpoint, user, e.getMessage()));
            alert.showAndWait();
            logger.error("deleteAllDocuments(): exception[{}], endpoint[{}], user[{}]",
                    e.getMessage(), endpoint, user);
            return;
        }
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(TITLE);
        alert.setHeaderText("Documents deletion success.");
        alert.setContentText(ALERT_SUCCESS_FMT.formatted(endpoint, user));
        alert.showAndWait();
        logger.info("deleteAllDocuments(): endpoint[{}], user[{}]", endpoint, user);
    }

    /**
     * Executes the query action to retrieve documents.
     */
    private void queryDocuments() {

        final String endpoint = getSelectedUserData(endpointsToggleGroup);
        final String user = getSelectedUserData(usersToggleGroup);
        tableView.getItems().clear();
        try {
            tableView.getItems().addAll(documentQueryService.queryDocuments(endpoint, user));
        } catch (Exception e) {
            final Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(TITLE);
            alert.setHeaderText("Query failure.");
            alert.setContentText(ALERT_EXCEPTION_FMT.formatted(endpoint, user, e.getMessage()));
            alert.showAndWait();
            logger.error("queryDocuments(): exception[{}], endpoint[{}], user[{}]",
                    e.getMessage(), endpoint, user);
            return;
        }
        logger.info("queryDocuments(): endpoint[{}], user[{}]", endpoint, user);
    }

    /**
     * Retrieves the selected user data from the given ToggleGroup, ensuring a String result.
     *
     * @param group the ToggleGroup from which to extract user data
     * @return the selected user data as a String, or an empty String if not present
     */
    private static String getSelectedUserData(ToggleGroup group) {

        return Optional.ofNullable(group.getSelectedToggle())
                .map(Toggle::getUserData)
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .orElse("");
    }
}