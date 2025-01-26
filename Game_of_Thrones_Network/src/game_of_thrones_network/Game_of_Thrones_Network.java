package game_of_thrones_network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class SeriesCharacter {
    String name;

    public SeriesCharacter(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Character name cannot be null or empty.");
        }
        this.name = name.trim();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeriesCharacter that = (SeriesCharacter) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }
}

class Graph {
    private Map<SeriesCharacter, Integer> vertexMap;
    private int[][] adjMatrix;
    private int vertexCount;

    public Graph() {
        vertexMap = new HashMap<>();
        vertexCount = 0;
    }


    private int getVertexNumber(SeriesCharacter character) {
        if (character == null) {
            throw new IllegalArgumentException("Character cannot be null.");
        }
        if (vertexMap.containsKey(character)) {
            return vertexMap.get(character);
        } else {
            vertexMap.put(character, vertexCount);
            vertexCount++;
            return vertexCount - 1;
        }
    }


    public void ReadGraphFromFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("File path cannot be null or empty.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                     System.err.println("Invalid line format: " + line + ". Skipping line.");
                    continue;
                }
                try {
                    SeriesCharacter vertex1 = new SeriesCharacter(parts[0]);
                    SeriesCharacter vertex2 = new SeriesCharacter(parts[1]);
                    int weight = Integer.parseInt(parts[2].trim());
                    int vertexNo1 = getVertexNumber(vertex1);
                    int vertexNo2 = getVertexNumber(vertex2);


                    if (adjMatrix == null || vertexCount > adjMatrix.length) {
                        int newSize = vertexCount + 10;
                        int[][] temp = new int[newSize][newSize];
                        if (adjMatrix != null) {
                            for (int i = 0; i < adjMatrix.length; i++) {
                                System.arraycopy(adjMatrix[i], 0, temp[i], 0, adjMatrix[i].length);
                            }
                        }
                        adjMatrix = temp;
                    }
                    adjMatrix[vertexNo1][vertexNo2] = weight;
                }catch (IllegalArgumentException e) {
                    System.err.println("Error processing line: " + line + ", " + e.getMessage());
                }

            }
        } catch (IOException e) {
             System.err.println("Error reading file: " + e.getMessage());
        }catch (Exception e){
            System.err.println("An unexpected error has occured reading the file:" + e.getMessage());
        }
    }

   public boolean isThereAPath(String name1, String name2) {
        if (name1 == null || name1.trim().isEmpty() || name2 == null || name2.trim().isEmpty()) {
            System.err.println("Character names cannot be null or empty.");
            return false;
        }
        SeriesCharacter vertex1 = new SeriesCharacter(name1);
        SeriesCharacter vertex2 = new SeriesCharacter(name2);
        if (!vertexMap.containsKey(vertex1) || !vertexMap.containsKey(vertex2)) {
             System.err.println("One or both characters not found in the graph.");
            return false;
        }
        int startVertex = vertexMap.get(vertex1);
        int endVertex = vertexMap.get(vertex2);
        boolean[] visited = new boolean[vertexCount];
        return isPathDFS(startVertex, endVertex, visited);
    }

    private boolean isPathDFS(int start, int end, boolean[] visited) {
        visited[start] = true;
        if (start == end) {
            return true;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[start][i] > 0 && !visited[i]) {
                if (isPathDFS(i, end, visited))
                    return true;
            }
        }
        return false;
    }


    public void allPathsShorterThanEqualTo(int pathLen, int vertexNo, String name1) {
          if (name1 == null || name1.trim().isEmpty()) {
              System.err.println("Character name cannot be null or empty.");
              return;
          }

        if(pathLen < 0 || vertexNo < 0 )
        {
            System.err.println("Path Length and Vertex Count cannot be less than 0");
            return;
        }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        if (!vertexMap.containsKey(startChar)) {
            System.err.println("Start vertex not found");
            return;
        }
        int startVertex = vertexMap.get(startChar);
        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];
        currentPath.add(startVertex);
        printAllPathsRec(startVertex, pathLen, vertexNo, currentPath, visited, 0);
    }

    private void printAllPathsRec(int currentVertex, int pathLen, int vertexNo,
                                 List<Integer> currentPath, boolean[] visited, int currentLength) {
        visited[currentVertex] = true;
        if (currentPath.size() >= vertexNo && currentLength <= pathLen) {
            printCurrentPath(currentPath);
        }

        if (currentLength >= pathLen || currentPath.size() > vertexCount) {
            visited[currentVertex] = false;
            return;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[currentVertex][i] > 0 && !visited[i]) {
                currentPath.add(i);
                printAllPathsRec(i, pathLen, vertexNo, currentPath, visited, currentLength + 1);
                currentPath.remove(currentPath.size() - 1); // Backtrack
            }
        }
        visited[currentVertex] = false;
    }


    private void printCurrentPath(List<Integer> path) {
        for (int vertex : path) {
            for (Map.Entry<SeriesCharacter, Integer> entry : vertexMap.entrySet()) {
                if (entry.getValue() == vertex) {
                    System.out.print(entry.getKey().toString() + ", ");
                }
            }
        }
        System.out.println();
    }


    public int shortestPathLengthFromTo(String name1, String name2) {
        if (name1 == null || name1.trim().isEmpty() || name2 == null || name2.trim().isEmpty()) {
           System.err.println("Character names cannot be null or empty.");
           return Integer.MAX_VALUE;
        }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        SeriesCharacter endChar = new SeriesCharacter(name2);
        if (!vertexMap.containsKey(startChar) || !vertexMap.containsKey(endChar)) {
             System.err.println("One or both characters not found in the graph.");
            return Integer.MAX_VALUE;
        }
        int startVertex = vertexMap.get(startChar);
        int endVertex = vertexMap.get(endChar);
        int[] dist = new int[vertexCount];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startVertex] = 0;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Comparator.comparingInt(v -> dist[v]));
        minHeap.offer(startVertex);
        while (!minHeap.isEmpty()) {
            int current = minHeap.poll();
            if (current == endVertex)
                return dist[endVertex];
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[current][i] > 0) {
                    int alt = dist[current] + 1;
                    if (alt < dist[i]) {
                        dist[i] = alt;
                        minHeap.offer(i);
                    }
                }
            }
        }
        return Integer.MAX_VALUE;
    }


    public int noOfPathsFromTo(String name1, String name2) {
           if (name1 == null || name1.trim().isEmpty() || name2 == null || name2.trim().isEmpty()) {
            System.err.println("Character names cannot be null or empty.");
              return 0;
        }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        SeriesCharacter endChar = new SeriesCharacter(name2);
        if (!vertexMap.containsKey(startChar) || !vertexMap.containsKey(endChar)) {
             System.err.println("One or both characters not found in the graph.");
             return 0;
        }
        int startVertex = vertexMap.get(startChar);
        int endVertex = vertexMap.get(endChar);
        List<Integer> currentPath = new ArrayList<>();
        boolean[] visited = new boolean[vertexCount];
        currentPath.add(startVertex);
        return noOfPathsRec(startVertex, endVertex, currentPath, visited);
    }

    private int noOfPathsRec(int currentVertex, int endVertex, List<Integer> currentPath,
                             boolean[] visited) {
        visited[currentVertex] = true;
        int paths = 0;
        if (currentVertex == endVertex) {
            visited[currentVertex] = false;
            return 1;
        }
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[currentVertex][i] > 0 && !visited[i]) {
                currentPath.add(i);
                paths += noOfPathsRec(i, endVertex, currentPath, visited);
                currentPath.remove(currentPath.size() - 1);
            }
        }
        visited[currentVertex] = false;
        return paths;
    }


    public void BFSfromTo(String name1, String name2) {
          if (name1 == null || name1.trim().isEmpty() || name2 == null || name2.trim().isEmpty()) {
             System.err.println("Character names cannot be null or empty.");
              return;
          }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        SeriesCharacter endChar = new SeriesCharacter(name2);
        if (!vertexMap.containsKey(startChar) || !vertexMap.containsKey(endChar)) {
             System.err.println("Start or End Vertex Not Found.");
            return;
        }
        int startVertex = vertexMap.get(startChar);
        int endVertex = vertexMap.get(endChar);

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[vertexCount];
        queue.offer(startVertex);
        visited[startVertex] = true;
        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            printVertex(currentVertex);
            if (currentVertex == endVertex)
                return;

            List<Pair> neighbours = new ArrayList<>();
            for (int i = 0; i < vertexCount; i++) {
                if (adjMatrix[currentVertex][i] > 0) {
                    neighbours.add(new Pair(i, adjMatrix[currentVertex][i]));
                }
            }
            Collections.sort(neighbours);

            for (Pair neighbour : neighbours) {
                if (!visited[neighbour.vertexNo]) {
                    queue.offer(neighbour.vertexNo);
                    visited[neighbour.vertexNo] = true;
                }
            }
        }
    }

    private void printVertex(int vertex) {
        for (Map.Entry<SeriesCharacter, Integer> entry : vertexMap.entrySet()) {
            if (entry.getValue() == vertex) {
                System.out.print(entry.getKey().toString() + ", ");
            }
        }
    }


    public void DFSfromTo(String name1, String name2) {
        if (name1 == null || name1.trim().isEmpty() || name2 == null || name2.trim().isEmpty()) {
             System.err.println("Character names cannot be null or empty.");
             return;
        }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        SeriesCharacter endChar = new SeriesCharacter(name2);
        if (!vertexMap.containsKey(startChar) || !vertexMap.containsKey(endChar)) {
            System.err.println("Start or End Vertex Not Found.");
            return;
        }
        int startVertex = vertexMap.get(startChar);
        int endVertex = vertexMap.get(endChar);
        boolean[] visited = new boolean[vertexCount];
        if (!dfsRec(startVertex, endVertex, visited))
            System.out.println("No path");
        else
            System.out.println();
    }

    private boolean dfsRec(int current, int endVertex, boolean[] visited) {
        visited[current] = true;
        printVertex(current);
        if (current == endVertex)
            return true;
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[current][i] > 0 && !visited[i]) {
                if (dfsRec(i, endVertex, visited)) {
                    return true;
                }
            }
        }
        return false;
    }

   public int noOfVerticesInComponent(String name1) {
        if (name1 == null || name1.trim().isEmpty()) {
             System.err.println("Character name cannot be null or empty.");
            return 0;
        }
        SeriesCharacter startChar = new SeriesCharacter(name1);
        if (!vertexMap.containsKey(startChar))
        {
            System.err.println("Start vertex not found.");
            return 0;
        }
        int startVertex = vertexMap.get(startChar);
        boolean[] visited = new boolean[vertexCount];
        return getVerticesInComponent(startVertex, visited);
    }

    private int getVerticesInComponent(int currentVertex, boolean[] visited) {
        visited[currentVertex] = true;
        int count = 1;
        for (int i = 0; i < vertexCount; i++) {
            if (adjMatrix[currentVertex][i] > 0 && !visited[i])
                count += getVerticesInComponent(i, visited);
        }
        return count;
    }

    class Pair implements Comparable<Pair> {
        public int vertexNo;
        public int edge;

        public Pair(int vertexNo, int edge) {
            this.vertexNo = vertexNo;
            this.edge = edge;
        }

        @Override
        public int compareTo(Pair other) {
            return Integer.compare(this.edge, other.edge);
        }
    }
}

