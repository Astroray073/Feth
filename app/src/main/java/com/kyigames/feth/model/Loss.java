package com.kyigames.feth.model;

public class Loss implements Comparable<Loss> {
    public String Name;
    public String Character;

    @Override
    public int compareTo(Loss loss) {
        return Name.compareTo(loss.Name);
    }
}
