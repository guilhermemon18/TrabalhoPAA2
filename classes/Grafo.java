package classes;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.MutableGraph;

enum Cor {branco,cinza,preto};//enumerado para as cores dos algoritmos DFS e BFS.

//Classe que representa um grafo.
public class Grafo {
	private List<Adjacencia>[] lista;
	private Integer v;
	private boolean orientado;
	private Integer timestamp = 0;
	private Cor c[];
	private Integer pi[] ;
	private Integer d[];
	private Integer f[];

	//Construtor do grafo.
	//Pré-condições: v>0.
	//Pós-condições: nenhuma.
	public Grafo(Integer v, boolean orientado) {
		this.v = v;
		this.orientado = orientado;
	}


	//Construtor do grafo.
	//Pré-condições: nomeArquivo != null.
	//Pós-condições: throws ioException se o arquivo não existir ou não for encontrado.
	public Grafo(String nomeArquivo) throws IOException {
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
		int c =  buffRead.read();
		char caractere = (char) c;
		while(caractere != '=' ) {
			c =  buffRead.read();
			caractere = (char) c;
		}
		this.orientado = buffRead.readLine().equalsIgnoreCase("sim");
		c =  buffRead.read();
		caractere = (char) c;
		while(caractere != '=' ) {
			c =  buffRead.read();
			caractere = (char) c;
		}
		//caractere = (char) buffRead.read();
		this.v = Integer.parseInt(buffRead.readLine());
		this.lista = new ArrayList[this.v];
		for(int i = 0; i < this.v; i++) {
			this.lista[i] = new ArrayList<Adjacencia>();
		}
		//lendo as arestas:
		int v = 0;
		int w = 0;
		int peso = 0;
		while(c != -1) {
			while(caractere != '(' ) {
				c =  buffRead.read();
				caractere = (char) c;
			}
			v = Integer.parseInt(String.valueOf((char)buffRead.read()));

			while(caractere != ',' ) {
				c =  buffRead.read();
				caractere = (char) c;
			}

			w = Integer.parseInt(String.valueOf((char)buffRead.read()));

			while(caractere != ':' ) {
				c =  buffRead.read();
				caractere = (char) c;
			}

			peso = Integer.parseInt(buffRead.readLine());

			inserir(new Aresta(v, w, peso));
			c =  buffRead.read();
			caractere = (char) c;

		}

		buffRead.close();

	}


	//Retorna a quantidade de vértices no grafo.
	//Pré-condições: nenhuma.
	//Pós-condições: quantidade de vertices.
	public Integer getV() {
		return v;
	}

	//Verifica se o grafo é orientado
	//Pré-condições: nenhuma.
	//Pós-condições: true se for orientado, caso contrário false.
	public boolean isOrientado() {
		return orientado;
	}

	//Insere uma aresta no grafo.
	//Pré-condições: a != null;
	//Pós-condições: nenhuma
	public void inserir (Aresta a) {
		lista[a.getV()].add(new Adjacencia(a.getW(),a.getPeso()));
		Collections.sort(lista[a.getV()]);
		if(!orientado) {
			lista[a.getW()].add(new Adjacencia(a.getV(),a.getPeso()));
			Collections.sort(lista[a.getW()]);
		}
	}

	//Visita o vértice indice
	//Pré-condições: v != null, indice >= 0, visita_vertices != null;
	//Pós-condições: nenhuma.
	private void DFS_visit(List<Adjacencia> v, Integer indice, List<Integer> visita_vertices) {
		c[indice] = Cor.cinza;
		visita_vertices.add(indice);
		timestamp++;
		d[indice] = timestamp;
		for (Adjacencia adj : v) {
			if(c[adj.getV()] == Cor.branco) {
				pi[adj.getV()] = indice;
				DFS_visit(lista[adj.getV()], adj.getV(), visita_vertices);
			}
		}
		c[indice] = Cor.preto;
		timestamp++;
		f[indice] = timestamp;
	}

	//Busca em profundidade
	//pré-requisitos: origem >= 0
	//Pós-requisitos: lista na ordem da visitação dos vértices.
	public List<Integer> DFS(Integer origem){
		List<Integer> visita_vertices = new ArrayList<Integer>();
		c = new Cor[v];
		pi = new Integer[v];
		d = new Integer[v];
		f = new Integer[v];

		for(int i = 0; i < v; i++) {
			c[i] = Cor.branco;
			pi[i] = -1;
		}
		timestamp = 0;

		DFS_visit(lista[origem], origem,visita_vertices);
		return visita_vertices;
	}

