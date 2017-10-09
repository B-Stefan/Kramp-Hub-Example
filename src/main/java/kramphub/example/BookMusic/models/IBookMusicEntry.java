package kramphub.example.BookMusic.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=IBookMusicEntry.class)
public interface IBookMusicEntry {

    public String getName();
    public String getType();
}
