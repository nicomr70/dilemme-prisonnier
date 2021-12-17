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
       StrategyFactory.STRATEGIES_MAP.forEach((k, v) -> log.info(k + " : " + v.getName()));
    }
}
