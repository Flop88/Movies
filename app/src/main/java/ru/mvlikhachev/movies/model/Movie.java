package ru.mvlikhachev.movies.model;

public class Movie {

    private String title;
    private String posterUrl;
    private String year;

    public Movie() {
    }

    public Movie(String title, String posterUrl, String year) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