public class Game_of_Thrones_Network {
    public static void main(String[] args) {
       Graph graph = new Graph();
       graph.ReadGraphFromFile("test2.txt");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. IsThereAPath");
            System.out.println("2. AllPathsShorterThanEqualTo");
            System.out.println("3. ShortestPathLengthFromTo");
            System.out.println("4. NoOfPathsFromTo");
            System.out.println("5. BFSfromTo");
            System.out.println("6. DFSfromTo");
            System.out.println("7. NoOfVerticesInComponent");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Invalid input, please enter an integer.");
                scanner.next();
                continue;
            }
            scanner.nextLine();

            switch (choice) {
                 case 1:
                    System.out.print("Enter name1: ");
                    String name1 = scanner.nextLine();
                    System.out.print("Enter name2: ");
                    String name2 = scanner.nextLine();
                    System.out.println("IsThereAPath(" + name1 + ", " + name2 + "): " + graph.isThereAPath(name1, name2));
                    break;
               case 2:
                    System.out.print("Enter pathLen: ");
                    int pathLen;
                    try {
                        pathLen = scanner.nextInt();
                    } catch (InputMismatchException e) {
                         System.err.println("Invalid path length. Please enter an integer.");
                         scanner.next();
                         continue;
                    }

                    System.out.print("Enter vertexNo: ");
                    int vertexNo;
                   try {
                       vertexNo = scanner.nextInt();
                    } catch (InputMismatchException e) {
                       System.err.println("Invalid vertex count. Please enter an integer.");
                       scanner.next();
                         continue;
                    }

                    scanner.nextLine();
                    System.out.print("Enter name1: ");
                    String name3 = scanner.nextLine();
                    graph.allPathsShorterThanEqualTo(pathLen, vertexNo, name3);
                    break;
                case 3:
                    System.out.print("Enter name1: ");
                    String name4 = scanner.nextLine();
                    System.out.print("Enter name2: ");
                    String name5 = scanner.nextLine();
                    int result = graph.shortestPathLengthFromTo(name4, name5);
                    System.out.println("Shortest PathLengthFromTo(" + name4 + ", " + name5 + "): "
                            + (result == Integer.MAX_VALUE ? "infinity" : result));
                    break;
                case 4:
                     System.out.print("Enter name1: ");
                     String name6 = scanner.nextLine();
                     System.out.print("Enter name2: ");
                     String name7 = scanner.nextLine();
                   System.out.println("NoOfPathsFromTo(" + name6 + ", " + name7 + "): " + graph.noOfPathsFromTo(name6, name7));
                    break;
                case 5:
                     System.out.print("Enter name1: ");
                     String name8 = scanner.nextLine();
                     System.out.print("Enter name2: ");
                     String name9 = scanner.nextLine();
                     System.out.print("BFSfromTo(" + name8 + ", " + name9 + "): ");
                     graph.BFSfromTo(name8, name9);
                     System.out.println();
                    break;
                case 6:
                     System.out.print("Enter name1: ");
                    String name10 = scanner.nextLine();
                    System.out.print("Enter name2: ");
                    String name11 = scanner.nextLine();
                    System.out.print("DFSfromTo(" + name10 + ", " + name11 + "): ");
                     graph.DFSfromTo(name10, name11);
                    break;
                case 7:
                    System.out.print("Enter name1: ");
                    String name12 = scanner.nextLine();
                   System.out.println("NoOfVerticesInComponent(" + name12 + "): " + graph.noOfVerticesInComponent(name12));
                    break;
                 case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
               default:
                    System.err.println("Invalid choice, please try again.");
            }
        }
    }
}