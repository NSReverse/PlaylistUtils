package mx.x10.reverseeffectapps.PlaylistUtils;

import android.content.Context;

import java.util.List;
import java.util.Map;

/**
 * Class PlaylistUtils -
 *
 * This class manages a playlist library that can be placed on the device persistently.
 *
 * @author Robert Brown (@ReverseEffect, @ReverseEffectApps)
 * 2016 PlaylistUtils, ChTube
 */
public class PlaylistUtils {
    private Context currentContext;
    private boolean verbose;

    /**
     * Constructor PlaylistUtils(Context) -
     *
     * This constructor initializes a PlaylistUtils used for managing a playlist library.
     *
     * @param currentContext The calling Activity's context for handling files.
     */
    public PlaylistUtils(Context currentContext) {
        this.currentContext = currentContext;
        verbose = false;
    }

    /**
     * Method getAllPlaylists() -
     *
     * This method returns a dictionary/map of all the playlists, labeled by a key with the name of the playlist.
     *
     * @return A dictionary/map containing all of the playlists with names as keys.
     */
    public Map<String, List<PlaylistItem>> getAllPlaylists() {
        return PlaylistLibrary.getAllPlaylists(currentContext, verbose);
    }

    /**
     * Method getPlaylist(String) -
     *
     * This method gets a playlist by its name, and returns a List of PlaylistItems.
     *
     * @param playlistName A String representing the name of the playlist.
     * @return A List<PlaylistItem> containing all of the items in a playlist.
     */
    public List<PlaylistItem> getPlaylist(String playlistName) {
        return PlaylistLibrary.getPlaylistWithName(currentContext, playlistName, verbose);
    }

    /**
     * Method addToPlaylist(String, PlaylistItem) -
     *
     * This method adds a PlaylistItem to a playlist.
     *
     * @param playlistName A String representing the name of the playlist.
     * @param item A PlaylistItem to add to the playlist.
     * @return A boolean indicating if the operation was successful.
     */
    public boolean addToPlaylist(String playlistName, PlaylistItem item) {
        return PlaylistLibrary.addItemToPlaylistWithName(currentContext, playlistName, item, verbose);
    }

    /**
     * Method removeFromPlaylist(String, PlaylistItem) -
     *
     * This method removes a PlaylistItem from a playlist.
     *
     * @param playlistName A String representing the name of the playlist.
     * @param item A PlaylistItem to remove from the playlist.
     * @return A boolean representing if the operation was successful.
     */
    public boolean removeFromPlaylist(String playlistName, PlaylistItem item) {
        return PlaylistLibrary.removeItemFromPlaylistWithName(currentContext, playlistName, item, verbose);
    }

    /**
     * Method createPlaylist(String) -
     *
     * This method creates a playlist in the app directory.
     *
     * @param playlistName A String representing the name of the playlist.
     * @return A boolean representing if the operation was successful.
     */
    public boolean createPlaylist(String playlistName) {
        return PlaylistLibrary.createPlaylistWithName(currentContext, playlistName, verbose);
    }

    /**
     * Method removePlaylist(String) -
     *
     * This method removes a playlist in the app directory.
     *
     * @param playlistName A String representing the name of the playlist.
     * @return A boolean representing if the operation was successful.
     */
    public boolean removePlaylist(String playlistName) {
        return PlaylistLibrary.deletePlaylistWithName(currentContext, playlistName, verbose);
    }

    /**
     * Method setUseConsoleOutput(boolean) -
     *
     * This method enables or disables console output.
     *
     * @param verbose A boolean representing whether or not to use console output.
     */
    public void setUseConsoleOutput(boolean verbose) {
        this.verbose = verbose;
    }
}
