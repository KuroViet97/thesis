package vgu.thesis.graph.vgu.thesis.graph.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.SimpleGraph;
import vgu.thesis.graph.extension.SingletonCollector;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GraphModel {
	SimpleGraph<Integer, org.jgrapht.graph.DefaultEdge> graph = new SimpleGraph<>(org.jgrapht.graph.DefaultEdge.class);
	Map<Integer, Integer> configurations = new HashMap<>();
	public GraphModel(){ }
	/**
	 * Add number of vertices
	 * @param number
	 */
	public void addNumberOfVertices(int number){
		for(int i=0; i<number; i++){
			graph.addVertex(i);
		}
	}

	/**
	 * Initialize/Reset chip configurations
	 */
	public void initializeConfiguration(){
		for(Integer vertex: this.graph.vertexSet()){
			this.configurations.put(vertex, 0);
		}
	}

	/**
	 * Add chips for one vertex
	 * @param vertex
	 * @param chips
	 */
	public void addConfiguration(Integer vertex, Integer chips){
		if(isExistent(vertex)) {
			this.configurations.put(vertex, chips);
		}else{
			errorVertexNotFoundPrint();
		}
	}

	/**
	 * Add chips for each vertex in case chip list = vertex list
	 * @param chips
	 */
	public void addConfigurations(List<Integer> chips){
		if(isCorrectConfigurationSet(chips)){
			int i = 0;
			List<Integer> vertexSet = this.graph.vertexSet().stream().collect(Collectors.toList());
			for(Integer chipSet: chips){
				this.configurations.put(vertexSet.get(i++), chipSet);
			}
		}
	}

	/**
	 * Fire one vertex - CFG
	 * @param vertexIndex
	 */
	public void fireOneVertex(Integer vertexIndex){
		if(isExistent(vertexIndex)){
			int degree = this.graph.degreeOf(vertexIndex);
			//find the vertex = minus "degree" chips from the vertex
			this.configurations.put(vertexIndex, this.configurations.get(vertexIndex) - degree);
			//each neighbor vertex gets one chip
			addChipForNeighbors(vertexIndex);
			successVertexFirePrint();
		}else{
			errorVertexNotFoundPrint();
		}
	}

	/**
	 * Fire a set of vertices - CFG
	 * @param vertexList
	 */
	public void fireSetOfVertices(List<Integer> vertexList){
		if(areAllVerticesExistent(vertexList)){
			//substract "degree" chips for each vertex in firing set
			for(Integer vertexIndex: vertexList){
				this.configurations.put(vertexIndex, this.configurations.get(vertexIndex) - this.graph.degreeOf(vertexIndex));
			}
			//add one chip for each neighbor
			for(Integer vertexIndex: vertexList){
				addChipForNeighbors(vertexIndex);
			}
			successVertexFirePrint();
		}else{
			errorVertexNotFoundPrint();
		}
	}

	/**
	 * Print out vertex list
	 */
	public void displayVertexList(){
		for(Integer vertex: this.graph.vertexSet().stream().collect(Collectors.toSet())){
			System.out.print(vertex + " ");
		}
		System.out.println();
	}

	/**
	 * Print out configuration list
	 */
	public void displayConfigurationList(){
		for(Integer vertexIndex: this.configurations.keySet()){
			Integer configuration = this.configurations.get(vertexIndex);
			System.out.println("Vertex: " + vertexIndex + " - Chips: " + configuration);
		}
	}

	public void displayEdgeList(){
		this.graph.edgeSet().stream().forEach(System.out::println);
	}

	/**
	 * Input from console to generate edges in loop
	 */
	public void generateEdge() {
		boolean operation = true;
		Integer source, target;
		while(operation!=false){
			System.out.println("Edge: ");
			Scanner input = new Scanner(System.in);
			source = input.nextInt();
			target = input.nextInt();
			if(isExistent(source) && isExistent(target)){
				graph.addEdge(source, target);
				System.out.println("Added");
			}else{
				if(source == -1 || target == -1){
					break;
				}else{
					errorVertexNotFoundPrint();
				}
			}
		}
	}

	public void autoFire(Integer sourceVertex, Integer targetVertex){
		if(isExistent(sourceVertex) && isExistent(targetVertex)){
			Integer sourceChips = this.configurations.get(sourceVertex);
			Integer targetChips = this.configurations.get(targetVertex);
			List<Integer> listCheck = new ArrayList<>();
			if(sourceChips>0){
				int i = 0;
				while(!(targetChips == sourceChips && isWinningConfiguration())){
					if(i != targetVertex) {
						fireOneVertex(i);
						listCheck.add(i);
					}
					i++;
					if(i == this.graph.vertexSet().size()){
						i = 0;
					}
					listCheck.stream().forEach(System.out::print);
					System.out.println();
				}
			}else{
				System.out.println("Not the correct purpose");
			}
		}
	}

	private void successVertexFirePrint(){
		System.out.println("Fired successfully!");
	}

	private void errorVertexNotFoundPrint(){
		System.out.println("Vertex not found, current list: ");
		displayVertexList();
	}
	private boolean isExistent(Integer vertex){
		return this.graph.vertexSet().stream()
				.anyMatch(item -> item == vertex);
	}

	/**
	 * Check if all vertices in the given list are existent in the graph
	 * @param vertexList
	 * @return true/fasle
	 */
	private boolean areAllVerticesExistent(List<Integer> vertexList){
		boolean existence = true;
		for(Integer vertex: vertexList){
			if(!isExistent(vertex)){
				existence = false;
			}
		}
		return existence;
	}

	private boolean isCorrectConfigurationSet(List<Integer> configurationSet){
		return this.graph.vertexSet().size() == configurationSet.size();
	}

	private boolean isWinningConfiguration(){
		boolean isWinning = true;
		for(Integer vertex: this.configurations.keySet()){
			if(this.configurations.get(vertex)<0) {
				isWinning = false;
			}
		}
		return isWinning;
	}
	/**
	 * Add 1 chip for each neighbor of input vertex
	 * @param vertexIndex
	 */
	private void addChipForNeighbors(Integer vertexIndex) {
		List<Integer> neighbors = Graphs.neighborListOf(this.graph, vertexIndex).stream().collect(Collectors.toList());
		for (Integer neighbor : neighbors) {
			this.configurations.put(neighbor, this.configurations.get(neighbor) + 1);
		}
	}
}

