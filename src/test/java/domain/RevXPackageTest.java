package domain;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RevXPackageTest {

    @Test
    public void should_return_its_parent(){

        RevXPackage xPackage = new RevXPackage("c:/foo/boo");

        String parentAsString = xPackage.getParentPath();

        assertEquals("c:/foo", parentAsString);
    }

}