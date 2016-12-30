package mx.x10.reverseeffectapps.PlaylistUtils;

/**
 * Class PlaylistItem
 *
 * @author Robert Brown (@ReverseEffect, @ReverseEffectApps)
 * 2016 PlaylistUtils, ChTube
 */
public class PlaylistItem {
    private String id;
    private String title;
    private String author;
    private String mediaPath;
    private String thumbnailPath;

    /**
     * Constructor PlaylistItem() -
     *
     * This is the default Constructor to initialize default values.
     */
    public PlaylistItem() {
        this.id = "";
        this.title = "";
        this.author = "";
        this.mediaPath = "";
        this.thumbnailPath = "";
    }

    /**
     * Constructor PlaylistItem(String, String, String, String, String) -
     *
     * This is a constructor to set custom values to the PlaylistItem.
     *
     * @param id A String representing the ID of the media in your library.
     * @param title A String representing the title of the media.
     * @param author A String representing the author of the media.
     * @param videoPath A String representing the file path to the media.
     * @param thumbnailPath A String representing the thumbnail path for the media.
     */
    public PlaylistItem(String id,
                           String title,
                           String author,
                           String videoPath,
                           String thumbnailPath) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.mediaPath = videoPath;
        this.thumbnailPath = thumbnailPath;
    }

    /**
     * Setter method setId(String) -
     *
     * This method sets the PlaylistItem's id.
     *
     * @param id A String representing the id of the media in your library.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter method setTitle(String) -
     *
     * This method sets the PlaylistItem's title.
     *
     * @param title A String representing the title of the media.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Setter method setAuthor(String) -
     *
     * This method sets the PlaylistItem's author.
     *
     * @param author A String representing the author of the media.
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Setter method setMediaPath(String) -
     *
     * This method sets the file path to the media.
     *
     * @param mediaPath A String representing the media's file path.
     */
    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    /**
     * Setter method setThumbnailPath(String) -
     *
     * This method sets the file path to the media's thumbnail.
     *
     * @param thumbnailPath A String representing the thumbnail's file path.
     */
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    /**
     * Getter method getId() -
     *
     * This method gets the id of the media.
     *
     * @return A String representing the id of the media.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter method getTitle() -
     *
     * This method gets the title of the media.
     *
     * @return A String representing the title of the media.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter method getAuthor() -
     *
     * This method gets the author of the media.
     *
     * @return A String representing the author of the media.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Getter getMediaPath() -
     *
     * This method gets the file path of the media.
     *
     * @return A String representing the file path of the media.
     */
    public String getMediaPath() {
        return mediaPath;
    }

    /**
     * Getter getThumbnailPath() -
     *
     * This method gets the thumbnail path of the media.
     *
     * @return A String representing the path of the thumbnail.
     */
    public String getThumbnailPath() {
        return thumbnailPath;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object playlistItem) {
        if (playlistItem instanceof PlaylistItem) {
            if (hashCode() == playlistItem.hashCode()) {
                return true;
            }
        }

        return false;
    }
}
