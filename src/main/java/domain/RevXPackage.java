package domain;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
public class RevXPackage {

    private final String absolutePath;
    private Set<RevXPackage> subPackages = new HashSet<>();
    private Set<RevXClass> classes = new HashSet<>();

    public void addSubPackage(RevXPackage revXPackage){
        this.subPackages.add(revXPackage);
    }

    public void addClass(RevXClass revXClass){
        this.classes.add(revXClass);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RevXPackage that = (RevXPackage) o;
        return Objects.equals(absolutePath, that.absolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(absolutePath);
    }
}
