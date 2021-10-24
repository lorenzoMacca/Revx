package infrastructure.dependenciesloader.jdeps.windows;

import domain.entity.RevXClass;
import domain.service.IDependenciesLoader;
import infrastructure.dependenciesloader.jdeps.dependencymapper.StringLineDependencyMapper;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

public class JDepsWindowsDependenciesLoader implements IDependenciesLoader {

    //TODO it would be great if we could read it from a config file
    private final String jDepsExecutablePath = "C:\\Program Files (x86)\\java\\jdk-15.0.1\\bin";
    private final String jDepsExecutableName = "jdeps.exe";
    private final String classesLocationPath = "C:\\Users\\LorenzoCozza\\projects\\Revx\\target\\classes\\";

    private final String command = jDepsExecutableName + " -verbose "+classesLocationPath+"{CLASS_PATH}";

    @Override
    public Set<RevXClass> get(RevXClass revXClass) {

        try {

            var builder = new ProcessBuilder();
            if (!isWindows) {
                throw new RuntimeException("It's running the custom windows implementation but it seems that the OS is not windows. ");
            }


            builder.command("cmd.exe", "/c", command.replace("{CLASS_PATH}", revXClass.getPath()));

            builder.directory(new File(jDepsExecutablePath));
            var process = builder.start();
            var dependenciesMapper = new StringLineDependencyMapper(revXClass);
            var streamGobbler = new StreamGobbler(process.getInputStream(), dependenciesMapper::readLine);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();

            return dependenciesMapper.get();
        }catch (InterruptedException | IOException e){
            e.printStackTrace();
        }

        throw new RuntimeException("something bad happened.");
    }

    private final boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");
}
