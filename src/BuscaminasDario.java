import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * El t�pico juego del buscaminas.
 * Es tan t�pico que no hacen falta explicaciones.
 */

/**
 * @author Dar�o
 *
 */
public class BuscaminasDario {

	//Declaramos un esc�ner para pedir introducir datos por pantalla
	//Lo hacemos como atributo de clase para que sea accesible desde todos los m�todos
	static Scanner sc = new Scanner(System.in);
	//Atributo que almacena la puntuaci�n m�s alta que ha sacado el jugador durante la ejecuci�n del programa
	//Lo inicializamos en 0 porque, si el jugador es un patata y nunca gana, tendremos que poder devolver alg�n valor
	//La verdad es que lo he definido como un atributo de clase porque lo he implementado al final y el main me
	//hab�a quedado muy bonito y limpio. No quer�a guarrearlo.
	static int maxScore = 0;
	
	/**
	 * @param args Los argumentos de inicializaci�n que se pueden introducir al llamar al programa desde consola
	 */
	public static void main(String[] args) {
		
		//Declaramos un valor booleano que nos servir� como flag para abandonar el bucle que contendr� el men� del programa
        boolean difficultyFlag = true;
        //La siguiente variable almacenar� la dificultad introducida por pantalla que desea el usuario
        int difficulty;
        
        //Declaramos una serie de constantes que utilizaremos en la opci�n de juego personalizado (lo har� copiando los valores del buscaminas de Windows)
        //El m�ximo de columnas aqu� es lo �nico distinto al buscaminas de Windows ya que depende del n�mero de letras que hay en el abecedario,
        //Esto es porque voy a presentar la tabla rodeada de letras y n�meros para facilitar la visualizaci�n de la posici�n
        final int minrow = 9;
        final int maxrow = 24;
        final int mincol = 9;
        final int maxcol = 26;
        final int minmine = 10;
        //El m�ximo de minas permitido lo calcularemos en base a una f�rmula usando los valores de filas y columnas introducidos por el usuario
        int maxmine;
        
        //Declaramos una variable que contendr� el n�mero de filas que contendr� la tabla
        int row = 0;
        //Declaramos una variable que contendr� el n�mero de columnas que contendr� la tabla
        int column = 0;
        //Declaramos una variable que contendr� el n�mero de minas que contendr� la tabla
        int mine = 0;
        
        //Mostramos el logotipo del juego por pantalla
        System.out.println("                                                                                   _ ._  _ , _ ._\n                                                                                 (_ ' ( `  )_  .__)\n______  _______                                                                ( (  (    )   `)  ) _)\n___   |/  /__(_)_____________________      ________________________________   (__ (_   (_ . _) _) ,__)\n__  /|_/ /__  /__  __ \\  _ \\_  ___/_ | /| / /  _ \\  _ \\__  __ \\  _ \\_  ___/       `~~`\\ ' . /`~~`\n_  /  / / _  / _  / / /  __/(__  )__ |/ |/ //  __/  __/_  /_/ /  __/  /                ;   ;\n/_/  /_/  /_/  /_/ /_/\\___//____/ ____/|__/ \\___/\\___/_  .___/\\___//_/                 /   \\\n                                                      /_/                _____________/_ __ \\_____________\n");
        //Esperamos a que el jugador pulse la tecla enter antes de mostrar el men� porque queda m�s elegante
        System.out.println("Presione enter para comenzar...");
        //Tiene que ser la tecla enter porque como es una aplicaci�n de consola, la introducci�n no avanza hasta que se pulse intro
        sc.nextLine();
        
        //Como hemos hecho que la variable difficultyFlag sea verdadera, el bucle se ejecutar� hasta que digamos lo contrario
        while(difficultyFlag) {
        	//Inicializamos la dificultad con valor 0 porque si el jugador jugara varias veces, podr�a volver a entrar en el case
        	//al introducir algo no v�lido, como una cadena, ya que el valor de difficulty no se modificar�a
        	difficulty = 0;
        	
        	System.out.println("Seleccione el nivel de dificultad:\n[1]F�cil 10x10\n[2]Moderado 16x16\n[3]Dif�cil 22x22\n[4]Imposible 26x26\n[5]Personalizado\n[6]Salir");
        	//En principio, si el usuario introduce algo que no es v�lido, el programa se limitar� a pedirlo otra vez
        	try {
            	//Pedimos por pantalla el nivel de dificultad deseado
            	difficulty = Integer.parseInt(sc.nextLine());
        	}
        	//Capturamos el error por argumento incorrecto para que el programa no se interrumpa
        	catch (IllegalArgumentException e) {
        	}
        	
        	//Comprobaremos en una estructura switch case el valor introducido para la dificultad
        	switch (difficulty) {
        	
        	case 1:
        		//F�cil 10x10
        		
        		//Declaramos las dimensiones de la tabla
        		row = 10;
        		column = 10;
        		
        		//Iniciamos el juego
        		mineGame(row, column, minePercentage(row, column, 10));
        		break;
        	case 2:
        		//Moderado 16x16
        		
        		//Declaramos las dimensiones de la tabla
        		row = 16;
        		column = 16;
        		
        		//Iniciamos el juego
        		mineGame(row, column, minePercentage(row, column, 15));
        		break;
        	case 3:
        		//Dif�cil 22x22
        		
        		//Declaramos las dimensiones de la tabla
        		row = 22;
        		column = 22;
        		
        		//Iniciamos el juego
        		mineGame(row, column, minePercentage(row, column, 25));
        		break;
        	case 4:
        		//Imposible 26x26
        		
        		//Declaramos las dimensiones de la tabla
        		row = 26;
        		column = 26;
        		
        		//Iniciamos el juego
        		mineGame(row, column, minePercentage(row, column, 35));
        		break;
        	case 5:
        		//Personalizado
        		
        		//Pedimos las filas
        		row = dataIntroduction("filas", minrow, maxrow);
        		
        		//Pedimos las columnas
        		column = dataIntroduction("columnas", mincol, maxcol);
        		
        		//Pedimos las minas
        		//El m�ximo de minas se calcular� en base a una f�rmula que he deducido observando c�mo se comporta el buscaminas de Windows
        		maxmine = row * column - (row + column - 1);
        		mine = dataIntroduction("minas", minmine, maxmine);
        		        		
        		//Iniciamos el juego
        		mineGame(row, column, mine);
        		
        		break;
        	case 6:
        		difficultyFlag = false;
        		break;
        		
        	}
        	
        }
        
        //Cerramos el esc�ner pues no vamos a introducir m�s datos por pantalla
        sc.close();
        
	}
	
