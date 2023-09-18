import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IndexTest {
    @BeforeEach
    void setUpBeforeClass() throws IOException{
        File exampleFile = new File("junit_example_file_data.txt");
        exampleFile.createNewFile();
        PrintWriter pw = new PrintWriter(exampleFile);
        pw.write("test file contents");
        pw.close();

        File indexFile = new File("index");
        File objectDirectory = new File("objects");
        
        if(indexFile.exists()){
            indexFile.delete();
        }

        if(objectDirectory.exists()){
            deleteDirectory(objectDirectory);
        }
    }

    @AfterEach
    void tearDownAfterClass() throws Exception {
        File indexFile = new File("index");
        File objectDirectory = new File("objects");
        
        if(indexFile.exists()){
            indexFile.delete();
        }

        if(objectDirectory.exists()){
            deleteDirectory(objectDirectory);
        }
    }

    @Test
    @DisplayName("[8] Test if index and objects folder initialized correctly")
    void testInitialize() throws Exception {

        // Run the person's code
        Index ind = new Index();

        // check if the file exists
        File index = new File("index");
        Path indexPath = Path.of("index");
        String contents = Files.readString(indexPath);

        File dir = new File("objects");
        int filesInDir = dir.listFiles().length;

        //checks if the index file  and objects folder exists
        assertTrue(index.exists());
        assertTrue(dir.exists());

        //check if the index file and bjects folder is empty
        assertEquals(0, filesInDir);
        assertEquals("", contents);
    }

    @Test
    void testAddBlob() throws IOException, NoSuchAlgorithmException {
        File exampleFile = new File("junit_example_file_data.txt");
        Path indexPath = Path.of("index");
        Index ind = new Index();

        //run code
        ind.addBlob("junit_example_file_data.txt");

        //check if index was updated
        String correctIndexEntry = "junit_example_file_data.txt : cbaedccfded0c768295aae27c8e5b3a0025ef340";
        String indexContents = Files.readString(indexPath);
        assertTrue(correctIndexEntry.equals(indexContents) || (correctIndexEntry + "\n").equals(indexContents));

        //attempt to add the same item again
        ind.addBlob("junit_example_file_data.txt");
        String newIndexContents = Files.readString(indexPath);
        assertTrue(correctIndexEntry.equals(indexContents) || (correctIndexEntry + "\n").equals(indexContents));
    }

    @Test
    void testRemoveBlob() {

    }

    public static void deleteDirectory(File file)
    {
        // store all the paths of files and folders present
        // inside directory
        for (File subfile : file.listFiles()) {
 
            // if it is a subfolder,e.g Rohan and Ritik,
            //  recursively call function to empty subfolder
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
 
            // delete files and empty subfolders
            subfile.delete();
        }
    }
}