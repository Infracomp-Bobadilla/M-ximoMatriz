import java.util.concurrent.ThreadLocalRandom;

public class T extends Thread {  

	// ....................................................... MATRIZ
	
	// .. Caracteristicas
	
	private final static int DIM = 5;
	private final static int LIM = 1000;
	private static int[][] matriz = new int[DIM][DIM];
	
	// .. Inicializar

	public static void inicializarMAtriz() {

		for(int i = 0; i < DIM; i++) {
			for(int j = 0; j < DIM; j++) {
				matriz[i][j] = ThreadLocalRandom.current().nextInt(0, LIM);
			}
		}
	}
	
	// .. Imprimir

	public static void imprimirMatriz() {

		for(int i = 0; i < DIM; i++) {
			for(int j = 0; j < DIM; j++) {
				System.out.print(matriz[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println("----------");
	}

	// ....................................................... Máximo 

	private static Maximo oMax;
	
	private int id = 0 ;
	private int maxEnFila = -1;
	
	// .. Inicializar cada 'Thread' con un identificador (id = fila de la matriz)

	public T (int pId) {
		id = pId ;
	}
	
	// .. Encontrar el maxímo de la fila y actualizar el máximo global. 

	public void run () {
		
		// .. Encontrar mayo de la fila
		
		for(int j = 0; j < DIM; j++){
			if(this.maxEnFila < matriz[this.id][j]) this.maxEnFila = matriz[this.id][j];
		}
		
		// .. Imprimir el mayor si ya todos los threads se ejecutaron.
		
		if(oMax.anotar(this.maxEnFila, this.id)) 
			System.out.println("El maxímo de la matriz es: " + oMax.darMaximo() + " - Localizado por el thread: " + oMax.darLocalizador());

	}

	public static void main(String[] args) {
		
		// .. Inicializo e imprimo la matriz
		
		System.out.println("----------");
		System.out.println("  Matriz  ");
		System.out.println("----------");

		inicializarMAtriz();
		imprimirMatriz();
		
		// .. Creo el objeto maximo y una cantidad FIJA de threads igual a las dimensiones de la matriz
		
		int numThreads = DIM;
		oMax = new Maximo(numThreads);

		for (int i = 0; i < numThreads; i++) 
			new T (i).start();

	}

}