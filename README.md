# Game of Thrones Network Project

This project models the relationships between characters in the Game of Thrones TV series using a graph data structure. It implements various graph traversal and analysis algorithms to explore these connections.

## Project Description

This project was developed for the COMP2102 Data Structures and Algorithms course. It demonstrates the application of graph theory concepts to analyze character relationships using a weighted graph.

### Key Features

*   **Graph Representation:** A weighted, undirected graph is used to represent the relationships between characters. Each character is a vertex, and the edges represent the strength of the connection between two characters. The graph is implemented using an adjacency matrix.
*   **Character Mapping:** A `HashMap` is used to map `SeriesCharacter` objects (which encapsulate character names) to their respective integer vertex numbers in the graph.
*   **Network Analysis Algorithms:** The following network analysis algorithms are implemented:
    *   **Path Existence Check:** Checks if a path exists between two characters using Depth-First Search (DFS).
    *   **All Paths:** Finds and prints all paths starting from a given character, within a specified path length and minimum number of vertices.
    *   **Shortest Path Length:** Calculates the shortest path length between two characters using Dijkstra's algorithm.
    *   **Number of Paths:** Calculates the number of unique paths between two characters using a modified DFS.
    *   **BFS (Breadth-First Search):** Traverses the graph using BFS from one character to another, printing the traversed vertices.
    *   **DFS (Depth-First Search):** Traverses the graph using DFS from one character to another, printing the traversed vertices.
    *   **Connected Component Size:** Calculates the number of vertices in the connected component containing a specific character.

## Technologies Used

*   **Java:** The primary programming language for this project.
*   **Data Structures:** `HashMap`, Adjacency Matrix, `PriorityQueue`, `Queue`, `List`.
*   **Algorithms:** DFS, Dijkstra, BFS.

## Setup and Execution

1.  **Required Files:** Ensure that the following files are in the same directory:
    *   `Game_of_Thrones_Network.java`
    *   `Graph.java`
    *   `SeriesCharacter.java`
2.  **Data File:** Place the `test2.txt` file in the project directory. This file contains the character relationship data.
3.  **Compilation:**
    ```bash
    javac *.java
    ```
4.  **Execution:**
    ```bash
    java game_of_thrones_network.Game_of_Thrones_Network
    ```

## Usage

After running the program, a menu will appear in the console. The menu options are:

1.  **Is There a Path:** Checks if a path exists between two characters.
2.  **All Paths Shorter Than or Equal To:** Prints all paths starting from a vertex, shorter than or equal to a given path length, with at least a given number of vertices.
3.  **Shortest Path Length From To:** Returns the length of the shortest path between two vertices.
4.  **Number of Paths From To:** Calculates and prints the number of paths between two vertices.
5.  **BFS From To:** Prints the sequence of vertices visited while performing a Breadth-First Search from one character to another.
6.  **DFS From To:** Prints the sequence of vertices visited while performing a Depth-First Search from one character to another.
7.  **Number of Vertices in Component:** Returns the number of vertices in the component that contains a given character.
8.  **Exit:** Exits the program.

### Input Instructions for Each Method with Examples

1.  **Is There a Path**
    *   Enter `1` when prompted.
    *   Enter the first character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *   Enter the second character's name (e.g., `Jon`). Note that character names should start with a capital letter.
    *   The program will output `true` if a path exists, or `false` otherwise.

    **Example:**
    ```
    Enter your choice: 1
    Enter name1: Arya
    Enter name2: Jon
    IsThereAPath(Arya, Jon): true
    ```

2.  **All Paths Shorter Than or Equal To**
    *   Enter `2` when prompted.
    *   Enter the maximum path length (e.g., `4`). This means, all paths with a length of 4 or less will be returned.
    *   Enter the minimum number of vertices to include in the path (e.g., `3`). This means that all paths with a number of vertices equal or greater to 3 will be returned.
    *    Enter the starting character's name (e.g., `Arya`). Note that character names should start with a capital letter.

    The program will print all paths starting from `Arya` that are no longer than length `4`, and that has a minimum number of 3 vertices or more.

    **Example:**
    ```
    Enter your choice: 2
    Enter pathLen: 4
    Enter vertexNo: 3
    Enter name1: Arya
    All the following list will be printed.
    ...
    ```

3.  **Shortest Path Length From To**
    *   Enter `3` when prompted.
    *   Enter the first character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *   Enter the second character's name (e.g., `Jon`). Note that character names should start with a capital letter.
    *   The program will output the shortest path length between the two characters, or `infinity` if no path is found.

    **Example:**
    ```
    Enter your choice: 3
    Enter name1: Arya
    Enter name2: Jon
    Shortest PathLengthFromTo(Arya, Jon): 1
    ```

4.  **Number of Paths From To**
    *   Enter `4` when prompted.
    *   Enter the first character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *   Enter the second character's name (e.g., `Jon`). Note that character names should start with a capital letter.
    *   The program will output the total number of different paths between these two characters.

   **Example:**
    ```
    Enter your choice: 4
    Enter name1: Arya
    Enter name2: Jon
    NoOfPathsFromTo(Arya, Jon): 2
    ```

5.  **BFS From To**
    *   Enter `5` when prompted.
    *   Enter the starting character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *    Enter the target character's name (e.g., `Jon`). Note that character names should start with a capital letter.
    *   The program will print the characters visited in a Breadth-First Search order from `Arya` to `Jon`.

     **Example:**
     ```
     Enter your choice: 5
     Enter name1: Arya
     Enter name2: Jon
     BFSfromTo(Arya, Jon): Arya, Robert, Tyrion, Cersei, Roose, Brynden, Joffrey, Gregor, Jon,
     ```

6.  **DFS From To**
    *   Enter `6` when prompted.
    *   Enter the starting character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *   Enter the target character's name (e.g., `Jon`). Note that character names should start with a capital letter.
    *   The program will print the characters visited in a Depth-First Search order from `Arya` to `Jon`.

    **Example:**
    ```
    Enter your choice: 6
    Enter name1: Arya
    Enter name2: Jon
    DFSfromTo(Arya, Jon): Arya, Jaime, Robert, Aemon, Grenn, Samwell, Mance, Craster, Karl, Gilly, Dalla, Qhorin, Rattleshirt, Styr, Val, Ygritte, Eddison, Janos, Alliser, Bowen, Thoros, Barristan, Stannis, Balon, Loras, Margaery, Olenna, Davos, Cressen, Salladhor, Renly, Varys, Pycelle, Melisandre, Tyrion, Oberyn, Ellaria, Mace, Gregor, Sandor, Beric, Anguy, Gendry, Ilyn, Meryn, Elia, Bronn, Podrick, Petyr, Shae, Chataya, Kevan, Lancel, Doran, Joffrey, Tommen, Myrcella, Brienne, Edmure, Brynden, Lothar, Roslin, Walder, Qyburn, Bran, Jon, 

    ```

7.  **Number of Vertices in Component**
    *   Enter `7` when prompted.
    *   Enter a character's name (e.g., `Arya`). Note that character names should start with a capital letter.
    *   The program will output the number of vertices in the connected component that contains the character `Arya`.

    **Example:**
    ```
    Enter your choice: 7
    Enter name1: Arya
    NoOfVerticesInComponent(Arya): 76
    ```

8.  **Exit**
    *   Enter `8` to exit the program.

## Notes

*   This project provides a basic implementation of character relationship analysis using graph data structures.
*   Further improvements can include optimization for graph algorithms, different data structures, and additional functionalities.


