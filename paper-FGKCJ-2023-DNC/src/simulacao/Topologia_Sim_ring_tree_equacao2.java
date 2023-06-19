package simulacao;

//Modificações de estudo estão neste arquivo


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Topologia_Sim_ring_tree_equacao2 {
	
	private static final double sc_R_s = 20;
	private static final double sc_T_s = 20;
//	private static final double sc_R_t = 20;
//	private static final double sc_T_t = 20;
//	private static final double sc_R_u = 20;
//	private static final double sc_T_u = 20;
	private static final double ac_r_0 = 5;
	private static final double ac_b_0 = 25;
//	private static final double ac_r_1 = 1.424E+6;
//	private static final double ac_b_1 = 1.424E+3;
	private static int N_u=1; //Qtde de fluxos urllc
//	private static int N_e=0; //Qtde de fluxos embb
	private static int N_server_s=7; // Quantidade de servidores
//	private static int N_server_t=4; // Quantidade de servidores
//	private static int N_server_u=5; // Quantidade de servidores

	private static boolean print_all=false;
	//Curva de Serviço
	private	static ServiceCurve sc_s = CurvePwAffine.getFactory().createRateLatency(sc_R_s,sc_T_s);
//	private	static ServiceCurve sc_t = CurvePwAffine.getFactory().createRateLatency(sc_R_t,sc_T_t);
//	private	static ServiceCurve sc_u = CurvePwAffine.getFactory().createRateLatency(sc_R_u,sc_T_u);
	//Curva de Chegada
	private	static ArrivalCurve ac_u = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0); //urllc
