import json
import numpy as np

allSplitsCost = []
allSplitsProfit = []
allSplitsRevenue = []

for i in range(1, 256):
    all_splits_obj = json.load(open("All_splits/random_{}.json".format(i)))
    allSplitsCost.append(all_splits_obj["solution"]["cost"])
    allSplitsProfit.append(all_splits_obj["solution"]["profit"])
    allSplitsRevenue.append(all_splits_obj["solution"]["revenue"])

print("Cost All Splits:", sum(allSplitsCost)/len(allSplitsCost))
print("Error: ", np.std(allSplitsCost))
print("Profit All Splits:", sum(allSplitsProfit)/len(allSplitsProfit))
print("Error: ", np.std(allSplitsProfit))
print("Revenue All Splits:", sum(allSplitsRevenue)/len(allSplitsRevenue))
print("Error: ", np.std(allSplitsRevenue))

print("------------------------------------------------------------------------------------------------------------")

allSplitsCost = []
allSplitsProfit = []
allSplitsRevenue = []

for i in range(1, 256):
    all_splits_obj = json.load(open("Only_D-RAN/random_{}.json".format(i)))
    allSplitsCost.append(all_splits_obj["solution"]["cost"])
    allSplitsProfit.append(all_splits_obj["solution"]["profit"])
    allSplitsRevenue.append(all_splits_obj["solution"]["revenue"])

print("Cost D-RAN:", sum(allSplitsCost)/len(allSplitsCost))
print("Error: ", np.std(allSplitsCost))
print("Profit D-RAN:", sum(allSplitsProfit)/len(allSplitsProfit))
print("Error: ", np.std(allSplitsProfit))
print("Revenue D-RAN:", sum(allSplitsRevenue)/len(allSplitsRevenue))
print("Error: ", np.std(allSplitsRevenue))

print("------------------------------------------------------------------------------------------------------------")

allSplitsCost = []
allSplitsProfit = []
allSplitsRevenue = []

for i in range(1, 256):
    all_splits_obj = json.load(open("Only_C-RAN/random_{}.json".format(i)))
    allSplitsCost.append(all_splits_obj["solution"]["cost"])
    allSplitsProfit.append(all_splits_obj["solution"]["profit"])
    allSplitsRevenue.append(all_splits_obj["solution"]["revenue"])

print("Cost C-RAN:", sum(allSplitsCost)/len(allSplitsCost))
print("Error: ", np.std(allSplitsCost))
print("Profit C-RAN:", sum(allSplitsProfit)/len(allSplitsProfit))
print("Error: ", np.std(allSplitsProfit))
print("Revenue C-RAN:", sum(allSplitsRevenue)/len(allSplitsRevenue))
print("Error: ", np.std(allSplitsRevenue))
