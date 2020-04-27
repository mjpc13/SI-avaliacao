package Domino;

//classe da peca de domino

public class dominoes {
	
	public String num1, num2; //os dois numeros associados a domino
	
	public int points;        //pontos que esta peça possui
	
	public dominoes(String num1, String num2) {
		
		this.num1=num1;
		this.num2=num2;
		
		int int1 = Integer.parseInt(num1);
		int int2 = Integer.parseInt(num2);
		
		if(isDouble() && num1.equals("0")){ // no caso de a peca ser a 0,0 os pontos da peça tem de ser 10
			
			this.points=10;
			
		}
		else {  //nos restantes casos, é apenas a soma dos dois numeros
			this.points=int1+int2;
		}
	}
	
	
	
	public void tradeNums() { // função que troca os numeros do domino
		
		String a= num1;
		num1=num2;
		num2=a;

	}
	

	public boolean isDouble() { //função que vê se esta peça é double ou não (se num1==num1)
		
		if(num1.equals(num2)) {
			return true;
		}
		
		else {
			return false;
		}
	}
	
	
	public void printDominoes(){  //função que faz print dos dominos
		
		String[][] matrix;
		
		if(isDouble()) { // se for um double
			
			matrix= new String[1][9];
			
			for(int i=0; i<9;i++) {  // cria a matrix para quando é double
				
				if(i==5 || i==7) {
					matrix[0][i] = num1;
				}
				else if(i==4 || i==6 || i==8){
					matrix[0][i] = "|";
				}
				else {
					matrix[0][i] = " ";
				}
			}
			
		}
		else {						// cria a matrix para quando não é double
			
			matrix= new String[3][3];
			for(int i=0;i<3;i++) {
				
				for(int j=0;j<3;j++) {
					
					if(i==0 || i==2) {
						matrix[i][j] = "|";
					}
					if(i==1) {
						matrix[i][j] = "-";
					}
				}
			}
			matrix[0][1] = num1;
			matrix[2][1] = num2;

		}

		printMatrix(matrix); // faz o print da matrix criada
	}
	
	
	private void printMatrix(String[][] dominoes) { // faz o print da matriz
		
		for(String[] str1 : dominoes) {
			
			if(isDouble()) {
				System.out.print(" ".repeat(5));
			}
			else {
				System.out.print(" ".repeat(10));
			}
			
			for(String str2 : str1) {
				
				System.out.print(str2);
				
			}
			
			System.out.println();
		}
		
	}
	
	
	
	
	
	
	//Funcoes para o jogo Extra, faz o print das peças ao chamar printExtradominoes(), NÃO SÃO ELEMENTOS DE AVALIAÇÃO PODEM SER IGNORADAS
	
	public void printExtraDominoes(){
		
		String[][] matrix;
		
		if(isDouble()) {
			
			matrix = emptyDoubleMatrix();
			matrix = matrixDoubleDraw(matrix);
			
		}
		else {
			matrix = emptyNormalMatrix();
			matrix = matrixNormalDraw(matrix);

		}

		printMatrix(matrix);
	}
	
	private String[][] emptyNormalMatrix() {
		
		String[][] matrix = new String[9][7];
		
		for(int i=0; i<9;i++) {
			for(int j=0; j<7;j++) {
				
				if(j==0 || j== 6) {
					
					matrix[i][j]="|";

				}
				else if(i==4) {
					matrix[i][j]="+";
				}
				else {
					matrix[i][j]=" ";
				}
				if(i==0) {
					
					matrix[i][j]="_";

				}
				else if(i==8) {
					
					matrix[i][j]="¯";

				}
			}
		}
		
		return matrix;
	}
	
	private String[][] emptyDoubleMatrix() {
		
		String[][] matrix = new String[5][17];
		
		for(int i=0; i<5;i++) {
			for(int j=0; j<17;j++) {
				
				if(i==0) {
					
					matrix[i][j]="_";

				}
				else if(i==4) {
					
					matrix[i][j]="¯";

				}
				else if(j==0 || j== 16) {
					
					matrix[i][j]="|";

				}
				else if(j==8) {
					matrix[i][j]="+";
				}
				else {
					matrix[i][j]=" ";
				}
				
			}
		}
		
		return matrix;
	}
	
