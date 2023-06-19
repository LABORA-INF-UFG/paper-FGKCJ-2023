package simulacao;

//Modificações de estudo estão neste arquivo


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Sim_2S {
	
	private static final double sc0_R = 20;
	private static final double sc0_T = 2;
	private static final double sc1_R = 20;
	private static final double sc1_T = 2;
	//private static final double sc2_R = 15;
	//private static final double sc2_T = 3;
	private static final double ac_r_0 = 5;
	private static final double ac_b_0 = 5;
	private static final double ac_r_1 = 5;
	private static final double ac_b_1 = 5;
	//private static final double ac_r_2 = 4;
	//private static final double ac_b_2 = 5;
	private static int N_u=1; //Qtde de fluxos urllc
	private static int N_e=1; //Qtde de fluxos embb
	//private static int N_x=1; //Qtde de fluxos embb
	private static int N_server=2; // Quantidade de servidores
	private static boolean print_all=false;
	//Curva de Serviço
		private	static ServiceCurve sc_0 = CurvePwAffine.getFactory().createRateLatency(sc0_R,sc0_T);
		private	static ServiceCurve sc_1 = CurvePwAffine.getFactory().createRateLatency(sc1_R,sc1_T);
		//private	static ServiceCurve sc_2 = CurvePwAffine.getFactory().createRateLatency(sc2_R,sc2_T);

			//Curva de Chegada
		private	static ArrivalCurve ac_u = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0); //urllc
		private	static ArrivalCurve ac_e = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1); //embb
		//private	static ArrivalCurve ac_x = CurvePwAffine.getFactory().createTokenBucket(ac_r_2, ac_b_2); //embb



	public static AnalysisConfig config = null;

	public static void rede_tfa_sfa() throws Exception {
		
		
		//Rede
		Network network = new Network();
		//Servidores/Switches
		Server[] s = new Server[N_server];
//		for (int i=0;i<N_server;i++) {
//			s[i]=network.addServer(sc);
//		}
		s[0]=network.addServer(sc_0);
		s[1]=network.addServer(sc_1);
		//s[2]=network.addServer(sc_2);


		//Enlace entre Switches
		for (int i=0;i<N_server-1;i++) {
			network.addLink(s[i], s[i+1]);

		}
//		Server s0 = network.addServer(sc);
//		Server s1 = network.addServer(sc);
		//Enlace entre Switches
//		network.addLink(s0, s1);
		//Fluxos urllc
		Flow[] flow_u= new Flow[N_u];		
		for (int i=0;i<N_u;i++) {
				flow_u[i] = network.addFlow(ac_u, s[0], s[N_server-1]);
		}
		//Fluxos embb
		Flow[] flow_e= new Flow[N_e];
		for (int i=0;i<N_e;i++) {
				flow_e[i] = network.addFlow(ac_e, s[0], s[N_server-1]);
		}
		
		/*Flow[] flow_x= new Flow[N_x];
		for (int i=0;i<N_x;i++) {
				flow_x[i] = network.addFlow(ac_x, s[0], s[N_server-2]);
		}
		*/

//		System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
//		System.out.println("Fluxos URLLC");
		if (print_all==true) {
		for (int i=0;i<N_u;i++) {
			analysis_sfa.performAnalysis(flow_u[i]);
			System.out.println("SFA para fluxo " + i + " -> " + analysis_sfa.getDelayBound().doubleValue());
	}
		for (int i=0;i<N_u;i++) {
			analysis_tfa.performAnalysis(flow_u[i]);
			System.out.println("TFA para fluxo " + i + " -> " + analysis_tfa.getDelayBound().doubleValue());
	}
		System.out.println("Fluxos EMBB");
		for (int i=0;i<N_e;i++) {
			analysis_sfa.performAnalysis(flow_e[i]);
			System.out.println("SFA para fluxo " + i + " -> " + analysis_sfa.getDelayBound().doubleValue());
	}
		for (int i=0;i<N_e;i++) {
			analysis_tfa.performAnalysis(flow_e[i]);
			System.out.println("TFA para fluxo " + i + " -> " + analysis_tfa.getDelayBound().doubleValue());
	}
		}
		else {		
			System.out.println("Fluxo de interesse URLLC");
				analysis_sfa.performAnalysis(flow_u[0]);
				System.out.println("SFA -> " + analysis_sfa.getDelayBound().doubleValue());
	
				analysis_tfa.performAnalysis(flow_u[0]);
				System.out.println("TFA  -> " + analysis_tfa.getDelayBound().doubleValue());
				
			System.out.println("Fluxo de interesse EMBB");

				analysis_sfa.performAnalysis(flow_e[0]);
				System.out.println("SFA  -> " + analysis_sfa.getDelayBound().doubleValue());

				analysis_tfa.performAnalysis(flow_e[0]);
				System.out.println("TFA  -> " + analysis_tfa.getDelayBound().doubleValue());
		}
		
		
//		analysis_sfa.performAnalysis(flow[1]);
//		System.out.println("SFA para fluxo 2: " + analysis_sfa.getDelayBound().doubleValue());
		
//		analysis_tfa.performAnalysis(flow[0]);
//		System.out.println("TFA para fluxo 1: " + analysis_tfa.getDelayBound().doubleValue());
//		analysis_tfa.performAnalysis(flow[1]);
//		System.out.println("TFA para fluxo 2: " + analysis_tfa.getDelayBound().doubleValue());
		
	}
	
