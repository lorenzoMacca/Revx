package domain;

import domain.entity.RevXPath;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class RevXPathTest {

    @Test
    public void return_file_name(){

        RevXPath xPath = RevXPath.of(Paths.get("a", "b", "c.java"));

        String filename = xPath.getFileName();

        assertEquals("c.java", filename);
    }
}