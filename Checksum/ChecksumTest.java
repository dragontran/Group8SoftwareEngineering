package Checksum;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;

public class ChecksumTest {

    private final String bigInputPath = "./bigInput.txt";
    private final String massiveFilePath = "C:/HonorsResearch/CNXA2C_R1.csv";

    private final String testInput = "This is my rifle. There are many like it, but this one is mine.\n" +
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
        Checksum testSum2 = new Checksum(testInput.getBytes());
        Assertions.assertEquals(testSum1.getCheckSum(), testSum2.getCheckSum());

        String newInput = multiplyString(testInput, 2);
        testSum2 = new Checksum(newInput.getBytes());
        Assertions.assertNotEquals(testSum1.getCheckSum(), testSum2.getCheckSum());

        testSum1 = new Checksum(newInput.getBytes());
        Assertions.assertEquals(testSum1.getCheckSum(), testSum2.getCheckSum());

        testSum1 = new Checksum(newInput.replaceAll("rifle","life").getBytes());
        Assertions.assertNotEquals(testSum1.getCheckSum(), testSum2.getCheckSum());

    }

    @Test
    public void CreateChecksumForEmptyFile() throws IOException {
        try {
            Checksum testSum = new Checksum("".getBytes());
            Assertions.assertEquals(true,true);
        } catch(Exception e) {
            Assertions.assertEquals(true,false);
        }
    }

    @Test
    public void CreateChecksumForLargeStrings() throws IOException {
        // Read in 500 MB file
        File inputFile = new File(bigInputPath);
        try {
            Checksum testSum1 = new Checksum(inputFile);
            Checksum testSum2 = new Checksum(inputFile);
            Checksum testSum3 = new Checksum(inputFile);
            Checksum testSum4 = new Checksum(inputFile);
            Checksum testSum5 = new Checksum(inputFile);
            Checksum testSum6 = new Checksum(inputFile);
            Checksum testSum7 = new Checksum(inputFile);
            Checksum testSum8 = new Checksum(inputFile);
            Checksum testSum9 = new Checksum(inputFile);
            Checksum testSum10 = new Checksum(inputFile);
            Assertions.assertEquals(true, testSum1.Equals(testSum2));
            Assertions.assertEquals(true, testSum2.Equals(testSum3));
            Assertions.assertEquals(true, testSum3.Equals(testSum4));
            Assertions.assertEquals(true, testSum4.Equals(testSum5));
            Assertions.assertEquals(true, testSum5.Equals(testSum6));
            Assertions.assertEquals(true, testSum6.Equals(testSum7));
            Assertions.assertEquals(true, testSum7.Equals(testSum8));
            Assertions.assertEquals(true, testSum8.Equals(testSum9));
            Assertions.assertEquals(true, testSum9.Equals(testSum10));
            Assertions.assertEquals(true, testSum10.Equals(testSum1));
        } catch (Exception e) {
            Assertions.assertEquals(true,false);
        }
    }

    @Test
    public void CreateChecksumForGargantuanString() throws IOException {
        // Read in very large file
        File massiveFile = new File(massiveFilePath);
        try {
            Checksum testSum = new Checksum(massiveFile);
            Assertions.assertEquals(true,true);
        } catch (Exception e) {
            Assertions.assertEquals(true,false);
        }
    }

    private String multiplyString(String s, int multiplier) {
        if (multiplier > 1) {
            double midPoint = multiplier / 2.0;
            String firstHalf = multiplyString(s,(int)Math.floor(midPoint));
            String secondHalf = multiplyString(s, (int)Math.ceil(midPoint));
            return firstHalf.concat(secondHalf);
        } else {
            return s;
        }
    }
}
