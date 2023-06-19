package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Sim_2S_teste {

	public static AnalysisConfig config = null;

	public static void rede() throws Exception {
		
		ServiceCurve sc = CurvePwAffine.getFactory().createRateLatency(10,10);

		ArrivalCurve ac1 = CurvePwAffine.getFactory().createTokenBucket(5, 25);
		ArrivalCurve ac2 = CurvePwAffine.getFactory().createTokenBucket(5, 25);
	
		Network network = new Network();

		Server s0 = network.addServer(sc);
		Server s1 = network.addServer(sc);
		
		network.addLink(s0, s1);

		Flow flow1 = network.addFlow(ac1, s0, s1);
		Flow flow2 = network.addFlow(ac2, s0, s1);

		System.out.println("Resultados com dois fluxos em uma fila");

		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		SeparateFlowAnalysis analysis_sfa = new SeparateFlowAnalysis(network, config);
		PmooAnalysis analysis_pmoo = new PmooAnalysis(network, config);

		
		
		analysis_sfa.performAnalysis(flow1);
		System.out.println("SFA para fluxo 1: " + analysis_sfa.getDelayBound().doubleValue());
		analysis_sfa.performAnalysis(flow2);
		System.out.println("SFA para fluxo 2: " + analysis_sfa.getDelayBound().doubleValue());
		
		analysis_tfa.performAnalysis(flow1);
		System.out.println("TFA para fluxo 1: " + analysis_tfa.getDelayBound().doubleValue());
		analysis_tfa.performAnalysis(flow2);
		System.out.println("TFA para fluxo 2: " + analysis_tfa.getDelayBound().doubleValue());
		
		analysis_pmoo.performAnalysis(flow1);
		System.out.println("PMOO para fluxo 1: " + analysis_pmoo.getDelayBound().doubleValue());
		analysis_pmoo.performAnalysis(flow2);
		System.out.println("PMOO para fluxo 2: " + analysis_pmoo.getDelayBound().doubleValue());

	}

	public static void fifo() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("FIFO_MUX");
			rede();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void blind() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
//		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("ARB_MUX");
			rede();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void pmoo() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("PMOO");
			rede();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {

		try {
			fifo();
			blind();
			pmoo();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