	private String[][] matrixNormalDraw(String[][] matrix) {

		
		// Escreve na Matrix o número mais pequeno
		
		if(num1.equals("1")) {
			matrix[2][3]="*";
		}
		else if(num1.equals("2")) {
			matrix[1][2]="*";
			matrix[3][4]="*";
		}
		else if(num1.equals("3")) {
			matrix[1][2]="*";
			matrix[2][3]="*";
			matrix[3][4]="*";
		}
		else if(num1.equals("4")) {
			matrix[1][2]="*";
			matrix[1][4]="*";
			matrix[3][2]="*";
			matrix[3][4]="*";
		}
		else if(num1.equals("5")) {
			matrix[1][2]="*";
			matrix[1][4]="*";
			matrix[2][3]="*";
			matrix[3][2]="*";
			matrix[3][4]="*";
		}
		
		else if(num1.equals("6")) {
			matrix[1][2]="*";
			matrix[2][4]="*";
			matrix[1][4]="*";
			matrix[2][2]="*";
			matrix[3][2]="*";
			matrix[3][4]="*";
		}
		
		if(num2.equals("1")) {
			matrix[6][3]="*";
		}
		else if(num2.equals("2")) {
			matrix[5][2]="*";
			matrix[7][4]="*";
		}
		else if(num2.equals("3")) {
			matrix[5][2]="*";
			matrix[6][3]="*";
			matrix[7][4]="*";
		}
		else if(num2.equals("4")) {
			matrix[5][2]="*";
			matrix[5][4]="*";
			matrix[7][2]="*";
			matrix[7][4]="*";
		}
		else if(num2.equals("5")) {
			matrix[5][2]="*";
			matrix[5][4]="*";
			matrix[6][3]="*";
			matrix[7][2]="*";
			matrix[7][4]="*";
		}
		
		else if(num2.equals("6")) {
			matrix[5][2]="*";
			matrix[6][4]="*";
			matrix[5][4]="*";
			matrix[6][2]="*";
			matrix[7][2]="*";
			matrix[7][4]="*";
		}
		return matrix;
		
	}

	private String[][] matrixDoubleDraw(String[][] matrix) {

		
		// Escreve na Matrix o número mais pequeno
		
		if(num1.equals("1")) {
			matrix[2][4]="*";
			
			matrix[2][12]="*";
		}
		else if(num1.equals("2")) {
			matrix[1][2]="*";
			matrix[3][5]="*";
			
			matrix[1][11]="*";
			matrix[3][14]="*";
		}
		else if(num1.equals("3")) {
			matrix[1][2]="*";
			matrix[3][6]="*";
			matrix[2][4]="*";
			
			matrix[1][10]="*";
			matrix[3][14]="*";
			matrix[2][12]="*";
		}
		else if(num1.equals("4")) {
			matrix[1][2]="*";
			matrix[1][5]="*";
			matrix[3][2]="*";
			matrix[3][5]="*";
			
			matrix[1][11]="*";
			matrix[1][14]="*";
			matrix[3][11]="*";
			matrix[3][14]="*";
		}
		else if(num1.equals("5")) {
			matrix[1][2]="*";
			matrix[1][6]="*";
			matrix[2][4]="*";
			matrix[3][2]="*";
			matrix[3][6]="*";
			
			matrix[1][10]="*";
			matrix[1][14]="*";
			matrix[2][12]="*";
			matrix[3][10]="*";
			matrix[3][14]="*";
		}
		
		else if(num1.equals("6")) {
			matrix[1][2]="*";
			matrix[1][4]="*";
			matrix[1][6]="*";
			matrix[3][2]="*";
			matrix[3][4]="*";
			matrix[3][6]="*";
			
			matrix[1][10]="*";
			matrix[1][12]="*";
			matrix[1][14]="*";
			matrix[3][10]="*";
			matrix[3][12]="*";
			matrix[3][14]="*";
		}
		
		return matrix;
		
	}
	
}
