package com.tomasdev.akhanta.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Setter
@Getter
@NoArgsConstructor
public class StringUtils {

    private static String randomLetterSequence() {
        Random rand = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String sequence = "";

        for (int i = 0; i < 3; i++) {
            boolean rndBool = rand.nextBoolean();
            int rndNum = rand.nextInt(letters.length());
            String letter = Character.toString(letters.charAt(rndNum));
            sequence += STR."\{rndBool ? letter.toLowerCase() : letter}";
        }
        return sequence;
    }

    public static String uniqueSequence(){
        String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sequence = "";

        for (int i = 0; i < date.length(); i++) {
            sequence += STR."\{Character.toString(date.charAt(i))}\{randomLetterSequence()}";
        }
        return sequence;
    }

    public static String normalizeToSearch(String text) {
        return text.toLowerCase().replace(" ", "-");
    }
}
