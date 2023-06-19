package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Delay_UE_5hops_Exemplo {
	/* 
	 * Comparar com Omnet++
	 * 18.10.2022
	 */	
	
	private static final double sc_R1 = 0.05E+9;
	private static final double sc_T1 = 40.96E-6; //40.96E-6 ok para 0.05E+9 com phi 0.5
	//private static final double sc_T1 = 0;

	private static final double sc_R2 = 0.1E+9;
	private static final double sc_T2 = 40.96E-6; //40.96E-6 ok para 0.1E+9 com phi 0.25
	//private static final double sc_T2 = 0;

	private static final double sc_R3 = 0.25E+9;
	private static final double sc_T3 = 20E-6+16.384E-6; //incluse atraso de propagação de 6km
	//private static final double sc_T3 = 0;
	
	private static final double sc_R4 = 0.25E+9;
	private static final double sc_T4 = 20E-6+8.192E-6;
	//private static final double sc_T4 = 0;
	
	private static final double sc_R5 = 0.25E+9;
	private static final double sc_T5 = 20E-6+4.096E-6;
	//private static final double sc_T5 = 0;

	private static final double ac_r_0 = 1024E+3;
	private static final double ac_b_0 = 1024;
	
	private static final double ac_r_1 = 512E+3;
	private static final double ac_b_1 = 4096;
	
	private static final double ac_r_2 = 256E+3;
	private static final double ac_b_2 = 2048;
	//private static final double ac_b_0 = 1280;
	//private static final double ac_r_1 = 5120;
	//private static final double ac_b_1 = 2560;
//	private static final double phi_R1 = 0.6; // percentual do servidor alocado ao urllc
//	private static final double phi_R2 = 0.3; // percentual do servidor alocado ao urllc
//	private static final double phi_R3 = 0.3; // percentual do servidor alocado ao urllc
//	private static final double phi_R4 = 0.15; // percentual do servidor alocado ao urllc
//	private static final double phi_R5 = 0.075; // percentual do servidor alocado ao urllc
	
	private static final double phi_R1 = 0.5; // percentual do servidor alocado ao urllc
	private static final double phi_R2 = 0.25; // percentual do servidor alocado ao urllc
	private static final double phi_R3 = 0.25; // percentual do servidor alocado ao urllc
	private static final double phi_R4 = 0.125; // percentual do servidor alocado ao urllc
	private static final double phi_R5 = 0.0625; // percentual do servidor alocado ao urllc
	
	//private static int N=10; //Qtde de fluxos urllc




	public static AnalysisConfig config = null;

	public static void rede_sfa() throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1*phi_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2*phi_R2,sc_T2);
		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
		ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);


		ArrivalCurve ac1 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1);
		ArrivalCurve ac3 = CurvePwAffine.getFactory().createTokenBucket(ac_r_2, ac_b_2);

		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
		Server s2 = network.addServer(sc3);
		Server s3 = network.addServer(sc4);
		Server s4 = network.addServer(sc5);

		network.addLink(s0, s1);
		network.addLink(s1, s2);
		network.addLink(s2, s3);
		network.addLink(s3, s4);
		
		Flow[] flow= new Flow[3];		
//		for (int i=0;i<N;i++) {
//				flow[i] = network.addFlow(ac, s0, s4);
//		}
		flow[0] = network.addFlow(ac1, s0, s4);
		flow[1] = network.addFlow(ac2, s0, s4);
		flow[2] = network.addFlow(ac3, s0, s4);


		//System.out.println("Resultados com dois fluxos em uma fila");

		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
		analysis_sfa.performAnalysis(flow[0]);
		System.out.println("SFA: " + analysis_sfa.getDelayBound().doubleValue());
		double sfa=analysis_sfa.getDelayBound().doubleValue();
		
//		return sfa;

		
	}
	
	public static void fifo_sfa() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("FIFO_MUX");
			rede_sfa();
//			return sfa;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	


		
public static void main(String[] args) {

		try {
//			int n=4; // Numero de UEs
			System.out.println("----------FIFO---------");
			fifo_sfa();

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

