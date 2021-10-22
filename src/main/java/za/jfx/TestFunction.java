package za.jfx;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestFunction {


    public static void main(String[] args){

    }

    private static void testScanNetwork() throws IOException {
        String networkFoScan = "10.39.11.";

        List<String> hostSToFile = new ArrayList<>();
        InetAddress hostScan;

        for (int i = 0; i < 256; i++) {
            String address = networkFoScan + i;
            try {
                hostScan = InetAddress.getByName(address);
                if (hostScan.isReachable(50)) {
                    hostSToFile.add(hostScan.getCanonicalHostName() + ";" + hostScan.getHostAddress() + ";" + hostScan.getHostName());
                    System.out.println(String.format("host %s is active", address));
                } else {
                    System.err.println(String.format("host %s isn't active", address));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Files.write(
                Paths.get("c:\\tmp\\scan_" + networkFoScan + ".txt"),
                hostSToFile
        );
    }

}