//public static void rede_pmoo() throws Exception {
//				
//		//Rede
//		Network network = new Network();
//		//Servidores/Switches
//		Server[] s = new Server[N_server];
//		for (int i=0;i<N_server;i++) {
//			s[i]=network.addServer(sc_0);
//		}
//		//Enlace entre Switches
//		for (int i=0;i<N_server-1;i++) {
//			network.addLink(s[i], s[i+1]);
//
//		}
////		Server s0 = network.addServer(sc);
////		Server s1 = network.addServer(sc);
//		//Enlace entre Switches
////		network.addLink(s0, s1);
//		//Fluxos urllc
//		Flow[] flow_u= new Flow[N_u];		
//		for (int i=0;i<N_u;i++) {
//				flow_u[i] = network.addFlow(ac_u, s[0], s[N_server-1]);
//		}
//		//Fluxos embb
//		Flow[] flow_e= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e[i] = network.addFlow(ac_e, s[0], s[N_server-1]);
//		}
//
////		System.out.println("Resultados com dois fluxos em uma fila");
//		
//		PmooAnalysis analysis_pmoo = new PmooAnalysis(network, config);
//
////		analysis_pmoo.performAnalysis(flow1);
////		System.out.println("PMOO para fluxo 1: " + analysis_pmoo.getDelayBound().doubleValue());
////		analysis_pmoo.performAnalysis(flow2);
////		System.out.println("PMOO para fluxo 2: " + analysis_pmoo.getDelayBound().doubleValue());
//
////		System.out.println("Fluxos URLLC para PMOO");
//		if (print_all==true) {
//		for (int i=0;i<N_u;i++) {
//			analysis_pmoo.performAnalysis(flow_u[i]);
//			System.out.println("PMOO para fluxo " + i + " -> " + analysis_pmoo.getDelayBound().doubleValue());
//	}
//		System.out.println("Fluxos EMBB para PMOO");
//		for (int i=0;i<N_e;i++) {
//			analysis_pmoo.performAnalysis(flow_e[i]);
//			System.out.println("PMOO para fluxo " + i + " -> " + analysis_pmoo.getDelayBound().doubleValue());
//	}
//		}
//		else {		
//			System.out.println("Fluxo de interesse URLLC para PMOO");
//				analysis_pmoo.performAnalysis(flow_u[0]);
//				System.out.println("PMOO -> " + analysis_pmoo.getDelayBound().doubleValue());
//				
//			System.out.println("Fluxo de interesse EMBB para PMOO");
//
//				analysis_pmoo.performAnalysis(flow_e[0]);
//				System.out.println("PMOO  -> " + analysis_pmoo.getDelayBound().doubleValue());
//
//		}
//
//	}

	public static void fifo_tfa_sfa() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("------------------FIFO_MUX-------------------");
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
			System.out.println("------------------ARB_MUX------------------");
			rede_tfa_sfa();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
//	public static void blind_pmoo() {
//		config = new AnalysisConfig();
////		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
//        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
//        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
//
//		try {
//			System.out.println("------------------ARB_MUX com PMOO------------------");
//			rede_pmoo();
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
//	}
	
	public static void main(String[] args) {

		try {
			fifo_tfa_sfa();
			blind_tfa_sfa();
//			blind_pmoo();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

