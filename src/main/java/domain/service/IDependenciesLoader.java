package domain.service;

import domain.entity.RevXClass;

import java.util.Set;

public interface IDependenciesLoader {

    Set<RevXClass> get(RevXClass revXClass);
}
