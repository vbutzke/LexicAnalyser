
public class ListaPalavrasReservadas {

	public final String [] listaPalavrasReservadas = { "do", "while", "if", "else", "for", "printf", "return", "null", 
													   "int", "float", "double", "string", "bool" };
	
	public boolean encontrarPalavraReservada(String token){
		
		for(int i=0; i<this.listaPalavrasReservadas.length; i++){
			if(listaPalavrasReservadas[i] == token){
				return true;
			}
		}
		
		return false;
	}
	
}
