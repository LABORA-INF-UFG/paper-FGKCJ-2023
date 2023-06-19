package simulacao;

public class Teste {
	static int sem=1;
	static String str="Resultado do Semestre Letivo:";
	static String aprovado="Você foi aprovado na disciplina";
	static String recuperacao="Você ficou de recuperação na disciplina";
	static String reprovado="Você foi reprovado na disciplina";
	static double nota=3.2;

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		imprimir();
		situacao();

	}
	public static void imprimir() {
		System.out.println(str+sem);
		System.out.println(sem);
	}
	public static void situacao() {
		if (nota>=7) {
			System.out.println(aprovado);
		}
		else if (nota < 7.0 && nota >= 5.0 ) {
			System.out.println(recuperacao);
		}
		else if (nota < 5.0) {
			System.out.println(reprovado);
		}
		
	}

}