//	private	static ArrivalCurve ac_e = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1); //embb


	public static AnalysisConfig config = null;

	public static void rede_tfa_sfa() throws Exception {
		
		
		//Rede
		Network network = new Network();
		//Servidores/Switches
		Server[] s = new Server[N_server_s];
		for (int i=0;i<N_server_s;i++) {
			s[i]=network.addServer(sc_s);
		}
		
//		Server[] t = new Server[N_server_t];
//		for (int i=0;i<N_server_t;i++) {
//			t[i]=network.addServer(sc_t);
//		}
//		
//		Server[] u= new Server[N_server_u];
//		for (int i=0;i<N_server_u;i++) {
//			u[i]=network.addServer(sc_u);
//		}
		
		//Enlace entre Switches
		network.addLink(s[0], s[1]);
		network.addLink(s[1], s[2]);
		network.addLink(s[2], s[5]);
		network.addLink(s[5], s[6]);
		network.addLink(s[4], s[5]);
		network.addLink(s[3], s[4]);
//		network.addLink(s[6], t[3]);
//		network.addLink(s[7], t[3]);
		//------------------------
//		network.addLink(t[0], u[0]);
//		network.addLink(t[1], u[1]);
//		network.addLink(t[2], u[2]);
//		network.addLink(t[3], u[3]);
		//--------------------------
//		network.addLink(u[1], u[0]);
//		network.addLink(u[0], u[4]);
//		network.addLink(u[2], u[3]);
//		network.addLink(u[3], u[4]);
//		Server s0 = network.addServer(sc);
//		Server s1 = network.addServer(sc);
		//Enlace entre Switches
//		network.addLink(s0, s1);
		
		//-------------------
		// Fluxos em s0
		//Fluxos urllc
		
		Flow flow_u_f1 = network.addFlow(ac_u, s[0], s[6]); //sintaxe: fluxo, inicio, fim.
		Flow flow_u_f0 = network.addFlow(ac_u, s[1], s[6]); //sintaxe: fluxo, inicio, fim.
		Flow flow_u_f2 = network.addFlow(ac_u, s[3], s[6]); //sintaxe: fluxo, inicio, fim.


//		Flow[] flow_u_s0= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s0[i] = network.addFlow(ac_u, s[0], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s0= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s0[i] = network.addFlow(ac_e, s[0], u[4]);
//		}
//		}
		//--------------------
		// Fluxos em s1
		//Fluxos urllc
//		Flow[] flow_u_s1= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s1[i] = network.addFlow(ac_u, s[1], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s1= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s1[i] = network.addFlow(ac_e, s[1], u[4]);
//		}
//		}
		//--------------------
		// Fluxos em s2
		//Fluxos urllc
//		Flow[] flow_u_s2= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s2[i] = network.addFlow(ac_u, s[2], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s2= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s2[i] = network.addFlow(ac_e, s[2], u[4]);
//		}
//		}
		//--------------------
		// Fluxos em s3
		//Fluxos urllc
//		Flow[] flow_u_s3= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s3[i] = network.addFlow(ac_u, s[3], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s3= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s3[i] = network.addFlow(ac_e, s[3], u[4]);
//		}
//		}
		//--------------------
		// Fluxos em s4
		//Fluxos urllc
//		Flow[] flow_u_s4= new Flow[N_u];
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s4[i] = network.addFlow(ac_u, s[4], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
//		//Fluxos embb
//		Flow[] flow_e_s4= new Flow[N_e];
//		if (N_e!=0) {
//		for (int i=0;i<N_e;i++) {
//				flow_e_s4[i] = network.addFlow(ac_e, s[4], u[4]);
//		}
//		}
//		//--------------------
//		// Fluxos em s5
//		//Fluxos urllc
//		Flow[] flow_u_s5= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s5[i] = network.addFlow(ac_u, s[5], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
//		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s5= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s5[i] = network.addFlow(ac_e, s[5], u[4]);
//		}
//		}
//		//--------------------
//		// Fluxos em s6
//		//Fluxos urllc
//		Flow[] flow_u_s6= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s6[i] = network.addFlow(ac_u, s[6], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
//		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s6= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s6[i] = network.addFlow(ac_e, s[6], u[4]);
//		}
//		}
//		//--------------------
//		// Fluxos em s7
//		//Fluxos urllc
//		Flow[] flow_u_s7= new Flow[N_u];		
//		if (N_u!=0) {
//		for (int i=0;i<N_u;i++) {
//				flow_u_s7[i] = network.addFlow(ac_u, s[7], u[4]); //sintaxe: fluxo, inicio, fim.
//		}
//		}
//		//Fluxos embb
//		if (N_e!=0) {
//		Flow[] flow_e_s7= new Flow[N_e];
//		for (int i=0;i<N_e;i++) {
//				flow_e_s7[i] = network.addFlow(ac_e, s[7], u[4]);
//		}
//		}
		//----Escolhendo o fluxo de interesse------------
		
		Flow flow_of_interest_u=flow_u_f0;
////		Flow[] flow_of_interest_e=new Flow[0];
////		if (N_e!=0) {
////		flow_of_interest_e=flow_e_s4;
////		}
//		if (N_u!=0) {
//		flow_of_interest_u=flow_u_s2;
//		}




//		System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
//		System.out.println("Fluxos URLLC");
		if (print_all==true) {
			System.out.println("Fluxos URLLC");
		for (int i=0;i<N_u;i++) {
			analysis_sfa.performAnalysis(flow_of_interest_u);
			System.out.println("SFA para fluxo " + i + " -> " + analysis_sfa.getDelayBound().doubleValue());
	}
		for (int i=0;i<N_u;i++) {
			analysis_tfa.performAnalysis(flow_of_interest_u);
			System.out.println("TFA para fluxo " + i + " -> " + analysis_tfa.getDelayBound().doubleValue()*1E+6 + " us");
	}
//		System.out.println("Fluxos EMBB");
//		for (int i=0;i<N_e;i++) {
//			analysis_sfa.performAnalysis(flow_of_interest_e[i]);
//			System.out.println("SFA para fluxo " + i + " -> " + analysis_sfa.getDelayBound().doubleValue()*1E+6 + " us");
//	}
//		for (int i=0;i<N_e;i++) {
//			analysis_tfa.performAnalysis(flow_of_interest_e[i]);
//			System.out.println("TFA para fluxo " + i + " -> " + analysis_tfa.getDelayBound().doubleValue()*1E+6 + " us");
//	}
		}
		else {		
			if (N_u!=0) {
			System.out.println("Fluxo de interesse URLLC");
				analysis_sfa.performAnalysis(flow_of_interest_u);
				System.out.println("SFA -> " + analysis_sfa.getDelayBound().doubleValue());
	
				analysis_tfa.performAnalysis(flow_of_interest_u);
				System.out.println("TFA  -> " + analysis_tfa.getDelayBound().doubleValue()*1E+6 + " us");
			}
//			if (N_e!=0) {	
//			System.out.println("Fluxo de interesse EMBB");
//
//				analysis_sfa.performAnalysis(flow_of_interest_e[0]);
//				System.out.println("SFA  -> " + analysis_sfa.getDelayBound().doubleValue()*1E+6 + " us");
//
//				analysis_tfa.performAnalysis(flow_of_interest_e[0]);
//				System.out.println("TFA  -> " + analysis_tfa.getDelayBound().doubleValue()*1E+6 + " us");
//			}
		}
		
		
//		analysis_sfa.performAnalysis(flow[1]);
//		System.out.println("SFA para fluxo 2: " + analysis_sfa.getDelayBound().doubleValue());
		
//		analysis_tfa.performAnalysis(flow[0]);
//		System.out.println("TFA para fluxo 1: " + analysis_tfa.getDelayBound().doubleValue());
//		analysis_tfa.performAnalysis(flow[1]);
//		System.out.println("TFA para fluxo 2: " + analysis_tfa.getDelayBound().doubleValue());
		
	}
	

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

