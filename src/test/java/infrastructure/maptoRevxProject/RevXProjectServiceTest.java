package infrastructure.maptoRevxProject;

import domain.ProjectRoot;
import domain.RevXProject;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RevXProjectServiceTest {

    @Test
    public void create_project_using_resources_path(){

        URL url = getClass().getClassLoader().getResource("learning/directoryTest");

        ProjectRoot root = new ProjectRoot(url.getPath().substring(1));

        RevXProject revXProject = new RevXProjectService()
                                        .create("TestProject", root);

    }

    @Test
    public void testing_extension(){

        Path p = Path.of("a", "b", "c.txt");

        Optional<String> s = RevXProjectService.getExtensionByStringHandling(p);

        assertEquals("txt", s.get());
    }
}