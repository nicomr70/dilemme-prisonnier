package fr.uga.miage.m1;

import fr.uga.miage.m1.models.strategy.IStrategy;
import org.reflections.Reflections;

import java.util.Set;
import java.util.logging.Logger;

public class Sandbox {
    private static final Logger LOGGER = Logger.getLogger(Sandbox.class.getPackageName());

    public static void main(String[] args) {
        Reflections reflections = new Reflections(IStrategy.class);
        Set<Class<? extends IStrategy>> classes = reflections.getSubTypesOf(IStrategy.class);
        classes.forEach(str -> LOGGER.info(str.getSimpleName()));
    }
}
