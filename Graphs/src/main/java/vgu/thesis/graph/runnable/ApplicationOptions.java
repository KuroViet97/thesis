package vgu.thesis.graph.runnable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import sun.awt.image.IntegerComponentRaster;
import vgu.thesis.graph.graphgenerator.GraphGenerator;
import vgu.thesis.graph.vgu.thesis.graph.model.GraphModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.util.Arrays.asList;

@Builder@AllArgsConstructor
public class ApplicationOptions {
	protected GraphModel graph;
	protected Integer option, vertexNumber, chipNumber;
	protected Scanner input;
	public ApplicationOptions(){}

	protected static final List<String> consoleMenu = asList(
			"0. Display Menu",
			"1. Generate Cycle Graph",
			"2. Add Configuration",
			"3. Fire A Vertex",
			"4. Show Vertex List",
			"5. Show Configuration List",
			"6. Show Edge List",
			"7. Fire sets of vertices",
			"-1. Terminate The Program"
	);

	public void startApplication(){
		option = 1000;
		Scanner optionInput = new Scanner(System.in);
		showMenu();
		do{
			System.out.print("Your option: ");
			option = optionInput.nextInt();
			switch(option){
				case 0:{
					showMenu();
					break;
				}
				case 1:{
					graph = GraphGenerator.generateCycleGraph(inputVertex());
					break;
				}
				case 2:{
					if(!Objects.isNull(graph)) {
						graph.addConfiguration(inputVertex(), inputChip());
					}else{
						System.out.println("Null object!");
					}
					break;
				}
				case 3:{
					graph.fireOneVertex(inputVertex());
					break;
				}
				case 4:{
					if(!Objects.isNull(graph)){
						graph.displayVertexList();
					}else{
						System.out.println("Null object!");
					}
					break;
				}
				case 5:{
					if(!Objects.isNull(graph)){
						graph.displayConfigurationList();
					}else{
						System.out.println("Null object!");
					}
					break;
				}
				case 6:{
					if(!Objects.isNull(graph)){
						graph.displayEdgeList();
					}else{
						System.out.println("Null object!");
					}
					break;
				}
				case 7:{
					chipFiringGame();
				}
				case -1: break;
				default: break;
			}
		}while(option!=-1);
	}

	private void chipFiringGame(){
		if(!Objects.isNull(graph)){
			boolean stop = false;
			do{
				System.out.print("Vertices to fire: ");
				List<Integer> vertexList = new ArrayList<Integer>();
				input = new Scanner(System.in);
				while(input.hasNextInt()){
					vertexList.add(input.nextInt());
				}
				if(vertexList.size() == 0){
					stop = true;
				}else {
					graph.fireSetOfVertices(vertexList);
					graph.displayConfigurationList();
				}
			}while(stop!=true);
		}else{
			System.out.println("Graph is empty");
		}
	}
	private void showMenu(){
		consoleMenu.stream().forEach(System.out::println);
	}

	private Integer inputVertex(){
		input = new Scanner(System.in);
		System.out.print("Vertex number: ");
		vertexNumber = input.nextInt();
		return vertexNumber;
	}

	private Integer inputChip(){
		input = new Scanner(System.in);
		System.out.print("Chip number: ");
		chipNumber = input.nextInt();
		return chipNumber;
	}
}
