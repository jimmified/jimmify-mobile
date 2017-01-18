package jimmified.jimmify.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchItemModel {

    @SerializedName("kind")
    @Expose(serialize = false, deserialize = true)
    private String kind;

    public String getKind() {
        return this.kind;
    }

    @SerializedName("title")
    @Expose(serialize = false, deserialize = true)
    private String title;

    public String getTitle() {
        return this.title;
    }

    @SerializedName("htmlTitle")
    @Expose(serialize = false, deserialize = true)
    private String htmlTitle;

    public String getHtmlTitle() {
        return this.htmlTitle;
    }

    @SerializedName("link")
    @Expose(serialize = false, deserialize = true)
    private String link;

    public String getLink() {
        return this.link;
    }

    @SerializedName("displayLink")
    @Expose(serialize = false, deserialize = true)
    private String displayLink;

    public String getDisplayLink() {
        return this.displayLink;
    }

    @SerializedName("snippet")
    @Expose(serialize = false, deserialize = true)
    private String snippet;

    public String getSnippet() {
        return this.snippet;
    }

    @SerializedName("htmlSnippet")
    @Expose(serialize = false, deserialize = true)
    private String htmlSnippet;

    public String getHtmlSnippet() {
        return this.htmlSnippet;
    }

    @SerializedName("cacheId")
    @Expose(serialize = false, deserialize = true)
    private String cacheID;

    public String getCacheID() {
        return this.cacheID;
    }

    @SerializedName("formattedUrl")
    @Expose(serialize = false, deserialize = true)
    private String formattedURL;

    public String getFormattedURL() {
        return this.formattedURL;
    }

    @SerializedName("htmlFormattedUrl")
    @Expose(serialize = false, deserialize = true)
    private String htmlFormattedURL;

    public String getHtmlFormattedURL() {
        return this.htmlFormattedURL;
    }

    public SearchItemModel() {}

    public SearchItemModel(String kind,
                           String title,
                           String htmlTitle,
                           String link,
                           String displayLink,
                           String snippet,
                           String htmlSnippet,
                           String cacheID,
                           String formattedURL,
                           String htmlFormattedURL) {
        this.kind = kind;
        this.title = title;
        this.htmlTitle = htmlTitle;
        this.link = link;
        this.displayLink = displayLink;
        this.snippet = snippet;
        this.htmlSnippet = htmlSnippet;
        this.cacheID = cacheID;
        this.formattedURL = formattedURL;
        this.htmlFormattedURL = htmlFormattedURL;
    }

}