	/**
	 * M�todo que me servir� para pedir los par�metros del juego personalizado por pantalla.
	 * El m�todo se asegurar� que el dato est� entre los m�nimos y los m�ximos establecidos.
	 * Si el dato se sale de los l�mites, los subir� o bajar� al l�mite dependiendo de si es 
	 * mayor o menor que los l�mites.
	 * @param stuff Lo que vamos a pedir por pantalla. Se introducir� una cadena que ser� el nombre de lo que se le va a pedir al usuario en una petici�n gen�rica.
	 * @param min El valor m�nimo. El dato no puede ser menor.
	 * @param max El valor m�ximo. El dato no puede ser mayor.
	 * @return Un entero que se le ha pedido al usuario y �ste ha introducido por pantalla.
	 */
	public static int dataIntroduction(String stuff, int min, int max) {
		
		//Declaramos un flag para controlar la salida del bucle
        boolean flag = false;
        //Declaramos una variable que almacene el dato que se pide por pantalla
        int data = 0;
        
        //El bucle se ejecutar� mientras el valor de flag sea false
		while (!flag) {
			//Controlamos que el usuario introduzca algo v�lido
    		try {
    			//Pedimos un valor informando del m�ximo y el m�nimo
        		System.out.printf("Escriba el n�mero de %s(min:%d, max:%d): \n", stuff, min, max);
        		data = Integer.parseInt(sc.nextLine());
        		//Si el usuario ha introducido bien el valor el flag cambiar� a true y se le dejar� de pedir
        		flag = true;
    		}
    		//Si el argumento introducido no es v�lido, devolvemos un mensaje por pantalla
    		catch (IllegalArgumentException e) {
    			System.out.println("El dato introducido no es v�lido.");
    		}
		}
		//Una vez tengamos introducido el valor, comprobamos si est� entre el m�ximo y el m�nimo
		//Si no est� entre los par�metros que consideramos aceptables, lo cambiamos al m�nimo o al m�ximo seg�n el caso
		//Comprobamos si el valor es mayor que el m�ximo, si lo es, reducimos su valor hasta el m�ximo
		if (data > max) {
			data = max;
		}
		//Comprobamos si el valor es menor que el m�nimo, si lo es, aumentamos su valor hasta el m�nimo
		if (data < min) {
			data = min;
		}
		
		//Devolvemos el dato
		return data;
		
	}
	
	/**
	 * M�todo que usar� flood filling para destapar las casillas contiguas que no tengan minas al seleccionar una casilla que no sea adyacente a una mina.
	 * El m�todo se llamar� recursivamente a s� mismo.
	 * El flood filling es un algoritmo que determina el �rea conectada a un nodo en un array multidimensional.
	 * En nuestro caso nos interesa que el algoritmo sea de 8 direcciones para que no queden casillas sin revelar en diagonal.
	 * @param tableMine La tabla que contiene la soluci�n del juego.
	 * @param tableEmpty La tabla que ve el jugador y que no contiene todos los datos.
	 * @param row La fila de la casilla que estamos comprobando.
	 * @param column La columna de la casilla que estamos comprobando.
	 * @param cnt El contador que se usa para saber si el jugador ha destapado todas las casillas en las que no hay una mina.
	 */
	public static int flood(int[][] tableMine, int[][] tableEmpty, int row, int column, int cnt) {
		
		//Hacemos un bloque try catch por si al hacer el flood filling recurrente alg�n �ndice se sale de la matriz
		//Vamos a hacer que no devuelva error para que el programa no se interrumpa y no tengamos que hacer m�s comprobaciones por la posici�n
		try {
			//Hacemos una serie de comprobaciones por las que no ejecutaremos el m�todo:
			//Si la casilla est� marcada
			//Si la casilla ya ha sido descubierta
			//Si la casilla es una mina
			//Si la fila es menor que 0 o mayor que la longitud de la primera dimensi�n del array (est� fuera de l�mites)
			//Si la columna es menor que 0 o mayor que la longitud de la segunda dimensi�n del array (est� fuera de l�mites)
			if (tableEmpty[row][column] == -3 || tableEmpty[row][column] != -2 || tableMine[row][column] == -1 || row < 0 || column < 0 || row > tableMine.length || column > tableMine[0].length) {
				//Salimos del m�todo devolviendo el contador (un dato que, por cierto, tenemos que arrastrar por el m�todo ya que no podemos hacer que sea un atributo de clase)
				return cnt;
			}	
			
			//Si la casilla no es 0, quiere decir que es adyacente a una mina, de manera que no ejecutaremos el algoritmo en ella.
			//De esta manera estableceremos unos l�mites dentro de los que se encontrar� el �rea de casillas que queremos desvelar.
			if (tableEmpty[row][column] != 0) {
				//Si no es una mina sumamos 1 al contador del juego
				cnt += 1;
				//Destapamos la casilla que hemos comprobado
				tableEmpty[row][column] = tableMine[row][column];
				
				//Llamamos al m�todo para que compruebe las casillas superior, inferior, anterior, posterior (en cruz) y sus diagonales
				//si la casilla que estamos comprobando es 0
				//Esto es para que no se destapen las casillas contiguas a una casilla que est� tocando una mina pero se pueda
				//revelar la propia casilla gracias a una ejecuci�n anterior del m�todo sobre una casilla 0 que sea contigua a esa
				//Adem�s, como el m�todo va manipulando el contador del juego, vamos cambi�ndolo en la llamada recursiva
				if (tableMine[row][column] == 0) {
					//Las casillas en cruz
					cnt = flood(tableMine, tableEmpty, row - 1, column, cnt);
					cnt = flood(tableMine, tableEmpty, row + 1, column, cnt);
					cnt = flood(tableMine, tableEmpty, row, column - 1, cnt);
					cnt = flood(tableMine, tableEmpty, row, column + 1, cnt);
					//Las casillas diagonales
					cnt = flood(tableMine, tableEmpty, row - 1, column - 1, cnt);
					cnt = flood(tableMine, tableEmpty, row - 1, column + 1, cnt);
					cnt = flood(tableMine, tableEmpty, row + 1, column - 1, cnt);
					cnt = flood(tableMine, tableEmpty, row + 1, column + 1, cnt);
				}
			}
			
		}
		catch (ArrayIndexOutOfBoundsException e) {
			//No hacemos nada
		}
		
		//Devolvemos el contador del juego
		return cnt;
		
	}

