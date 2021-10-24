package infrastructure.dependenciesloader.jdeps.dependencymapper;

import domain.entity.RevXClass;
import lombok.Value;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Value
public class StringLineDependencyMapper {

    Set<RevXClass> dependencies = new HashSet<>();

    RevXClass currentRevXClass;

    List<String> notAddTheseDependencies = List.of("java");

    public void readLine(String line){

        var splittedLine = line.split("->");

        var currentClassName = splittedLine[0].trim();
        var dep = splittedLine[1].trim().split(" ")[0];
        if(dep.contains("not"))
            return;

        if(notAddTheseDependencies
                .stream()
                .anyMatch(dep::contains)){
            return;
        }

        var array = dep.split("\\.");
        var lastPart = array.length-1;

        this.dependencies
                .add(new RevXClass(
                    array[lastPart],
                    dep+".class",
                    null
                ));
    }

    public Set<RevXClass> get() {
        return dependencies;
    }
}
