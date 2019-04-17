package vgu.thesis.graph.runnable;

import vgu.thesis.graph.graphgenerator.GraphGenerator;
import vgu.thesis.graph.vgu.thesis.graph.model.GraphModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main {
	public static void main(String args[]){
		ApplicationOptions app = new ApplicationOptions();
		app.startApplication();
//		GraphModel graph = GraphGenerator.generateCycleGraph(10);
//		graph.addConfiguration(2, 1);
//		graph.addConfiguration(9,1);
//		List<Integer> vertexSet = Arrays.asList(
//			//1,1,1,2,2,3,3,4,7,8,8,9,9 //1 -> 6 ~ vertex 9
////			0,0,0,9,9,9,1,1,1,8,8,8,2,2,7,7,3,6//chip 1, 8 -> 5,6 ~ vertex 10
//			2,2,2,3,3,3,5,4,4,8,9,9,0,0,0,1,1,1//chip 2,9 -> 6,7
//				);
//		graph.fireSetOfVertices(vertexSet);
//		graph.displayConfigurationList();
//		//graph.autoFire(2, 4);

	}
}
