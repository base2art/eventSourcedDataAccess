package com.base2art.eventSourcedDataAccess.tooling.commands.generation.generators;

import com.base2art.eventSourcedDataAccess.tooling.commands.generation.ClassifiedGeneratableItem;

public interface Generator {

    GeneratorContent generate(final ClassifiedGeneratableItem item);
}
