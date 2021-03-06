package nl.ekholabs.nlp.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Asset {

  private String id;

  private String title;

  private Subtitles subtitles;

  Asset() {
  }

  public Asset(final String id, final String title, final Subtitles subtitles) {
    this.id = id;
    this.title = title;
    this.subtitles = subtitles;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Asset asset = (Asset) o;

    if (id != null ? !id.equals(asset.id) : asset.id != null) {
      return false;
    }
    if (title != null ? !title.equals(asset.title) : asset.title != null) {
      return false;
    }
    return subtitles != null ? subtitles.equals(asset.subtitles) : asset.subtitles == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (subtitles != null ? subtitles.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Asset{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        '}';
  }
}