# -*- coding: utf-8 -*-
import time
import json
from docplex.cp.model import CpoModel
from docplex.cp.parameters import CpoParameters
from os.path import exists
import sys


class Path:
    def __init__(self, id, source, target, seq, delay, delay_p1, delay_p2, delay_p3, links_p1, links_p2, links_p3):
        self.id = id
        self.source = source
        self.target = target
        self.seq = seq
        self.delay = delay
        self.delay_p1 = delay_p1
        self.delay_p2 = delay_p2
        self.delay_p3 = delay_p3
        self.links_p1 = links_p1
        self.links_p2 = links_p2
        self.links_p3 = links_p3
    def __str__(self):
        return "ID: {}\tSEQ: {}\tDELAY {}\t" \
               "dP1: {}\t dP2: {}\t dP3: {}\t " \
               "L_p1: {}\t L_p2: {}\t L_p3: {}".format(self.id,
                                                       self.seq,
                                                       self.delay,
                                                       self.delay_p1,
                                                       self.delay_p2,
                                                       self.delay_p3,
                                                       self.links_p1,
                                                       self.links_p2,
                                                       self.links_p3)


class DRC:
    def __init__(self, id, cpu_CU, cpu_DU, ram_CU, ram_DU, Fs_CU, Fs_DU, delay_BH, delay_MH, bw_BH, bw_MH, q_CRs, proc_delay, URLLC_pkt, eMBB_pkt):
        self.id = id
        self.cpu_CU = cpu_CU
        self.ram_CU = ram_CU
        self.Fs_CU = Fs_CU
        self.cpu_DU = cpu_DU
        self.ram_DU = ram_DU
        self.Fs_DU = Fs_DU
        self.delay_BH = delay_BH
        self.delay_MH = delay_MH
        self.bw_BH = bw_BH
        self.bw_MH = bw_MH
        self.q_CRs = q_CRs
        self.proc_delay = proc_delay
        self.URLLC_pkt = URLLC_pkt
        self.eMBB_pkt = eMBB_pkt
    def __str__(self):
        return "Split O{} Fs_CU: {} - Fs_DU: {}\tproc_delay: {}s".format(self.id, self.Fs_CU, self.Fs_DU, self.proc_delay)


class Slice():
    def __init__(self, id, vDU, max_delay, requirements, n_flows, UE_revenue):
        self.id = id
        self.vDU = vDU
        self.max_delay = max_delay
        self.requirements = requirements
        self.UE_revenue = UE_revenue
        self.n_flows = n_flows
    def __str__(self):
        return "ID: {}\t vDU: {}\t LAT: {}\t".format(self.id, self.vDU, self.max_delay)


paths = []
links = []
DRCs = {}
links_capacity = {}
links_delay = {}
nodes = []
DUs = []
slices = []
slices_eMBB = []
vDU_eMBB_requirement = {}
slice_VDUs = {}


def read_topology(file):
    json_file = json.load(open(file, 'r'))
    json_links = json_file["links"]
    for l in json_links:
        links.append((l["from_Node"], l["to_Node"]))
        links_capacity[(l["from_Node"], l["to_Node"])] = l["bw"]
        links_delay[(l["from_Node"], l["to_Node"])] = l["delay"]
        links.append((l["to_Node"], l["from_Node"]))
        links_capacity[(l["to_Node"], l["from_Node"])] = l["bw"]
        links_delay[(l["to_Node"], l["from_Node"])] = l["delay"]
        links.append((l["from_Node"], l["from_Node"]))
        links.append((l["to_Node"], l["to_Node"]))
        links_capacity[(l["from_Node"], l["from_Node"])] = l["bw"]
        links_capacity[(l["to_Node"], l["to_Node"])] = l["bw"]
        links_delay[(l["from_Node"], l["from_Node"])] = l["delay"]
        links_delay[(l["to_Node"], l["to_Node"])] = l["delay"]
    json_nodes = json_file["nodes"]
    for n in json_nodes:
        n_copy = n
        if "R" in n_copy:
            n_copy["R"] = n_copy["R"] * 10**9
            n = n_copy
        nodes.append(n)
    json_DUs = json_file["DUs"]
    for du in json_DUs:
        DUs.append(du)

    slice_file = json.load(open("../topologies/evaluation_topologies/{}_vDUs_{}_nodes_{}/slices.json".format(sys.argv[1], sys.argv[2], sys.argv[3]), 'r'))
    count = 0
    for slice in slice_file["slices"]:
        if slice["type"] == "URLLC":
            if count + 1 not in slice_VDUs:
                slice_VDUs[count + 1] = []
            slices.append(Slice(count, slice["vDU"], slice["max_delay"], slice["requirements"], slice["n_flows"], slice["UE revenue"]))
            slice_VDUs[count + 1].append(count)
            count += 1
        else:
            slices_eMBB.append({"vDU": slice["vDU"], "requirement": int(slice["requirement"])*1000})
            vDU_eMBB_requirement[slice["vDU"]] = int(slice["requirement"]) * 1000


