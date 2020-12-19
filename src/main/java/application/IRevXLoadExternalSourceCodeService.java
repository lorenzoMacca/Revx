package application;

import domain.entity.ProjectRoot;
import domain.entity.RevXProject;

public interface IRevXLoadExternalSourceCodeService {

    /**
     * Load all the packages and class (*.java) and map them to the internal
     * RevX domain structure.
     *
     * @param projectName name of the external project
     * @param projectRoot root folder of the external project
     * @return a RevXProject
     */
    RevXProject createFromExternalSourceCode(String projectName, ProjectRoot projectRoot);

}
