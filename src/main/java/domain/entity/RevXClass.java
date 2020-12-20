package domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class RevXClass {

    private final String name;
    @Getter
    private final String path;

    private final RevXPackage parent;

    private final Set<RevXClass> dependency = new HashSet<>();

    public void addDependency(RevXClass  revXClass){
        this.dependency.add(revXClass);
    }

    public String getAbsolutePath(){
        return path + "/" + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevXClass revXClass = (RevXClass) o;
        return Objects.equals(name, revXClass.name) && Objects.equals(path, revXClass.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }

    public static RevXClass of(RevXPath xPath, RevXPackage xPackage) {
        return new RevXClass(xPath.getFileName(), xPath.getParentPathAsString(), xPackage);
    }

    public void addDependencies(Set<RevXClass> dependencies) {
        dependencies.forEach(this::addDependency);
    }
}
