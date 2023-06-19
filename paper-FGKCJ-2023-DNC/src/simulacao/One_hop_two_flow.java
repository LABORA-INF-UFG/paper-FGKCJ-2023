package simulacao;

import de.uni_kl.cs.discodnc.curves.ArrivalCurve;
import de.uni_kl.cs.discodnc.curves.CurvePwAffine;
import de.uni_kl.cs.discodnc.curves.ServiceCurve;
import de.uni_kl.cs.discodnc.network.Flow;
import de.uni_kl.cs.discodnc.network.Network;
import de.uni_kl.cs.discodnc.network.NetworkFactory;
import de.uni_kl.cs.discodnc.network.Server;

public class One_hop_two_flow implements NetworkFactory {
	private static final int sc_R = 10;
	private static final int sc_T = 10;
	private static final int ac_r_0 = 4;
	private static final int ac_b_0 = 10;
	private static final int ac_r_1 = 5;
	private static final int ac_b_1 = 25;
	protected Server s0;
	protected Flow f0, f1;
	private ServiceCurve service_curve = CurvePwAffine.getFactory().createRateLatency(sc_R, sc_T);
	private ArrivalCurve arrival_curve_0 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
	private ArrivalCurve arrival_curve_1 = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1);
	private Network network;

	public One_hop_two_flow() {
		network = createNetwork();
	}

	public Network getNetwork() {
		return network;
	}

	public Network createNetwork() {
		network = new Network();

		s0 = network.addServer(service_curve);
		s0.setUseGamma(false);
		s0.setUseExtraGamma(false);

		try {
			f0 = network.addFlow(arrival_curve_0, s0);
			f1 = network.addFlow(arrival_curve_1, s0);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return network;
	}

	public void reinitializeCurves() {
		service_curve = CurvePwAffine.getFactory().createRateLatency(sc_R, sc_T);
		for (Server server : network.getServers()) {
			server.setServiceCurve(service_curve);
		}

		arrival_curve_0 = CurvePwAffine.getFactory().createTokenBucket(ac_r_0, ac_b_0);
		f0.setArrivalCurve(arrival_curve_0);

		arrival_curve_1 = CurvePwAffine.getFactory().createTokenBucket(ac_r_1, ac_b_1);
		f1.setArrivalCurve(arrival_curve_1);
	}
}

