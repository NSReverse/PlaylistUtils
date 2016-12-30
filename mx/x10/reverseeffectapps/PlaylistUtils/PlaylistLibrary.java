package mx.x10.reverseeffectapps.PlaylistUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.util.Log;

/**
 * Class PlaylistLibrary -
 *
 * This class is not for use with the library user. Please use PlaylistUtils instead.
 *
 * @author Robert Brown (@ReverseEffect, @ReverseEffectApps)
 * 2016 PlaylistUtils, ChTube
 */
public class PlaylistLibrary {

    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_GET_PLAYLISTS = "GET_ALL";
    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_GET_SINGLE_PLAYLIST = "GET_ONE";
    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_MAKE_SINGLE_PLAYLIST = "CREATE_ONE";
    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_DELETE_SINGLE_PLAYLIST = "DELETE_ONE";
    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_ADD_ITEM_TO_PL = "ADD_PL_ITEM";
    @SuppressWarnings("FieldCanBeLocal") private static final String LOG_TAG_REMOVE_ITEM_FROM_PL = "REMOVE_PL_ITEM";

    //region: GET ALL PLAYLISTS
    protected static Map<String, List<PlaylistItem>> getAllPlaylists(Context currentContext,
                                                                     boolean verbose) {
        Map<String, List<PlaylistItem>> playlistsMap = new HashMap<>();

        File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

        if (!playlistsDir.exists()) {
            if (verbose) { out(LOG_TAG_GET_PLAYLISTS, "Library doesn't exist. Creating..."); }

            if (playlistsDir.mkdirs()) {
                if (verbose) {
                    out(LOG_TAG_GET_PLAYLISTS, "Library has been created. Returning null to signify empty library.");
                }
            }
            else {
                if (verbose) {
                    out(LOG_TAG_GET_PLAYLISTS, "Unable to create library. Returning null to signify failure.");
                }
            }

            return null;
        }

        if (verbose) {
            out(LOG_TAG_GET_PLAYLISTS, "Library found. Reading...");
        }

        File[] libraryDirectoryListing = playlistsDir.listFiles();

        for (File currentFile : libraryDirectoryListing) {
            String playlistPath = currentFile.getAbsolutePath();
            Pattern namePattern = Pattern.compile("Playlists/(.*?).txt");
            Matcher nameMatcher = namePattern.matcher(playlistPath);

            while (nameMatcher.find()) {
                String playlistName = nameMatcher.group(1);
                playlistsMap.put(playlistName, getPlaylistWithName(currentContext, playlistName, verbose));
            }
        }

        return playlistsMap;
    }
    //endregion

    //region: GET PLAYLIST WITH NAME
    protected static List<PlaylistItem> getPlaylistWithName(Context currentContext,
                                                            String playlistName,
                                                            boolean verbose) {
        List<PlaylistItem> playlist = new ArrayList<>();

        File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

        if (!playlistName.contains(".txt")) {
            playlistName = playlistName + ".txt";
        }

        if (!playlistsDir.exists()) {
            if (verbose) { out(LOG_TAG_GET_SINGLE_PLAYLIST, "Library doesn't exist. Creating..."); }

            if (playlistsDir.mkdirs()) {
                if (verbose) {
                    out(LOG_TAG_GET_SINGLE_PLAYLIST, "Library has been created. Returning null to signify empty library.");
                }
            }
            else {
                if (verbose) {
                    out(LOG_TAG_GET_SINGLE_PLAYLIST, "Unable to create library. Returning null to signify failure.");
                }
            }

            return null;
        }

        if (verbose) { out(LOG_TAG_GET_SINGLE_PLAYLIST, "Library found. Reading..."); }

        File playlistFile = new File(playlistsDir, playlistName);

        try {
            StringBuilder builder = new StringBuilder();
            Scanner scn = new Scanner(playlistFile);

            while (scn.hasNextLine()) {
                builder.append(scn.nextLine());
            }

            String result = builder.toString();
            String[] rawItems = result.split(",");

            for (String currentItem : rawItems) {
                String id = "";
                String title = "";
                String author = "";
                String mediaPath = "";
                String thumbPath = "";

                // TO DO: Refactor this.
                Pattern pattern = Pattern.compile("<id>(.*?)</id>");
                Matcher matcher = pattern.matcher(currentItem);

                while (matcher.find()) {
                    id = matcher.group(1);
                }

                pattern = Pattern.compile("<title>(.*?)</title>");
                matcher = pattern.matcher(currentItem);

                while (matcher.find()) {
                    title = matcher.group(1);
                }

                pattern = Pattern.compile("<author>(.*?)</author>");
                matcher = pattern.matcher(currentItem);

                while (matcher.find()) {
                    author = matcher.group(1);
                }

                pattern = Pattern.compile("<mediaPath>(.*?)</mediaPath>");
                matcher = pattern.matcher(currentItem);

                while (matcher.find()) {
                    mediaPath = matcher.group(1);
                }

                pattern = Pattern.compile("<thumbPath>(.*?)</thumbPath>");
                matcher = pattern.matcher(currentItem);

                while (matcher.find()) {
                    thumbPath = matcher.group(1);
                }

                PlaylistItem currentPlaylistItem = new PlaylistItem();
                currentPlaylistItem.setId(id);
                currentPlaylistItem.setTitle(title);
                currentPlaylistItem.setAuthor(author);
                currentPlaylistItem.setMediaPath(mediaPath);
                currentPlaylistItem.setThumbnailPath(thumbPath);

                if (!id.equals("") && !title.equals("") && !author.equals("") &&
                        !mediaPath.equals("") && !thumbPath.equals("")) {
                    playlist.add(currentPlaylistItem);
                }
            }
        }
        catch (FileNotFoundException ex) {
            if (verbose) { out(LOG_TAG_GET_SINGLE_PLAYLIST, "Playlist doesn't exist. Null returned."); }
            return null;
        }

        return playlist;
    }
    //endregion

