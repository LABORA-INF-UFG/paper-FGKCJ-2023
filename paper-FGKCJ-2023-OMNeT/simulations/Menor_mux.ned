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



network Menor_mux
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
        p2du1: ActivePacketSource {
            @display("p=64.22,108.159996,c,100");
        }
        L1du1: ContentBasedLabeler {
            @display("p=325.3846,30.769232");
        }
        L2du1: ContentBasedLabeler {
            @display("p=325.3846,108.46154");
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
        muxdu1: PacketMultiplexer {
            @display("p=682.76,153.79;b=33.159996,33.159996");
        }
        muxR1: PacketMultiplexer {
            @display("p=1462,316;b=33.159996,33.159996");
        }
        L1du2: ContentBasedLabeler {
            @display("p=325.3846,373.84616");
        }
        L2du2: ContentBasedLabeler {
            @display("p=325.3846,451.53848");
        }

        L3du2: ContentBasedLabeler {
            @display("p=325.3846,539.2308");
        }
        L4du2: ContentBasedLabeler {
            @display("p=325.3846,627.6923");
        }

        mux1du2: PacketMultiplexer {
            @display("p=267.69232,373.07693;b=26.923079,28.46154");
        }
        mux3du2: PacketMultiplexer {
            @display("p=263.84616,540;b=23.846155,26.923079");
        }
        muxdu2: PacketMultiplexer {
            @display("p=682.76,496.86;b=33.159996,33.159996");
        }
        p1du2[np_vdu3]: ActivePacketSource {
            @display("p=64.22,373.49,c,100");
        }
        b1du2[np_vdu3]: TokenBucket {
            @display("p=196.15385,373.84616,c,100");
        }
        t1du2[np_vdu3]: TimeBasedTokenGenerator {
            @display("p=693.8462,404.6154,c,100");
            storageModule = "^.b1du2["+string(ancestorIndex(0))+"].server";
        }
        p2du2: ActivePacketSource {
            @display("p=64.22,451.22998,c,100");
        }
        p3du2[np_vdu4]: ActivePacketSource {
            @display("p=64.22,539.11,c,100");
        }
        b3du2[np_vdu4]: TokenBucket {
            @display("p=196.15385,540.7692,c,100");
        }
        t3du2[np_vdu4]: TimeBasedTokenGenerator {
            @display("p=693.8462,577.6923,c,100");
            storageModule = "^.b3du2["+string(ancestorIndex(0))+"].server";
        }
        p4du2: ActivePacketSource {
            @display("p=64.22,630.37,c,100");
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
        CU1_URLLC_4: PassivePacketSink {
            @display("p=2587,31,c,60");
        }
        CU2_eMBB_4: PassivePacketSink {
            @display("p=2686,77");
        }
        CU3_URLLC_4: PassivePacketSink {
            @display("p=2636,189,c,60");
        }
        CU4_eMBB_4: PassivePacketSink {
            @display("p=2675,317");
        }
        CU5_URLLC_4: PassivePacketSink {
            @display("p=2655.3848,404.6154,c,60");
        }
        CU6_eMBB_4: PassivePacketSink {
            @display("p=2636,517");
        }
        CU7_URLLC_4: PassivePacketSink {
            @display("p=2626,659,c,60");
        }
        CU8_eMBB_4: PassivePacketSink {
            @display("p=2469,630");
        }
        X1du1[np_vdu1]: ContentBasedLabeler {
            @display("p=131.53847,30.769232,c,100");
        }
        X2du1[np_vdu2]: ContentBasedLabeler {
            @display("p=131.53847,174.61539,c,100");
        }
        X1du2[np_vdu3]: ContentBasedLabeler {
            @display("p=131.53847,367.69232,c,100");
        }
        X2du2[np_vdu4]: ContentBasedLabeler {
            @display("p=131.53847,527.6923,c,100");
        }
    connections allowunconnected:
        for i=0..np_vdu1-1 {
            p1du1[i].out --> X1du1[i].in;
            X1du1[i].out --> b1du1[i].in;
            b1du1[i].out --> mux1du1.in++;
        }
        for i=0..np_vdu2-1 {
            p3du1[i].out --> X2du1[i].in;
            X2du1[i].out --> b3du1[i].in;
            b3du1[i].out --> mux3du1.in++;
        }
        for i=0..np_vdu3-1 {
            p1du2[i].out --> X1du2[i].in;
            X1du2[i].out --> b1du2[i].in;
            b1du2[i].out --> mux1du2.in++;
        }
        for i=0..np_vdu4-1 {
            p3du2[i].out --> X2du2[i].in;
            X2du2[i].out --> b3du2[i].in;
            b3du2[i].out --> mux3du2.in++;
        }
        mux1du1.out --> L1du1.in;
        mux3du1.out --> L3du1.in;
        mux1du2.out --> L1du2.in;
        mux3du2.out --> L3du2.in;
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
        p2du1.out --> L2du1.in;
        p4du1.out --> L4du1.in;
        p2du2.out --> L2du2.in;
        p4du2.out --> L4du2.in;
        L1du1.out --> muxdu1.in++;
        L2du1.out --> muxdu1.in++;
        L3du1.out --> muxdu1.in++;
        L4du1.out --> muxdu1.in++;
        L1du2.out --> muxdu2.in++;
        L2du2.out --> muxdu2.in++;
        L3du2.out --> muxdu2.in++;
        L4du2.out --> muxdu2.in++;
        muxdu1.out --> muxR1.in++;
        muxdu2.out --> muxR1.in++;
        muxR1.out --> cR4.in;
        cCU.out++ --> CU1_URLLC_4.in;
        cCU.out++ --> CU2_eMBB_4.in;
        cCU.out++ --> CU3_URLLC_4.in;
        cCU.out++ --> CU4_eMBB_4.in;
        cCU.out++ --> CU5_URLLC_4.in;
        cCU.out++ --> CU6_eMBB_4.in;
        cCU.out++ --> CU7_URLLC_4.in;
        cCU.out++ --> CU8_eMBB_4.in;
}
