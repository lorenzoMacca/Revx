package domain;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class RevXProject {

    private final String name;

    private final Set<RevXPackage> subPackages = new HashSet<>();
    private final Map<String,RevXClass> classesMap = new HashMap<>();
    private final Map<String, RevXPackage> packagesMap = new HashMap<>();

    public void addPackage(RevXPackage revXPackage){

        if(subPackages.isEmpty()){

            this.subPackages.add(revXPackage);
            this.packagesMap.put(revXPackage.getAbsolutePath(), revXPackage);

        }else {

            addPackageToParent(revXPackage);
        }

    }

    private void addPackageToParent(RevXPackage revXPackage) {

        RevXPackage parent = this.packagesMap.get(revXPackage.getParentPath());

        if(Objects.isNull(parent)){
            throw new RuntimeException("Parent not found: "+revXPackage.getParentPath());
        }

        parent.addSubPackage(revXPackage);
        this.packagesMap.put(revXPackage.getAbsolutePath(), revXPackage);

    }

    public void addClass(RevXPath xPath){

        RevXPackage parent = this.packagesMap.get(xPath.getParentPathAsString());

        if(! Objects.isNull(parent)){
            RevXClass xClass = RevXClass.of(xPath, parent);
            parent.addClass(xClass);
            classesMap.put(xClass.getAbsolutePath(), xClass);
        }else {
            //TODO: manage if the parent is not found. Actually it should be thrown an Exception ?!
        }

    }

}
