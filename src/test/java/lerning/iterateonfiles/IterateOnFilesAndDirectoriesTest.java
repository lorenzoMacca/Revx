package lerning.iterateonfiles;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IterateOnFilesAndDirectoriesTest {

    @Test
    public void just_iterate_from_root_path(){

        Path path = Path.of("C:/Project/revX/src/test/resources/learning/directoryTest");

        if(Files.exists(path)){

            try (Stream<Path> walk = Files.walk(path)) {

                List<String> result = walk.filter(Files::isDirectory)
                        .map(x -> x.toString()).collect(Collectors.toList());

                result.forEach(System.out::println);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
