package main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import classes.Aresta;
import classes.Conjunto;
import classes.Grafo;

//Classe main com o método estático main para inciar o programa.
public class Main {
	
	//Função main.
	public static void main(String[] args) {
		Grafo g = null;
		Scanner entrada = new Scanner(System.in);
		String nome_arquivo = null;
		
		while(g == null) {
			System.out.println("Carregar Grafo");
			System.out.println("Digite o nome do arquivo");
			nome_arquivo = entrada.nextLine();

			try {
				g = new Grafo(nome_arquivo);
			} catch (IOException e) {
				System.out.println("Nome de arquivo inválido!");
			}
		}
		
		int option = 0;
		int origem = 0;

		do {
			System.out.print("Escolha uma opção:\n");
			System.out.print("0.Busca em Profundidade\n");
			System.out.print("1.Busca em Largura\n");
			System.out.print("2.Bellman-Ford\n");
			System.out.print("3.Kruskal\n");
			System.out.print("4.Prim\n");
			System.out.println("5.Desenhar Grafo");
			System.out.print("6.Sair\n");
			option = entrada.nextInt();
			switch(option) {
			case 0://Busca em profundidade
				System.out.print("Digite o vértice de origem!\n");
				origem = entrada.nextInt();
				List<Integer> caminho = g.DFS(origem);
				for (Integer integer : caminho) {
					System.out.print(integer + " ");
				}
				System.out.print("\n");
				break;

			case 1://Busca em Largura.
				System.out.print("Digite o vértice de origem!\n");
				origem = entrada.nextInt();
				caminho = g.BFS(origem);
				for (Integer integer : caminho) {
					System.out.print(integer + " ");
				}
				System.out.print("\n");
				break;
			case 2://Bellman-Ford
				if(!g.isOrientado()) {
					System.out.print("O grafo carregado não é orientado! Bellman Ford precisa Grafo Orientado!\n");
				} else {
					System.out.print("Digite o vértice de origem!\n");
					origem = entrada.nextInt();
					if(g.Bellman_Ford(origem)) {
						System.out.println("Cálculo efetuado com sucesso!");
					}else {
						System.out.println("Existe ciclo de peso negativo!");
					}
				}
				break;
			case 3://Kruskal
				if(g.isOrientado()) {
					System.out.print("O grafo carregado é orientado! Kruskal precisa Grafo Não-Orientado!\n");
				} else {
					Conjunto<Aresta> c =g.KRUSKALL();
					List<Aresta> l = c.getElementos();
					int pesoTotal = 0;
					
					for (Aresta aresta : l) {
						pesoTotal += aresta.getPeso();
					}
					System.out.println("Peso total: " + pesoTotal);
					System.out.print("Arestas: ");
					c.printConjunto();
					
					String nomeGrafo = nome_arquivo.substring(0, nome_arquivo.indexOf('.')) + "Kruskal";
					g.desenhaGrafo(nomeGrafo, l);
				}
				break;

			case 4://Prim
				if(g.isOrientado()) {
					System.out.print("O grafo carregado é orientado! Prim precisa Grafo Não-Orientado!\n");
				} else {
					System.out.print("Digite o vértice de origem!\n");
					origem = entrada.nextInt();
					List<Aresta> l = g.Prim(origem);
					int pesoTotal = 0;
					for (Aresta aresta : l) {
						if(aresta != null) {
							pesoTotal += aresta.getPeso();
						}
					}
					System.out.println("Vértice inicial: " + origem);
					System.out.println("peso total: " + pesoTotal);
					System.out.println("arestas: ");
					for (Aresta aresta : l) {
						System.out.print(aresta + " , ");
					}
					System.out.println();
					String nomeGrafo = nome_arquivo.substring(0, nome_arquivo.indexOf('.')) + "Prim";
					g.desenhaGrafo(nomeGrafo, l);
				}
				break;
			case 5://Desenhar Grafo.
				String nomeGrafo = nome_arquivo.substring(0, nome_arquivo.indexOf('.'));
				g.desenhaGrafo(nomeGrafo);
				break;
			case 6://Sair
				System.out.println("Programa Encerrado!");
				break;
			default://Opção inválida.
				System.out.print("Escolha uma opção válida!\n");

			}
		} while(option != 6);



		entrada.close();

	}
}
