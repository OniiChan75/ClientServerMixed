import java.io.*;
import java.net.*;
import java.lang.Math;

public class App {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a UDP socket on port 8001 with a timeout of 60 seconds
            socket = new DatagramSocket(8001);    // enter the port number (8001 is default)
            socket.setSoTimeout(60000); // Set timeout to 60 seconds

            while (true) {
                byte[] receiveData = new byte[1024];

                // Create a DatagramPacket to receive data
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Receive the packet from the client
                socket.receive(receivePacket);

                // Extract the data from the reception
                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println(receivedData);

                // Split the coordinates from the received string
                String[] coordinates = receivedData.split(";");
                // System.out.println("Coordinate ricevute: " + coordinates);

                if (coordinates.length != 2) {
                    System.out.println("Pacchetto non valido: " + receivedData);
                } else {
                    // Extract and process coordinates using Haversine formula
                    String[] coordinates1 = coordinates[0].split(",");
                    double lat1 = Double.parseDouble(coordinates1[0]);
                    double lon1 = Double.parseDouble(coordinates1[1]);

                    String[] coordinates2 = coordinates[1].split(",");
                    double lat2 = Double.parseDouble(coordinates2[0]);
                    double lon2 = Double.parseDouble(coordinates2[1]);

                    // Calculate the distance using Haversine formula
                    double distance = calculateHaversineDistance(lat1, lon1, lat2, lon2);

                    // Create a response string
                    String response = "Distance between the two points: " + distance + " kilometers";
                    byte[] responseData = response.getBytes();

                    // Create a DatagramPacket to send the response to the client
                    DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length,
                            receivePacket.getAddress(), receivePacket.getPort());

                    // Send the response to the client
                    socket.send(sendPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    // Function to calculate distance using Haversine formula
    public static double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in kilometers
    }
}
