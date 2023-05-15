#get file object reference to the file
VNCs = {1: 0, 2: 0, 4: 0, 6: 0, 8: 0, 9: 0}
count = 0
for i in range(1, 256):
    file = open("All_splits/log_random_{}".format(i), "r")
    data = file.read()

    if data.count(str(1000/1000) + " pkt"):
        VNCs[1] += data.count(str(1000/1000) + " pkt")
    if data.count(str(1016/1000) + " pkt"):
        VNCs[2] += data.count(str(1016/1000) + " pkt")
    if data.count(str(1055/1000) + " pkt"):
        VNCs[4] += data.count(str(1055/1000) + " pkt")
    if data.count(str(1070/1000) + " pkt"):
        VNCs[6] += data.count(str(1070/1000) + " pkt")
    if data.count(str(6621/1000) + " pkt"):
        VNCs[8] += data.count(str(6621/1000) + " pkt")
    if data.count(str(7634/1000) + " pkt"):
        VNCs[9] += data.count(str(7634/1000) + " pkt")

print(VNCs)
print(count)