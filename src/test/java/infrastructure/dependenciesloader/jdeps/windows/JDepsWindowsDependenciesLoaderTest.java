package infrastructure.dependenciesloader.jdeps.windows;

import domain.entity.RevXClass;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JDepsWindowsDependenciesLoaderTest {


    @Test
    public void load_dependencies_from_a_specific_class(){

        JDepsWindowsDependenciesLoader loader = new JDepsWindowsDependenciesLoader();

        Set<RevXClass> dependencies = loader.get(null);

        //TODO: assert that the dependencies are listed as expected
    }

}