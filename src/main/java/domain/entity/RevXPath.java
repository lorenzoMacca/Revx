package domain.entity;

import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

@RequiredArgsConstructor
public class RevXPath {

    private  final Path path;

    public String getParentPathAsString(){
        return path.getParent().toString().replace("\\", "/");
    }

    public static RevXPath of(Path path){
        return new RevXPath(path);
    }

    public static RevXPath of(String path){
        return RevXPath.of(Path.of(path));
    }


    public String getFileName() {
        return path.getFileName().toString();
    }
}
