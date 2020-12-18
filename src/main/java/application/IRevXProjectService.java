package application;

import domain.ProjectRoot;
import domain.RevXProject;

public interface IRevXProjectService {


    RevXProject create(String name, ProjectRoot projectRoot);

}
