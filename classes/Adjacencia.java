package classes;

//Classe respons�vel por armazenar um elemento adjacente a um v�rtice.
public class Adjacencia implements Comparable<Adjacencia>{

	private Integer v;
	private Integer peso;
	
	//Construtor de uma adjacencia.
	//Pr�-condi�oes: v >= 0, peso != null.
	//P�s-condi��es: nenhuma
	public Adjacencia(Integer v, Integer peso) {
		super();
		this.v = v;
		this.peso = peso;
	}

	//Obt�m o v�rtice armazenado neste elemento adjacente.
	//Pr�-condi�oes: nenhuma.
	//P�s-condi��es: vert�ce armazenado.
	public Integer getV() {
		return v;
	}

	//Obtem o peso no elemento adjacente a um v�rtice.
	//Pr�-condi�oes: nenhuma.
	//P�s-condi��es: peso.
	public Integer getPeso() {
		return peso;
	}

	//Compara duas adjacencias.
	//Pr�-condi��es: arg0 != null.
	//P�s-condi��es: -1 se menor, 1 se maior e 0 se iguais.
	@Override
	public int compareTo(Adjacencia arg0) {
		// TODO Auto-generated method stub
		return this.v.compareTo(arg0.v);
	}

	//Converte para string
	//Pr�-condi��es: nenhuma.
	//P�s-condi��es: String representando adjacencia.
	@Override
	public String toString() {
		return "Adjacencia [v=" + v + ", peso=" + peso + "]";
	}
	
	
	
}
