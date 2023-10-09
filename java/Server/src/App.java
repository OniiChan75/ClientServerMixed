import java.io.*;
import java.net.*;

public class App {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Crea una socket UDP sulla porta 9876
            socket = new DatagramSocket(8001);

            while (true) {
                byte[] receiveData = new byte[1024];

                // Crea un pacchetto DatagramPacket per ricevere i dati
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                // Ricevi il pacchetto dal client
                socket.receive(receivePacket);

                // Estrai i dati dalla ricezione
                String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());

                // Dividi le coordinate dalla stringa ricevuta
                String[] coordinates = receivedData.split(",");

                if (coordinates.length != 2) {
                    System.out.println("Pacchetto non valido: " + receivedData);
                } else {
                    // Ottieni le coordinate x e y
                    String x = coordinates[0];
                    String y = coordinates[1];

                    // Crea una risposta con le coordinate ricevute
                    String response = "Coordinate ricevute: X = " + x + ", Y = " + y;
                    byte[] responseData = response.getBytes();

                    // Crea un pacchetto DatagramPacket per inviare la risposta al client
                    DatagramPacket sendPacket = new DatagramPacket(responseData, responseData.length,
                            receivePacket.getAddress(), receivePacket.getPort());

                    // Invia la risposta al client
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
}
