package fr.saphyr.ce.core.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;
import fr.saphyr.ce.CEFiles;
import fr.saphyr.ce.world.area.cell.AbstractCell;
import fr.saphyr.ce.world.area.MoveAreaAttribute;
import fr.saphyr.ce.core.Logger;
import fr.saphyr.ce.core.register.Resources;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public final class MoveAreaAttributeLoader extends AsynchronousAssetLoader<MoveAreaAttribute, AssetLoaderParameters<MoveAreaAttribute>> {

    private MoveAreaAttribute moveAreaAttribute;

    public MoveAreaAttributeLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<MoveAreaAttribute> parameter) {
        moveAreaAttribute = createFrom(fileName);
    }

    @Override
    public MoveAreaAttribute loadSync(AssetManager manager, String fileName, FileHandle file, AssetLoaderParameters<MoveAreaAttribute> parameter) {
        return createFrom(fileName);
    }

    public static void load(String module) {
        CEFiles.foundInternal(module).forEach(s -> {
            Resources.MANAGER.load(s, MoveAreaAttribute.class);
            Logger.info("Loader : " + s);
        });
    }

    public static MoveAreaAttribute createFrom(String filepath) {
        try {
            final var gson = new Gson();
            final Reader reader = Files.newBufferedReader(Paths.get(Gdx.files.internal(filepath).path()));
            final Map<?, ?> map = gson.fromJson(reader, Map.class);
            reader.close();
            final var keys = (Map<?, ?>) map.get("keys");
            final var listAreaAttributes = new Array<AbstractCell.MoveAreaAttribute>();
            keys.forEach((key, value) -> {
                final var values = (Map<?, ?>) value;
                listAreaAttributes.add(new AbstractCell.MoveAreaAttribute(
                        Integer.parseInt((String) key),
                        (String) values.get("texture"),
                        (boolean) values.get("explorable")));
            });
            return new MoveAreaAttribute(listAreaAttributes,
                    ((ArrayList<ArrayList<Double>>) map.get("pattern")).stream().map(doubles -> doubles
                            .parallelStream().mapToInt(Double::intValue).boxed().toList()
                            .stream().mapToInt(Integer::intValue).toArray()).toArray(int[][]::new));
        } catch (IOException e) {
            Logger.error("Impossible to found the resource " + filepath);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AssetLoaderParameters<MoveAreaAttribute> parameter) {
        return null;
    }


}
