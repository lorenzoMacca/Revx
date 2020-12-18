package infrastructure.maptoRevxProject;

import application.IRevXProjectService;
import domain.ProjectRoot;
import domain.RevXPackage;
import domain.RevXProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RevXProjectService implements IRevXProjectService {

    @Override
    public RevXProject create(String name, ProjectRoot projectRoot) {

        validatePath(projectRoot);

        RevXProject revXProject = new RevXProject(name);

        //load directoriesTree
        mapExternalStructureToRevXProject(revXProject, projectRoot);

        //load classes
        

        return revXProject;
    }

    private RevXProject mapExternalStructureToRevXProject(RevXProject revXProject, ProjectRoot projectRoot) {


        try (Stream<Path> walk = Files.walk(Path.of(projectRoot.getPath()))) {

            walk.filter(Files::isDirectory)
                .forEach(directory -> revXProject.addPackage(new RevXPackage(directory.toAbsolutePath().toString().replace("\\", "/"))));

        } catch (IOException e) {
            e.printStackTrace();
        }



        return revXProject;
    }

    private void validatePath(ProjectRoot projectRoot) {

        if(! Files.exists(Path.of(projectRoot.getPath()))){
            throw new RuntimeException("Path does not exists: " + projectRoot.getPath());
        }
    }
}
