
public class Lexico {
	
	protected String [] lexicos;
	
	public Lexico(String [] lex){
		this.lexicos = lex;
	}
	
	public void addLexico(String newLexico, int index){
		this.lexicos[index] = newLexico;
	}
	
	public void imprimeLexicos(String [] lexico){
		
		for(int i=0; i<lexico.length; i++){
			System.out.println(lexico[i]);
		}
		
	}
	
	public String[] constroiLexico(String[] args){
		
		int index         = 0;
		String [] lexicos = new String[args.length]; 
		
		removeComentarios(args);
		
		while(args[index] != null){
			constroiTokens(lexicos[index], index);
			index++;
		}
		
		return lexicos;
		
	}
	
	public void constroiTokens(String linha, int index){
		
		removeEspacosEmBranco(linha);
		identificaTokens(linha);
		addLexico(constroiListaDeLexicos(linha), index);
	
	}
	
	public String removeEspacosEmBranco(String linha){
		return linha.replaceAll(" ", "");	
	}
	
	public String removeComentarios(String [] args){
		
		String prefixo           = "//";
		String inicioBloco       = "*/";
		String finalBloco        = "/*";
		String linha             = "";
		int primeiroIndexDeleta  = 0;
		int segundoIndexDeleta   = 0;
		int indexBloco           = 0;
		
		for(int i=0; i<args.length; i++){

			linha = args[i];
			
			if(linha.contains((CharSequence)prefixo)){
				primeiroIndexDeleta = linha.indexOf(prefixo);
				linha = linha.substring(primeiroIndexDeleta, linha.length()-1);
			}else if(linha.contains((CharSequence)inicioBloco)){
				primeiroIndexDeleta = linha.indexOf(inicioBloco);
				if(linha.contains((CharSequence)finalBloco)){
					segundoIndexDeleta = linha.indexOf(finalBloco);
					linha = linha.substring(primeiroIndexDeleta, segundoIndexDeleta);
				}else{
					linha = linha.substring(primeiroIndexDeleta, linha.length()-1);
					indexBloco = i++;
					linha = args[indexBloco];
					while(!linha.contains(finalBloco)){
						linha.substring(0, linha.length()-1);
						indexBloco++;
						linha = args[indexBloco];
					}
					linha = linha.substring(0, linha.indexOf(finalBloco));
				}

			}		
		}
			
		return linha;
		
	}
	
	public void identificaTokens(String linha){
		
		char [] tokenAtual   = new char[linha.length()];
		String linhaDeTokens = "";
		int indexLinha       = 0;
		int indexArray       = 0;
		String [] listaVariaveis = new String[linha.length()];
		String tokenFormatado = "null";
			
		do{
			tokenAtual[indexArray] = linha.charAt(indexLinha);
			tokenFormatado = verificaTiposDeDados(tokenAtual.toString());
			if(tokenFormatado.matches(null)){
				linhaDeTokens = linhaDeTokens+tokenFormatado.toString()+"$";
				clearTokenAtual(tokenAtual);
				//to ficando com muito lixo
				//deletar da lista de variaveis
				indexArray = 0;
			}else{
				criaListaDeVariaveis(listaVariaveis, tokenAtual.toString(), linha.charAt(indexLinha+1));
				if(linha.charAt(indexLinha+1) == '='){
					//é uma variável, então libera a memória e pula pra próxima
				}
				indexArray++;
			}
			indexLinha++;
		}while(tokenAtual != null);
		
		linha = linhaDeTokens;
		
	}
	
	public void criaListaDeVariaveis(String [] listaVariaveis, String token, char caractereAtual){
		
		boolean existe = false;
		
		for(int i=0; i<listaVariaveis.length; i++){
			if(token.contains(listaVariaveis[i])){
				listaVariaveis[i] = token;
				existe = true;
				break;
			}
		}
		
		if(!existe){
			listaVariaveis[listaVariaveis.length-1] = token;
		}
		

	}
	
	public void clearTokenAtual(char[] token){
		
		for(int i=0; i<token.length; i++){
			token[i] = '\0';
		}
		
	}
	
	public String verificaTiposDeDados(String token){
	
		if(verificaConstante(token) != null){
			return "[num, " + token + "]";
		}else if(verificaPalavraReservada(token) != null){
			return "[reserved_word, " + token + "]";
		}else if(verificaOperador(token) != null){
			return "[Relational_Op, " + token + "]";
		}else if(verificaFloat(token)!=null){
			return "[num, " + token + "]";
		}

		return null;
		
	}
	
	public String verificaConstante(String token){
		
		int tokenNumerico;
		
		try{
			tokenNumerico = Integer.parseInt(token);
		}catch(NumberFormatException e){
			return null;
		}
		
		return token;
		
	}
	
	public String verificaPalavraReservada(String token){
		
		ListaPalavrasReservadas listaPalavrasReservadas = new ListaPalavrasReservadas();
		
		if(listaPalavrasReservadas.encontrarPalavraReservada(token)){
			return token;
		}
		
		return null;
	
	}

	public String verificaOperador(String token){
		
		ListaDeOperadores listaOperadores = new ListaDeOperadores();
		
		if(listaOperadores.encontrarSimbolo(token)){
			return token;
		}
		
		return null;
	
	}
	
	public String verificaFloat(String token){
		
		float tokenNumerico = 0.0f;
		
		try{
			tokenNumerico = Float.parseFloat(token);
		}catch(NumberFormatException e){
			return null;
		}
		
		return token;
	
	}
	
	public String verificaVariavel(String [] listaVariaveis, String token, int index){
		
		String variavel = "";
		
		for(int i=0; i<listaVariaveis.length; i++){
			if(token == listaVariaveis[i]){
				index = i;
				break;
			}
		}
		
		variavel = "(id, "+index+")";
		index = listaVariaveis.length-1;
	
		return variavel;
	
	}
	
	public String constroiListaDeLexicos(String linha){
		
		String lista = linha.replaceAll("$", " ");
		return lista;
		
	}
}
