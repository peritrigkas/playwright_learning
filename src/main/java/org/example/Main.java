package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        String str = "Hello how are you";

        String[] splitterStr = str.split(" ");
        System.out.println(Arrays.toString(splitterStr));
        
        for (String word : splitterStr) {
            System.out.println(word);
        }
        System.out.println("**************----");
        for(int i=splitterStr.length-1;i>=0;i--)
        {

            System.out.println(splitterStr[i]);
        }
        System.out.println("**************---");
        List<String> words = new ArrayList<>(List.of(str.split(" ")));

        for (String word : words) {
            System.out.println(word);
        }
        System.out.println("**************");
        System.out.println(words);


    }
}