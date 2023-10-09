import socket
import struct
import math

def calculate_haversine_distance(lat1, lon1, lat2, lon2):
    # Earth's radius in kilometers
    R = 6371.0

    # Convert latitude and longitude from degrees to radians
    lat1 = math.radians(lat1)
    lon1 = math.radians(lon1)
    lat2 = math.radians(lat2)
    lon2 = math.radians(lon2)

    # Haversine formula
    dlat = lat2 - lat1
    dlon = lon2 - lon1
    a = math.sin(dlat / 2)**2 + math.cos(lat1) * math.cos(lat2) * math.sin(dlon / 2)**2
    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
    distance = R * c

    return distance

def main():
    host = "192.168.1.61"
    port = 8001

    # Create a UDP socket
    with socket.socket(socket.AF_INET, socket.SOCK_DGRAM) as s:
        s.bind((host, port))
        s.settimeout(60)  # Set a timeout of 60 seconds
        print("Server is listening on {}:{}".format(host, port))

        while True:
            try:
                data, addr = s.recvfrom(1024)
                received_data = data.decode()
                print(received_data)

                coordinates = received_data.split(";")

                if len(coordinates) != 2:
                    print("Invalid packet:", received_data)
                else:
                    coordinates1 = coordinates[0].split(",")
                    lat1, lon1 = map(float, coordinates1)

                    coordinates2 = coordinates[1].split(",")
                    lat2, lon2 = map(float, coordinates2)

                    distance = calculate_haversine_distance(lat1, lon1, lat2, lon2)

                    response = "Distance between the two points: {:.2f} kilometers".format(distance)
                    s.sendto(response.encode(), addr)

            except socket.timeout:
                print("Socket timed out after 60 seconds of inactivity.")
                # s.close()
            except Exception as e:
                print("An error occurred:", str(e))

if __name__ == "__main__":
    main()
