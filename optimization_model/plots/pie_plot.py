import matplotlib.pyplot as plt

plt.rcParams["figure.figsize"] = [6, 6]

labels = 'Split O4', 'Split O6', 'Others'
sizes = [544, 472, 0]

fig, ax = plt.subplots()
plt.rcParams.update({'font.size': 20})
ax.pie(sizes, labels=labels, autopct='%1.2f%%', colors=['royalblue', 'lightgray', 'cornflowerblue'])

plt.savefig("split.pdf", bbox_inches='tight')
plt.show()
