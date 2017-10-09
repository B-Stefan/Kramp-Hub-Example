package kramphub.example.BookMusic.models;


import kramphub.example.BookMusic.models.IBookMusicEntry;

public class Book implements IBookMusicEntry {


    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getType() {
        return Book.class.getSimpleName();
    }
}