	/**
	 * M�todo que marcar� casillas que el jugador cree que pueden ser minas.
	 * @param row La fila de la casilla a marcar.
	 * @param column La columna de la casilla a marcar.
	 * @param table La tabla de juego.
	 */
	public static void mark(int row, int column, int[][] table) {
		
		//Cambiamos el valor de la casilla a -3, el valor de las casillas marcadas,
		//si la casilla no est� marcada y no ha sido descubierta, lo que se indica con el valor -2
		if (table[row][column] == -2) {
			table[row][column] = -3;
		}
		//Si la casilla est� marcada, la desmarcamos
		else if (table[row][column] == -3) {
			table[row][column] = -2;
		}
		
	}
	
	/**
	 * M�todo que me calcula la puntuaci�n que he sacado en una partida de buscaminas.
	 * @param row El n�mero de filas de la tabla.
	 * @param column El n�mero de columnas de la tabla.
	 * @param mine El n�mero de minas de la tabla.
	 * @param time El tiempo transcurrido durante la partida.
	 * @return Un entero con la puntuaci�n obtenida.
	 */
	public static int points(int row, int column, int mine, long time) {
		
		//Variable que almacenar� la puntuaci�n a devolver
		int score = 0;
		//Variable que almacenar� el tiempo en segundos
		long s;
		//Variable que almacenar� el m�ximo de tiempo que se podr� estar en la partida para obtener alg�n punto
		int limit;
		//Variable que almacenar� el tama�o de la tabla
		int size = row * column;
		
		//Almacenamos el tiempo a segundos
		s = TimeUnit.MILLISECONDS.toSeconds(time);
		
		//Aplicamos la f�rmula para calcular el m�ximo de tiempo que puede transcurrir y que se sigan consiguiendo puntos
		//La f�rmula me la he inventado yo, simplemente estuve comprobando cosas hasta que me diera algo razonable
		//No uso la dificultad en la f�rmula porque cree un quinto apartado (personalizado) que no es una dificultad 
		//en s� y que ser�a m�s engorroso de comprobar, as� que he obviado la utilizaci�n de la dificultad en la f�rmula
		limit = mine * (size / 2);
		
		//Establecemos un limit m�nimo de 2 minutos para prevenir que d� una cantidad muy baja de tiempo
		if (limit < 120) {
			limit = 120;
		}
		
		//Calculo la puntuaci�n restando el tiempo al m�ximo
		score = limit - (int) s;
		
		//Me aseguro de que la puntuaci�n no sea negativa
		if (score < 0) {
			score = 0;
		}
		
		//Devuelvo la puntuaci�n obtenida
		return score;
		
	}
	
	/**
	 * M�todo que convierte una letra a un entero en funci�n de su posici�n en el abecedario
	 * @param coord La letra introducida.
	 * @return Un entero que equivale a la posici�n de la letra introducida en el abecedario.
	 */
	public static int coordLetter(String coord) {
		
		//Variable que contendr� el valor de la letra introducida como coordenada
		int value;
		//Variable que contendr� la letra introducida convertida a caracter
		char letter;
		
		//Convertimos la letra de cadena a caracter
		//La pasamos a min�scula para que sea m�s f�cil controlar la conversi�n de su valor en ASCII
		letter = coord.toLowerCase().charAt(0);

		//Pasamos el valor de char a int
		//Usar� los valores de ASCII, as� que restamos 96 para relacionar cada letra con su n�mero.
		//'a' pasar�a de ser 97 a 1
		value = letter - 96;
		
		//Devolvemos el valor
		return value;
		
	}
	