	//Busca em Largura
	//pré-requisitos: origem >= 0
	//Pós-requisitos: lista na ordem da visitação dos vértices.
	public List<Integer> BFS(Integer origem){
		List<Integer> visita_vertices = new ArrayList<Integer>();
		Queue<Integer> q = new LinkedList<Integer>();


		c = new Cor[v];
		pi = new Integer[v];
		d = new Integer[v];
		f = new Integer[v];

		for(int i = 0; i < v; i++) {
			if(i != origem) {
				c[i] = Cor.branco;
				d[i] = Integer.MAX_VALUE;
				pi[i] = -1;
			}
		}

		c[origem] = Cor.cinza;
		visita_vertices.add(origem);
		pi[origem] = -1;
		d[origem] = 0;
		q.add(origem);

		while(!q.isEmpty()) {
			Integer u = q.peek();
			List<Adjacencia> aux = lista[u];
			for (Adjacencia adjacencia : aux) {
				int info = adjacencia.getV();
				if(c[info] == Cor.branco) {
					c[info] = Cor.cinza;
					visita_vertices.add(info);
					d[info] = d[u] + 1;
					pi[info] = u;
					q.add(info);
				}
			}
			q.poll();
			c[u] = Cor.preto;
		}

		return visita_vertices;
	}

	//Inicia os vetores auxiliares de acordo com a o vertice de origem.
	//Pré-condições: origem>=0;
	//Pós-condições: nenhuma.
	private void InicializaOrigem(int origem) {
		for(int i = 0; i < v; i++) {
			d[i] =  Integer.MAX_VALUE;
			pi[i] = -1;
		}
		d[origem] = 0;
	}


	// Metodo que repetidamente diminui o limite superior do peso do menor caminho.
	//Pré-condições: a!= null.
	//Pós-condições: nenhuma.
	private void Relax(Aresta a) {
		if(d[a.getW()] > d[a.getV()] + a.getPeso() && d[a.getV()] != Integer.MAX_VALUE) {
			d[a.getW()] = d[a.getV()] + a.getPeso();
			pi[a.getW()] = a.getV();
		}
	}

	//Obtem a aresta formada por u e v (u,v)
	//Pré-condições: u >= 0, v>= 0.
	//Pós-condições: retorna a Aresta.
	private Aresta getAresta(Integer u, Integer v) {

		List<Adjacencia> l = lista[u];
		for (Adjacencia adjacencia : l) {
			if(adjacencia.getV() == v) {
				return new Aresta(u,v,adjacencia.getPeso());
			}
		}
		return null;

	}


	//Preenche o vetor com as arestas de g
	//Pré-condições: a!= null.
	//Pós-condições: Retorna |E|, quantidade de arestas
	private int grafoArestas(Aresta a[], boolean Repetir) {
		int count = 0;
		List<Adjacencia> aux;
		for(int i = 0; i < v; i++) {
			aux = lista[i];
			for (Adjacencia adjacencia : aux) {
				if( !Repetir) {
					if(adjacencia.getV() > i ) 
					{
						a[count++] = new Aresta(i,adjacencia.getV(),adjacencia.getPeso());
					}
				}else {
					a[count++] = new Aresta(i,adjacencia.getV(),adjacencia.getPeso());
				}
			}
		}
		return count;
	}

	//Obtem as arestas do grafo.
	//Pré-condições: nenhuma.
	//Pós-condições: Lista de arestas pertencentes ao grafo.
	public List<Aresta> getArestas(){
		List<Aresta> l = new LinkedList<Aresta>();
		List<Adjacencia> aux;
		if(isOrientado()) {
			for(int i = 0; i < v; i++) {
				aux = lista[i];
				for (Adjacencia adjacencia : aux) {
					l.add(new Aresta(i,adjacencia.getV(),adjacencia.getPeso()));
				}
			}
		}else {
			for(int i = 0; i < v; i++) {
				aux = lista[i];
				for (Adjacencia adjacencia : aux) {
					if(adjacencia.getV() > i)
						l.add(new Aresta(i,adjacencia.getV(),adjacencia.getPeso()));
				}
			}
		}
		return l;
	}

	//Retorna a quantidade de arestas do grafo.
	//Pré-condições: nenhuma.
	//Pós-condições: quantidade de arestas.
	public int countArestas() {
		int count = 0;
		List<Adjacencia> aux;
		for(int i = 0; i < v; i++) {
			aux = lista[i];
			for (Adjacencia adjacencia : aux) {
				if(adjacencia.getV() > i) 
					count++;
			}
		}
		return count;
	}

	//Bellman-Ford: Calcula o menor caminho
	//Pré-condições: origem >=0
	//Pós-condições: caso exista ciclo de peso negativo, o algoritmo retorna false, caso contrário retorna true.
	public boolean Bellman_Ford(int origem) {
		d = new Integer[v];
		pi = new Integer[v];

		InicializaOrigem(origem);

		Aresta e[] = new Aresta[v * v];
		int count_arestas = grafoArestas(e,true);

		for(int i = 0; i < v -1; i++) {
			for(int j = 0; j < count_arestas; j++) {
				Relax(e[j]);
			}
		}
		for(int i = 0; i < count_arestas; i++) {
			if(d[e[i].getW()] > d[e[i].getV()] + e[i].getPeso() && d[e[i].getV()] != Integer.MAX_VALUE)
				return false;
		}


		//imprimir os caminhos:
		System.out.println("Origem: " + origem);
		for(int i = 0; i < v; i++) {
			System.out.print("Destino:  " + i + " dist.:  " + d[i] +  " caminho:  ");
			printCaminho(i);
			System.out.println(i);
		}


		return true;
	}