def read_paths(file):
    json_file = json.load(open(file, 'r'))
    id = 0
    for p in json_file:
        delay_p1 = json_file[p]["delay"][0]
        delay_p3 = json_file[p]["delay"][len(json_file[p]["delay"]) - 1]
        delay_p2 = 0
        for i in range(1, len(json_file[p]["delay"]) - 1):
            delay_p2 += json_file[p]["delay"][i]
        links_p1 = []
        links_p2 = []
        links_p3 = []
        for i in range(0, len(json_file[p]["seq"]) - 1):
            if i == 0:
                links_p1.append((json_file[p]["seq"][i], json_file[p]["seq"][i+1]))
            elif i == len(json_file[p]["seq"]) - 2:
                links_p3.append((json_file[p]["seq"][i], json_file[p]["seq"][i + 1]))
            else:
                links_p2.append((json_file[p]["seq"][i], json_file[p]["seq"][i + 1]))
        paths.append(Path(id=id,
                          source=json_file[p]["source"],
                          target=json_file[p]["target"],
                          seq=json_file[p]["seq"],
                          delay=json_file[p]["delay"],
                          delay_p1=delay_p1,
                          delay_p2=delay_p2,
                          delay_p3=delay_p3,
                          links_p1=links_p1,
                          links_p2=links_p2,
                          links_p3=links_p3))
        id += 1


def DRC_structure():
    delayFullStack = 750
    CU_RCs = 32
    DU_RCs = 16
    CU_load_O1 = 0.0217
    CU_load_O2 = CU_load_O1 + 0.187
    CU_load_O4 = CU_load_O2 + 0.091
    CU_load_O6 = CU_load_O4 + 0.1324
    CU_load_O8 = CU_load_O6 + 0.3526
    CU_load_O9 = CU_load_O8 + 0.989
    delay_O1 = ((delayFullStack * CU_load_O1) / CU_RCs + delayFullStack * (1 - CU_load_O1) / DU_RCs) / (10 ** 6)
    delay_O2 = ((delayFullStack * CU_load_O2) / CU_RCs + delayFullStack * (1 - CU_load_O2) / DU_RCs) / (10 ** 6)
    delay_O4 = ((delayFullStack * CU_load_O4) / CU_RCs + delayFullStack * (1 - CU_load_O4) / DU_RCs) / (10 ** 6)
    delay_O6 = ((delayFullStack * CU_load_O6) / CU_RCs + delayFullStack * (1 - CU_load_O6) / DU_RCs) / (10 ** 6)
    delay_O8 = ((delayFullStack * CU_load_O8) / CU_RCs + delayFullStack * (1 - CU_load_O8) / DU_RCs) / (10 ** 6)
    delay_O9 = ((delayFullStack * CU_load_O9) / CU_RCs + delayFullStack * (1 - CU_load_O9) / DU_RCs) / (10 ** 6)
    DRC1 = DRC(1, 0.98, 1.56, 0.1, 0.1, ['f5'], ['f4', 'f3', 'f2', 'f1', 'f0'], 10, 10, 0, 0, 0, delay_O1, 1000, 1000)
    DRC2 = DRC(2, 0.98, 1.56, 0.1, 0.1, ['f5', 'f4'], ['f3', 'f2', 'f1', 'f0'], 10, 10, 0, 0, 0, delay_O2, 1016, 1001)
    DRC4 = DRC(4, 0.98, 1.56, 0.1, 0.1, ['f5', 'f4', 'f3'], ['f2', 'f1', 'f0'], 10, 10, 0, 0, 0, delay_O4, 1055, 1005)
    DRC6 = DRC(6, 0.98, 1.56, 0.1, 0.1, ['f5', 'f4', 'f3', 'f2'], ['f1', 'f0'], 10, 10, 0, 0, 0, delay_O6, 1070, 1006)
    DRC8 = DRC(8, 0.98, 1.56, 0.1, 0.1, ['f5', 'f4', 'f3', 'f2', 'f1'], ['f0'], 10, 10, 0, 0, 0, delay_O8, 6621, 6223)
    DRC9 = DRC(9, 0.98, 1.56, 0.1, 0.1, ['f5', 'f4', 'f3', 'f2', 'f1', 'f0'], [], 10, 10, 0, 0, 0, delay_O9, 7634, 7175)
    DRCs = {1: DRC1, 2: DRC2, 4: DRC4, 6: DRC6, 8: DRC8, 9: DRC9}

    return DRCs


