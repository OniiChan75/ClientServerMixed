import socket

host = "127.0.0.1"
port = 8001

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((host, port))

latitudine1 = 45.4219
longitudine1 = 9.1342
latitudine2 = 41.8919
longitudine2 = 12.4958

data = [latitudine1, longitudine1, latitudine2, longitudine2]
bytes = b"".join([bytes(str(dato), "utf-8") for dato in data])
print(bytes)
s.sendall(bytes)

data = s.recv(1024)
print(data.decode())

s.close()