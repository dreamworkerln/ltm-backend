package ru.geekbrains.handmade.ltmbackend.utils;

import java.util.Set;

public class SetUtils {

    public static boolean equals(Set<?> set1, Set<?> set2){

        if(set1 == null || set2 ==null){
            return false;
        }

        if(set1.size()!=set2.size()){
            return false;
        }

        return set1.containsAll(set2) && set2.containsAll(set1);

    }
}