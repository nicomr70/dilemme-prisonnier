package fr.uga.miage.m1;

import fr.uga.miage.m1.sharedstrategy.IStrategy;
import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.util.Set;
import java.util.stream.Collectors;

@Log
public class Sandbox {
    public static void main(String[] args) {
        Reflections reflections = new Reflections("fr.uga.miage.m1");
        Set<Class<? extends IStrategy>> classes = reflections.getSubTypesOf(IStrategy.class);
        classes.forEach(str -> log.info(str.getName()))
    }
}
