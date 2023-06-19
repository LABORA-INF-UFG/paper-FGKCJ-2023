package simulacao;


import de.uni_kl.cs.discodnc.network.*;
import de.uni_kl.cs.discodnc.curves.*;
import de.uni_kl.cs.discodnc.nc.*;
import de.uni_kl.cs.discodnc.nc.analyses.*;

public class Sim {

	public static AnalysisConfig config = null;

	public static void OneHopTwoFlows() throws Exception {
		ServiceCurve switch_serv_curve = CurvePwAffine.getFactory().createRateLatency(10,10);

		ArrivalCurve flow1 = CurvePwAffine.getFactory().createTokenBucket(4, 10);
		ArrivalCurve flow2 = CurvePwAffine.getFactory().createTokenBucket(5, 25);
	
		Network network = new Network();

		Server s0 = network.addServer(switch_serv_curve);

		Flow bh_flow = network.addFlow(flow1, s0);
		Flow fh_flow = network.addFlow(flow2, s0);

		System.out.println("Resultados com dois fluxos em uma fila");

		SeparateFlowAnalysis analysis = new SeparateFlowAnalysis(network, config);
		TotalFlowAnalysis analysis_tfa = new TotalFlowAnalysis(network, config);
		
		analysis.performAnalysis(bh_flow);
		System.out.println("SFA para fluxo 1: " + analysis.getDelayBound().doubleValue());
		analysis_tfa.performAnalysis(bh_flow);
		System.out.println("TFA para fluxo 1: " + analysis_tfa.getDelayBound().doubleValue());

		analysis.performAnalysis(fh_flow);
		System.out.println("SFA para fluxo 2: " + analysis.getDelayBound().doubleValue());
		analysis_tfa.performAnalysis(fh_flow);
		System.out.println("TFA para fluxo 2: " + analysis_tfa.getDelayBound().doubleValue());

	}

	public static void fifo() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_FIFO);
		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PBOO_CONCATENATION);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("FIFO_MUX");
			OneHopTwoFlows();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static void blind() {
		config = new AnalysisConfig();
		config.setMultiplexingDiscipline(AnalysisConfig.MuxDiscipline.GLOBAL_ARBITRARY);
		config.setArrivalBoundMethod(AnalysisConfig.ArrivalBoundMethod.PMOO);
        config.setUseGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);
        config.setUseExtraGamma(AnalysisConfig.GammaFlag.GLOBALLY_OFF);

		try {
			System.out.println("ARB_MUX");
			OneHopTwoFlows();
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
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

