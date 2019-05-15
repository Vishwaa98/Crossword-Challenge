package com.example.crosswordchallenge;

import java.util.ArrayList;
import java.util.List;

public class Words {
    public static String words[] = {"OBJECTIVEC", "VARIABLE", "ANDROID", "KOTLIN", "MOBILE", "SWIFT", "JAVA", "XML"};
    public static List<String> foundWords = new ArrayList<String>();
    public static boolean[][] foundChars = new boolean[10][10];

    public static boolean checkIfValidWord(String word){
        for (int i=0; i<words.length; i++){
            if(words[i].equals(word)){
                return true;
            }
        }
        return false;
    }

    public static void addWordToFoundList(String word){
        foundWords.add(word);
    }

    public static void setFoundChars(boolean[][] selectedChars){
        for (int i=0; i<foundChars.length; i++){
            for (int j=0; j<foundChars[0].length; j++){
                if (selectedChars[i][j]){
                    selectedChars[i][j] = false;
                    foundChars[i][j] = true;
                }
            }
        }
    }

}
