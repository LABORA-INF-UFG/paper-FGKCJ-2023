package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Tandem_4hops {
	/* 
	 * Comparar com Omnet++
	 * Pacotes de 32 e 64Bytes
	 * 4 servidores com as seguintes capacidades: 16384bps; 32768bps; 2 de 131072bps; 
	 * Se considerar o processamento do maior pacote, então: T1=64*8/16384=0.03125;T2=64*8/32768=0.015625;T3=64*8/131072=0.00390625;
	 */
	
	private static final double sc_R1 = 16384;
	private static final double sc_T1 = 0.03125;
	//private static final double sc_T1 = 0;

	private static final double sc_R2 = 32768;
	private static final double sc_T2 = 0.015625;
	//private static final double sc_T2 = 0;

	private static final double sc_R3 = 131072;
	private static final double sc_T3 = 0.00390625;
	//private static final double sc_T3 = 0;
	
	private static final double sc_R4 = 131072;
	private static final double sc_T4 = 0.00390625;
	//private static final double sc_T4 = 0;
	
	//private static final double sc_R5 = 131072;
	//private static final double sc_T5 = 0.00390625;
	//private static final double sc_T5 = 0;

	private static final double ac_r_0 = 2560;
	private static final double ac_b_0 = 0;
	//private static final double ac_b_0 = 1280;
	//private static final double ac_r_1 = 5120;
	//private static final double ac_b_1 = 2560;
	private static final double phi_R1 = 0.5; // percentual do servidor alocado ao urllc
	private static final double phi_R2 = 0.25; // percentual do servidor alocado ao urllc
	private static final double phi_R3 = 0.125; // percentual do servidor alocado ao urllc
	private static final double phi_R4 = 0.0625; // percentual do servidor alocado ao urllc
	//private static final double phi_R5 = 0.0625; // percentual do servidor alocado ao urllc



	public static AnalysisConfig config = null;

	public static void rede_tfa_sfa() throws Exception {
		
		ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1*phi_R1,sc_T1);
		ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2*phi_R2,sc_T2);
		ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3*phi_R3,sc_T3);
		ServiceCurve sc4 = CurvePwAffine.getFactory().createRateLatency(sc_R4*phi_R4,sc_T4);



		ArrivalCurve ac1 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	
		Network network = new Network();

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
		Server s2 = network.addServer(sc3);
		Server s3 = network.addServer(sc4);


		
		network.addLink(s0, s1);
		network.addLink(s1, s2);
		network.addLink(s2, s3);



		Flow flow1 = network.addFlow(ac1, s0, s3);
		Flow flow2 = network.addFlow(ac2, s0, s3);

		System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
		
		analysis_sfa.performAnalysis(flow1);
		System.out.println("SFA para fluxo 1: " + analysis_sfa.getDelayBound().doubleValue());
		analysis_sfa.performAnalysis(flow2);
		System.out.println("SFA para fluxo 2: " + analysis_sfa.getDelayBound().doubleValue());
		
		analysis_tfa.performAnalysis(flow1);
		System.out.println("TFA para fluxo 1: " + analysis_tfa.getDelayBound().doubleValue());
		analysis_tfa.performAnalysis(flow2);
		System.out.println("TFA para fluxo 2: " + analysis_tfa.getDelayBound().doubleValue());
		
	}
	
public static void rede_pmoo() throws Exception {
		
	ServiceCurve sc1 = CurvePwAffine.getFactory().createRateLatency(sc_R1,sc_T1);
	ServiceCurve sc2 = CurvePwAffine.getFactory().createRateLatency(sc_R2,sc_T2);
	ServiceCurve sc3 = CurvePwAffine.getFactory().createRateLatency(sc_R3,sc_T3);


	ArrivalCurve ac1 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	
		Network network = new Network();
		
//		 for(int i = 1; i <= 10; i++) {  
//	         System.out.println(i);  
//	      }   

		Server s0 = network.addServer(sc1);
		Server s1 = network.addServer(sc2);
		Server s2 = network.addServer(sc3);
		Server s3 = network.addServer(sc3);
		Server s4 = network.addServer(sc3);


		
		network.addLink(s0, s1); // link é feito de dois em dois
		network.addLink(s1, s2);
		network.addLink(s2, s3);
		network.addLink(s3, s4);



		Flow flow1 = network.addFlow(ac1, s0, s4); // De s0 até s1, ou seja, inicio e fim (ex: s0,s2)
		Flow flow2 = network.addFlow(ac2, s0, s4);

		System.out.println("Resultados com dois fluxos em uma fila");

		PmooAnalysis analysis_pmoo = new PmooAnalysis(network, config);

		analysis_pmoo.performAnalysis(flow1);
		System.out.println("PMOO para fluxo 1: " + analysis_pmoo.getDelayBound().doubleValue());
		analysis_pmoo.performAnalysis(flow2);
		System.out.println("PMOO para fluxo 2: " + analysis_pmoo.getDelayBound().doubleValue());

	}

	public static void fifo_tfa_sfa() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("FIFO_MUX");
			rede_tfa_sfa();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void blind_tfa_sfa() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("ARB_MUX");
			rede_tfa_sfa();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
	public static void blind_pmoo() {
		config = new AnalysisConfig();
//		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("ARB_MUX com PMOO");
			rede_pmoo();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {

		try {
			fifo_tfa_sfa();
			blind_tfa_sfa();
			blind_pmoo();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