	/**
	 * M�todo que ejecutar� la l�gica del juego en s� vali�ndose de los datos introducidos y los dem�s m�todos definidos.
	 * @param row El n�mero de filas de la tabla.
	 * @param column El n�mero de columnas de la tabla.
	 * @param mine El n�mero de minas que contiene la tabla.
	 */
	public static void mineGame(int row, int column, int mine) {
		
		//Inicializamos la tabla vac�a del juego
		int[][] tableEmpty = tableEmptyMaker(row, column);
		//Inicializamos la tabla solucionada del juego
		int[][] tableMines = tableMinesMaker(row, column, mine);
		//Creamos un contador que llevar� la cuenta de las casillas que quedan por descubrirse
		int cnt = 0;
		//Variable que almacenar� la coordenada introducida como cadena
		String coord;
		//Array que almacenar� el resultado de dividir la coordenada por la coma para obtener la posici�n en ambos ejes
		String[] coordAr = new String[2];
		//Variables que almacenar�n la posici�n en el eje x y la posici�n en el eje y respectivamente
		int coordX;
		int coordY;
		//Variable booleana que controlar� si el usuario quiere meter una marca este turno
		boolean mark;
		//Variables que me controlar�n el tiempo inicial, el final, y la duraci�n, que ser� la diferencia de ambas
		long timeAtStart;
		long timeAtEnding;
		long timeDuration;
		//Variable que almacenar� la puntuaci�n de la �ltima partida ganada
		int score;
		
		//Almacenamos el tiempo al comenzar el juego en milisegundos
		timeAtStart = System.currentTimeMillis();
		//El bucle no dejar� de ejecutarse hasta que hayamos descubierto todas las casillas que no tienen minas
		while (cnt != row * column - mine) {
			//Inicializamos la variable que controla si el usuario quiere meter marcas en for en cada iteraci�n del bucle
			mark = false;
			//Mostramos la tabla de juego por pantalla
			formatTable(tableEmpty);
			//Pedimos al usuario que introduzca una coordenada para comprobarla
			System.out.println("Si desea marcar una casilla, h�galo de la siguiente forma: marcar(coordenada).");
			System.out.println("Introduzca una coordenada (x, y): ");
			coord = sc.nextLine();
			//Comprobamos si el usuario quiere introducir marcas
			if (coord.matches("^marcar[(].*[)]$")) {
				//Si quiere introducir marcas, pasaremos el booleano a true
				mark = true;
				//Eliminamos el indicador de marcar que ha introducido el usuario para quedarnos s�lo con los datos y poder trabajar con ellos
				coord = coord.replaceAll("marcar[(]", "");
				coord = coord.replaceAll("[)]", "");
			}			
			//Dividimos la coordenada en dos subcadenas por la coma para obtener las posiciones del eje x y del eje y
			coordAr = coord.split(",");
			//Nos aseguramos de que no haya errores de overflow en los datos introducidos
			//Comenzamos desde aqu� porque, si el usuario introdujera un n�mero en vez de una coordenada,
			//el array de subcadenas s�lo tendr�a un elemento y dar�a un error en la conversi�n inferior a este comentario
			//Tambi�n podr�a pasar que el valor que va a utilizarse en la conversi�n no sea v�lido, por ejemplo, una palabra
			try {
				//Almacenamos las subcadenas anteriores convertidas a entero para poder trabajar con ellas
				//Queremos que la primera parte de la coordenada se pueda introducir como n�mero o como letra
				//porque vamos a mostrar la tabla se�alizando el eje X con letras
				//Usamos una expresi�n regular para comprobar si el primer caracter de la coordenada es una letra (puede estar rodeada de espacios)
				if (coordAr[0].matches("^\\s*[a-zA-Z]\\s*$")) {
					//Quitamos todos los espacios de la cadena para que el m�todo que usaremos ahora no d� ning�n error
					coordAr[0] = coordAr[0].replaceAll("\\s", "");
					//Si es una letra, llamamos a un m�todo que convierta el valor de la letra a un n�mero seg�n su posici�n en el abecedario
					//Restamos 1 porque queremos que el usuario empiece la introducci�n de datos en 1 (la posici�n en el abecedario de a)
					//aunque, en realidad, el �ndice empieza por cero
					coordX = coordLetter(coordAr[0]) - 1;
				}
				else {
					//Si no es una letra, simplemente convertimos el valor a n�mero
					//Restamos 1 porque queremos que el usuario empiece la introducci�n de datos en 1
					//aunque, en realidad, el �ndice empieza por cero
					coordX = Integer.parseInt(coordAr[0]) - 1;
				}
				//Comprobamos que no haya espacios entre los números de la segunda coordenada pero que sí se permita introducir un espacio antes.
				if (coordAr[1].matches("^\\s*([0]?[1-9]|1[0-9]|2[0-6])\\s*$")) {
					//Quitamos todos los espacios de la segunda cadena para que el siguiente m�todo no d� ning�n error
					coordAr[1] = coordAr[1].replaceAll("\\s", "");
				}
				//La segunda parte de la coordenada s�lo podr� ser un n�mero, as� que simplemente convertimos el valor a n�mero
				//Restamos 1 porque queremos que el usuario empiece la introducci�n de datos en 1
				//aunque, en realidad, el �ndice empieza por cero
				coordY = Integer.parseInt(coordAr[1]) - 1;
				
				//Comprobamos si el usuario hab�a pedido introducir una marca
				if (mark) {
					//Llamamos al m�todo que maneja las marcas
					mark(coordY, coordX, tableEmpty);
				}
				//Si no hab�a pedido introducir una marca seguimos con el juego normalmente
				else {
					//Comprobamos si la coordenada introducida ya ha sido descubierta
					//Si ha sido descubierta, no hacemos nada con ella. Si no ha sido descubierta seguimos las comprobaciones
					//Adem�s, si la casilla est� marcada no hacemos nada porque el valor de marcado es distinto
					if (tableEmpty[coordY][coordX] == -2) {
						//Comprobamos si la coordenada introducida es una mina
						if (tableMines[coordY][coordX] != -1) {
							
							//Si no es una mina, nos vamos al algoritmo de flood filling que ser� el que compruebe
							//si destapamos s�lo la casilla actual o, en el caso de que no est� contigua a ninguna mina,
							//destapamos todas las casillas de una zona delimitada
							cnt = flood(tableMines, tableEmpty, coordY, coordX, cnt);
							
						}
						else {
							//Si la coordenada introducida es una mina, mostramos la posici�n de las minas
							displayMines(tableMines, tableEmpty);
							//Adem�s, devolvemos el mensaje de derrota y acabamos el juego
							System.out.println("�BUM!, ha perdido.\n");
							//Rompemos el bucle para que se acabe el juego
							//En mi caso, en vez de finalizar el programa directamente cuando pierda como pide el PDF,
							//vuelvo al men� principal. Me resulta m�s elegante
							break;
							
						}
					}
				}
			}
			//Controlamos si se intenta acceder al array en un �ndice que no existe
			//Controlamos si se intenta introducir en una variable un valor de tipo distinto
			catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
					System.out.println("El dato introducido no es v�lido.");
					System.out.println("Pulse enter para continuar.");
					sc.nextLine();
			}
								
		}
		
