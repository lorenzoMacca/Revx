package domain.service;

import domain.entity.RevXClass;
import domain.entity.RevXProject;
import infrastructure.dependenciesloader.jdeps.windows.JDepsWindowsDependenciesLoader;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

import java.io.*;
import java.util.Set;
import java.util.stream.Collectors;

public class DependenciesEvaluator implements IDependenciesEvaluator{

    //FIXME instantiate loader using a proper factory method.
    private final IDependenciesLoader dependenciesLoader = new JDepsWindowsDependenciesLoader();

    @Override
    public void evaluate(RevXProject revXProject) {

        revXProject.getClassesAsStream()
                .forEach(this::evaluateClass);
    }

    private void evaluateClass(RevXClass revXClass) {

        Set<RevXClass> dependencies = dependenciesLoader.get(revXClass);
        revXClass.addDependencies(dependencies);

    }

    // if you read this comment again please delete following method.
    private CtClass<?> loadSpoonClass(String absolutePath) throws FileNotFoundException {

        InputStream is = new FileInputStream(absolutePath);

        String result = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));

        return Launcher.parseClass(result);
    }
}
