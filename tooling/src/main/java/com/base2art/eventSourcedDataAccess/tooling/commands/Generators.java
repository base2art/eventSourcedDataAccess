package com.base2art.eventSourcedDataAccess.tooling.commands;

import com.base2art.eventSourcedDataAccess.tooling.DirectoryLoader;
import com.base2art.eventSourcedDataAccess.tooling.Executor;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratorItemConfiguration;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.GeneratableItem;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.GeneratorSearcher;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.EntityFilterOptionsGenerator;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.EntityGenerator;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.EntityOrderOptionsGenerator;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.EntityReaderGenerator;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.Generator;
import com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators.GeneratorContent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class Generators implements Executor {
    private final String[] params;

    public Generators(final String[] params) {

        this.params = params;
    }

    @Override
    public int execute() {

        if (params.length != 2) {
            return -2;
        }

        final File targetDir = new File(params[0]);
        final File srcDir = new File(params[1]);

        final List<GeneratableItem> files = new GeneratorSearcher().find(srcDir, targetDir);

        ClassLoader cl = new DirectoryLoader(targetDir);

        Generator[] gens = new Generator[]{
                new EntityGenerator(),
                new EntityReaderGenerator(),
                new EntityFilterOptionsGenerator(),
                new EntityOrderOptionsGenerator(),
                };
        for (GeneratableItem file : files) {

            ClassifiedGeneratableItem classified = new ClassifiedGeneratorItemConfiguration(cl, file);

            Arrays.stream(gens)
                  .forEach(generator -> {
                      GeneratorContent output = generator.generate(classified);
                      if (output != null) {

                          try (PrintWriter out = new PrintWriter(new File(file.getOutputDirectory(), output.getFileName()))) {
                              out.print(output.getContent());
                          }
                          catch (FileNotFoundException e) {
                              throw new RuntimeException(e);
                          }
                      }
                  });
        }

        return 0;
    }
}
