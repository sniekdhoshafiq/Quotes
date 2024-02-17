package com.sniekdho.jsonwithvolley;

import java.io.Serializable;

public class Quotes implements Serializable {

    String quote;
    String author;

    public Quotes(String quote, String author) {
        this.quote = quote;
        this.author = author;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
