
package ponny.org.prueba.modelo.entidades.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entry {

    private ImName imName;
    private List<ImImage> imImage = new ArrayList<ImImage>();
    private Summary summary;
    private ImPrice imPrice;
    private ImContentType imContentType;
    private Rights rights;
    private Title title;
    private Link link;
    private Id id;
    private ImArtist imArtist;
    private Category category;
    private ImReleaseDate imReleaseDate;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Entry() {
    }

    /**
     * @param id
     * @param summary
     * @param imContentType
     * @param category
     * @param title
     * @param imArtist
     * @param imReleaseDate
     * @param link
     * @param rights
     * @param imPrice
     * @param imImage
     * @param imName
     */
    public Entry(ImName imName, List<ImImage> imImage, Summary summary, ImPrice imPrice, ImContentType imContentType, Rights rights, Title title, Link link, Id id, ImArtist imArtist, Category category, ImReleaseDate imReleaseDate) {
        this.imName = imName;
        this.imImage = imImage;
        this.summary = summary;
        this.imPrice = imPrice;
        this.imContentType = imContentType;
        this.rights = rights;
        this.title = title;
        this.link = link;
        this.id = id;
        this.imArtist = imArtist;
        this.category = category;
        this.imReleaseDate = imReleaseDate;
    }

    /**
     * @return The imName
     */
    public ImName getImName() {
        return imName;
    }

    /**
     * @param imName The im:name
     */
    public void setImName(ImName imName) {
        this.imName = imName;
    }

    /**
     * @return The imImage
     */
    public List<ImImage> getImImage() {
        return imImage;
    }

    /**
     * @param imImage The im:image
     */
    public void setImImage(List<ImImage> imImage) {
        this.imImage = imImage;
    }

    /**
     * @return The summary
     */
    public Summary getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    /**
     * @return The imPrice
     */
    public ImPrice getImPrice() {
        return imPrice;
    }

    /**
     * @param imPrice The im:price
     */
    public void setImPrice(ImPrice imPrice) {
        this.imPrice = imPrice;
    }

    /**
     * @return The imContentType
     */
    public ImContentType getImContentType() {
        return imContentType;
    }

    /**
     * @param imContentType The im:contentType
     */
    public void setImContentType(ImContentType imContentType) {
        this.imContentType = imContentType;
    }

    /**
     * @return The rights
     */
    public Rights getRights() {
        return rights;
    }

    /**
     * @param rights The rights
     */
    public void setRights(Rights rights) {
        this.rights = rights;
    }

    /**
     * @return The title
     */
    public Title getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(Title title) {
        this.title = title;
    }

    /**
     * @return The link
     */
    public Link getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(Link link) {
        this.link = link;
    }

    /**
     * @return The id
     */
    public Id getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Id id) {
        this.id = id;
    }

    /**
     * @return The imArtist
     */
    public ImArtist getImArtist() {
        return imArtist;
    }

    /**
     * @param imArtist The im:artist
     */
    public void setImArtist(ImArtist imArtist) {
        this.imArtist = imArtist;
    }

    /**
     * @return The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category The category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return The imReleaseDate
     */
    public ImReleaseDate getImReleaseDate() {
        return imReleaseDate;
    }

    /**
     * @param imReleaseDate The im:releaseDate
     */
    public void setImReleaseDate(ImReleaseDate imReleaseDate) {
        this.imReleaseDate = imReleaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;

        Entry entry = (Entry) o;

        if (!rights.equals(entry.rights)) return false;
        if (!title.equals(entry.title)) return false;
        if (!link.equals(entry.link)) return false;
        if (!id.equals(entry.id)) return false;
        return additionalProperties != null ? additionalProperties.equals(entry.additionalProperties) : entry.additionalProperties == null;

    }

    @Override
    public int hashCode() {
        int result = rights.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + link.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (additionalProperties != null ? additionalProperties.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getId().getLabel();
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
