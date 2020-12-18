package infrastructure.maptoRevxProject;

import application.IRevXProjectService;
import domain.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class RevXProjectService implements IRevXProjectService {

    @Override
    public RevXProject create(String name, ProjectRoot projectRoot) {

        validatePath(projectRoot);

        RevXProject revXProject = new RevXProject(name);

        //load directoriesTree
        mapExternalStructureToRevXProject(revXProject, projectRoot);

        //load classes
        mapExternalClassesToRevXClasses(revXProject, projectRoot);

        return revXProject;
    }

    private void mapExternalClassesToRevXClasses(RevXProject revXProject, ProjectRoot projectRoot){

        try (Stream<Path> walk = Files.walk(Path.of(projectRoot.getPath()))) {

            walk.filter(Files::isRegularFile)
                    .filter(file -> {
                        Optional<String> opt = getExtensionByStringHandling(file);
                        return opt.map(s -> s.equals("java")).orElse(false);
                    })
                    .map(RevXPath::new)
                    .forEach(xPath -> {
                        RevXClass revXClass = RevXClass.of(xPath);
                        revXProject.addClass(revXClass);
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mapExternalStructureToRevXProject(RevXProject revXProject, ProjectRoot projectRoot) {


        try (Stream<Path> walk = Files.walk(Path.of(projectRoot.getPath()))) {

            walk.filter(Files::isDirectory)
                .forEach(directory -> revXProject.addPackage(new RevXPackage(directory.toAbsolutePath().toString().replace("\\", "/"))));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void validatePath(ProjectRoot projectRoot) {

        if(! Files.exists(Path.of(projectRoot.getPath()))){
            throw new RuntimeException("Path does not exists: " + projectRoot.getPath());
        }
    }

    public static Optional<String> getExtensionByStringHandling(Path path) {
        String filename = path.getFileName().toString();
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
