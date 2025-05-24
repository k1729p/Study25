package kp.client.gui.utils;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import kp.client.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Helper class for GUI-related utilities.
 */
public class Helper {
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getName());

    /**
     * Private constructor to prevent instantiation.
     */
    private Helper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Loads the application icon.
     *
     * @return an {@link Optional} containing the icon image if available,
     * otherwise an empty {@link Optional}
     */
    public static Optional<Image> loadIcon() {
        return Optional.ofNullable(Helper.class.getResourceAsStream(Constants.ICON_FILE_NAME))
                .map(Image::new);
    }

    /**
     * Loads the stylesheet resource.
     *
     * @return an {@link Optional} containing the stylesheet URL as a string if available,
     * otherwise an empty {@link Optional}
     */
    public static Optional<String> loadStylesheet() {

        String stylesheet = null;
        try {
            final URL url = Helper.class.getResource(Constants.STYLESHEET_NAME);
            if (url != null) {
                stylesheet = url.toExternalForm();
            } else {
                logger.error("loadStylesheet(): resource 'stylesheet.css' not found in classpath.");
                final File file = new File(Constants.RESOURCES_DIR + Constants.STYLESHEET_NAME);
                if (file.canRead()) {
                    stylesheet = file.toURI().toString();
                } else {
                    logger.error("loadStylesheet(): resource 'stylesheet.css' not readable from file[{}].", file);
                }
            }
        } catch (Exception e) {
            logger.error("loadStylesheet(): loading file 'stylesheet.css', exception[{}]",
                    e.getMessage());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("loadStylesheet():");
        }
        return Optional.ofNullable(stylesheet).filter(Predicate.not(String::isBlank));
    }

    /**
     * Creates a circle with a specified color.
     *
     * @param color the fill color of the circle
     * @return the created circle shape
     */
    public static Shape createCircle(Paint color) {

        final Circle circle = new Circle(8, color);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(1.0);
        return circle;
    }

    /**
     * Adds radio buttons to the specified pane, using the provided labels and user data.
     *
     * @param toggleGroup  the toggle group to which the radio buttons will belong
     * @param labelList    the list of labels for the radio buttons
     * @param userDataList the list of user data objects for the radio buttons
     * @param pane         the pane to which radio buttons will be added
     */
    public static void addRadioButtons(ToggleGroup toggleGroup, List<String> labelList,
                                       List<String> userDataList, Pane pane) {

        IntStream.range(0, labelList.size()).forEach(index -> {
            final RadioButton radioButton = new RadioButton(labelList.get(index));
            radioButton.setToggleGroup(toggleGroup);
            radioButton.setUserData(userDataList.get(index));
            radioButton.setSelected(index == 0);
            radioButton.getStyleClass().add("kp-radio");
            pane.getChildren().add(radioButton);
        });
    }
}