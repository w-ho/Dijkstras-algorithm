package webBasedRouting;


import java.util.*;

public class NodeHandler {

    private int minDistance = 0;
    private int smallest = 16;
    private int currentlink = 0;
    private boolean done = false;
    private String place = null;
    private String pointBefore = "whoKnows";

    public List<Nodes> nodeList = new ArrayList<Nodes>();
    Set<String> visited = new HashSet();
    Map<String ,Integer> notDone = new TreeMap<String, Integer>();

    public void nodeCreator(String name,int x ,int y){
        Nodes currentNode = new Nodes(name, x, y);
        nodeList.add(currentNode);
    }

    public void createlink(String start, String  end, int weight){
        for(Nodes currentNode: nodeList){
            findNodetoCreateLink(currentNode , start, end ,weight);
        }
    }

    public Set dijkstraRoute(String start, String end) {
        int[][] array = new int[nodeList.size()][nodeList.size()];
        String[] route = new String[nodeList.size()];

        setup(start, route);

        if (start.equals(end)){
            smallest = 0;
        }

        findShortestPath(start, end, array, route);
        return routetoDestination(start, end, route);
    }

    public int stringtoInt(String character){
        char c = character.charAt(0);
        return Character.getNumericValue(c) - 10;
    }

    private String getLinksForNode(String start ,String end , int[][] array, int iteration, Map notDone, String [] route){
        int currentSmallest = 15;

        gotoAllLinksOnNode(start, currentSmallest, iteration, array, route);
        findAnythingSmaller(currentSmallest, notDone);
        minDistance = currentlink + minDistance;

        isShortestPathDestination(end);
        addToDoneList();
        return place;
    }

    private void addToDoneList(){
        visited.add(place);
        notDone.remove(place);
    }

    private void setup(String start, String[] route){
        done = false;
        visited.clear();
        visited.add(start);
        route[0] = start;
    }

    private void findNodetoCreateLink(Nodes currentNode, String start, String end, int weight){
        if(currentNode.getName().equals(start)){
            currentNode.linkage.put(end,weight);
        }
    }

    private void findShortestPath(String start, String end, int[][] array,String[] route){
        for (int i = 0; i < nodeList.size(); i++) {
            array[i][stringtoInt(start)] = minDistance;
            String newStartPlace = getLinksForNode(start, end, array, i ,notDone, route);

            if (done){
                smallest = minDistance;
                return;
            }

            start = newStartPlace;
        }
    }

    private void gotoAllLinksOnNode(String start,int currentSmallest, int iteration, int[][] array, String[] route){
        int startPoint = stringtoInt(start);
        Map linkage = nodeList.get(startPoint).linkage;

        Collection<Map.Entry<String, Integer>> entrySet = linkage.entrySet();
        Iterator<Map.Entry<String ,Integer>> entryIt = entrySet.iterator();

        while(entryIt.hasNext()) {
            currentlink = 0;
            Map.Entry<String, Integer> entry = entryIt.next();

            if (!(start.equals(entry.getKey())) && (visitedFinal(entry.getKey()))) {
                if ( currentDistance(entry.getKey(), entry.getValue()) > (entry.getValue() + minDistance)) {
                    int distance = (entry.getValue() + minDistance);
                    currentlink = (array[iteration][stringtoInt(entry.getKey())] = distance);
                    route[stringtoInt(entry.getKey())] = start;
                    notDone.put(entry.getKey(), distance);
                } else {
                    currentlink = currentDistance(entry.getKey(), entry.getValue());
                }

                if (currentlink < currentSmallest) {
                    currentSmallest = currentlink;
                    place = entry.getKey();
                }
            }
        }
    }

    private void findAnythingSmaller(int currentSmallest, Map notDone){
        Collection<Map.Entry<String, Integer>> beenSet = notDone.entrySet();
        Iterator<Map.Entry<String ,Integer>> beenIt = beenSet.iterator();

        while (beenIt.hasNext()) {
            int tiny = currentSmallest;
            Map.Entry<String, Integer> entrytwo = beenIt.next();
            if (tiny >= entrytwo.getValue()) {
                currentSmallest = entrytwo.getValue();
                place = entrytwo.getKey();
                minDistance = entrytwo.getValue();
                currentlink = 0;
            }
        }
    }

    private Set routetoDestination(String start, String end, String[] route){
        Set routeTaken = new LinkedHashSet();
        routeTaken.add(end);
        for (int i = 0; i < stringtoInt(end); i++){
            pointBefore = route[stringtoInt(end)];
            routeTaken.add(pointBefore);
            end = pointBefore;
        }
        routeTaken.add(start);
        return routeTaken;
    }

    private void isShortestPathDestination(String end){
        if (end.toUpperCase().equals(place)){
            done = true;
        }
    }

    private int currentDistance(String key, int value){
        int fastestDistance = 14;
        Collection<Map.Entry<String, Integer>> beenSet = notDone.entrySet();
        Iterator<Map.Entry<String ,Integer>> beenIt = beenSet.iterator();

        while(beenIt.hasNext()){
            Map.Entry<String, Integer> entrytwo = beenIt.next();
            if (entrytwo.getKey().equals(key)){
               fastestDistance = entrytwo.getValue();
               return fastestDistance;
            }
        }
        return fastestDistance;
    }

    private boolean visitedFinal(String place){
        for (String done: visited){
            if (place.equals(done)){
                return false;
            }
        }
        return true;
    }

    public int getMinDistance() {
        return minDistance;
    }
}
