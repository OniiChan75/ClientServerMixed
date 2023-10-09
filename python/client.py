import socket

# Indirizzo IP e porta del server
server_ip = '192.168.1.61'
server_port = 8001

# Crea un socket UDP
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

timeout = 100.0
client_socket.settimeout(timeout)

try:
    client_socket.connect((server_ip, server_port))
    print("Connesso al server.")
    
    # Input delle coordinate dal client (lat, lon ; lat, lon)
    latituine1 = 45.4642700
    longitudine1 = 9.1895100
    latituine2 = 45.8558900
    longitudine2 = 9.3970400
        
    # Crea una stringa con le coordinate
    coordinate = f"{latituine1},{longitudine1};{latituine2},{longitudine2}"
    print(coordinate)

    # Invia i dati al server
    client_socket.sendall(coordinate.encode())

    try:
        # Ricevi la risposta dal server
        response, server_address = client_socket.recvfrom(1024)
        risultato = response.decode()
        #if(float(risultato) < 0):
        #    risultato = risultato * -1
        #print("Distanza tra i due punti:" + risultato + " km")
        print(risultato)
                      
    except socket.timeout:
        print("Timeout scaduto. Riprova.")
        
except KeyboardInterrupt:
    print("Client interrotto.")

finally:
    client_socket.close()
