package infrastructure.dependenciesloader.jdeps.windows;

import domain.entity.RevXClass;
import domain.service.IDependenciesLoader;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class JDepsWindowsDependenciesLoader implements IDependenciesLoader {

    //TODO it would be great if we could read it from a config file
    private final String jDepsExecutablePath = "C:\\Program Files (x86)\\java\\jdk-15.0.1\\bin";
    private final String jDepsExecutableName = "jdeps.exe";
    private final String classesLocationPath = "C:\\Users\\LorenzoCozza\\projects\\Revx\\target\\classes";

    private final String commandForTesting = "jdeps.exe -verbose C:\\Users\\LorenzoCozza\\projects\\Revx\\target\\classes\\infrastructure\\maptoRevxProject\\RevXLoadExternalSourceCodeService.class";

    @Override
    public Set<RevXClass> get(RevXClass revXClass) {

        try {

            Set<RevXClass> dependencies = new HashSet<>();

            ProcessBuilder builder = new ProcessBuilder();
            if (!isWindows) {
                throw new RuntimeException("It's running the custom windows implementation but it seems that the OS is not windows. ");
            }

            builder.command("cmd.exe", "/c", commandForTesting);

            builder.directory(new File(jDepsExecutablePath));
            Process process = builder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();

            return dependencies;
        }catch (InterruptedException | IOException e){
            e.printStackTrace();
        }

        throw new RuntimeException("something bad happened.");
    }

    private boolean isWindows = System.getProperty("os.name")
            .toLowerCase().startsWith("windows");

    private static class StreamGobbler implements Runnable {
        private InputStream inputStream;
        private Consumer<String> consumer;

        public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
            this.inputStream = inputStream;
            this.consumer = consumer;
        }

        @Override
        public void run() {
            new BufferedReader(new InputStreamReader(inputStream)).lines()
                    .forEach(consumer);
        }
    }
}
