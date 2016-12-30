# PlaylistUtils
A simple set of Classes to create and manage playlists on Android.

###Placing into your project
1. Download or clone the git repo.
2. In Android Studio, move or copy the 'mx' folder into the root of your 'java' folder.

###Creating a playlist called "TestPlaylist" on Device

```java
PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
// utils.setUseConsoleOutput(true);
boolean created = utils.createPlaylist("TestPlaylist");

...
```

###Adding a PlaylistItem into "TestPlaylist"

```java
PlaylistItem item = new PlaylistItem();
item.setId("<enter an id here>");
item.setTitle("<enter a title>");
item.setAuthor("<enter an author>");
item.setMediaPath("<enter file path for media>");
item.setThumbnailPath("<enter thumbnail file path>");

PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
utils.addToPlaylist("TestPlaylist", item);
```

###Removing a PlaylistItem from "TestPlaylist"

```java
PlaylistItem item = ...

PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
boolean removed = utils.removeFromPlaylist("TestPlaylist", item);

...
```

###Getting an existing playlist called "TestPlaylist"

```java
PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
List<PlaylistItem> playlist = utils.getPlaylist("TestPlaylist");
```

###Deleting an existing playlist called "TestPlaylist"

```java
PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
boolean removed = utils.removePlaylist("TestPlaylist");

...
```

###Getting all playlists in a Map

```java
PlaylistUtils utils = new PlaylistUtils(getApplicationContext());
Map<String, List<PlaylistItem>> playlists = utils.getAllPlaylists();
```

###Things that I'm working on
1. Creating a method for the manager to set an entire list into a playlist. Mainly for reordering items.
