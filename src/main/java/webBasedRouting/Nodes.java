package webBasedRouting;

import java.util.Map;
import java.util.TreeMap;

public class Nodes {

    public String name;
    public int x;
    public int y;

    Map<String ,Integer> linkage = new TreeMap<String, Integer>();

    public Nodes(String name, int x , int y){
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
