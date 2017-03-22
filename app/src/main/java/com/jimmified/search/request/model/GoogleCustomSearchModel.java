package com.jimmified.search.request.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GoogleCustomSearchModel {

    @SerializedName("items")
    @Expose(serialize = false)
    private SearchItemModel[] items;

    public GoogleCustomSearchModel(SearchItemModel[] items) {
        this.items = items;
    }

    public String[] getItemSnippets() {
        ArrayList<String> snippets = new ArrayList<>();

        for (SearchItemModel item : this.items)
            snippets.add(item.getSnippet());

        return snippets.toArray(new String[snippets.size()]);
    }

}
