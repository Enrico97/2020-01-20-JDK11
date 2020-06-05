package it.polito.tdp.artsmia.model;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenze;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao = new ArtsmiaDAO();
	Graph<Artists, DefaultWeightedEdge> grafo;
	
	public List<String> ruoli() {
		return dao.ruoli();
	}
	
	public Graph<Artists, DefaultWeightedEdge> creaGrafo(String ruolo) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.vertici(ruolo));
		for (Adiacenze a : dao.archi(ruolo)) {
			if (grafo.containsVertex(a.getA1()) && grafo.containsVertex(a.getA2()))
				Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
		}
		return grafo;
	}
	
	public List<Adiacenze> archi(String ruolo) {
		return dao.archi(ruolo);
	}
	
	public int numVertici() {
		return grafo.vertexSet().size();
	}
	
	public int numArchi() {
		return grafo.edgeSet().size();
	}
}
