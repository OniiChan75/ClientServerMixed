//java server in 8001 port 

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {

    public static void main(String[] args) throws IOException {
        Server();
    }

    public static void Server() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8001);
        Socket clientSocket = serverSocket.accept();

        InputStream inputStream = clientSocket.getInputStream();
        byte[] dati = new byte[1024];
        int latitudine1 = inputStream.read(dati);
        int longitudine1 = inputStream.read(dati);
        int latitudine2 = inputStream.read(dati);
        int longitudine2 = inputStream.read(dati);

        double distanza = distanzaTraPunti(latitudine1, longitudine1, latitudine2, longitudine2);
        System.out.println("La distanza tra i due punti è di: " + distanza);

        OutputStream outputStream = clientSocket.getOutputStream();
        String distanzaStringa = String.valueOf(distanza);
        outputStream.write(distanzaStringa.getBytes());

        clientSocket.close();
        serverSocket.close();
    }

    private static double distanzaTraPunti(double latitudine1, double longitudine1, double latitudine2,
            double longitudine2) {
        // Converti le latitudini e le longitudini in gradi decimali
        latitudine1 = latitudine1 * Math.PI / 180;
        longitudine1 = longitudine1 * Math.PI / 180;
        latitudine2 = latitudine2 * Math.PI / 180;
        longitudine2 = longitudine2 * Math.PI / 180;

        // Calcola la distanza tra i due punti
        double R = 6371.01; // Raggio della Terra in chilometri
        double deltaLatitudine = latitudine2 - latitudine1;
        double deltaLongitudine = longitudine2 - longitudine1;
        double s = Math
                .sqrt(Math.pow(deltaLatitudine * R, 2) + Math.pow(Math.cos(latitudine1) * deltaLongitudine * R, 2));

        return s;
    }
}