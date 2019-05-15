package com.example.crosswordchallenge;

import java.util.ArrayList;
import java.util.List;

public class Words {
    public static String words[] = {"OBJECTIVEC", "VARIABLE", "ANDROID", "KOTLIN", "MOBILE", "SWIFT", "JAVA", "XML"};
    public static List<String> foundWords = new ArrayList<String>();

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

    public static int getWordIndex(String word){
        for (int i=0; i<words.length; i++){
            if (words[i].equals(word)){
                return i;
            }
        }
        return -1;
    }

}
