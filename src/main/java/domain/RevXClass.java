package domain;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class RevXClass {

    private final String name;
    private final String path;

    private final Set<RevXClass> dependency = new HashSet<>();

    public void addDependency(RevXClass  revXClass){
        this.dependency.add(revXClass);
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
}
