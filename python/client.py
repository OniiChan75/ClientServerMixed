import socket

# Indirizzo IP e porta del server
server_ip = '127.0.0.1'  # Sostituisci con l'indirizzo IP del tuo server
server_port = 8001

# Crea un socket UDP
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

try:
    while True:
        # Input delle coordinate dal client
        x = input("Inserisci la coordinata X: ")
        y = input("Inserisci la coordinata Y: ")

        # Crea una stringa con le coordinate
        coordinate = f"{x},{y}"

        # Invia i dati al server
        client_socket.sendto(coordinate.encode(), (server_ip, server_port))

        # Ricevi la risposta dal server
        response, server_address = client_socket.recvfrom(1024)
        print("Risposta dal server:", response.decode())

except KeyboardInterrupt:
    print("Client interrotto.")

finally:
    client_socket.close()
