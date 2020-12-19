package domain.entity;

import domain.service.DependenciesEvaluator;
import domain.service.IDependenciesEvaluator;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class RevXProject {

    private final String name;
    private final Set<RevXPackage> subPackages = new HashSet<>();
    private final RevXMap<String,RevXClass> classesMap = RevXMap.newInstance();
    private final RevXMap<String, RevXPackage> packagesMap = RevXMap.newInstance();

    private final IDependenciesEvaluator dependenciesEvaluator = new DependenciesEvaluator();

    public void evaluateDependencies(){
        dependenciesEvaluator.evaluate();
    }

    public void addPackage(RevXPackage revXPackage){

        if(subPackages.isEmpty()){

            this.subPackages.add(revXPackage);
            this.packagesMap.put(revXPackage.getAbsolutePath(), revXPackage);

        }else {

            addPackageToParent(revXPackage);
        }

    }

    private void addPackageToParent(RevXPackage revXPackage) {

        this.packagesMap.get(revXPackage.getParentPath())
                .ifPresentOrElse(
                        xPackageParent -> {
                            xPackageParent.addSubPackage(revXPackage);
                            this.packagesMap.put(revXPackage.getAbsolutePath(), revXPackage);
                        },
                        () -> {
                            throw new RuntimeException("Parent not found: "+revXPackage.getParentPath());
                        });

    }

    public void addClass(RevXPath xPath){

        this.packagesMap.get(xPath.getParentPathAsString())
                .ifPresentOrElse(
                        xPackageParent -> {
                            RevXClass xClass = RevXClass.of(xPath, xPackageParent);
                            xPackageParent.addClass(xClass);
                            classesMap.put(xClass.getAbsolutePath(), xClass);
                        },
                        () -> {
                            //TODO: manage if the parent is not found. Actually it should be thrown an Exception ?!}
                        }
                );
    }

}