    //region: CREATE PLAYLIST WITH NAME
    protected static boolean createPlaylistWithName(Context currentContext,
                                                    String playlistName,
                                                    boolean verbose) {
        File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

        if (!playlistName.contains(".txt")) {
            playlistName = playlistName + ".txt";
        }

        if (!playlistsDir.exists()) {
            if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Library doesn't exist. Creating..."); }

            if (playlistsDir.mkdirs()) {
                if (verbose) {
                    out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Library has been created. Continuing...");
                }
            }
            else {
                if (verbose) {
                    out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Unable to create library. Returning null to signify failure.");
                    return false;
                }
            }
        }

        if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Library has been found..."); }

        File playlistFile = new File(playlistsDir, playlistName);

        if (playlistFile.exists()) {
            if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Playlist has been found with same name, aborting..."); }
            return false;
        }
        else {
            try {
                if (playlistFile.createNewFile()) {
                    if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Playlist has been created."); }
                }
                else {
                    if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Failed to create playlist."); }
                    return false;
                }
            }
            catch (IOException ex) {
                if (verbose) { out(LOG_TAG_MAKE_SINGLE_PLAYLIST, "Failed to create playlist: " + ex.getMessage()); }
                return false;
            }

            return true;
        }
    }
    //endregion

    //region: DELETE PLAYLIST WITH NAME
    protected static boolean deletePlaylistWithName(Context currentContext,
                                                    String playlistName,
                                                    boolean verbose) {
        File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

        if (!playlistsDir.exists()) {
            if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Library doesn't exist, abort."); }
            return false;
        }
        else {
            if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Library found, looking for playlist..."); }

            File[] libraryDirectoryListing = playlistsDir.listFiles();

            for (File currentFile : libraryDirectoryListing) {
                String name = currentFile.getAbsolutePath();
                name = name.substring(name.indexOf("Playlists/")).split("/")[1];
                name = name.substring(0, name.indexOf(".txt"));

                if (name.equals(playlistName)) {
                    if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Playlist found. Deleting..."); }

                    if (currentFile.delete()) {
                        if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Playlist deleted"); }
                    }
                    else {
                        if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Failed to delete: " + name); }
                        return false;
                    }
                }
            }
        }

        if (verbose) { out(LOG_TAG_DELETE_SINGLE_PLAYLIST, "Failed to delete playlist."); }
        return false;
    }
    //endregion

