package classes;

import java.util.LinkedList;
import java.util.List;

//Classe que representa um Conjunto e suas opera��es.
public class Conjunto <T extends Comparable<T>>{

	private List<T> elementos;

	//Construtor-Padr�o de Conjunto
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: nenhuma.
	public Conjunto() {
		elementos = new LinkedList<T>();
	}

	//Construtor.
	//Pr�-condi��es: elemento!= null.
	//P�s-condi��es: nenhuma.
	public Conjunto(T elemento) {
		this();
		elementos.add(elemento);
	}
	
	//Retorna a lista dos elementos do conjunto.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: lista de elementos.
	public List<T> getElementos() {
		return elementos;
	}
	
	//Insere um elemento no conjunto.
	//Pr�-condi��es: elemento != null.
	//P�s-condi��es: nenhuma.
	public void inserir(T elemento) {
		elementos.add(elemento);
	}
	
	//Faz uni�o entre conjuntos de elementos.
	//Pr�-condi��es: Other != null.
	//P�s-condi��es: nenhuma.
	public void uniao(Conjunto<T> other) {
		List<T> list = other.elementos;
		for (T t : list) {
			if(!this.elementos.contains(t)) {
				this.inserir(t);
			}
		}
	}

	//Compara 2 conjuntos.
	//Pr�-condi��es: obj != null.
	//P�s-condi��es: false se diferentes, true se iguais.
	@Override
	public boolean equals(Object obj) {
		Conjunto<T> other = (Conjunto<T>) obj;
		if(this.elementos.size() != other.elementos.size()) {
			return false;
		}
		
		return this.elementos.equals(other.elementos);
	}
	
	//Remove um elemento do conjunto.
	//Pr�-condi��es: elemento != null.
	//P�s-condi��es: nenhuma.
	public void remover(T elemento) {
		elementos.remove(elemento);
	}
	
	//Verifica se um elemento est� no conjunto.
	//Pr�-condi��es: elemento != null.
	//P�s-condi��es: true se est�, false se n�o est�.
	public boolean contains(T elemento) {
		return elementos.contains(elemento);
	}
	
	//Imprime o conjunto.
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: nenhuma.
	public void printConjunto() {
		for (T t : elementos) {
			System.out.print(t + " ");
		}
		System.out.println();
	}
	
	
}
