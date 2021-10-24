package infrastructure.maptoRevxProject;

import domain.entity.ProjectRoot;
import domain.entity.RevXProject;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RevXLoadExternalSourceCodeServiceTest {

    @Test
    public void create_project_using_resources_path(){

        var url = getClass().getClassLoader().getResource("learning/directoryTest");

        var root = new ProjectRoot(url.getPath().substring(1));

        var revXProject = new RevXLoadExternalSourceCodeService()
                                        .createFromExternalSourceCode("TestProject", root);

    }

    @Test
    public void testing_extension(){

        Path p = Path.of("a", "b", "c.txt");

        Optional<String> s = RevXLoadExternalSourceCodeService.getExtensionByStringHandling(p);

        assertEquals("txt", s.get());
    }
}