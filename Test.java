import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Test{
    public static void main(String[] args){
        String path_movies = "/home/houdghiri/CSE017/movies.csv";
        String path_rtings = "/home/houdghiri/CSE017/ratings.csv"; //25 mil ratings!
        if( args.length == 2){
            path_movies = args[0];
            path_rtings = args[1];
        }else{ // Summer 2024
            System.out.println( "No args found. You may specify the path to the movies.csv and ratings.csv as arguments, in that order." );
            path_movies = "movies.csv";
            path_rtings = "ratings_250k.csv";
        }
        System.out.printf( "Using paths:%nMovies: %s%nRatings: %s%n", path_movies, path_rtings );
        ArrayList<MapEntry<Integer, Movie>> movieList = new ArrayList<>(70000);
        // Creating the hashmap
        HashMapLP<Integer, Movie> moviesHMLP = new HashMapLP<>(70000, 0.5);
        HashMapSC<Integer, Movie> moviesHMSC = new HashMapSC<>(70000, 0.9);

        // Populating the array list movieList with the data from the files
        readMovies(moviesHMLP,moviesHMSC, path_movies);
        System.out.println(moviesHMLP.size() + " movies read from the file");
        readRatings(moviesHMLP, path_rtings);
        
        // print the characetristics of the two implementations of the hashmap
        System.out.println("\nHash table characteristics (Separate Chaining)");
        moviesHMSC.printCharacteristics();

        System.out.println("\nHash table characteristics (Linear Probing)");
        moviesHMLP.printCharacteristics();

        // Test the performance of the get methods in the two implementations of the hashmap
        int ids[] = {1544, 2156, 31349, 3048, 4001, 356, 5672, 6287, 25738, 26};
        testGet(moviesHMLP, moviesHMSC, ids);

        // Test the performance of the remove methods in the two implementations of the hashmap
        testRemove(moviesHMLP, moviesHMSC, ids);


        // print the characetristics of the two implementations of the hashmap after adding more movies
        System.out.println("\nHash table characteristics (Separate Chaining)");
        moviesHMSC.printCharacteristics();

        System.out.println("\nHash table characteristics (Linear Probing)");
        moviesHMLP.printCharacteristics();
       
        // sort the movies
        System.out.println("\nSorting the movies from the hashtable with separate chaining");
        mergeSortMovies(moviesHMSC);
        System.out.println("\nSorting the movies from the hashtable with linear probing");
        mergeSortMovies(moviesHMLP);
        
        
    }
    /**
     * read the list of movies from filename and adds the pairs (movieid, movie) to the two hash maps
     * 
     * Note 1: if your read.hasNextLine() is terminating before reading the whole file, then you should
     * attempt to specify the character encoding when opening the file for reading. 
     * For example: new Scanner(new File(filename), "UTF-8" )
     * 
     * Note 2: you are NOT expected to correctly parse a movie title that appears within quotes
     * (which may then may optionally contain commas between those quotes)
     * It is most important to make the correct number of movie objects as shown in the sample output.
     * @param hm1 the first hash table
     * @param hm2 the second hash table
     * @param filename the name of the file to read
     */
    public static void readMovies(HashMap<Integer, Movie> hm1, HashMap<Integer, Movie> hm2, String filename){
     
    }

    /**
     * read the ratings of the movies from filename and update the number of ratings and the average rating of the movies in hm
     * @param hm the hash table of movies to be updated
     * @param filename the name of the file with the movie ratings
     */
    public static void readRatings(HashMap<Integer, Movie> hm, String filename){
        
    }

    /**
     * calls the get method of a list of movie ids and displays the number of iterations for each hash tabel
     * @param hm1 the first hashtable
     * @param hm2 the second hashtable
     * @param ids an array of movie ids to lookup
     */
    public static void testGet(HashMap<Integer, Movie> hm1, HashMap<Integer, Movie> hm2, int[] ids){
        System.out.println("\nResults of the search operation in the two hashmaps");
        System.out.printf("%-5s\t%-50s\t%-20s\t%-20s\n", 
                          "Id", "Title","Iterations(SC:get)", "Iterations(LP:get)");
        for(int id: ids){
            Movie m = hm1.get(id);
            hm2.get(id);
            if(m == null){
                System.out.println("Movie id not found.");
            }
            else{
                System.out.printf("%-5d\t%-50s\t%-20d\t%-20d\n", 
                                  id, m.getTitle(),HashMapSC.getIterations, HashMapLP.getIterations);
            }
        }
    }
    /**
     * calls the remove method of a list of movie ids and displays the number of iterations for each hashtable
     * @param hm1 the first hashtable
     * @param hm2 the second hashtable
     * @param ids an array of movie ids to remove
     */
    public static void testRemove(HashMap<Integer, Movie> hm1, HashMap<Integer, Movie> hm2, int[] ids){
        System.out.println("\nResults of the remove operation in the two hashmaps");
        System.out.printf("%-5s\t%-50s\t%-20s\t%-20s\n", 
                          "Id", "Title","Iterations(SC:remove)", "Iterations(LP:remove)");
        for(int id: ids){
            Movie m = hm1.get(id);
            if( m != null ){
                hm1.remove(id);
                hm2.remove(id);
                System.out.printf("%-5d\t%-50s\t%-20d\t%-20d\n", 
                                id, m.getTitle(), HashMapSC.removeIterations, HashMapLP.removeIterations);
            }else{
                System.err.println( "Did not find movie with id " + id );
            }
        }
    }
    /**
     * sorts the list of movies by ratings first, then select the movies with more than 10,000 ratings and 
     * sort them by the average rating
     * The method uses the merge sort algorithm for the two sortings
     * displays the bottom/top ten rated movies in the hashmap
     */
    public static void mergeSortMovies(HashMap<Integer, Movie> movies){
        
        
    }

}