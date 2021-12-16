package fr.uga.miage.m1;

import fr.uga.miage.m1.models.strategy.StrategyFactory;
import fr.uga.miage.m1.sharedstrategy.IStrategy;
import lombok.extern.java.Log;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

@Log
public class Sandbox {
    public static void main(String[] args) {
        new Reflections("fr.uga.miage.m1")
                .getSubTypesOf(IStrategy.class).forEach(str -> {
                    try {
                        log.info(String.valueOf(str.getDeclaredConstructor().newInstance().getFullName()));
                    } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }

                });
    }
}
