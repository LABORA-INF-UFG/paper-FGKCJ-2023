package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Teste_definicao {
	/* 
	 * Comparar com Omnet++
	 * 18.10.2022
	 */	
	
	private static final double sc_R1 = 12800;
	private static final double sc_T1 = 0.02;
	//private static final double sc_T1 = 0;

	private static final double sc_R2 = 12800;
	private static final double sc_T2 = 0.02;
	//private static final double sc_T2 = 0;

//	private static final double sc_R3 = 2.5E+9;
//	private static final double sc_T3 = 0.8192E-6;
//	//private static final double sc_T3 = 0;
//	
//	private static final double sc_R4 = 2.5E+9;
//	private static final double sc_T4 = 0.8192E-6;
//	//private static final double sc_T4 = 0;
//	
//	private static final double sc_R5 = 2.5E+9;
//	private static final double sc_T5 = 0.8192E-6;
//	//private static final double sc_T5 = 0;

	private static final double ac_r_0 = 1280;
	private static final double ac_b_0 = 0;
	//private static final double ac_b_0 = 1280;
	//private static final double ac_r_1 = 5120;
	//private static final double ac_b_1 = 2560;
//	private static final double phi_R1 = 0.5; // percentual do servidor alocado ao urllc
//	private static final double phi_R2 = 0.25; // percentual do servidor alocado ao urllc
//	private static final double phi_R3 = 0.25; // percentual do servidor alocado ao urllc
//	private static final double phi_R4 = 0.125; // percentual do servidor alocado ao urllc
//	private static final double phi_R5 = 0.0625; // percentual do servidor alocado ao urllc
	
	//private static int N=10; //Qtde de fluxos urllc




	public static AnalysisConfig config = null;

	public static double rede_sfa(int N) throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2,sc_T2);
//		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
//		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
//		ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);


		ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		//ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	
		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
//		Server s2 = network.addServer(sc3);
//		Server s3 = network.addServer(sc4);
//		Server s4 = network.addServer(sc5);

		network.addLink(s0, s1);
//		network.addLink(s1, s2);
//		network.addLink(s2, s3);
//		network.addLink(s3, s4);
		
		Flow[] flow= new Flow[N];		
		for (int i=0;i<N;i++) {
				flow[i] = network.addFlow(ac, s0, s1);
		}

		//System.out.println("Resultados com dois fluxos em uma fila");

		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
		analysis_sfa.performAnalysis(flow[0]);
		System.out.println("SFA: " + analysis_sfa.getDelayBound().doubleValue());
		double sfa=analysis_sfa.getDelayBound().doubleValue();
		
		return sfa;

		
	}
	
public static double rede_tfa(int N) throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2,sc_T2);
//		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
//		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
//		ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);


		ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		//ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	
		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
//		Server s2 = network.addServer(sc3);
//		Server s3 = network.addServer(sc4);
//		Server s4 = network.addServer(sc5);

		network.addLink(s0, s1);
//		network.addLink(s1, s2);
//		network.addLink(s2, s3);
//		network.addLink(s3, s4);
		
		Flow[] flow= new Flow[N];		
		for (int i=0;i<N;i++) {
				flow[i] = network.addFlow(ac, s0, s1);
		}

		//System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		
		analysis_tfa.performAnalysis(flow[0]);
		System.out.println("TFA: " + analysis_tfa.getDelayBound().doubleValue());
		double tfa=analysis_tfa.getDelayBound().doubleValue();
		
		return tfa;

		
	}

	
public static double rede_pmoo(int N) throws Exception {
		
	ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1,sc_T1);
	ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2,sc_T2);
//	ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
//	ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);
//	ServiceCurve sc5 = CurvePwAffine.getFactory().createRateLatency(sc_R5*phi_R5,sc_T5);


	ArrivalCurve ac = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	//ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);

	Network network = new Network();

	Server s0 = network.addServer(sc1);
	Server s1 = network.addServer(sc2);
//	Server s2 = network.addServer(sc3);
//	Server s3 = network.addServer(sc4);
//	Server s4 = network.addServer(sc5);

	network.addLink(s0, s1);
//	network.addLink(s1, s2);
//	network.addLink(s2, s3);
//	network.addLink(s3, s4);
	
	Flow[] flow= new Flow[N];		
	for (int i=0;i<N;i++) {
			flow[i] = network.addFlow(ac, s0, s1);
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
			int n=2; // Numero de UEs
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

