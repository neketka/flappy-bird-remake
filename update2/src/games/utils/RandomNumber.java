package games.utils;

import java.util.*;

/**
 * Created by Nikita on 2/3/14.
 */
public class RandomNumber {
    public static long randomInt(long seed, long end, int randomness){
        List<Long> numbers = new ArrayList<Long>();
        for (long i = seed;i < end+1;i++){
            numbers.add(i);
        }
        for (int i = 0;i<randomness;i++){
            Collections.shuffle(numbers);
        }
        return numbers.get(0);
    }
    public static long randomInt(long seed, long end){
        List<Long> numbers = new ArrayList<Long>();
        for (long i = seed;i < end+1;i++){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }
    public static double randomTenth(long seed, long end, int randomness){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.1;i = i + 0.1){
            numbers.add(i);
        }
        for (double i = 0;i<randomness;i++){
            Collections.shuffle(numbers);
        }
        return numbers.get(0);
    }
    public static double randomTenth(long seed, long end){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.1;i = i + 0.1){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }
    public static double randomHundredth(long seed, long end, int randomness){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.01;i = i + 0.01){
            numbers.add(i);
        }
        for (double i = 0;i<randomness;i++){
            Collections.shuffle(numbers);
        }
        return numbers.get(0);
    }
    public static double randomHundredth(long seed, long end){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.01;i = i + 0.01){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }
    public static double randomThousandth(long seed, long end, int randomness){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.001;i = i + 0.001){
            numbers.add(i);
        }
        for (double i = 0;i<randomness;i++){
            Collections.shuffle(numbers);
        }
        return numbers.get(0);
    }
    public static double randomThousandth(long seed, long end){
        List<Double> numbers = new ArrayList<Double>();
        for (double i = seed;i < end+0.001;i = i + 0.001){
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        return numbers.get(0);
    }
}
