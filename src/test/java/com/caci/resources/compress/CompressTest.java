package test.java.com.caci.resources.compress;

import org.junit.jupiter.api.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import main.java.com.caci.resources.compress.Compress;

public class CompressTest {

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
    public void CreateCompressedByteArray() {
    	File inputFile = new File(bigInputPath);
    	
    	Compress compress = new Compress();
    	byte[] c;
		compress.CompressFile(inputFile.toPath());
    	
    	//System.out.println(testInput.getBytes().length + " " + c.length);
    	//Assertions.assertNotEquals(testInput.getBytes().length, c.length);
    	

    }

    
}
