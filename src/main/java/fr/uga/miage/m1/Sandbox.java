package fr.uga.miage.m1;

import fr.uga.miage.m1.models.strategy.IStrategy;
import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.util.Set;

@Log
public class Sandbox {
    public static void main(String[] args) {
        Reflections reflections = new Reflections(IStrategy.class);
        Set<Class<? extends IStrategy>> classes = reflections.getSubTypesOf(IStrategy.class);
        classes.forEach(str -> log.info(str.getSimpleName()));
    }
}
