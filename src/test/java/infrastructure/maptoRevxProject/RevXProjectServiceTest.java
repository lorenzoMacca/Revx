package infrastructure.maptoRevxProject;

import domain.ProjectRoot;
import domain.RevXProject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RevXProjectServiceTest {

    @Test
    public void create_project_using_existing_path(){

        RevXProject revXProject = new RevXProjectService()
                                        .create("TestProject",
                                                new ProjectRoot("C:/Project/revX/src/test/resources/learning/directoryTest"));


    }

}