package kp.client.gui.tabs;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import kp.client.gui.buttons.ActionButtonsCreator;
import kp.client.gui.utils.Helper;
import kp.client.model.DocumentProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

import static kp.client.Constants.*;

/**
 * Creator for the tab containing action controls.
 */
public class ActionsTabCreator {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Creates and initializes the action tab.
     *
     * @param tableView the table view
     * @return the action tab
     */
    Tab createActionsTab(TableView<DocumentProperties> tableView) {

        final ToggleGroup endpointsToggleGroup = new ToggleGroup();
        final Node endpointsBox = createEndpointsBox(endpointsToggleGroup);

        final ToggleGroup usersToggleGroup = new ToggleGroup();
        final Node usersBox = createUsersBox(usersToggleGroup);

        final ActionButtonsCreator actionButtonsCreator =
                new ActionButtonsCreator(tableView, endpointsToggleGroup, usersToggleGroup);
        final Node buttonsBox = createButtonsBox(actionButtonsCreator);

        final HBox spacerBox = new HBox();
        HBox.setHgrow(spacerBox, Priority.ALWAYS);
        final HBox hBox = new HBox(15, endpointsBox, usersBox, spacerBox, buttonsBox);
        hBox.getStyleClass().add("kp-actions-box");
        final Tab tab = new Tab();
        tab.setText("Actions");
        tab.setContent(hBox);
        if (logger.isDebugEnabled()) {
            logger.debug("createActionsTab():");
        }
        return tab;
    }

    /**
     * Creates and initializes the endpoints box.
     *
     * @param endpointsToggleGroup the toggle group for endpoints
     * @return the endpoints box
     */
    private Node createEndpointsBox(ToggleGroup endpointsToggleGroup) {

        final Label label = new Label("Endpoints");
        label.getStyleClass().add("kp-radio-label");
        final VBox endpointsBox = new VBox(15, label);
        endpointsBox.getStyleClass().add("kp-endpoints-box");
        Helper.addRadioButtons(
                endpointsToggleGroup, ENDPOINT_LABELS, ENDPOINT_URLS, endpointsBox);
        return endpointsBox;
    }

    /**
     * Creates and initializes the users box.
     *
     * @param usersToggleGroup the toggle group for users
     * @return the users box
     */
    private Node createUsersBox(ToggleGroup usersToggleGroup) {

        final Label label = new Label("Users");
        label.getStyleClass().add("kp-radio-label");
        final VBox usersBox = new VBox(15, label);
        usersBox.getStyleClass().add("kp-users-box");
        Helper.addRadioButtons(usersToggleGroup, USERS, USERS, usersBox);
        return usersBox;
    }

    /**
     * Creates and initializes the box with buttons.
     *
     * @param actionButtonsCreator the action buttons creator
     * @return the buttons box
     */
    private Node createButtonsBox(ActionButtonsCreator actionButtonsCreator) {

        final VBox buttonsBox = new VBox(15,
                actionButtonsCreator.createMutateButton(),
                actionButtonsCreator.createQueryButton(),
                new Separator(),
                actionButtonsCreator.createDeleteButton());
        buttonsBox.getStyleClass().add("kp-buttons-box");
        return buttonsBox;
    }
}