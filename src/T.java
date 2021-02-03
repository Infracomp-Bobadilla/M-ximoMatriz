import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class T extends Thread {  

	// ....................................................... MATRIZ

	// .. Caracteristicas

	private final static int LIM = 1000;

	private static int DIM;
	private static int[][] matriz;

	// .. Inicializar

	public static void inicializarMAtriz() {

		matriz = new int[DIM][DIM];

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

	private int id, maxEnFila;
	private ArrayList<Integer> filasEncargadas;

	// .. Inicializar cada 'Thread' con un identificador y sus filas correspondientes

	public T (int pId, ArrayList<Integer> pFilasEncargadas) {
		id = pId ;
		maxEnFila = -1;
		filasEncargadas = pFilasEncargadas;
	}

	// .. Encontrar el maxímo de las filas y actualizar el máximo global. 

	public void run () {

		// .. Encontrar mayo de la fila
		
		for(int i = 0; i < this.filasEncargadas.size(); i++) {
			for(int j = 0; j < DIM; j++){
				if(this.maxEnFila < matriz[filasEncargadas.get(i)][j]) this.maxEnFila = matriz[filasEncargadas.get(i)][j];
			}
		}
		
		

		// .. Imprimir el mayor si ya todos los threads se ejecutaron.

		if(oMax.anotar(this.maxEnFila, this.id)) 
			System.out.println("----------\n" + "El maxímo de la matriz es: " + oMax.darMaximo() + " - Localizado por el thread: " + oMax.darLocalizador());

	}

	public static void main(String[] args) {

		// .. Cantidad de filas de la matriz

		@SuppressWarnings("resource")
		Scanner lector = new Scanner (System.in);
		System.out.println("Ingrese el número de filas de la matriz:");
		DIM = lector.nextInt();

		// .. Cantidad de filas por thread

		System.out.println("Ingrese el número de filas por cada thread:");
		int filas = lector.nextInt();	

		// .. Inicializo e imprimo la matriz

		System.out.println("----------");
		System.out.println("  Matriz  ");
		System.out.println("----------");

		inicializarMAtriz();
		imprimirMatriz();

		// ........ Creo el objeto maximo y una cantidad FIJA de threads que cubren las dimensiones de la matriz

		// .. Cantidad de threads

		int numThreads = (int) Math.ceil((double) DIM/filas);

		// .. Máximo

		oMax = new Maximo(numThreads);

		// .. Contador para asignar

		int contador = 0;

		// .. Arrancar threads

		for (int i = 0; i < numThreads; i++) {

			// .. Asignar filas y impimir asignación

			ArrayList<Integer> cubrir = new ArrayList<>();
			ArrayList<Integer> asig = new ArrayList<>();

			for(int j = 0; j < filas; j++) {
				if(contador != DIM) {
					cubrir.add(contador++);
					asig.add(contador);
				}
					
			}
			
			System.out.println("El thread con id: " + (i+1) + " se encarga de las fila(s) " + asig.toString());
			
			// .. Iniciar Thread 
			
			new T (i,cubrir).start();
		}


	}

}