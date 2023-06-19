package simulacao;

//import de.uni_kl.cs.discodnc.network.Flow;
//import de.uni_kl.cs.discodnc.network.Network;
//import simulacao.Sim_2S;
//import simulacao.Sim_3S;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import java.util.Scanner;


public class Run_teste {
	
	public static void main(String[] args) throws IOException {
	System.out.println("alo");
//		Sim_3S sim_3S = new Sim_3S();
//		Sim_2S sim_2S = new Sim_2S();
//		Sim_1S.main(null);
		//Sim_2S.fifo_tfa_sfa();
	
	FileWriter arq = new FileWriter("C:\\Users\\flavi\\git\\eclipse_java\\DNC-jar\\src\\simulacao\\tabuada.txt");
    PrintWriter gravarArq = new PrintWriter(arq);
	
	int N=100;	
	for (int i=1;i<=N;i++) {
		System.out.println(i);
		double sfa=Delay_export_file.fifo_sfa(i);
		double tfa=Delay_export_file.fifo_tfa(i);
		gravarArq.printf("%e %e%n",sfa,tfa);
	}
	arq.close();
//	Tandem_5hops.fifo_tfa_sfa(30);
//		Sim_3S.main(null);
//		Sim_4S.main(null);
//		Sim_5S.main(null);
//		Sim_6S.main(null);
//		Sim_7S.main(null);
//		Sim_8S.main(null);
//		Sim_9S.main(null);
//		Sim_10S.main(null);
	}






	}