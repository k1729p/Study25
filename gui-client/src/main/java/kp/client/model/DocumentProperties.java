package kp.client.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Map;

/**
 * Represents the document properties.
 *
 * @param id                      the document id
 * @param officialInformation     the information with classification level "official"
 * @param restrictedInformation   the information with classification level "restricted"
 * @param confidentialInformation the information with classification level "confidential"
 * @param secretInformation       the information with classification level "secret"
 */
public record DocumentProperties(StringProperty id,
                                 StringProperty officialInformation,
                                 StringProperty restrictedInformation,
                                 StringProperty confidentialInformation,
                                 StringProperty secretInformation) {
    /**
     * Parameterized constructor.
     *
     * @param map the map with the document data
     */
    public DocumentProperties(Map<String, String> map) {
        this(new SimpleStringProperty(map.get("id")),
                new SimpleStringProperty(map.get("officialInformation")),
                new SimpleStringProperty(map.get("restrictedInformation")),
                new SimpleStringProperty(map.get("confidentialInformation")),
                new SimpleStringProperty(map.get("secretInformation")));
    }
}