		//Comprobamos si el jugador ha ganado
		if (cnt == row * column - mine) {
			//Almacenamos el tiempo de ejecuci�n al final del juego en milisegundos
			timeAtEnding = System.currentTimeMillis();
			//Almacenamos la diferencia entre el tiempo inicial y el final para saber la duraci�n de la partida
			timeDuration = timeAtEnding - timeAtStart;
			
			//Calculamos la puntuaci�n obtenida
			score = points(row, column, mine, timeDuration);
			//Almacenamos la puntuaci�n m�s alta, si lo es
			if (score > maxScore) {
				maxScore = score;
			}

			//Como hemos ganado, mostramos la tabla resuelta y damos un mensaje de victoria
			formatTable(tableMines);
			System.out.println("�Enhorabuena! Ha ganado.");
			//Devolvemos el tiempo por pantalla
			System.out.printf("El tiempo transcurrido ha sido: %s\n", formatTime(timeDuration));
			//Devolvemos la puntuaci�n por pantalla
			System.out.printf("La puntuaci�n obtenida es de: %d\n", score);
			
			//Llamamos al siguiente m�todo para preguntar al jugador si desea volver a jugar o no
			playAgain();
		}
		
	}
	
	/**
	 * M�todo que pregunta al jugador si desea volver a jugar.
	 * Si es as�, no hace nada porque el programa est� dise�ado para que se vuelva al men� directamente al final de cada partida.
	 * Si no es as�, el programa termina directamente.
	 */
	public static void playAgain() {
		
		//Variable que contiene la respuesta del jugador
		String answer;
		//Variable que contendr� la respuesta del jugador como caracter
		char ans = ' ';
		//Variable booleana que controlar� cu�ndo el jugador ha respondido correctamente
		boolean flag = false;
		
		//Seguimos preguntando al jugador hasta que responda correctamente
		while (!flag) {
			//Comprobamos que la introducci�n de datos sea correcta
			try {
				//Le pedimos al jugador su respuesta
				System.out.println("�Desea volver a jugar? (s/n): ");
				answer = sc.nextLine();
				
				//Comprobamos que la respuesta del jugador tenga el formato adecuado
				if (answer.matches("^[snSN]$")) {
					//Lo pasamos a min�sculas
					ans = answer.toLowerCase().charAt(0);
					//Cambiamos el booleano de valor para abandonar el bucle
					flag = true;
				}
				//Si no es una respuesta que entre en los par�metros que consideramos adecuados, devolvemos un error
				else {
					throw new IllegalArgumentException();
				}
			}
			//Controlamos que no haya introducido nada indebido
			catch (IllegalArgumentException e) {
				System.out.println("El dato introducido no es v�lido.");
				System.out.println("Pulse enter para continuar.");
				sc.nextLine();
			}
			
			//Si el usuario no quiere volver a jugar, finalizamos el programa
			//En caso contrario, no hacemos nada porque el flujo del programa va a hacer
			//que vuelva al men� de dificultad de todas formas
			if (ans == 'n') {
				//Mostramos la puntuaci�n m�xima
				System.out.printf("Su puntuaci�n m�xima ha sido de %d puntos. Pulse enter para salir.", maxScore);
				sc.nextLine();
				//Salimos con 0 para indicar que el programa se ha finalizado correctamente
				System.exit(0);
			}

		}
		
	}
	
	/**
	 * M�todo que me formatea un tiempo introducido en milisegundos en formato visible (00:00:00).
	 * @param time Un tiempo en milisegundos.
	 * @return Una cadena que contiene el tiempo traducido a formato visible.
	 */
	public static String formatTime(long time) {
		
		//Declaramos una variable de cadena en la que devolveremos el tiempo formateado
		String formatTime = "";
		//Variables de tiempo
		long h = 0;
		long m = 0;
		long s;
		
		//Comenzamos pasando el tiempo, que se encuentra en milisegundos, a segundos
		s = TimeUnit.MILLISECONDS.toSeconds(time);
		
		//Pasamos a almacenar el tiempo
		//Comparamos si ha pasado suficiente tiempo para almacenar horas
		if (s / 3600 > 0) {
			//Si podemos almacenar horas, convertimos los segundos a horas
			h = s / 3600;
			//Los segundos sobrantes que no se pueden pasar a horas, se almacenan en la variable de segundos
			s = s % 3600;
		}
		//Comparamos si ha pasado suficiente tiempo para almacenar minutos
		if (s / 60 > 0) {
			//Si podemos almacenar minutos, convertimos los segundos a minutos
			m = s / 60;
			//Los segundos sobrantes que no se pueden pasar a minutos, se almacenan en la variable de segundos
			s = s % 60;
		}
		
		//Formateamos el tiempo de forma que me a�ada 0 a la izquierda en cada variable hasta que haya dos d�gitos
		//(de la forma 00:00:00)
		formatTime = String.format("%02d:%02d:%02d", h, m ,s);
		
		//Devolvemos la cadena de tiempo formateada
		return formatTime;
	}
	
	/**
	 * M�todo que, cuando el jugador pierda, le mostrar� la posici�n de todas las minas.
	 * @param tableMines La tabla con la soluci�n.
	 * @param tableEmpty La tabla del jugador.
	 */
	public static void displayMines(int[][] tableMines, int[][] tableEmpty) {
		
		//Recorremos la tabla que contiene la soluci�n del juego
		for (int i = 0; i < tableMines.length; i++) {
			for (int j = 0; j < tableMines[0].length; j++) {
				//Cuando encontremos una mina, la pasamos a la tabla del jugador
				if (tableMines[i][j] == -1) {
					tableEmpty[i][j] = tableMines[i][j];
				}
			}
		}
		
		//Terminamos mostrando la tabla para que el jugador pueda ver d�nde se encontraban la minas
		formatTable(tableEmpty);
		
	}
	
	/**
	 * M�todo que se encargar� de hacer la tabla resuelta con el contenido de las minas.
	 * @param row El n�mero de filas.
	 * @param column El n�mero de columnas.
	 * @param mine El n�mero de minas.
	 * @return La tabla con el resultado del juego almacenado.
	 */
	public static int[][] tableMinesMaker(int row, int column, int mine) {
		
		//Creamos la tabla, que vamos a manipular y devolver, usando los datos introducidos por el usuario
		int[][] table = new int[row][column];
		
		//A�adimos las minas a la tabla que acabamos de crear
		randomMinePositioning(mine, table);
		
		//Devolvemos la tabla completada
		return table;
		
	}

	/**
	 * M�todo que coloca minas en posiciones aleatorias.
	 * @param mine El n�mero de minas a introducir.
	 * @param table La tabla en la que introducir las minas.
	 */
	public static void randomMinePositioning(int mine, int[][] table) {
		
		//Definimos un generador de n�meros aleatorios usando como semilla el tiempo actual
		Random rd = new Random(System.currentTimeMillis());
		//Variables que almacenar�n la fila y la columna en la que vamos a introducir la mina
		int row;
		int column;
		//Definimos un contador que nos ayudar� a saber cu�ndo hemos introducido todas las minas
		int cnt = 0;
		
		//Ejecutamos el bucle mientras queden minas por introducir
		while (cnt < mine) {
			//Damos un valor a la fila y la columna entre 0 y el �ltimo �ndice de la tabla
			row = rd.nextInt(table.length);
			column = rd.nextInt(table[0].length);
			
			//Comprobamos si la casilla generada es una mina
			if (table[row][column] != -1) {
				//Si no es una mina la convertimos en una mina
				table[row][column] = -1;
				//Sumamos 1 al contador
				cnt++;
				//Llamamos a un m�todo para que sume 1 a las casillas de alrededor
				checkMines(row, column, table.length, table[0].length, table);
			}
		}
		
	}
	
	/**
	 * M�todo que suma 1 en la tabla a los valores que rodean a la coordenada introducida.
	 * @param row La fila en la que se encuentra la coordenada.
	 * @param column la columna en la que se encuentra la coordenada.
	 * @param totalRow Total de filas que tiene la tabla.
	 * @param totalColumn Total de columnas que tiene la tabla.
	 * @param table La tabla en la que se encuentra la coordenada.
	 */
	public static void checkMines(int row, int column, int totalRow, int totalColumn, int[][] table) {
		
		//Declaramos una constante mina para que el m�todo sea m�s f�cil de leer
		final int mine = -1;
		
		//Comprobamos que la coordenada no se encuentreen la primera fila
        if (row != 0) {
        	//Si la coordenada superior a la actual no es una mina, le sumamos 1 a su valor
            if (table[row - 1][column] > mine) {
                table[row - 1][column] = table[row - 1][column] + 1;
            }
            //Comprobamos que la coordenada no est� en la primera columna
            //Si, adem�s, la coordenada del noroeste no es una mina, le sumamos 1 a su valor
            if (column != 0 && table[row - 1][column - 1] > mine) {
                table[row - 1][column - 1] = table[row - 1][column - 1] + 1;
            }
            //Comprobamos que la coordenada no est� en la �ltima columna
            //Si, adem�s, la columna del noreste no es una mina, le sumamos 1 a su valor
            if (column != totalColumn - 1 && table[row - 1][ column + 1] > mine) {
                table[row - 1][column + 1] = table[row - 1][column + 1] + 1;
            }
        }
        //Comprobamos que la coordenada no se encuentre en la �ltima fila
        if (row != totalRow - 1) {
        	//Si la coordenada inferior a la actual no es una mina, le sumamos 1 a su valor
            if (table[row + 1][column] > mine) {
                table[row + 1][column] = table[row + 1][column] + 1;
            }
            //Comprobamos que la coordenada no est� en la primera columna
            //Si, adem�s, la coordenada del suroeste no es una mina, le sumamos 1 a su valor
            if (column != 0 && table[row + 1][column - 1] > mine) {
                table[row + 1][column - 1] = table[row + 1][column - 1] + 1;
            }
            //Comprobamos que la coordenada no est� en la �ltima columna
            //Si, adem�s, la coordenada del sureste no es una mina, le sumamos 1 a su valor
            if (column != totalColumn - 1 && table[row + 1][column + 1] > mine) {
                table[row + 1][column + 1] = table[row + 1][column + 1] + 1;
            }
        }
        //Comprobamos que la coordenada no est� en la primera columna
        //Si, adem�s, la cordenada anterior a la actual no es una mina, le sumamos 1 a su valor
        if (column != 0 && table[row][column - 1] > mine) {
            table[row][column - 1] = table[row][column - 1] + 1;
        }
        //Comprobamos que la coordenada no est� en la �ltima columa
        //Si, adem�s, la coordenada posterior a la actual no es una mina, le sumamos 1 a su valor
        if (column != totalColumn - 1 && table[row][column + 1] > mine) {
            table[row][column + 1] = table[row][column + 1] + 1;
        }
        
	}
	
	/**
	 * M�todo que se encargar� de hacer la tabla vac�a que se usar� en el juego para compararla con la tabla que contiene la respuesta.
	 * @param row El n�mero de filas.
	 * @param column El n�mero de columnas.
	 * @return La tabla con los datos que va destapando el usuario (al principio vac�a/llena de -2, que representan el s�mbolo ?)
	 */
	public static int [][] tableEmptyMaker(int row, int column) {
		
		//Creamos la tabla usando los datos introducidos por el usuario
		int[][] table = new int[row][column];
		
		//Rellenamos todas sus posiciones con el valor -2, que ser� representado por el s�mbolo ? al mostrarse por pantalla
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				table[i][j] = -2;
			}
		}
		
		//Devolvemos la tabla
		return table;
		
	}
	
	/**
	 * M�todo que me muestra la tabla formateada de forma agradable por pantalla.
	 * Como desconozco las formas correctas de formatear cosas gr�ficas m�s complejas
	 * seguramente habr� una forma m�s eficiente y elegante de hacerlo
	 * @param table
	 */
	public static void formatTable(int[][] table) {
		
		//Variable que contendr� una cadena que representar� la tabla formateada y cuyo prop�sito es devolverse por pantalla
		//La inicializaremos con 2 espacios, m�s abajo explicar� el por qu� en el cuarto p�rrafo contando desde despu�s del 
		//dibujo del comentario largo
		String GUITable = "  ";
		//Variable que contendr� el n�mero de filas que tiene la tabla
		int row = table.length;
		//Variable que contendr� el n�mero de columnas que tiene la tabla
		int column = table[0].length;
		
		/*
		 * El objetivo del m�todo actual es crear una cadena con el siguiente formato para mostrarlo por pantalla:
		 * 
		 *      A   B   C   D
		 *    +---+---+---+---+
		 * 01 | 1 | 2 | 3 | 4 | 2 -> 0
		 *    +---+---+---+---+
		 * 02 | 3 | 4 | 5 | 6 | 4 -> 1
		 *    +---+---+---+---+
		 * 03 | 5 | 6 | 7 | 8 | 6 -> 2
		 *    +---+---+---+---+
		 * 04 | 7 | 8 | 9 | 0 | 8 -> 3
		 *    +---+---+---+---+
		 *   
		 *   A la derecha de la tabla explico el valor que tiene esa fila con respecto el valor que tiene que usarse
		 *   para acceder al dato que tiene que introducirse en esa fila en espec�fico. Es decir, que la fila 2 de la 
		 *   tabla que estoy representando se corresponde con la fila 0 de la tabla desde la que tengo que sacar datos.
		 *   
		 *   De la misma forma, tendr� que calcular los n�meros que aparecen a la izquierda aprovech�ndome de que su valor
		 *   es la mitad de la fila real que me encuentro representando. Es decir, que cuando quiero representar la primera fila
		 *   y marcarla como uno, en realidad me encuentro en la fila 2 de la representaci�n, la tercera fila.
		 *   
		 *   Para facilitar la generaci�n de la tabla he intentado buscar un patr�n en cada fila a introducir para introducir fragmentos
		 *   de la tabla recursivamente. Normalmente, en cada fila, hay una parte sobrante que no puedo introducir recursivamente porque
		 *   tiene un patr�n distinto. Yo he identificado esa parte como el principio de cada fila y lo he introducido directamente en la
		 *   introducci�n de la primera columna.
		 *   
		 *   De hecho, arriba he hecho una peque�a trampa, ya que he comenzado introduciendo en la cadena GUITable que vamos a devolver
		 *   los dos primeros espacios sobrantes de la primera l�nea ya que el patr�n de introducci�n que encontr� es 3 espacios y una letra.
		 *   Como la primera vez hay 5 espacios antes de la A, introduzco los sobrantes directamente.
		 *   Al ser la primera l�nea de la representaci�n, puedo introducir esos espacios directamente sin necesidad de comprobar nada
		 *   porque s� que siempre van a estar ah�. Este es un peque�o ejemplo de mi estrategia para generar la estructura.
		 *   
		 *   En el propio c�digo intentar� explicar paso a paso el proceso.
		 */
		
		//Como vamos a recorrer un array con estructura de tabla, recorremos filas y columnas con dos for
		//En el caso de las filas tendremos que recorrer m�s ya que las columnas sobrantes podemos introducirlas como parte del String por
		//cada iteraci�n que hagamos de las columnas
		//En el caso de las filas no es as�, ya que necesitamos recorrer las filas m�s veces s� o s� para poder mostrar m�s l�neas en consola
		//Si nos fijamos en la tabla podemos llegar a la conclusi�n de que por cada fila de datos tengo que mostrar dos filas, la fila superior
		//que representa una l�nea y la propia fila de datos
		//Adem�s, habr� otras dos filas, la fila que contiene a las letras que identifican las columnas y la �ltima fila que representa la l�nea
		//que cierra la tabla. Por lo tanto, el total de filas ser�a: filas * 2 + 2.
		for(int i = 0; i < row * 2 + 2; i++) {
			for (int j = 0; j < column; j++) {
				
				//Si la fila es la primera, quiere decir que vamos a introducir la fila que
				//indica la posici�n del eje X usando letras may�sculas
				if (i == 0) {
					//Primera fila (la de las letras)
					//En este caso el patr�n es 3 espacios antes de cada letra
					//Las letras representan las columnas as� que simplemente llamamos a un
					//m�todo que me traduzca el valor de las columnas por letras en may�scula
					GUITable += String.format("   %c", numberToLetter(j));
					//Comprobamos si ya no hay m�s columnas
					//De ser as�, introducimos un cambio de l�nea
					if (j == column - 1) {
						GUITable += "\n";
					}
				} 
				//Si miramos la tabla podemos darnos cuenta de que hay dos tipos de filas
				//Una tipo que contiene s�lo s�mbolos y otro tipo que contiene datos formateados
				//entre s�mbolos
				//Las filas pares corresponden a las que tienen s�mbolos, as� que hago la comprobaci�n
				//de si es par o no.
				else if (i % 2 != 0) {
					//Fila de s�mbolos
					//En este caso, el patr�n sobrante son los espacios iniciales y la primera intersecci�n
					//Habr� 3 espacios antes de la intersecci�n ya que los n�meros de la izquierda se rellenan
					//hasta tener 2 posiciones, lo que da lugar a que los espacios son las dos posiciones del n�mero
					//m�s el espacio que lo separa de la tabla
					if (j == 0) {
						GUITable += "   +";
					}
					//El patr�n de esta fila son 3 guiones y una intersecci�n, as� que los introducimos por todas
					//las columnas que haya
					GUITable += "---+";
					//Comprobamos si ya no hay m�s columnas
					//De ser as�, introducimos un cambio de l�nea
					if (j == column - 1) {
						GUITable += "\n";
					}
				} 
				//Si la fila no es par, significar� que es el tipo de fila que contiene datos formateados entre s�mbolos
				else {
					//Fila con letras
					//En este caso, la primera parte sigue la l�gica del patr�n excepto porque el dato que introducimos no
					//es parte de la tabla. Es, simplemente, un n�mero que identifica cada columna. Ese n�mero da la casualidad
					//que coincide con la mitad del �ndice de las filas actual (fila / 2) as� que nos aprovecharemos.
					if (j == 0) {
						GUITable += String.format("%02d | ", i / 2); 
					}
					//En este caso, el patr�n es dato seguido de espacio, tuber�a y espacio.
					//Al final nos sobrar� un espacio al cerrar la �ltima casilla pero, 
					//como va seguido de un cambio de l�nea, nos da igual.
					GUITable += String.format("%c | ", formatCell(table[i / 2 - 1][j])) ;
					//Comprobamos si ya no hay m�s columnas
					//De ser as�, introducimos un cambio de l�nea
					if (j == column - 1) {
						GUITable += "\n";
					}
				}

			}
			
		}
		
		//Mostramos la tabla formateada por pantalla
		System.out.println(GUITable);
		
	}
	
	/**
	 * M�todo que recibe un n�mero y lo formatea seg�n la siguiente l�gica:
	 * Si es 0 la casilla estar� vac�a.
	 * Si es mayor que 0 devuelve el mismo n�mero.
	 * Si es -1, devuelve "M". Es una mina.
	 * Si es -2, devuelve "?". La casilla no ha sido descubierta.
	 * Si es -3, devuelve "#". La casilla est� marcada.
	 * @param num El valor a comprobar.
	 * @return Un caracter que se saca a partir del valor introducido.
	 */
	public static char formatCell(int num) {
		
		//Declaramos una constantes que contienen los caracteres especiales que vamos a aplicar
		final char empty = ' ';
		final char mine = 'M';
		final char hidden = '?';
		final char marked = '#';
		//Variable que contiene el caracter que vamos a devolver
		char data;
		
		//Formateamos seg�n hemos explicado antes
		switch (num) {
		case 0:
			data = empty;
			break;
		case -1:
			data = mine;
			break;
		case -2:
			data = hidden;
			break;
		case -3:
			data = marked;
			break;
		default:
			data = (char) (num + 48);
		}
		
		//Devolvemos el valor
		return data;
		
	}
	
	/**
	 * M�todo que recibe un n�mero y me vuelve la letra may�scula del abecedario usando ASCII.
	 * Se considera que se comienza en el 0, es decir, A ser� 0, B ser� 1, etc.
	 * @param num El n�mero a convertir a letra.
	 * @return El caracter convertido.
	 */
	public static char numberToLetter(int num) {
		
		//Variable que contendr� la posici�n del caracter en la tabla ASCII
		int pos;
		//Variable que contendr� el caracter convertido desde n�mero
		char letter;
		
		//Inicializamos pos como la el n�mero introducido, que es lo que queremos convertir a caracter
		pos = num;
		//Sumamos 65 a la variable para inicializar en la posici�n de las letras
		pos += 65;
		
		//Realizamos la conversi�n usando los valores de la tabla ASCII
		letter = (char) pos;
		
		//Devolvemos el caracter
		return letter;
	}
	
	/**
	 * M�todo que devuelve la cantidad de minas que van a usarse en un nivel de dificultad seg�n el porcentaje introducido.
	 * @param row El n�mero de filas de la tabla.
	 * @param column El n�mero de columnas de la tabla.
	 * @param percQuant El porcentaje de minas.
	 * @return
	 */
	public static int minePercentage(int row, int column, int percQuant) {
		
		//Variable que contendr� el n�mero de minas
		int mine;
		
		//Calculamos el n�mero de minas
		mine = row * column * percQuant / 100;
		
		//Devolvemos el resultado
		return mine;
		
	}
	
}
