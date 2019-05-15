package com.example.crosswordchallenge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO: make all the functions private

public class Grid {
    private int height;
    private int width;
    private char[][] grid;
    private String[] words = Words.words;
    public static final int ASCII_VAL = 65;

    public Grid(int height, int width){
        this.height = height;
        this.width = width;
        grid = new char[width][height];
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                grid[i][j] = ' ';
            }
        }
        adjustWords();
    }

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public char[] getListGrid(){
        char[] listGrid = new char[height*width];
        int count = 0;
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                listGrid[count] = grid[i][j];
                count++;
            }
        }
        return listGrid;
    }

    public char[][] getGrid(){
        return grid;
    }

    public int getRandomIndex(int size){
        Random random = new Random();
        return random.nextInt(size);
    }

    public void adjustWords(){
        List<Possibility> possibilities;
        int index;
        for(int i=0; i<words.length; i++){
            possibilities = getWordPossibility(words[i]);
            index = getRandomIndex(possibilities.size());
            placeWord(words[i], possibilities.get(index));
        }
        fillOtherSpaces();
    }

    public void placeWord(String word, Possibility possibility){
        int col = possibility.getCol();
        int row = possibility.getRow();

        switch(possibility.getDirection()){
            case LEFT:
                for (int i=0; i<word.length(); i++){
                    grid[row][col-i] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case RIGHT:
                for (int i=0; i<word.length(); i++){
                    grid[row][col+i] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case UP:
                for (int i=0; i<word.length(); i++){
                    grid[row-i][col] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case DOWN:
                for (int i=0; i<word.length(); i++){
                    grid[row+i][col] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case UP_RIGHT_DIAGONAL:
                for (int i=0; i<word.length(); i++){
                    grid[row-i][col+i] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case UP_LEFT_DIAGONAL:
                for (int i=0; i<word.length(); i++){
                    grid[row-i][col-i] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case DOWN_LEFT_DIAGONAL:
                for (int i=0; i<word.length(); i++){
                    grid[row+i][col-i] = Character.toUpperCase(word.charAt(i));
                }
                break;

            case DOWN_RIGHT_DIAGONAL:
                for (int i=0; i<word.length(); i++){
                    grid[row+i][col+i] = Character.toUpperCase(word.charAt(i));
                }
                break;
        }
    }

    // TODO: make the words overlap
    public List<Possibility> getWordPossibility(String word){
        List<Possibility> possibilityList = new ArrayList<Possibility>();
        for (int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (isDirectionPossible(word, i, j, Direction.UP)){
                    possibilityList.add(new Possibility(Direction.UP, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.UP_LEFT_DIAGONAL)){
                    possibilityList.add(new Possibility(Direction.UP_LEFT_DIAGONAL, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.UP_RIGHT_DIAGONAL)){
                    possibilityList.add(new Possibility(Direction.UP_RIGHT_DIAGONAL, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.DOWN)){
                    possibilityList.add(new Possibility(Direction.DOWN, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.DOWN_LEFT_DIAGONAL)){
                    possibilityList.add(new Possibility(Direction.DOWN_LEFT_DIAGONAL, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.DOWN_RIGHT_DIAGONAL)){
                    possibilityList.add(new Possibility(Direction.DOWN_RIGHT_DIAGONAL, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.LEFT)){
                    possibilityList.add(new Possibility(Direction.LEFT, i, j));
                }
                if (isDirectionPossible(word, i, j, Direction.RIGHT)){
                    possibilityList.add(new Possibility(Direction.RIGHT, i, j));
                }
            }
        }
        return possibilityList;
    }


    public boolean isDirectionPossible(String word, int row, int col, Direction direction) {
        int count = 0;
        switch (direction) {
            case LEFT:
                if (word.length() > col+1){
                    return false;
                }
                for (int i=0; i<word.length(); i++){
                    if(grid[row][col-i] != ' '){
                        return false;
                    }
                }
                return true;

            case RIGHT:
                if (word.length()+col > width){
                    return false;
                }
                for (int i=0; i<word.length(); i++){
                    if(grid[row][col+i] != ' '){
                        return false;
                    }
                }
                return true;

            case UP:
                if (word.length() > row+1){
                    return false;
                }
                for (int i=0; i<word.length(); i++){
                    if(grid[row-i][col] != ' '){
                        return false;
                    }
                }
                return true;

            case DOWN:
                if (word.length()+row > height){
                    return false;
                }
                for (int i=0; i<word.length(); i++){
                    if(grid[row+i][col] != ' '){
                        return false;
                    }
                }
                return true;

            case UP_RIGHT_DIAGONAL:
                count = 0;
                while(row>=0 && col<width && count<word.length()){
                    if (grid[row][col] != ' '){
                        return false;
                    }
                    row--;
                    col++;
                    count++;
                }
                if (count == word.length()){
                    return true;
                }
                return false;

            case UP_LEFT_DIAGONAL:
                count = 0;
                while(row>=0 && col>=0 && count<word.length()){
                    if (grid[row][col] != ' '){
                        return false;
                    }
                    row--;
                    col--;
                    count++;
                }
                if (count == word.length()){
                    return true;
                }
                return false;

            case DOWN_LEFT_DIAGONAL:
                count = 0;
                while(row<height && col>=0 && count<word.length()){
                    if (grid[row][col] != ' '){
                        return false;
                    }
                    row++;
                    col--;
                    count++;
                }
                if (count == word.length()){
                    return true;
                }
                return false;

            case DOWN_RIGHT_DIAGONAL:
                count = 0;
                while(row<height && col<width && count<word.length()){
                    if (grid[row][col] != ' '){
                        return false;
                    }
                    row++;
                    col++;
                    count++;
                }
                if (count == word.length()){
                    return true;
                }
                return false;

            default:
                return false;
        }
    }

    public void fillOtherSpaces(){
        char curr = ' ';
        for(int i=0; i<width; i++){
            for (int j=0; j<height; j++){
                if (grid[i][j] == ' '){
                    int index = getRandomIndex(26);
                    curr = (char)(index + ASCII_VAL);
                    grid[i][j] = curr;
                }
            }
        }
    }
}
