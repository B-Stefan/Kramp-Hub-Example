package kramphub.example.BookMusic.models;


import kramphub.example.BookMusic.models.IBookMusicEntry;

import java.io.Serializable;

public class Album implements IBookMusicEntry, Serializable {

    private String artistName;

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public String getName() {
        return this.getArtistName();
    }

    @Override
    public String getType() {
        return Album.class.getSimpleName();
    }
}
