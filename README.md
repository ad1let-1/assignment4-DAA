## Assignment 4 – Graph Algorithms (SCC, Condensation, Topo Sort, DAG Paths)
This project was made for the DAA course.
It shows how to analyze graphs and find relationships between components using SCC, Topo Sort, and DAG path algorithms.

## What the program does
Reads graph data from data/tasks.json.
Finds Strongly Connected Components using Tarjan’s algorithm.
Builds Condensation Graph (turns SCC into DAG).
Performs Topological Sort (Kahn’s algorithm).
Finds both Shortest and Longest (Critical) paths on the DAG.
Prints results and metrics for each graph.
## Project structure
assignment4/ 
├── data/ 
│ └── tasks.json 
├── src/ 
│ ├── main/java/ 
│ │ ├── model/ – Graph and Edge classes 
│ │ ├── scc/ – TarjanSCC and CondensationBuilder 
│ │ ├── topo/ – Topological sort (Kahn) 
│ │ ├── dag/ – Shortest and Longest path algorithms 
│ │ └── util/ – JSON loader, metrics, and Main 
│ └── test/java/ – Tests for SCC, Topo, and DAG 
└── pom.xml


## How to run
mvn clean compile
mvn exec:java "-Dexec.mainClass=util.Main" "-Dexec.args=data/tasks.json"
Input data
File data/tasks.json includes 3 graphs:

small (6 vertices)

medium (10 vertices)

large (15 vertices)


## Example output

==== GRAPH 1 ====
SCC (component per vertex): [3, 3, 3, 2, 1, 0]
Condensation DAG size: 4
Critical path length: 12

==== GRAPH 2 ====
SCC (component per vertex): [7, 6, 6, 6, 5, 4, 3, 2, 1, 0]
Condensation DAG size: 8
Critical path length: 22

==== GRAPH 3 ====
SCC (component per vertex): [12, 12, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
Condensation DAG size: 13
Critical path length: 38
## Results summary
Graph	Vertices (n)	SCC Count	DAG Size	Shortest Path Length	Critical Path Length	scc_dfs	topo_pop	topo_push	dag_relax	dag_relax_long
Small	6	4	4	12	12	6	12	9	3	3
Medium	10	8	8	19	22	10	24	21	6	7
Large	15	13	13	28	38	15	39	36	9	12

## Chart

Critical Path Length vs Graph Size

<img width="1003" height="592" alt="Снимок экрана 2025-11-03 004859" src="https://github.com/user-attachments/assets/4f8e66ce-c229-4b5a-8b1a-4a059a284b69" />


SCC Count vs Graph Size
<img width="1030" height="594" alt="Снимок экрана 2025-11-03 005009" src="https://github.com/user-attachments/assets/4cc37d84-c7ba-4544-afc2-6e333607bc56" />


The chart clearly shows that as the number of vertices increases,
the critical path length grows — meaning the algorithms are working as expected.

## Conclusion
Tarjan’s algorithm correctly finds SCCs.

The condensation graph (DAG) is built correctly.

Topological sort works fine and gives proper order.

The shortest and longest paths are correct.

Metrics grow consistently with graph size.

Overall, the implementation is correct and efficient.
