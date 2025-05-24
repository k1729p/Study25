package kp.client.gui.tabs;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import kp.client.model.DocumentProperties;

/**
 * Creates and initializes a tab pane for displaying actions and details.
 */
public class TabPaneCreator {
    /**
     * Creates and initializes the tab pane.
     *
     * @param tableView    the table view containing documents
     * @param selectedItem the selected item in the table view
     * @return the tab pane node
     */
    public Node createTabPane(TableView<DocumentProperties> tableView,
                              ObjectProperty<DocumentProperties> selectedItem) {

        final TabPane tabPane = new TabPane(
                new ActionsTabCreator().createActionsTab(tableView),
                new DetailsTabCreator().createDetailsTab(selectedItem));
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }
}
