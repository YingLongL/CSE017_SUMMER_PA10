public class Movie implements Comparable<Movie> {
    private int id;
    private String title;
    private String genre;
    private int ratings;
    private double rating;

    public Movie(int id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.ratings = 0;
        this.rating = 0.0;
    }

    public int getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getRatings() {
        return ratings;
    }

    public double getRating() {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void addRating(double r) {
        rating = ((rating * ratings) + r) / (ratings + 1);
        ratings++;
    }

    @Override
    public int compareTo(Movie m) {
        return Integer.compare(this.ratings, m.ratings);
    }

    @Override
    public String toString() {
        return id + ", " + title + ", " + genre + ", " + ratings + ", " + rating;
    }
}
