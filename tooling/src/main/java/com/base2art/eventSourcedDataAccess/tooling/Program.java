package com.base2art.eventSourcedDataAccess.tooling;

import com.base2art.eventSourcedDataAccess.tooling.commands.Generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class Program {
    public static void main(String[] args) {

        HashMap<String, Function<String[], Executor>> map = new HashMap<>();

        map.put("generate", Generators::new);

        if (args.length > 0) {

            final String commandName = args[0].toLowerCase();

            final List<String> newArgs = new ArrayList<>(Arrays.asList(args));
            newArgs.remove(0);
            if (map.containsKey(commandName)) {
                Executor exec = map.get(commandName).apply(newArgs.toArray(new String[0]));
                System.exit(exec.execute());
            }
        }

        System.exit(-1);
    }
}