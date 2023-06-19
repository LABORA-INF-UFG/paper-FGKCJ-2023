//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with this program.  If not, see http://www.gnu.org/licenses/.
//
import inet.queueing.flow.FlowMeasurementRecorder;
import inet.queueing.source.ActivePacketSource;
import inet.queueing.queue.PacketQueue;
import inet.queueing.server.PacketServer;
import inet.queueing.sink.PassivePacketSink;
import inet.queueing.common.PacketMultiplexer;
import inet.queueing.scheduler.PriorityScheduler;
import inet.queueing.scheduler.WrrScheduler;
import inet.queueing.marker.ContentBasedLabeler;
import inet.queueing.marker.ContentBasedTagger;
import inet.queueing.classifier.ContentBasedClassifier;
import inet.queueing.classifier.LabelClassifier;
import inet.queueing.scheduler.WrrScheduler;
import inet.queueing.shaper.TokenBucket;
import inet.queueing.tokengenerator.TimeBasedTokenGenerator;
import inet.queueing.common.PacketDelayer;



network Route1
{
    parameters:
        int np_vdu1;
        int np_vdu2;
        int np_vdu3;
        int np_vdu4;
        @display("bgb=2763.826,1432.444");
    submodules:
        p1du1[np_vdu1]: ActivePacketSource {
            @display("p=64.22,30.42,c,100");
        }
        b1du1[np_vdu1]: TokenBucket {
            @display("p=204.61539,30.769232,c,100");
        }
        t1du1[np_vdu1]: TimeBasedTokenGenerator {
            @display("p=693.8462,21.538462,c,100");
            storageModule = "^.b1du1["+string(ancestorIndex(0))+"].server";
        }
        mux1du1: PacketMultiplexer {
            @display("p=265.3846,30.000002;b=31.538464,28.46154");
        }
        S1du1: PacketServer {
            @display("p=583.05,77.74");
        }
        Q1du1: PacketQueue {
            @display("p=370.11,30.42");
        }
        Q2du1: PacketQueue {
            @display("p=370.11,108.159996");
        }
        sch1du1: WrrScheduler {
            @display("p=485.03,77.74");
        }
        p2du1: ActivePacketSource {
            @display("p=64.22,108.159996,c,100");
        }
        L1du1: ContentBasedLabeler {
            @display("p=325.3846,30.769232");
        }
        cdu1: LabelClassifier {
            @display("p=770.63995,153.79");
        }
        Q1eth1: PacketQueue {
            @display("p=888.94,30.42");
        }
        Q2eth1: PacketQueue {
            @display("p=888.94,118.299995");
        }
        Seth1: PacketServer {
            @display("p=1074,154");
        }
        scheth1: WrrScheduler {
            @display("p=993.72,153.79");
        }
        L2du1: ContentBasedLabeler {
            @display("p=325.3846,108.46154");
        }

        cR2: LabelClassifier {
            @display("p=1167.4,822.89996");
        }
        L3du1: ContentBasedLabeler {
            @display("p=325.3846,196.15385");
        }
        L4du1: ContentBasedLabeler {
            @display("p=325.3846,284.6154");
        }

        mux3du1: PacketMultiplexer {
            @display("p=265.3846,195.38463;b=29.23077,30.769232");
        }
        Q3du1: PacketQueue {
            @display("p=370.11,196.04");
        }
        Q4du1: PacketQueue {
            @display("p=370.11,283.91998");
        }
        S2du1: PacketServer {
            @display("p=583.05,238.29");
        }
        p3du1[np_vdu2]: ActivePacketSource {
            @display("p=64.22,196.04,c,100");
        }
        b3du1[np_vdu2]: TokenBucket {
            @display("p=196.15385,196.15385,c,100");
        }
        t3du1[np_vdu2]: TimeBasedTokenGenerator {
            @display("p=693.8462,243.07693,c,100");
            storageModule = "^.b3du1["+string(ancestorIndex(0))+"].server";
        }
        p4du1: ActivePacketSource {
            @display("p=64.22,287.3,c,100");
        }
        sch2du1: WrrScheduler {
            @display("p=485.03,238.29");
        }
        muxdu1: PacketMultiplexer {
            @display("p=682.76,153.79;b=33.159996,33.159996");
        }
        Q3eth1: PacketQueue {
            @display("p=887.25,196.04");
        }
        Q4eth1: PacketQueue {
            @display("p=888.94,283.91998");
        }
        Q1R2: PacketQueue {
            @display("p=1245.4,703.3");
        }
        Q2R2: PacketQueue {
            @display("p=1245.4,795.6");
        }
        Q3R2: PacketQueue {
            @display("p=1245.4,868.39996");
        }
        Q4R2: PacketQueue {
            @display("p=1245.4,959.39996");
        }
        SR2: PacketServer {
            @display("p=1420.9,826.8");
        }
        schR2: WrrScheduler {
            @display("p=1339,826.8");
        }
        cR3: LabelClassifier {
            @display("p=1537.8999,317.72");
        }
        Q1R3: PacketQueue {
            @display("p=1662.96,30.42");
        }
        Q2R3: PacketQueue {
            @display("p=1662.96,99.71");
        }
        Q3R3: PacketQueue {
            @display("p=1662.96,167.31");
        }
        Q4R3: PacketQueue {
            @display("p=1662.96,250.12");
        }
        SR3: PacketServer {
            @display("p=1889.4199,317.72");
        }
        schR3: WrrScheduler {
            @display("p=1779.57,317.72");
        }
        muxR1: PacketMultiplexer {
            @display("p=1462,316;b=33.159996,33.159996");
        }
        Q5R3: PacketQueue {
            @display("p=1662.96,338");
        }
        Q6R3: PacketQueue {
            @display("p=1662.96,420.81");
        }
        Q7R3: PacketQueue {
            @display("p=1662.96,493.47998");
        }
        Q8R3: PacketQueue {
            @display("p=1662.96,581.36");
        }
        cdu2: LabelClassifier {
            @display("p=780,826.8");
        }
        L1du2: ContentBasedLabeler {
            @display("p=336.69998,703.3");
        }
        L2du2: ContentBasedLabeler {
            @display("p=336.69998,781.3");
        }

        L3du2: ContentBasedLabeler {
            @display("p=336.69998,868.39996");
        }
        L4du2: ContentBasedLabeler {
            @display("p=336.69998,956.8");
        }

        mux1du2: PacketMultiplexer {
            @display("p=278.19998,702;b=26.923079,28.46154");
        }
        mux3du2: PacketMultiplexer {
            @display("p=274.3,869.7;b=23.846155,26.923079");
        }
        muxdu2: PacketMultiplexer {
            @display("p=694.2,826.8;b=33.159996,33.159996");
        }
        p1du2[np_vdu3]: ActivePacketSource {
            @display("p=75.4,703.3,c,100");
        }
        b1du2[np_vdu3]: TokenBucket {
            @display("p=206.7,703.3,c,100");
        }
        t1du2[np_vdu3]: TimeBasedTokenGenerator {
            @display("p=704.6,734.5,c,100");
            storageModule = "^.b1du2["+string(ancestorIndex(0))+"].server";
        }
        p2du2: ActivePacketSource {
            @display("p=75.4,781.3,c,100");
        }
        p3du2[np_vdu4]: ActivePacketSource {
            @display("p=75.4,868.39996,c,100");
        }
        b3du2[np_vdu4]: TokenBucket {
            @display("p=206.7,869.7,c,100");
        }
        t3du2[np_vdu4]: TimeBasedTokenGenerator {
            @display("p=704.6,907.39996,c,100");
            storageModule = "^.b3du2["+string(ancestorIndex(0))+"].server";
        }
        p4du2: ActivePacketSource {
            @display("p=75.4,959.39996,c,100");
        }
        Q1du2: PacketQueue {
            @display("p=380.9,703.3");
        }
        Q1eth2: PacketQueue {
            @display("p=899.6,703.3");
        }
        Q2du2: PacketQueue {
            @display("p=380.9,781.3");
        }
        Q2eth2: PacketQueue {
            @display("p=899.6,790.39996");
        }
        Q3du2: PacketQueue {
            @display("p=380.9,868.39996");
        }
        Q3eth2: PacketQueue {
            @display("p=898.3,868.39996");
        }
        Q4du2: PacketQueue {
            @display("p=380.9,956.8");
        }
        Q4eth2: PacketQueue {
            @display("p=899.6,956.8");
        }
        scheth2: WrrScheduler {
            @display("p=1004.89996,826.8");
        }
        sch1du2: WrrScheduler {
            @display("p=496.6,750.1");
        }
        sch2du2: WrrScheduler {
            @display("p=496.6,911.3");
        }
        Seth2: PacketServer {
            @display("p=1085.5,826.8");
        }
        S1du2: PacketServer {
            @display("p=594.1,750.1");
        }
        S2du2: PacketServer {
            @display("p=594.1,911.3");
        }
        cR4: LabelClassifier {
            @display("p=2011,317");
        }
        Q1R4: PacketQueue {
            @display("p=2120.95,27.039999");
        }
        Q2R4: PacketQueue {
            @display("p=2120.95,96.329994");
        }
        Q3R4: PacketQueue {
            @display("p=2120.95,163.93");
        }
        Q4R4: PacketQueue {
            @display("p=2120.95,246.73999");
        }
        Q5R4: PacketQueue {
            @display("p=2120.95,334.62");
        }
        Q6R4: PacketQueue {
            @display("p=2120.95,417.43");
        }
        Q7R4: PacketQueue {
            @display("p=2120.95,490.09998");
        }
        Q8R4: PacketQueue {
            @display("p=2120.95,577.98");
        }
        schR4: WrrScheduler {
            @display("p=2293,313");
        }
        SR4: PacketServer {
            @display("p=2364,313");
        }
        cCU: LabelClassifier {
            @display("p=2430,313");
        }
        CU1_URLLC_4[np_vdu1]: PassivePacketSink {
            @display("p=2587,31,c,60");
        }
        CU2_eMBB_4: PassivePacketSink {
            @display("p=2686,77");
        }
        CU3_URLLC_4[np_vdu2]: PassivePacketSink {
            @display("p=2636,189,c,60");
        }
        CU4_eMBB_4: PassivePacketSink {
            @display("p=2675,317");
        }
        CU5_URLLC_4[np_vdu3]: PassivePacketSink {
            @display("p=2655.3848,404.6154,c,60");
        }
        CU6_eMBB_4: PassivePacketSink {
            @display("p=2636,517");
        }
        CU7_URLLC_4[np_vdu4]: PassivePacketSink {
            @display("p=2626,659,c,60");
        }
        CU8_eMBB_4: PassivePacketSink {
            @display("p=2469,630");
        }
        cR1: LabelClassifier {
            @display("p=1155,151");
        }
        Q1R1: PacketQueue {
            @display("p=1233,31");
        }
        Q2R1: PacketQueue {
            @display("p=1233,124");
        }
        Q3R1: PacketQueue {
            @display("p=1233,197");
        }
        Q4R1: PacketQueue {
            @display("p=1233,288");
        }
        schR1: WrrScheduler {
            @display("p=1328,154");
        }
        SR1: PacketServer {
            @display("p=1409,154");
        }
        X1du1[np_vdu1]: ContentBasedLabeler {
            @display("p=131.53847,30.769232,c,100");
        }
        X2du1[np_vdu2]: ContentBasedLabeler {
            @display("p=131.53847,174.61539,c,100");
        }
        X1du2[np_vdu3]: ContentBasedLabeler {
            @display("p=143,696.8,c,100");
        }
        X2du2[np_vdu4]: ContentBasedLabeler {
            @display("p=143,856.7,c,100");
        }
        cCU_UE1: LabelClassifier {
            @display("p=2572.3079,117.692314");
        }
        cCU_UE3: LabelClassifier {
            @display("p=2577.6924,246.15385");
        }
        cCU_UE5: LabelClassifier {
            @display("p=2566.154,380.76926");
        }
        cCU_UE7: LabelClassifier {
            @display("p=2498.4617,496.15387");
        }
        Q5R1: PacketQueue {
            @display("p=1401.4,546");
        }
        Q7R2: PacketQueue {
            @display("p=1323.4,540.8");
        }
        Q6R2: PacketQueue {
            @display("p=1245.4,542.1");
        }
        Q5R2: PacketQueue {
            @display("p=1167.4,540.8");
        }
        cR5: LabelClassifier {
            @display("p=1245.4,430.3");
        }
        cR6: LabelClassifier {
            @display("p=1537.9,546");
        }
    connections allowunconnected:
        for i=0..np_vdu1-1 {
            p1du1[i].out --> X1du1[i].in;
            X1du1[i].out --> b1du1[i].in;
            b1du1[i].out --> mux1du1.in++;
            cCU_UE1.out++ --> CU1_URLLC_4[i].in;
        }
        for i=0..np_vdu2-1 {
            p3du1[i].out --> X2du1[i].in;
            X2du1[i].out --> b3du1[i].in;
            b3du1[i].out --> mux3du1.in++;
            cCU_UE3.out++ --> CU3_URLLC_4[i].in;
        }
        for i=0..np_vdu3-1 {
            p1du2[i].out --> X1du2[i].in;
            X1du2[i].out --> b1du2[i].in;
            b1du2[i].out --> mux1du2.in++;
            cCU_UE5.out++ --> CU5_URLLC_4[i].in;

        }
        for i=0..np_vdu4-1 {
            p3du2[i].out --> X2du2[i].in;
            X2du2[i].out --> b3du2[i].in;
            b3du2[i].out --> mux3du2.in++;
            cCU_UE7.out++ --> CU7_URLLC_4[i].in;

        }
        Q1du1.out --> sch1du1.in++;
        Q2du1.out --> sch1du1.in++;
        sch1du1.out --> S1du1.in;
        Q1eth1.out --> scheth1.in++;
        Q2eth1.out --> scheth1.in++;
        scheth1.out --> Seth1.in;
        cdu1.out++ --> Q1eth1.in; //primeira conexao é a saida 0 do classifier
        cdu1.out++ --> Q2eth1.in;
        mux1du1.out --> L1du1.in;
        mux3du1.out --> L3du1.in;
        mux1du2.out --> L1du2.in;
        mux3du2.out --> L3du2.in;
        L1du1.out --> Q1du1.in;
        L2du1.out --> Q2du1.in;
        L3du1.out --> Q3du1.in;
        Q3du1.out --> sch2du1.in++;
        sch2du1.out --> S2du1.in;
        L4du1.out --> Q4du1.in;
        Q4du1.out --> sch2du1.in++;
        S1du1.out --> muxdu1.in++;
        S2du1.out --> muxdu1.in++;
        muxdu1.out --> cdu1.in;
        cdu1.out++ --> Q3eth1.in;
        cdu1.out++ --> Q4eth1.in;
        Q3eth1.out --> scheth1.in++;
        Q4eth1.out --> scheth1.in++;
        Q2R2.out --> schR2.in++;
        Q1R2.out --> schR2.in++;
        schR2.out --> SR2.in;
        Q3R2.out --> schR2.in++;
        Q4R2.out --> schR2.in++;
        cR2.out++ --> Q1R2.in;
        cR2.out++ --> Q2R2.in;
        cR2.out++ --> Q3R2.in;
        cR2.out++ --> Q4R2.in;
        Q1R3.out --> schR3.in++;
        Q2R3.out --> schR3.in++;
        Q3R3.out --> schR3.in++;
        schR3.out --> SR3.in;
        Q4R3.out --> schR3.in++;
        muxR1.out --> cR3.in;
        cR3.out++ --> Q1R3.in;
        cR3.out++ --> Q2R3.in;
        cR3.out++ --> Q3R3.in;
        cR3.out++ --> Q4R3.in;
        cR3.out++ --> Q5R3.in;
        cR3.out++ --> Q6R3.in;
        cR3.out++ --> Q7R3.in;
        cR3.out++ --> Q8R3.in;
        Q5R3.out --> schR3.in++;
        Q6R3.out --> schR3.in++;
        Q7R3.out --> schR3.in++;
        Q8R3.out --> schR3.in++;
        Q2du2.out --> sch1du2.in++;
        sch2du2.out --> S2du2.in;
        L4du2.out --> Q4du2.in;
        Q3du2.out --> sch2du2.in++;
        L1du2.out --> Q1du2.in;
        Q1eth2.out --> scheth2.in++;
        Q2eth2.out --> scheth2.in++;
        L2du2.out --> Q2du2.in;
        Q4du2.out --> sch2du2.in++;
        S2du2.out --> muxdu2.in++;
        sch1du2.out --> S1du2.in;
        scheth2.out --> Seth2.in;
        Q3eth2.out --> scheth2.in++;
        muxdu2.out --> cdu2.in;
        cdu2.out++ --> Q1eth2.in; //primeira conexao é a saida 0 do classifier
        cdu2.out++ --> Q2eth2.in;
        cdu2.out++ --> Q3eth2.in;
        cdu2.out++ --> Q4eth2.in;

        Q1du2.out --> sch1du2.in++;
        L3du2.out --> Q3du2.in;
        S1du2.out --> muxdu2.in++;

        Q4eth2.out --> scheth2.in++;
        cR4.out++ --> Q1R4.in;
        cR4.out++ --> Q2R4.in;
        cR4.out++ --> Q3R4.in;
        cR4.out++ --> Q4R4.in;
        cR4.out++ --> Q5R4.in;
        cR4.out++ --> Q6R4.in;
        cR4.out++ --> Q7R4.in;
        cR4.out++ --> Q8R4.in;
        Q6R4.out --> schR4.in++;
        Q7R4.out --> schR4.in++;
        Q2R4.out --> schR4.in++;
        Q3R4.out --> schR4.in++;
        Q8R4.out --> schR4.in++;
        Q4R4.out --> schR4.in++;
        Q1R4.out --> schR4.in++;
        Q5R4.out --> schR4.in++;
        schR4.out --> SR4.in;
        SR4.out --> cCU.in;
        cR1.out++ --> Q1R1.in;
        cR1.out++ --> Q2R1.in;
        cR1.out++ --> Q3R1.in;
        cR1.out++ --> Q4R1.in;
        Q4R1.out --> schR1.in++;
        Q3R1.out --> schR1.in++;
        schR1.out --> SR1.in;
        Q2R1.out --> schR1.in++;
        Q1R1.out --> schR1.in++;
        cCU.out++ --> cCU_UE1.in;
        cCU.out++ --> CU2_eMBB_4.in;
        cCU.out++ --> cCU_UE3.in;
        cCU.out++ --> CU4_eMBB_4.in;
        cCU.out++ --> cCU_UE5.in;
        cCU.out++ --> CU6_eMBB_4.in;
        cCU.out++ --> cCU_UE7.in;
        cCU.out++ --> CU8_eMBB_4.in;
        Seth1.out --> cR1.in;
        Seth2.out --> cR2.in;
        SR1.out --> cR5.in;
        SR3.out --> cR4.in;
        p2du1.out --> L2du1.in;
        p4du1.out --> L4du1.in;
        p2du2.out --> L2du2.in;
        p4du2.out --> L4du2.in;
        cR5.out++ --> muxR1.in++;
        cR5.out++ --> Q7R2.in;
        Q7R2.out --> schR2.in++;
        cR5.out++ --> Q6R2.in;
        cR5.out++ --> Q5R2.in;
        Q5R2.out --> schR2.in++;
        Q6R2.out --> schR2.in++;
        SR2.out --> cR6.in;
        Q5R1.out --> schR1.in++;
        cR6.out++ --> muxR1.in++;
        cR6.out++ --> muxR1.in++;
        cR6.out++ --> Q5R1.in;
        cR6.out++ --> muxR1.in++;
}
