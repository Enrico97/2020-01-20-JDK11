package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.Adiacenze;
import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	ArtsmiaDAO dao = new ArtsmiaDAO();
	Graph<Artists, DefaultWeightedEdge> grafo;
	int best;
	List<Artists> cammino;
	
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
	
	public List<Artists> vertici(String ruolo) {
		return dao.vertici(ruolo);
	}
	
	public List<Adiacenze> archi(String ruolo) {
		return dao.archi(ruolo);
	}
	
	public Map<Integer, Artists> artisti() {
		return dao.mappaArtisti();
	}
	
	public int numVertici() {
		return grafo.vertexSet().size();
	}
	
	public int numArchi() {
		return grafo.edgeSet().size();
	}
	
	public List<Artists> cerca(int id) {
		
		List<Artists> parziale = new ArrayList<>();
		best = 0;
		cammino = new ArrayList<>();
		team(parziale, 0, id);
		return cammino;
	}
	
	public void team(List<Artists> parziale, int l, int id) {
		
		if(l==0) {
		for (Artists aa : grafo.vertexSet()) {
			if (aa.getArtist_id()==id) {
				parziale.add(aa);}}}
				for (Artists ar : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
					if (!parziale.contains(ar) && l==0) {
						parziale.add(ar);
						team(parziale, l+1, id);
						parziale.remove(parziale.size()-1);
					}
					else if (!parziale.contains(ar)) {
							if (grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), ar))==grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), parziale.get(parziale.size()-2)))) {
								parziale.add(ar);
								team(parziale, l+1, id);
								parziale.remove(parziale.size()-1);
							}
					}
				}
				
				if (l>best) {
					best=l;
					cammino = new ArrayList<>(parziale);
				}
			}
	
	public int peso() {
		int peso = 0;
		for (Artists a : cammino) {
			for (Artists aa : Graphs.neighborListOf(grafo, a)) {
				if (cammino.contains(aa)) {
				 peso=(int) grafo.getEdgeWeight(grafo.getEdge(a, aa));
				 break;
				}
			}
		}
		return peso*(cammino.size()-1);
	}
		
	}
