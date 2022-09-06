package fr.saphyr.ce;

import com.badlogic.gdx.Gdx;
import fr.saphyr.ce.core.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CEFiles {

    public static List<String> foundInternal(String moduleFolder) {
        return found(Gdx.files.internal(moduleFolder).name());
    }

    public static List<String> found(String filename) {
        List<String> result = new ArrayList<>();
        try (Stream<Path> walk = Files.walk(Paths.get(filename))) {
            walk.filter(Files::isRegularFile)
                    .map(Path::toString)
                    .toList().iterator()
                    .forEachRemaining(s -> result.add(s.replace("\\", "/")));
        } catch (IOException e) {
            Logger.warning("Nothing found in the folder " + filename);
        }
        return result;
    }

}
