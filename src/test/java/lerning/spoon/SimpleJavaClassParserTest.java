package lerning.spoon;

import lombok.SneakyThrows;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.*;

public class SimpleJavaClassParserTest {

    @SneakyThrows
    @Test
    public void should_parser_a_simple_java_class(){

        //InputStream is = getClass().getClassLoader().getResourceAsStream("learning/spoon/Dbe.java");

        InputStream is = new FileInputStream("C:\\Users\\LorenzoCozza\\projects\\Revx\\src\\main\\java\\domain\\entity\\RevXProject.java");

        String result = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));

        var classDefinition = Launcher.parseClass(result);

        assertThat(classDefinition.getAllFields().size(), is(5));

    }
}
