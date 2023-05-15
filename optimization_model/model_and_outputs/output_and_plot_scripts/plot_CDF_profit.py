import numpy as np
from matplotlib import pyplot as plt
import json
from matplotlib.lines import Line2D

plt.rcParams["figure.figsize"] = [10, 4]
plt.rcParams["figure.autolayout"] = True

cost_list = []
revenue_list = []
profit_list = []

for i in range(1, 256):
    json_obj = json.load(open("All_splits/random_{}.json".format(i)))
    cost_list.append(json_obj["solution"]["cost"])
    revenue_list.append(json_obj["solution"]["revenue"])
    profit_list.append(json_obj["solution"]["profit"])

plt.xlim(-6, 30)
plt.ylim(0, 1.1)
plt.xticks([-6, -4, -2, 0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28], fontsize=20)
plt.yticks([0.25, 0.5, 0.75, 1], fontsize=20)
plt.grid(linestyle='--', linewidth=0.5)
# plt.hist(profit_list, bins=5, density=True, color="lightgray")

plt.ylabel('CDF', fontsize=20)
plt.xlabel('Profit (#)', fontsize=20)
# plt.ylim(0, 1.1)

N = 50
count, bins_count = np.histogram(profit_list, bins=10)
pdf = count / sum(count)
cdf = np.cumsum(pdf)
plt.plot(bins_count[1:], cdf, label="CDF", color="navy")

cost_list = []
revenue_list = []
profit_list = []

for i in range(1, 256):
    json_obj = json.load(open("Only_D-RAN/random_{}.json".format(i)))
    cost_list.append(json_obj["solution"]["cost"])
    revenue_list.append(json_obj["solution"]["revenue"])
    profit_list.append(json_obj["solution"]["profit"])

# plt.hist(profit_list, bins=5, density=True, color="lightgray")

N = 50
count, bins_count = np.histogram(profit_list, bins=10)
pdf = count / sum(count)
cdf = np.cumsum(pdf)
plt.plot(bins_count[1:], cdf, label="CDF", color="firebrick")

cost_list = []
revenue_list = []
profit_list = []

for i in range(1, 256):
    json_obj = json.load(open("Only_C-RAN/random_{}.json".format(i)))
    cost_list.append(json_obj["solution"]["cost"])
    revenue_list.append(json_obj["solution"]["revenue"])
    profit_list.append(json_obj["solution"]["profit"])

# plt.hist(profit_list, bins=5, density=True, color="lightgray")

N = 50
count, bins_count = np.histogram(profit_list, bins=13)
pdf = count / sum(count)
cdf = np.cumsum(pdf)
plt.plot(bins_count[1:], cdf, label="CDF", color="royalblue")

plt.rcParams.update({'font.size': 16})
legend_elements = [Line2D([0], [0], color='firebrick', lw=3, label='D-RAN'),
                   Line2D([0], [0], color='royalblue', lw=3, label='C-RAN'),
                   Line2D([0], [0], color='navy', lw=3, label='NG-RAN Splits')]
plt.legend(handles=legend_elements, loc="lower right", ncol=1)

plt.savefig("profit_CDF.pdf", bbox_inches="tight")

plt.show()
