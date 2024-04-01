package xyz.pikzstudio;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class License {

    private static final String URL = "https://license.pikzstudio.xyz";

    private static final String PRODUCT = "Border Core";

    public static boolean isLicenseValid(String id) {
        if (!isAlive()) {
            Border.log("§4------------------");
            Border.log("§4------------------");
            Border.log("§4PLEASE CONTACT THE AUTHOR ON DISCORD.GG/PIKZSTUDIOS");
            Border.log("§4NOTE: THE PLUGIN HAS BEEN ENABLED.");
            Border.log("§4------------------");
            Border.log("§4------------------");
            return true;
        }

        LicenseData licenseData = getLicenseById(id);

        if (licenseData == null || !licenseData.product.equals(PRODUCT)) {

            Border.log("§4------------------");
            Border.log("§4------------------");
            Border.log("§4INVALID LICENSE KEY '" + id + "'");
            Border.log("§4");
            Border.log("§4PLEASE CONTACT THE AUTHOR ON DISCORD.GG/PIKZSTUDIO");
            Border.log("§4TO GET YOUR BORDER SETUP LICENSE KEY.");
            Border.log("");
            Border.log("§4What's going on? In order to prevent re-distribution,");
            Border.log("§4this plugin requires a license.");
            Border.log("§4------------------");
            Border.log("§4------------------");
            return false;
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat();

        Border.log("§a-------------------");
        Border.log("§fLicense Key: §2" + id.substring(0, id.length() / 5) + "**********");
        Border.log("§fOwner: §2" + licenseData.owner);
        Border.log("§fCreation Date: §2" + dateFormat.format(new Date(licenseData.creation_date)));
        Border.log("§fProduct: §2" + licenseData.product);
        Border.log("§fAllowed IPs: §2" + Arrays.toString(licenseData.ips));
        Border.log("§a-------------------");

        return true;
    }

    public static boolean isAlive() {

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL))
                    .setHeader("User-Agent", "Licensing System") // add request header
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.statusCode() == 200;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static LicenseData getLicenseById(String id) {
        String requestStr = "/license/id/" + id;

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(URL + requestStr))
                    .setHeader("User-Agent", "Licensing System") // add request header
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200)
                return null;


            Gson gson = new Gson();

            JsonObject jsonObject = new JsonParser().parse(response.body()).getAsJsonObject();

            String owner = jsonObject.get("owner").getAsString();
            String product = jsonObject.get("product").getAsString();
            long creationDate = jsonObject.get("creation_date").getAsLong();
            List<String> ips = new ArrayList<>();
            jsonObject.get("ips").getAsJsonArray().forEach(jsonElement -> ips.add(jsonElement.getAsString()));
            return new LicenseData(id, owner, product, creationDate, ips.toArray(String[]::new));

        } catch (Exception e) {
            return null;
        }

    }


    public record LicenseData(String id, String owner, String product, long creation_date, String[] ips) {
    }
}
