
public class analisadorLexico {

	public static void main(String[] args) {
		
		Lexico lex        = new Lexico(args);
		String [] lexicos = new String [args.length];
		
		lexicos = lex.constroiLexico(args);
		lex.imprimeLexicos(lexicos);

	}

}
