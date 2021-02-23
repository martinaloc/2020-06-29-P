package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	private PremierLeagueDAO dao;
	private Map<Integer,Match> map;
	private Graph<Match, DefaultWeightedEdge> graph;
	private List<Adiacenze> adiacenzeNelGrafo;

	public Model() {
		this.dao= new PremierLeagueDAO();
		this.map= new HashMap<Integer, Match>();
		this.dao.listAllMatches(map);
		
	}

	public void creaGrafo(int min, int mese) {
		
	this.graph= new SimpleWeightedGraph<Match, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	this.adiacenzeNelGrafo= new ArrayList<Adiacenze>();
	if(this.dao.listAllV(mese, map) == null)
	{
		System.out.print("Errore lettura vertex\n");
		return;
	}
	
	Graphs.addAllVertices(this.graph, this.dao.listAllV(mese, map));
	if( this.dao.listAdiacenze(mese, map,min)== null)
	{
		System.out.print("Errore lettura edge\n");
		return;
	}

	for(Adiacenze a : this.dao.listAdiacenze(mese, map,min)) 
	{
		Graphs.addEdge(this.graph,a.getM1(), a.getM2(), a.getPeso());
			/*
			 * if(this.graph.containsVertex(a.getM1()) &&
			 * this.graph.containsVertex(a.getM2())) { DefaultWeightedEdge e =
			 * this.graph.getEdge(a.getM1(), a.getM2()); if(e==null) {
			 * Graphs.addEdgeWithVertices(this.graph,a.getM1(), a.getM2(), a.getPeso());
			 * System.out.print(a.getM1().getMatchID() +" "+ a.getM2().getMatchID() +
			 * " "+a.getPeso()+"\n");
			 * 
			 * } }
			 */
	}
		}

	public Set<Match> setV()
	{
		return this.graph.vertexSet();
	}
	

	public Set<DefaultWeightedEdge> setE()
	{
		return this.graph.edgeSet();
	}

	public List<Adiacenze> connessioniMax(int mese) {
		List<Adiacenze> list = new ArrayList<Adiacenze>();	
		
		for(DefaultWeightedEdge e : this.graph.edgeSet())
		{
			if(this.graph.getEdgeWeight(e) == PesoMaxGrafo())
			{
				list.add(new Adiacenze(this.graph.getEdgeSource(e), this.graph.getEdgeTarget(e), (int) this.graph.getEdgeWeight(e)));
			}
		}
		Collections.sort(list);
		return list;
	}
	
	
	
	public int PesoMaxGrafo()
	{
		int pesoMax=0;
		for(DefaultWeightedEdge e : this.graph.edgeSet())
		{
			if(this.graph.getEdgeWeight(e) > pesoMax)
			{
				pesoMax=(int) this.graph.getEdgeWeight(e);
			}
		}
		return pesoMax;
	}
	
}
