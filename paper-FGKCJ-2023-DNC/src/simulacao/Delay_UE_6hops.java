package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Delay_UE_6hops {
	/* 
	 * Comparar com Omnet++
	 * 18.10.2022
	 */	
	// D-RAN
//	private static final double ac_r_0 = 1.024E+6;
//	private static final double ac_b_0 = 1.024E+3;
//	private static final double pkt_size = 12000;
	// C-RAN
//	private static final double ac_r_0 = 7.817E+6;
//	private static final double ac_b_0 = 7.817E+3;
//	private static final double max_pkt_size = 86101;
	
	private static final double ac_r_0 = 1.0704*1.024E+6;
	private static final double ac_b_0 = 1.0704*1.024E+3;
	private static final double max_pkt_size = 86101;
	
	
	//private static final double ac_r_0 = 1.02*1.024E+6;
	//private static final double ac_b_0 = 1.02*1.024E+3;
	
	private static final double sc_R1 = 1.2E+9;
	private static final double sc_T1 = max_pkt_size/sc_R1; //40.96E-6 ok para 0.05E+9 com phi 0.5
	//private static final double sc_T1 = 0;

	private static final double sc_R2 = 8E+9;
	private static final double sc_T2 = max_pkt_size/sc_R2; //40.96E-6 ok para 0.1E+9 com phi 0.25
	//private static final double sc_T2 = 0;

	private static final double sc_R3 = 20E+9;
	private static final double sc_T3 = max_pkt_size/sc_R3; //incluse atraso de propagação de 6km
	//private static final double sc_T3 = 0;
	
	private static final double sc_R4 = sc_R3;
	private static final double sc_T4 = max_pkt_size/sc_R4;
	//private static final double sc_T4 = 0;
	
	private static final double sc_R5 = sc_R3;
	private static final double sc_T5 = max_pkt_size/sc_R5;
	//private static final double sc_T5 = 0;
	
	private static final double sc_R6 = sc_R3;
	private static final double sc_T6 = max_pkt_size/sc_R6;
	
	//private static final double cte = 0.8;



	
//	private static final double ac_r_1 = 512E+3;
//	private static final double ac_b_1 = 512;
	//private static final double ac_b_0 = 1280;
	//private static final double ac_r_1 = 5120;
	//private static final double ac_b_1 = 2560;
	private static final double phi_R1 = 0.60; // percentual do servidor alocado ao urllc
	private static final double phi_R2 = 0.10; // percentual do servidor alocado ao urllc
	private static final double phi_R3 = 0.18; // percentual do servidor alocado ao urllc
	private static final double phi_R4 = 0.08; // percentual do servidor alocado ao urllc
	private static final double phi_R5 = 0.08; // percentual do servidor alocado ao urllc
	private static final double phi_R6 = 0.04; // percentual do servidor alocado ao urllc

	
//	private static final double phi_R1 = 0.667; // percentual do servidor alocado ao urllc
//	private static final double phi_R2 = 0.334; // percentual do servidor alocado ao urllc
//	private static final double phi_R3 = 0.334; // percentual do servidor alocado ao urllc
//	private static final double phi_R4 = 0.167; // percentual do servidor alocado ao urllc
//	private static final double phi_R5 = 0.167; // percentual do servidor alocado ao urllc
	
	//private static int N=10; //Qtde de fluxos urllc




	public static AnalysisConfig config = null;

	public static double rede_sfa(int N) throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1*phi_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2*phi_R2,sc_T2);
		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
		ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);
		ServiceCurve sc6 = CurvePwAffine.getFactory().createRateLatency(sc_R6*phi_R6,sc_T6);



		ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
//		ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1);
	
		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
		Server s2 = network.addServer(sc3);
		Server s3 = network.addServer(sc4);
		Server s4 = network.addServer(sc5);
		Server s5 = network.addServer(sc6);


		network.addLink(s0, s1);
		network.addLink(s1, s2);
		network.addLink(s2, s3);
		network.addLink(s3, s4);
		network.addLink(s4, s5);
		
		Flow[] flow= new Flow[N];		
		for (int i=0;i<N;i++) {
				flow[i] = network.addFlow(ac, s0, s5);
		}
