import socket

# Server IP address and port
server_ip = ''              # enter the IP you want to connect to 
server_port = 8001          # enter the port you want to connect to (8001 is default)

# Create a UDP socket
client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

timeout = 100.0
client_socket.settimeout(timeout)

try:
    client_socket.connect((server_ip, server_port))
    print("Connected to the server.")
    
    # Coordinate input from client (lat, lon ; lat, lon)
    latitude1 = input("Enter latitude1: ")
    longitude1 = input("Enter longitude1: ")
    latitude2 = input("Enter latitude2: ")
    longitude2 = input("Enter longitude2: ")
        
    # Create a string with coordinates
    coordinates = f"{latitude1},{longitude1};{latitude2},{longitude2}"
    print(coordinates)

    # Send data to server
    client_socket.sendall(coordinates.encode())

    try:
        # Get response from server
        response, server_address = client_socket.recvfrom(1024)
        result = response.decode()
        print(result)
                      
    except socket.timeout:
        print("Timeout expired. Try again.")
        
except KeyboardInterrupt:
    print("Client stopped.")

finally:
    client_socket.close()
