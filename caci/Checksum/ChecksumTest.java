package caci.Checksum;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ChecksumTest {

    private String bigInputPath = "./bigInput.txt";

    private String testInput = "This is my rifle. There are many like it, but this one is mine.\n" +
            "My rifle is my best friend. It is my life. I must master it as I must master my life.\n" +
            "Without me, my rifle is useless. Without my rifle, I am useless. I must fire my rifle true. " +
            "I must shoot straighter than my enemy who is trying to kill me. " +
            "I must shoot him before he shoots me. I will…\n" +
            "My rifle and I know that what counts in war is not the rounds we fire, " +
            "the noise of our burst, nor the smoke we make. We know that it is the hits that count. We will hit…\n" +
            "My rifle is human, even as I, because it is my life. Thus, I will learn it as a brother. " +
            "I will learn its weaknesses, its strength, its parts, its accessories, its sights and its barrel. " +
            "I will keep my rifle clean and ready, even as I am clean and ready. " +
            "We will become part of each other. We will…\n" +
            "Before God, I swear this creed. My rifle and I are the defenders of my country. " +
            "We are the masters of our enemy. We are the saviors of my life.\n" +
            "So be it, until victory is America's and there is no enemy, but peace!";

    @Test
    public void CompareTwoStrings() {
        // Creates a check sum from the testInput
        Checksum testSum1 = new Checksum(testInput.getBytes());
        System.out.printf(testInput.getBytes().length + "\n");
        Checksum testSum2 = new Checksum(testInput.getBytes());
    }

    @Test
    public void CreateChecksumForLargeString() throws IOException {
        // Read in 500 MB string
        String bigInput = new Scanner(new File(bigInputPath)).useDelimiter("\\Z").next();
        System.out.printf(bigInput.getBytes().length + "\n");
    }

}