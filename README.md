# Optimal Resource Allocation with Delay Guarantees for Network Slicing in Disaggregated RAN

This repository contains additional data from the paper "**Optimal Resource Allocation with Delay Guarantees for Network Slicing in Disaggregated RAN**" under review. The data from the experiments, the implementation of the optimization model, the simulation parameters and the results obtained are publicly accessible in this repository.

## How to cite
```
@misc{rocha2023optimal,
      title={Optimal Resource Allocation with Delay Guarantees for Network Slicing in Disaggregated RAN}, 
      author={Flávio G. C. Rocha and Gabriel M. F. de Almeida and Kleber V. Cardoso and Cristiano B. Both and José F. de Rezende},
      year={2023},
      eprint={2305.17321},
      archivePrefix={arXiv},
      primaryClass={cs.NI}
}
```

## Introduction

In this work, we propose a novel formulation for the resource allocation problem of a sliced and disaggregated Radio Access Network (RAN) and its transport network. Our proposal assures an end-to-end delay bound for the Ultra-Reliable and Low-Latency Communication (URLLC) use case while jointly considering the number of admitted users and transmission rate allocation per slice, the functional split of RAN nodes and routing in the transport network. We use deterministic network calculus theory to calculate delay along the transport network connecting disaggregated RANs deploying network functions at the Radio Unit (RU), Distributed Unit (DU), and Central Unit (CU) nodes. The maximum end-to-end delay is a constraint in the optimization-based formulation that aims to maximize Mobile Network Operator (MNO) profit, considering a cash flow analysis to model revenue and operational costs using data from one of the world’s leading MNOs. The optimization model leverages a Flexible Functional Split (FFS) approach to provide a new degree of freedom to the resource allocation strategy. Simulation results reveal that, due to its non-linear nature, there is no trivial solution to the proposed joint RAN slicing, functional split, and routing optimization. The proposed approach guarantees a maximum delay for URLLC services while satisfying minimal bandwidth requirements for enhanced Mobile BroadBand (eMBB) services and maximizing the MNO’s profit.

## Metodology and parameters setup

To evaluate our proposals, we considered a disaggregated 5G NG-RAN network topology connecting UEs to 5GC and we employed the following tools: IBM CPLEX, DiscoDNC, and OMNeT++. We use IBM CPLEX to obtain the optimal solution for the problem formulation presented in the paper. Regarding comparisons with benchmarks and simulation results, on the one hand, we use DiscoDNC to automate the estimation of theoretical bounds on end-to-end delay, and we leverage the toolset provided by DiscoDNC to obtain results through diverse analyses provided by Network Calculus Theory. On the other hand, we also conduct simulations using OMNeT++ (event-driven simulation tool), getting end-to-end delay results in a more realistic network scenario where data packetization, buffer occupancy, and resource scheduling dynamics are considered.

The results presented in this repository were obtained through simulations in the downlink direction using the transport network topology depicted in the Fig below:

<p align="center">
  <img src="https://github.com/LABORA-INF-UFG/paper-FGKCJ-2023/blob/main/figs/fig1.png"/>
</p>

Our scenario is composed of a single CU (*v10*), two DUs (*v5* and *v6*) split into four vDUs (*v1*, *v2*, *v3*, and *v4*) co-localized with four RUs. All DUs connect to the CU through a midhaul transport network (ring composed of *v7*, *v8*, and *v9* transport nodes). Every RU supports connections to UEs using PDU sessions (flows) associated with instances of two different slicing use cases: URLLC and eMBB. NSIs of these use cases transport traffic with fixed packet sizes of *128 bytes* and *1500 bytes*, respectively. The arrival processes for the URLLC NSI are bounded by the arrival curve of URLLC flows. Moreover, the eMBB NSI is considered a bandwidth consumer, and four aggregated eMBB traffic demands  are evaluated, as listed in the table below: 

<p align="center">
  <img src="https://github.com/LABORA-INF-UFG/paper-FGKCJ-2023/blob/main/figs/fig2.png"/>
</p>

Therefore, we name as an *optimization instance* each one of the network scenarios with a specific set of eMBB demands. Our aim is to evaluate all possible scenarios with this set of varying demands. Assuming the topology presented earlyer as the default topology with four vDUs, we obtain 256 *optimization instances* for our evaluations.

The service characteristics for the URLLC NSI consider multiple robot units in factories in the context of Industry 4.0, where such units are controlled by periodic transmissions of data with a packet size of 128 bytes and inter-packet time of 1 ms. The end-to-end delay requirement for this service is 1 ms. The table below summarizes the parameters for the arrival and service curves and the capacities for each transport node used in this work, and other parameter values employed in the evaluation:

<p align="center">
  <img src="https://github.com/LABORA-INF-UFG/paper-FGKCJ-2023/blob/main/figs/fig3.png"/>
</p>

Our performance evaluation is carried out in a network scenario conservatively dimensioned since we intend to show its efficiency even for scenarios with capacity restrictions. Although, our approach can be applied to different topologies and network dimensioning.

We considered that each RU transmits data to UEs using a wireless channel interface dimensioned conservatively using NR numerology 0 with a bandwidth of 20 MHz with total capacity for data plane provided by 100 RBs per TTI. A single RB per TTI can serve each URLLC UE. In this approach, if the amount of eMBB traffic corresponds to, for example,  20\% of the RU capacity, 80 RBs are left to URLLC traffic. Each network node has a transmission capacity determined by a rate-latency service curve. In the ring plus sink-tree topology, the transport nodes are distributed equally with an internode distance of 5 km, exception for RUs which are physically close to the vDUs. The ring at the midhaul interconnects the CU to the DUs and provides different paths to the network flows in downlink direction.

The number of VNFs deployed at a vDU is related to the functional split option. The split options O8 and O9 include subdivisions for the PHY layer. Therefore, it is also necessary to map the processing time demanded by functions inside the PHY layer. We considered as RC to our analyses one core of an Intel Haswell i7-4770 3.40 GHz, which processes, during a single TTI, packets from an aggregated data rate of 1 Gbps in the time interval of 750 microseconds. The measures used in our analyses are based on other works. In such an approach, the PHY layer is divided into three main functions: FFT, modulation, and encoding, where 26% of the total of 64.99% of the PHY layer processing time is demanded by modulation. The encoding/decoding processing time is sensitive to the \ac{MCS} order, however, processing times for FFT and modulation functions are less sensitive to MCS variations. The distribution of PHY processing time for FFT, modulation, encoding, and other PHY functions (mainly mapping functions) are respectively 8.95%, 21.58%, 54.25%, and 15.22%. The table below shows for each protocol stack layer the VNF processing time in percentage terms of the full-stack processing time. In this approach, PHY-A is always placed at the RU, while all other VNFs are placed at the DU or CU.

<p align="center">
  <img src="https://github.com/LABORA-INF-UFG/paper-FGKCJ-2023/blob/main/figs/fig4.png"/>
</p>

Regarding overheads imposed on the network traffic packets in the TN, the table bellow presents the overhead factors for packets of 128 bytes (URLLC) and 1500 bytes (eMBB) due to the split options:

<p align="center">
  <img src="https://github.com/LABORA-INF-UFG/paper-FGKCJ-2023/blob/main/figs/fig5.png"/>
</p>

From *O1* to *O6*, the overhead factor complies with the 5G NR standards for each layer, while for *O8* and *O9* (which represent subdivisions of the PHY layer), the overhead factor is  estimated taking into account the proportional of capacity demand required for each split option.
