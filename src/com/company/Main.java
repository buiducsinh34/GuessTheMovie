package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static String[] getMoviesArray(String filename){
        File file = new File(filename);
        int moviesCount = 0;
        String [] moviesArray = new String[0];
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                moviesCount += 1;
                scanner.nextLine();
            }
            scanner.close();
            scanner = new Scanner(file);
            int moviePosition = 0;
            moviesArray = new String[moviesCount];
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                line = line.replaceAll("[^a-zA-Z0-9]","");
                moviesArray[moviePosition] = line;
                moviePosition += 1;
            }
        } catch (FileNotFoundException exception){
            System.out.println("Cannot find that file");
        }
        return moviesArray;
    }

    public static boolean checkNewGame(){
        System.out.println("Do you want to start a new game?(y/n)");
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().toLowerCase().equals("y")){
            return true;
        } else{
            return false;
        }
    }

    public static String getMovie(String[] moviesArray){
        int moviesCount = moviesArray.length;
        Random rand = new Random();
        int movieIndex = rand.nextInt(moviesCount);
        return moviesArray[movieIndex];
    }

    public static String startGame(String movie){
        int len = movie.length();
        String board = "";
        for (int i=0; i<len; i++){
            board += "_ ";
        }
        System.out.println("Here's the movie: \n" + board);
        return board;
    }

    public static String updateBoard(String board, String movie){
        Scanner scanner = new Scanner(System.in);
        int len = movie.length();
        String letter = scanner.nextLine();
        for (int i = 0; (i = movie.indexOf(letter, i)) != -1; i++) {
            if (i==0){
                board = letter+board.substring(2*i+1);
            }else if (i==len){
                board = board.substring(0, 2*i)+letter;
            }else{
                board = board.substring(0,2*i)+letter+board.substring(2*i+1);
            }

        }
        return board;
    }

    public static boolean boardCheck(String board){
        if (board.contains("_")){
            return false;
        }else{
            return true;
        }
    }

    public static void winCheck(String movie){
        System.out.println("So, what is the movie's name: ");
        Scanner scanner = new Scanner(System.in);
        String guess = scanner.nextLine();
        if (movie.toLowerCase().equals(guess.toLowerCase())){
            System.out.println("Congratulations, you won!!! :D");
        }else{
            System.out.println("Sorry, you lost :(");
        }
    }

    public static void main(String[] args){
        String[] moviesArray = getMoviesArray("movies.txt");
        String movie;
        while(checkNewGame()){
            movie = getMovie(moviesArray);
            String board = startGame(movie);
            for (int i=10; i>0; i--) {
                System.out.println("You have " + i + " times to guess. Please guess a letter: " + movie);
                board = updateBoard(board, movie);
                System.out.println(board);
                if (boardCheck(board)){
                    System.out.println("Congratulations, you won!!! :D");
                    break;
                }
            }
            if (!boardCheck(board)){
                winCheck(movie);
            }
        }
    }
}
