public class DNA_seq implements DNAclass {
    public static strand s1;
    public static strand s2;
    public int rows; //total number of rows
    public int columns; //total number of columns.
    public int [][] DNA; // DNA, 2d matrix.
    public byte [][] aux; //aux matrix used in traceback.

    DNA_seq (String a, String b){ //constructor
	s1 = new strand(a, b);
	initialize();
    }
/*
    public static String randseq (int n) {
	String s3 = "";
	String s4 = "ACGT";
	for (int i = 1; i <= n; i++){
	    int r = (int)(Math.random()*4);
	    s3 += s4.charAt(r);
	}
	return s3;
    }

*/
    public byte w(){ return 0;} //gap penalty. simple scenario.
	
    public void initialize(){
	DNA = new int[s1.b.length()+1][s1.a.length()+1];
	aux = new byte[s1.b.length()+1][s1.a.length()+1];
	rows = s1.a.length()+1;
	columns = s1.b.length()+1;
	for (int i = 0; i < rows; i++) w();
	for (int j = 0; j < columns; j++) w();
    }
	
	public int score (int y, int x){
		if (s1.a.charAt(y-1) == s1.b.charAt(x-1)) {
		DNA[y][x] = 1;
		}
		else{return 0;}
		return 1;
	}
	
	public void max (int y, int x){
		int l = DNA[y][x-1] + w(); //left
		int d = DNA[y-1][x-1] + score(y, x); //diagonal
		int t = DNA[y-1][x] + w(); //above

		if (l==d && l==t){DNA[y][x] = d;} // diagonal has priority in a tie.
		else{
		
			if (l > d && l >= t) { // left check. left takes priority over top.
				DNA[y][x] = l;
				aux[y][x] = 1; //1 = came from left
			}
		
			else{
				if (d >= l && d >= t) { //diagonal check. one has to be > because they're not both =.
				DNA[y][x] = d;
				aux[y][x] = 2; //2 = came from diagonal
				}
				else{ //we know it has to come from the top
					DNA[y][x] = t; 
					aux[y][x] = 3; //3 = came from above.
				}
		        }	
		}
	} //end max
	

    public void fill(){
		for (int i = 1; i < rows; i++){
			for (int j = 1; j < columns; j++){
				max (i, j);
			}
		}
	}//end fill
	
	/*public void getStrand(int y, int x){
	rows = s1.a.length()+1;
	columns = s1.b.length()+1;
	while (DNA[y][x] != 0)
	
	
	}*/
	

    public void traceback(){
    	int i = rows;
    	int j = columns;
    	String tempA = ""; //holds temporary restructured string a
    	String tempB = ""; //holds temporary restructured string b
    	String tempC = ""; //holds temporary restructured string c
    	while (i != 0 && j != 0){
    		if (aux[i-1][j-1] == 2 && s1.a.charAt(i-2) == s1.b.charAt(j-2)) { // why -2 here and not -1? 
      			s1.c += "|";
     		}
     		
     	else s1.c += " ";
     	
     	if (aux[i-1][j-1] == 1){ //restructuring my strings isn't going so well.
     	tempA = "-";
     	}
     	
     	if (aux[i-1][j-1] == 3){
     	tempB = "-";
     	}
     	
     	i--;
     	j--;
     	}
     
     String b = "";
     for (int x = s1.c.length()-2; x >= 0; x--){
     	tempA += s1.a.charAt(x);
     	tempB += s1.b.charAt(x);
     	tempC += s1.c.charAt(x);
     }
     
     s1.a = tempA;
     s1.b = tempB;
     s1.c = tempC;
     	}

    public static void main(String[] args){
	DNA_seq d = new DNA_seq("GHWHGHGG", "GWWHHGHG");
	d.fill();
	d.traceback();
	//d.matrixPrint();//nested for loop to print the matrixs.
	s1.printStrand();//inside s1(strand class).
    }


}