    //region: ADD ITEM TO PLAYLIST WITH NAME
    protected static boolean addItemToPlaylistWithName(Context currentContext,
                                                       String playlistName,
                                                       PlaylistItem item,
                                                       boolean verbose) {
        List<PlaylistItem> currentPlaylist = getPlaylistWithName(currentContext, playlistName, verbose);

        if (currentPlaylist == null) {
            if (verbose) { out(LOG_TAG_ADD_ITEM_TO_PL, "Playlist doesn't exist. Aborting..."); }
            return false;
        }
        else {
            StringBuilder builder = new StringBuilder("");
            String separator = System.getProperty("line.separator");

            for (PlaylistItem currentItem : currentPlaylist) {
                builder.append("<id>").append(currentItem.getId()).append("</id>").append(separator)
                       .append("<title>").append(currentItem.getTitle()).append("</title>").append(separator)
                       .append("<author>").append(currentItem.getAuthor()).append("</author>").append(separator)
                       .append("<mediaPath>").append(currentItem.getMediaPath()).append("</mediaPath>").append(separator)
                       .append("<thumbPath>").append(currentItem.getThumbnailPath()).append("</thumbPath>").append(",");
            }

            builder.append("<id>").append(item.getId()).append("</id>").append(separator)
                   .append("<title>").append(item.getTitle()).append("</title>").append(separator)
                   .append("<author>").append(item.getAuthor()).append("</author>").append(separator)
                   .append("<mediaPath>").append(item.getMediaPath()).append("</mediaPath>").append(separator)
                   .append("<thumbPath>").append(item.getThumbnailPath()).append("</thumbPath>");

            File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

            if (!playlistName.contains(".txt")) {
                playlistName = playlistName + ".txt";
            }

            if (!playlistsDir.exists()) {
                if (verbose) { out(LOG_TAG_ADD_ITEM_TO_PL, "Library doesn't exist. Creating..."); }

                if (playlistsDir.mkdirs()) {
                    if (verbose) {
                        out(LOG_TAG_ADD_ITEM_TO_PL, "Library has been created. Returning false for empty library.");
                    }
                }
                else {
                    if (verbose) {
                        out(LOG_TAG_ADD_ITEM_TO_PL, "Unable to create library. Returning false to signify failure.");
                    }
                }

                return false;
            }

            File playlistFile = new File(playlistsDir, playlistName);

            try {
                OutputStream stream = new FileOutputStream(playlistFile);
                OutputStreamWriter writer = new OutputStreamWriter(stream, Charset.forName("utf-8"));
                writer.write(builder.toString());
                writer.flush();
                writer.close();
                return true;
            }
            catch (FileNotFoundException ex) {
                if (verbose) { out(LOG_TAG_ADD_ITEM_TO_PL, "Unable to write to playlist: " + ex.getMessage()); }
                return false;
            }
            catch (IOException ex) {
                if (verbose) { out(LOG_TAG_ADD_ITEM_TO_PL, "Error writing to playlist: " + ex.getMessage()); }
                return false;
            }
        }
    }
    //endregion

    //region: REMOVE ITEM FROM PLAYLIST WITH NAME
    protected static boolean removeItemFromPlaylistWithName(Context currentContext,
                                                       String playlistName,
                                                       PlaylistItem item,
                                                       boolean verbose) {
        List<PlaylistItem> playlist = getPlaylistWithName(currentContext, playlistName, verbose);

        if (playlist == null) {
            if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Unable to load playlist."); }
            return false;
        }

        if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Playlist found. Reading..."); }

        StringBuilder builder = new StringBuilder("");
        String separator = System.getProperty("line.separator");

        for (PlaylistItem currentItem : playlist) {
            if (!currentItem.equals(item)) {
                builder.append("<id>").append(currentItem.getId()).append("</id>").append(separator)
                       .append("<title>").append(currentItem.getTitle()).append("</title>").append(separator)
                       .append("<author>").append(currentItem.getAuthor()).append("</author>").append(separator)
                       .append("<videoPath>").append(currentItem.getMediaPath()).append("</videoPath>").append(separator)
                       .append("<thumbPath>").append(currentItem.getThumbnailPath()).append("</thumbPath>").append(",");
            }
            else {
                if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Remove playlist item successful."); }
            }
        }

        String playlistData = builder.toString();

        if (playlistData.charAt(playlistData.length() - 1) == ',') {
            playlistData = playlistData.substring(0, playlistData.length() - 2);
        }

        File playlistsDir = new File(currentContext.getFilesDir(), "/Playlists/");

        if (!playlistName.contains(".txt")) {
            playlistName = playlistName + ".txt";
        }

        File playlistFile = new File(playlistsDir, playlistName);

        if (!playlistFile.exists()) {
            if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Playlist doesn't exist. Aborting..."); }
            return false;
        }
        else {
            try {
                OutputStream stream = new FileOutputStream(playlistFile);
                OutputStreamWriter writer = new OutputStreamWriter(stream);
                writer.write(builder.toString());
                writer.flush();
                writer.close();
                if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Playlist has been modified."); }
                return true;
            }
            catch (IOException ex) {
                if (verbose) { out(LOG_TAG_REMOVE_ITEM_FROM_PL, "Unable to write to playlist: " + ex.getMessage()); }
                return false;
            }
        }
    }
    //endregion

    private static void out(String tag, String out) {
        Log.i(tag, out);
    }
}
