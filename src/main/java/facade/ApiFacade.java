package facade;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import dto.CarDTO;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Fen
 */
public class ApiFacade {

    String baseURL = "http://localhost:3333/availableCars?week=";
    String[] companies = {"avis", "hertz", "europcar", "budget", "alamo"};
    //private Gson GSON = new Gson();

    class FetchAPI implements Callable<String> {

        String week, company, address;

        FetchAPI(String week, String company, String address) {
            this.week = week;
            this.company = company;
            this.address = address;
        }

        @Override
        public String call() throws Exception {
            return getCarsByCompany(week, company, address);
            //return getCarDataFromCompany(week, company, address);
        }
    }

    public String getAllCarData(String week, String address) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        for (int i = 0; i < companies.length; i++) {
            FetchAPI fetcher = new FetchAPI(week, companies[i], address);
            Future future = executor.submit(fetcher);
            futures.add(future);
        }
        List<String> results = new ArrayList();
        for (Future<String> f : futures) {
            String result = f.get(5, TimeUnit.SECONDS);
            results.add(result);
        }
        executor.shutdown();
        for (String f: results) {
            System.out.println(f);
        }
        
        String str = "";
        for (String res: results) {
            str = str.concat(res);
        }
        return str;
    }

    private String getCarsByCompany(String week, String company, String address) throws MalformedURLException, IOException {
        URL url = new URL(baseURL + week + "&comp=" + company + "&addr=" + address);
        System.out.println(url.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");        
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = "";
        while (scan.hasNext()) {
            jsonStr += scan.nextLine() + "\n";
        }
        System.out.println("Cars from " + company + " " + jsonStr);
        scan.close();
        return jsonStr;
    }

//    private List<CarDTO> getCarDataFromCompany(String week, String company, String address) throws MalformedURLException, IOException {
//        URL url = new URL("http://localhost:3333/availableCars?week=" + week + "&comp=" + company + "&addr=" + address);
//        System.out.println(url.toString());
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
//        con.setRequestProperty("User-Agent", "server");
//        System.out.println("111111111111111111");
//        System.out.println("111111111111111111");
//        System.out.println("111111111111111111");
//        System.out.println("111111111111111111");
//        return readJsonStream(con.getInputStream());
////        Gson gson = new Gson();
////        JsonReader reads = gson.newJsonReader(new InputStreamReader(con.getInputStream()));
////        //Scanner scan = new Scanner(con.getInputStream());
////        //StringBuilder sb = new StringBuilder();
////        //String jsonStr = null;
////        ArrayList<CarDTO> cars = new ArrayList();
////        while (reads.hasNext()) {
////            CarDTO car = gson.fromJson(reads, CarDTO.class);
////            cars.add(car);
////        }
////        if (scan.hasNext()) {
////            jsonStr = scan.nextLine();
////        }
//        //System.out.println(jsonStr);
//        //scan.close();
////        reads.close();
////        return cars;
//    }
//
//    private List<CarDTO> readJsonStream(InputStream in) throws IOException {
//        Gson gson = new Gson();
//        JsonReader reader = gson.newJsonReader(new InputStreamReader(in));
//        System.out.println("22222222222222222");
//        System.out.println("22222222222222222");
//        System.out.println("22222222222222222");
//        System.out.println("22222222222222222");
//        try {
//            return readMessagesArray(reader);
//        } finally {
//            reader.close();
//        }
//    }
//
//    private List<CarDTO> readMessagesArray(JsonReader reader) throws IOException {
//        List<CarDTO> cars = new ArrayList<>();
//        System.out.println("33333333333333");
//        System.out.println("33333333333333");
//        System.out.println("33333333333333");
//        reader.beginArray();
//        while (reader.hasNext()) {
//            cars.add(readMessageCar(reader));
//        }
//        reader.endArray();
//        return cars;
//    }
//
//    private CarDTO readMessageCar(JsonReader reader) throws IOException {
//        Gson gson = new Gson();
//        CarDTO car = null;
//        System.out.println("4444444444444444");
//        System.out.println("4444444444444444");
//        System.out.println("4444444444444444");
//        System.out.println("4444444444444444");
//        reader.beginObject();
//        if (reader.hasNext()) {
//            CarDTO car2 = new CarDTO(reader.nextName(), reader.nextName(), reader.nextName(), reader.nextName(), reader.nextName(), reader.nextName());
//        }
//        reader.endObject();
//        return car;
//    }
}
