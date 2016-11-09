
package ponny.org.prueba.modelo.entidades.entry;

import java.util.HashMap;
import java.util.Map;

public class Attributes_releaseDate {

    private String label;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Attributes_releaseDate() {
    }

    /**
     * @param label
     */
    public Attributes_releaseDate(String label) {
        this.label = label;
    }

    /**
     * @return The label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label The label
     */
    public void setLabel(String label) {
        this.label = label;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
