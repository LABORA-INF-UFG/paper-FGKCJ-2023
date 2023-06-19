package simulacao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Delay_run {
	
	public static void main(String[] args) throws IOException {
	
	FileWriter arq1 = new FileWriter("C:\\Users\\flavi\\git\\eclipse_java\\DNC-jar\\src\\simulacao\\delay_UE.txt");
    PrintWriter gravarArq1 = new PrintWriter(arq1);
	
	int N1=60;	//Numero de UEs
	//int sigma1=10;
	gravarArq1.printf("fifo_sfa fifo_tfa blind_sfa blind_tfa blind_pmoo%n");
	for (int i=1;i<=N1;i++) {
		System.out.println(i);
//		double sfa=Delay_UE_5hops.fifo_sfa(i);
//		double tfa=Delay_UE_5hops.fifo_tfa(i);
//		double fifo_sfa=Teste_definicao.fifo_sfa(i);
//		double fifo_tfa=Teste_definicao.fifo_tfa(i);
//		double blind_sfa=Teste_definicao.blind_sfa(i);
//		double blind_tfa=Teste_definicao.blind_tfa(i);
//		double blind_pmoo=Teste_definicao.blind_pmoo(i);
//		double fifo_sfa=Bursty_2hops.fifo_sfa(i);
//		double fifo_tfa=Bursty_2hops.fifo_tfa(i);
//		double blind_sfa=Bursty_2hops.blind_sfa(i);
//		double blind_tfa=Bursty_2hops.blind_tfa(i);
//		double blind_pmoo=Bursty_2hops.blind_pmoo(i);
//		double fifo_sfa=Delay_UE_5hops.fifo_sfa(i);
//		double fifo_tfa=Delay_UE_5hops.fifo_tfa(i);
//		double blind_sfa=Delay_UE_5hops.blind_sfa(i);
//		double blind_tfa=Delay_UE_5hops.blind_tfa(i);
//		double blind_pmoo=Delay_UE_5hops.blind_pmoo(i);
		double fifo_sfa=Delay_UE_6hops.fifo_sfa(i);
		double fifo_tfa=Delay_UE_6hops.fifo_tfa(i);
		double blind_sfa=Delay_UE_6hops.blind_sfa(i);
		double blind_tfa=Delay_UE_6hops.blind_tfa(i);
		double blind_pmoo=Delay_UE_6hops.blind_pmoo(i);
		
		gravarArq1.printf("%e %e %e %e %e%n",fifo_sfa,fifo_tfa,blind_sfa,blind_tfa,blind_pmoo);
	}
	arq1.close();
	
//	FileWriter arq2 = new FileWriter("C:\\Users\\flavi\\git\\eclipse_java\\DNC-jar\\src\\simulacao\\delay_sigma.txt");
//    PrintWriter gravarArq2 = new PrintWriter(arq2);
//    
//	int N2=10;	//Numero de UEs
//	int sigma2=10;
//	gravarArq2.printf("fifo_sfa fifo_tfa blind_sfa blind_tfa blind_pmoo%n");
//	for (int i=1;i<=sigma2;i++) {
//		System.out.println(i);
//
//		double fifo_sfa=Delay_sigma_5hops.fifo_sfa(N2,i);
//		double fifo_tfa=Delay_sigma_5hops.fifo_tfa(N2,i);
//		double blind_sfa=Delay_sigma_5hops.blind_sfa(N2,i);
//		double blind_tfa=Delay_sigma_5hops.blind_tfa(N2,i);
//		double blind_pmoo=Delay_sigma_5hops.blind_pmoo(N2,i);
//		
//		gravarArq2.printf("%e %e %e %e %e%n",fifo_sfa,fifo_tfa,blind_sfa,blind_tfa,blind_pmoo);
//	}
//	arq2.close();

	}






	}