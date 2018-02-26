package Checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

public class Checksum {

    // this is the object that stores the
    private CRC32 crc32;

    // Creates a checksum from a byte array
    public Checksum(byte[] fileStream) {
        crc32 = new CRC32();
        updateChecksum(fileStream);
    }

    // Creates a checksum from a given file after first converting the file to a byte array
    public Checksum(File file) throws IOException{
        crc32 = new CRC32();
        setChecksum(file);
    }

    // sets the value of the checksum based on a given byte array
    public void updateChecksum(byte[] fileStream) {
        crc32.update(fileStream);
    }

    // sets the value of the checksum based on the contents of a given file
    public void setChecksum(File file) throws IOException{

        final int BUFFER_SIZE = 1024*1024;

        // used to read in large file
        FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
        byte[] buffer = new byte[BUFFER_SIZE];
        // reads in file from buffer and generates the checksum from it
        int read = 0;
        while( ( read = fis.read( buffer ) ) > 0 ){
            crc32.update(buffer);
        }

        fis.close();
    }

    public boolean Equals(Checksum checksum) {
        return Equals(checksum.getCRC32());
    }

    public boolean Equals(CRC32 checksum) {
        return crc32.getValue() == checksum.getValue();
    }

    public CRC32 getCRC32() {
        return crc32;
    }

    public long getCheckSum() {return crc32.getValue();}
}