//		flow[0] = network.addFlow(ac, s0, s4);
//		flow[1] = network.addFlow(ac2, s0, s4);

		//System.out.println("Resultados com dois fluxos em uma fila");

		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
		analysis_sfa.performAnalysis(flow[0]);
		System.out.println("SFA: " + analysis_sfa.getDelayBound().doubleValue());
		double sfa=analysis_sfa.getDelayBound().doubleValue();
		
		return sfa;

		
	}
	
public static double rede_tfa(int N) throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1*phi_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2*phi_R2,sc_T2);
		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
		ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);
		ServiceCurve sc6 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R6,sc_T6);


		ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		//ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	
		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
		Server s2 = network.addServer(sc3);
		Server s3 = network.addServer(sc4);
		Server s4 = network.addServer(sc5);
		Server s5 = network.addServer(sc6);

		network.addLink(s0, s1);
		network.addLink(s1, s2);
		network.addLink(s2, s3);
		network.addLink(s3, s4);
		network.addLink(s4, s5);
		
		Flow[] flow= new Flow[N];		
		for (int i=0;i<N;i++) {
				flow[i] = network.addFlow(ac, s0, s5);
		}

		//System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		
		analysis_tfa.performAnalysis(flow[0]);
		System.out.println("TFA: " + analysis_tfa.getDelayBound().doubleValue());
		double tfa=analysis_tfa.getDelayBound().doubleValue();
		
		return tfa;

		
	}

	
public static double rede_pmoo(int N) throws Exception {
		
	ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1*phi_R1,sc_T1);
	ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2*phi_R2,sc_T2);
	ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
	ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
	ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);
	ServiceCurve sc6 = CurvePwAffine.getFactory().createRateLatency(sc_R6*phi_R6,sc_T6);


	ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	//ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);

	Network network = new Network();

	Server s0 = network.addServer(sc1);
	Server s1 = network.addServer(sc2);
	Server s2 = network.addServer(sc3);
	Server s3 = network.addServer(sc4);
	Server s4 = network.addServer(sc5);
	Server s5 = network.addServer(sc6);

	network.addLink(s0, s1);
	network.addLink(s1, s2);
	network.addLink(s2, s3);
	network.addLink(s3, s4);
	network.addLink(s4, s5);
	Flow[] flow= new Flow[N];		
	for (int i=0;i<N;i++) {
			flow[i] = network.addFlow(ac, s0, s5);
	}

		//System.out.println("Resultados com dois fluxos em uma fila");

		PmooAnalysis analysis_pmoo = new PmooAnalysis(network, config);

		analysis_pmoo.performAnalysis(flow[0]);
		System.out.println("PMOO: " + analysis_pmoo.getDelayBound().doubleValue());
		double pmoo=analysis_pmoo.getDelayBound().doubleValue();

		return pmoo;

	}

	public static double fifo_sfa(int N) {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("FIFO_MUX");
			double sfa=rede_sfa(N);
			return sfa;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static double fifo_tfa(int N) {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("FIFO_MUX");
			double tfa=rede_tfa(N);
			return tfa;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static double blind_sfa(int N) {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("ARB_MUX");
			double sfa=rede_sfa(N);
			return sfa;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static double blind_tfa(int N) {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("ARB_MUX");
			double tfa=rede_tfa(N);
			return tfa;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
		
	public static double blind_pmoo(int N) {
		config = new AnalysisConfig();
//		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			//System.out.println("ARB_MUX com PMOO");
			double pmoo=rede_pmoo(N);
			return pmoo;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {

		try {
			int n=40; // Numero de UEs
			System.out.println("----------FIFO---------");
			fifo_sfa(n);
			fifo_tfa(n);
			System.out.println("----------ARB---------");
			blind_sfa(n);
			blind_tfa(n);
			blind_pmoo(n);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
