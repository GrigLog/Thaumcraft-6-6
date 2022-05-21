package griglog.thaumcraft.utils;

import griglog.thaumcraft.Thaumcraft;
import griglog.thaumcraft.aspect.AspectList;
import griglog.thaumcraft.aspect.Aspects;
import net.minecraft.resources.IResource;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FileUtils {
    public static List<String> readLines(IResource resource) throws IOException {
        String file = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        ArrayList<String> res = new ArrayList<>();
        for (String line : file.split("\n")){
            line = line.split("//")[0];
            res.add(line);
        }
        return res;
    }

    public static List<AspectHolderParsed> readAspects(IResource resource) throws IOException {
        List<AspectHolderParsed> res = new ArrayList<>();
        for (String line : readLines(resource)){
            String[] parts = line.split(" ");
            AspectList list = new AspectList();
            int arrStart = 1;
            Mode mode = Mode.DEFAULT;
            if (parts.length < 2)
                continue;
            if (parts[1].equals("override"))
                mode = Mode.OVERRIDE;
            else if (parts[1].equals("add"))
                mode = Mode.ADD_TO_GENERATED;
            if (mode != Mode.DEFAULT)
                arrStart = 2;
            for (int i = arrStart; i < parts.length; i += 2){
                list.add(Aspects.get(parts[i+1]), Integer.parseInt(parts[i]));
            }
            res.add(new AspectHolderParsed(parts[0], mode, list));
        }
        return res;
    }

    public static class AspectHolderParsed{
        public String id;
        public Mode mode;
        public AspectList aspects;

        public AspectHolderParsed(String id, Mode mode, AspectList aspects) {
            this.id = id;
            this.aspects = aspects;
            this.mode = mode;
        }
    }

    public static enum Mode{
        DEFAULT,
        ADD_TO_GENERATED,
        OVERRIDE
    }
}
