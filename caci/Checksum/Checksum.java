package Checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;

public class Checksum {
    private CRC32 crc32;

    // Creates a checksum from a byte array
    public Checksum(byte[] fileStream) {
        setChecksum(fileStream);
    }

    // Creates a caci.Checksum from a given file after first converting the file to a byte array
    public Checksum(File file) throws IOException{
        setChecksum(file);
    }

    // sets the value of the checksum based on a given byte array
    public void setChecksum(byte[] fileStream) {
        crc32 = new CRC32();
        crc32.update(fileStream);
    }

    // sets the value of the checksum based on the contents of a given file
    public void setChecksum(File file) throws IOException{
        byte[] fileContents = convertFileToByteArray(file);
        setChecksum(fileContents);
    }

    public boolean Equals(byte[] fileStream) {
        CRC32 otherSum = new CRC32();
        otherSum.update(fileStream);
        return Equals(otherSum);
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

    // Converts the given file into an array of bytes
    private byte[] convertFileToByteArray(File file) throws IOException {
        // input stream for reading in the file's contents
        FileInputStream fileInputStream = null;
        try
        {
            // initializes the file stream with file
            fileInputStream = new FileInputStream(file);

            // instantiates byte array of necessary size for file
            byte[] fileContent = new byte[(int)file.length()];

            // reads the file into fileContent
            fileInputStream.read();

            // Sets the value of the check sum
            return fileContent;

        } catch(FileNotFoundException f) {
            throw new FileNotFoundException("File not found.");
        } catch(IOException i) {
            throw new IOException("Failed to read file.");
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException i) {
                throw new IOException("Failed to close file stream.");
            }
        }
    }
}