package kramphub.example.bookmusic.models;


import java.io.Serializable;
import java.util.Arrays;

public class Book implements IBookMusicEntry, Serializable  {

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public class VolumeInfo {
        private String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String[] getAuthors() {
            return authors;
        }

        public void setAuthors(String[] authors) {
            this.authors = authors;
        }

        private String[] authors;
    }

    private VolumeInfo volumeInfo = null;

    @Override
    public String getName() {
        assert this.volumeInfo != null;
        return String.join(",", this.volumeInfo.authors);

    }

    @Override
    public String getType() {
        return Book.class.getSimpleName();
    }
}
