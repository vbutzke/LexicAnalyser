
public class ListaDeOperadores {
	
	protected final String [] simbolos = { ">", "<", "+", "-", ";", ":", ".", ",", "*", "/", "|", "^", "&", "@", "!",
	                                       "%", "~", "==", "<=", ">=", "||", "&&", "!=", "++", "--", "?:", "<<", ">>",
	                                       ">>>", "instanceOf", "(", ")", "[", "]", "{", "}" };
	                                            
	public boolean encontrarSimbolo(String token){
		
		for(int i=0; i<this.simbolos.length; i++){
			if(simbolos[i] == token){
				return true;
			}
		}
		
		return false;
	}
	
}