	//Encontra o primeiro conjunto que contém o elemento.
	//Pré-condições: elemento != null
	//Pós-condições: retorna o conjunto se encontrar o elemento, retorna null se não encontrar.
	private Conjunto<Integer> findConjuntobyElemento(ArrayList<Conjunto<Integer>> c, Integer elemento){

		for (Conjunto<Integer> conjunto : c) {
			if(conjunto.contains(elemento)) {
				return conjunto;
			}
		}
		return null;
	}

	//Kruskal: Calcula a árvore geradora mínima.
	//Pré-condições: nenhuma.
	//Pós-condições: Conjunto com as arestas da árvore geradora mínima.
	public Conjunto<Aresta> KRUSKALL() {
		Aresta a[] = new Aresta[countArestas()];
		int qtdArestas = grafoArestas(a,false);
		Conjunto<Aresta> A = new Conjunto<Aresta>();
		ArrayList<Conjunto<Integer>> conjutosVertices = new ArrayList<Conjunto<Integer>>();

		for(int i = 0; i < v; i++) {
			conjutosVertices.add(new Conjunto<Integer>(i));
		}
		Arrays.sort(a);

		for(int i = 0; i < qtdArestas; i++){
			Conjunto<Integer> u = findConjuntobyElemento(conjutosVertices, a[i].getV());
			Conjunto<Integer> v = findConjuntobyElemento(conjutosVertices, a[i].getW());

			if(!u.equals(v)) {
				A.uniao(new Conjunto<Aresta>(a[i]));
				u.uniao(v);
				conjutosVertices.remove(v);

			}
		}
		return A;
	}


	//Prim: calcula a árvore geradora mínima.
	//Pré-condições: origem >= 0;
	//Pós-condições: lista com as arestas da árvore geradora mínima.
	public List<Aresta> Prim(int origem){
		List<Aresta> l = new LinkedList<Aresta>();
		Queue<Integer> q = new LinkedList<Integer>();
		Integer key[] = new Integer[v];
		pi = new Integer[v];

		q.add(origem);
		for(int i = 0; i < v; i++) {
			if(i != origem)
				q.add(i);
			key[i] = Integer.MAX_VALUE;
		}

		key[origem] = 0;
		pi[origem] = -1;



		while(!q.isEmpty()) {
			Integer u = q.poll();
			for(Adjacencia adj : lista[u]) {
				Integer v = adj.getV();
				Integer w = getAresta(u,v).getPeso();
				if(q.contains(v) &&  w < key[v]) {
					pi[v] = u;
					key[v] = w;
				}
			}

			Collections.sort((List<Integer>)q, new Comparator<Integer>() {
				@Override
				public int compare(Integer i1, Integer i2) {
					if(key[i1] < key[i2]) {
						return -1;
					}else if(key[i1] > key[i2])
						return 1;
					return 0;
				}
			});
		}
		for(int i = 0; i < v; i++) {
			if( pi[i] != null && pi[i] != -1) {
				l.add(getAresta(i, pi[i]));
			}
		}

		return l;
	}


	//imprime o caminho a partir de n.
	//Pré-condições: n >=0
	//Pós-condições: nenhuma.
	private void printCaminho(int n) {
		if(pi[n] != -1) {
			printCaminho(pi[n]);
			System.out.print(pi[n] + " ");
		}
	}

	//imprime o grafo
	//Pré-condições: nenhuma.
	//Pós-condições: nenhuma.
	public void imprimirGrafo() {
		for (int i = 0; i < v ; i++) {
			List<Adjacencia> l = lista[i];
			for (Adjacencia adjacencia : l) {
				System.out.println(new Aresta(i,adjacencia.getV(),adjacencia.getPeso()));
			}
		}
	}

	//Desenha o grafo
	//Pré-condições: nome_arquivo != null;
	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta> aux = this.getArestas();
		for (Aresta aresta : aux){
			Link a = mutNode(aresta.getW().toString()).linkTo();
			a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			G.add(mutNode(aresta.getV().toString()).addLink(a));

		}

		try {
			Graphviz.fromGraph(G).width(200).render(Format.PNG).toFile(new File("desenhos/" + nome_arquivo + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//Desenha o grafo
	//Pré-condições: nome_arquivo != null, l != null;
	//Pós-condições: nenhuma
	public void desenhaGrafo(String nome_arquivo,List<Aresta> l) {
		MutableGraph G = mutGraph(nome_arquivo).setDirected(this.isOrientado());

		List<Aresta> aux = this.getArestas();
		for (Aresta aresta : aux){
			Link a = mutNode(aresta.getW().toString()).linkTo();
			if(l.contains(aresta) || l.contains(new Aresta(aresta.getW(),aresta.getV(),aresta.getPeso()))) {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.RED);

			}else {
				a = a.with(Style.BOLD, Label.of(aresta.getPeso().toString()), Color.BLACK);
			}
			G.add(mutNode(aresta.getV().toString()).addLink(a));
		}

		try {
			Graphviz.fromGraph(G).width(200).render(Format.PNG).toFile(new File("desenhos/" + nome_arquivo + ".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}



}
