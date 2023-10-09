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
    
    # Input delle coordinate dal client (lon, lat; lon, lat)
    longitudine1 = input("Inserisci longitudine: ")
    latituine1 = input("Inserisci latitudine: ")
    longitudine2 = input("Inserisci longitudine: ")
    latituine2 = input("Inserisci latitudine: ")
        
    # Crea una stringa con le coordinate
    coordinate = f"{longitudine1},{latituine1};{longitudine2},{latituine2}"
    print(coordinate)

    # Invia i dati al server
    client_socket.sendall(coordinate.encode())

    try:
        # Ricevi la risposta dal server
        response, server_address = client_socket.recvfrom(1024)
        risultato = response.decode()
        if(float(risultato) < 0):
            risultato = risultato * -1
        print("Distanza tra i due punti:" + risultato)
                      
    except socket.timeout:
        print("Timeout scaduto. Riprova.")
        
except KeyboardInterrupt:
    print("Client interrotto.")

finally:
    client_socket.close()