def run_model(enumerable):
    print("Running Model")
    print("-----------------------------------------------------------------------------------------------------------")
    create_model_time = time.time()
    myparams = CpoParameters(LogPeriod=500000, SearchType="IterativeDiving")
    mdl = CpoModel()
    mdl.set_parameters(myparams)
    DU_node_to_ID = {}
    for DU in DUs:
        DU_node_to_ID[DU["node"]] = DU["ID"]
    i = [(d, b["ID"]) for d in DRCs for b in DUs]
    mdl.x = mdl.binary_var_dict(keys=i, name='x')
    y = [(p.id, slice.id) for p in paths for slice in slices if slice.vDU == p.seq[len(p.seq) - 1]]
    mdl.y = mdl.binary_var_dict(keys=y, name='y')
    z = [(s.id, n["ID"]) for s in slices for n in nodes if n["ID"]]
    mdl.z = mdl.integer_var_dict(keys=z, name='z')
    w = [slice.id for slice in slices]
    mdl.w = mdl.integer_var_dict(keys=w, name="w")
    mdl.w_aux = mdl.binary_var_dict(keys=w, name="w_aux")
    y_eMBB = []
    for s_eMBB in slices_eMBB:
        for p in paths:
            if p.target == s_eMBB["vDU"]:
                y_eMBB.append((p.id, s_eMBB["vDU"]))
    mdl.y_eMBB = mdl.binary_var_dict(keys=y_eMBB, name="y_eMBB")
    x_aux = [b["ID"] for b in DUs]
    mdl.x_aux = mdl.integer_var_dict(keys=x_aux, name="x_aux")
    mdl.x_eMBB = mdl.integer_var_dict(keys=x_aux, name="x_aux")
    vCU = 1
    vDU = DUs[0]["node"]
    psi_R = mdl.sum(mdl.w[slice.id] * slice.UE_revenue for slice in slices)
    CU_cost = mdl.sum(mdl.x[(d, b["ID"])] * len(DRCs[d].Fs_CU) * nodes[vCU]["cost"] for d in DRCs for b in DUs)
    DUs_cost = mdl.sum(mdl.x[(d, b["ID"])] * len(DRCs[d].Fs_DU) * nodes[vDU]["cost"] for d in DRCs for b in DUs)
    mdl.maximize(psi_R - (CU_cost + DUs_cost))
    for b in DUs:
        mdl.add(mdl.x_aux[b["ID"]] == mdl.sum(mdl.x[it_x] * DRCs[it_x[0]].URLLC_pkt for it_x in i if it_x[1] == b["ID"]))
        mdl.add(mdl.x_eMBB[b["ID"]] == mdl.sum(mdl.x[it_x] * DRCs[it_x[0]].eMBB_pkt for it_x in i if it_x[1] == b["ID"]))
    for n in nodes:
        if n["ID"]:
            for s in slices:
                mdl.add(mdl.z[(s.id, n["ID"])] <= (mdl.sum(mdl.y[p.id, s.id] * enumerable[n["ID"]] for p in paths if p.target == s.vDU and n["ID"] in p.seq))+1)
    for s_eMBB in slices_eMBB:
        mdl.add(mdl.sum(mdl.y_eMBB[p.id, s_eMBB["vDU"]] for p in paths if p.target == s_eMBB["vDU"]) == 1)
    for n in nodes:
        if n["ID"]:
            mdl.add(mdl.sum(mdl.z[slice.id, n["ID"]] for slice in slices) <= enumerable[n["ID"]])
            mdl.add(mdl.sum(mdl.sum(mdl.y[(p.id, slice.id)] * (mdl.z[(slice.id, n["ID"])]/enumerable[n["ID"]]) for p in paths
                                                                                    if n["ID"] in p.seq
                                                                                    and slice.vDU == p.target) for slice in slices) * n["R"]
                    + mdl.sum(mdl.y_eMBB[it_eMBB] * int(vDU_eMBB_requirement[it_eMBB[1]]) * (mdl.x_eMBB[DU_node_to_ID[paths[it_eMBB[0]].target]]/1000) for it_eMBB in y_eMBB if n["ID"] in paths[it_eMBB[0]].seq) <= n["R"])
            for slice in slices:
                mdl.add(mdl.z[(slice.id, n["ID"])] >= 0)
                mdl.add(mdl.z[(slice.id, n["ID"])] <= enumerable[n["ID"]])
    for b in DUs:
        mdl.add(mdl.sum(mdl.x[it] for it in i if it[1] == b["ID"]) == 1)
    for slice in slices:
        mdl.add(mdl.sum(mdl.y[it] for it in y if it[1] == slice.id) == 1)
        mdl.add(mdl.w_aux[slice.id] <= mdl.w[slice.id])
        mdl.add(mdl.w_aux[slice.id] >= mdl.w[slice.id]/slice.n_flows)
        mdl.add(mdl.w[slice.id] <= slice.n_flows)
        mdl.add(mdl.w[slice.id] >= 0)
        mdl.add(mdl.sum(mdl.sum(mdl.x[v, DU_node_to_ID[slice.vDU]] for v in DRCs if v in [1, 2, 4]) * mdl.y[(p.id, slice.id)] * mdl.w_aux[slice.id] * (((eval(slice.requirements)[0]*(mdl.x_aux[DU_node_to_ID[slice.vDU]]/1000))/mdl.min(nodes[n]["R"] * mdl.z[slice.id, nodes[n]["ID"]]/enumerable[nodes[n]["ID"]] - (mdl.w[slice.id] - 1) * (eval(slice.requirements)[1]*(mdl.x_aux[DU_node_to_ID[slice.vDU]]/1000)) for n in p.seq if nodes[n]["ID"])) +
                        (mdl.sum((((12000*7175/1000)/(nodes[n]["R"]/10**6))/10**6) + ((((eval(slice.requirements)[0]*(mdl.x_aux[DU_node_to_ID[slice.vDU]]/1000)) + (eval(slice.requirements)[1] * (mdl.x_aux[DU_node_to_ID[slice.vDU]]/1000) *
                            mdl.sum((((12000*7175/1000)/(nodes[n2]["R"]/10**6))/10**6) for n2 in p.seq if nodes[n2]["ID"] and p.seq.index(n2) > p.seq.index(n)))) * (mdl.w[slice.id] - 1))/(nodes[n]["R"] * (mdl.z[slice.id, nodes[n]["ID"]]/enumerable[nodes[n]["ID"]]))) for n in p.seq if nodes[n]["ID"])) +
                        (len(p.seq) - 2) * (0.000005 * 5) + (mdl.w[slice.id] * eval(slice.requirements)[1])/10**9 * mdl.sum(mdl.x[it_x] * DRCs[it_x[0]].proc_delay for it_x in i if it_x[1] == DU_node_to_ID[slice.vDU]))
                        for p in paths if p.target == slice.vDU) <= slice.max_delay)
        for n in nodes:
            if n["ID"]:
                mdl.add(n["R"] * (mdl.z[slice.id, n["ID"]]/enumerable[n["ID"]]) >= mdl.sum(mdl.y[p.id, slice.id] for p in paths if n["ID"] in p.seq and p.target == slice.vDU) * mdl.w[slice.id] * eval(slice.requirements)[1] * (mdl.x_aux[DU_node_to_ID[slice.vDU]]/1000))
    print("Model creation time:\t{}".format(time.time() - create_model_time))
    print("-----------------------------------------------------------------------------------------------------------")
    solving_time = time.time()
    if exists('/opt/ibm/ILOG/CPLEX_Studio201/cpoptimizer/bin/x86-64_linux/cpoptimizer'):
        msol = mdl.solve(agent='local', execfile='/opt/ibm/ILOG/CPLEX_Studio201/cpoptimizer/bin/x86-64_linux/cpoptimizer')
    elif exists('/opt/ibm/ILOG/CPLEX_Studio221/cpoptimizer/bin/x86-64_linux/cpoptimizer'):
        msol = mdl.solve(agent='local', execfile='/opt/ibm/ILOG/CPLEX_Studio221/cpoptimizer/bin/x86-64_linux/cpoptimizer')
    else:
        msol = mdl.solve(agent='local', execfile='/opt/ibm/ILOG/CPLEX_Studio221/cpoptimizer/bin/x86-64_linux/cpoptimizer')
    solution_dict = {}
    if msol:
        print("Solution status: " + msol.get_solve_status())
        print("------------------------ vRAN positioning ------------------------")
        cost = 0
        for j in i:
            if msol[mdl.x[j]] > 0:
                print("vDU: {}\tO{}\tCU-DU: {} - {}".format(j[1], j[0], DRCs[j[0]].Fs_CU, DRCs[j[0]].Fs_DU))
                cost += len(DRCs[j[0]].Fs_CU) * 0.2585 + len(DRCs[j[0]].Fs_DU)
        print("------------------------ Selected Paths URLLC ------------------------")
        for j in y:
            if msol[mdl.y[j]] > 0:
                print("Slice {} path: {}".format(j[1], paths[j[0]].seq))

        print("------------------------ Selected Paths eMBB ------------------------")
        for j in y_eMBB:
            if msol[mdl.y_eMBB[j]] > 0:
                print("Slice eMBB of vDU {} path: {}".format(j[1], paths[j[0]].seq))

        print("------------------------ Selected Phis ------------------------")
        for j in z:
            if msol[mdl.z[j]] > 0:
                for k in y:
                    if k[1] == j[0] and j[1] in paths[k[0]].seq and msol[mdl.y[k]] > 0:
                        print("For slice {} Phi in node {} is {:.0f}%".format(j[0], j[1], (msol[mdl.z[j]]/enumerable[j[1]])*100))

        print("------------------------ Admitted flows ------------------------")
        revenue = 0
        for j in w:
            if msol[mdl.w[j]] > 0:
                print("{} flows admitted in slice {}".format(msol[mdl.w[j]], j))
                revenue += msol[mdl.w[j]] * 0.125
        for j in x_aux:
            print("{} pkt size to vDU {}".format(msol[mdl.x_aux[j]]/1000, j))
        print("-------------------------------------------------------------------------------------------------------")
        print("Model solve time:\t{}".format(time.time() - solving_time))
        print("-------------------------------------------------------------------------------------------------------")
        json.dump({"solution": {"cost": cost, "revenue": revenue, "profit": revenue-cost, "time": time.time() - solving_time}}, open("new_plot_results/Only_D-RAN/{}.json".format(sys.argv[3]), 'w'))
    else:
        print("NO SOLUTION FOUND")


if __name__ == '__main__':
    q_vDUs = sys.argv[1]
    q_nodes = sys.argv[2]
    type_t = sys.argv[3]
    print(q_vDUs, q_nodes)
    print("Creating topology")
    print("-----------------------------------------------------------------------------------------------------------")
    alocation_time_start = time.time()
    read_topology("../topologies/evaluation_topologies/{}_vDUs_{}_nodes_{}/topology_{}_vDUs_{}_nodes.json".format(q_vDUs, q_nodes, type_t, q_vDUs, q_nodes))
    read_paths("../topologies/evaluation_topologies/{}_vDUs_{}_nodes_{}/paths.json".format(q_vDUs, q_nodes, sys.argv[3]))
    DRCs = DRC_structure()
    print("Creation time:\t{}".format(time.time() - alocation_time_start))
    print("-----------------------------------------------------------------------------------------------------------")
    run_model(enumerable=[0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100])
