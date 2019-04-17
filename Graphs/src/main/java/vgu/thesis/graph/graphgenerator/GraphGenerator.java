package vgu.thesis.graph.graphgenerator;

import lombok.AllArgsConstructor;
import vgu.thesis.graph.vgu.thesis.graph.model.GraphModel;

@AllArgsConstructor
public class GraphGenerator {
	public static GraphModel generateCycleGraph(Integer vertexNumber){
		GraphModel graph = new GraphModel();
		graph.addNumberOfVertices(vertexNumber);
		graph.initializeConfiguration();
		for(int i=0; i<vertexNumber-1; i++){
			graph.getGraph().addEdge(i, i+1);
		}
		graph.getGraph().addEdge(vertexNumber-1 , 0);
		return graph;
	}
}
