package domain;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class RevXProject {

    private final String name;

    private final Set<RevXPackage> subPackages = new HashSet<>();
    private final Set<RevXClass> classes = new HashSet<>();

    public void addPackage(RevXPackage revXPackage){
        this.subPackages.add(revXPackage);
    }

    public void addClass(RevXClass revXClass){
        this.classes.add(revXClass);
    }

}
