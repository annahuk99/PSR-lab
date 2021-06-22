import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {


    ClientConfiguration clientConfiguration =
            ClientConfiguration.builder().connectedTo("localhost:9200").build();
    RestHighLevelClient client = RestClients.create(clientConfiguration).rest();


        while(true) {
            System.out.println("\nWybierz opcje:");
            System.out.println("1. Dodawanie.");
            System.out.println("2. Edycja.");
            System.out.println("3. Usuwanie.");
            System.out.println("4. Wyświetlanie wszystkich elementów.");
            System.out.println("5. Wyświetlanie po ID.");

            Scanner scan = new Scanner(System.in);
            int s = scan.nextInt();

            switch(s){
                case 1:
                    int n = wyborTabeli();
                    if(n==1){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj imie:");
                        String imie = scanner.next();

                        System.out.println("Podaj nazwisko:");
                        String nazwisko = scanner.next();

                        System.out.println("Podaj pensje:");
                        Integer pensja = scanner.nextInt();

                        Lekarz lekarz = Lekarz.builder().imie(imie).nazwisko(nazwisko).pensja(pensja).build();

                        XContentBuilder builder = null;
                        builder = XContentFactory.jsonBuilder()
                                .startObject()
                                .field("imie", lekarz.getImie())
                                .field("nazwisko", lekarz.getNazwisko())
                                .field("pensja", lekarz.getPensja())
                                .endObject();
                        IndexRequest indexRequest = new IndexRequest("lekarz");
                        indexRequest.source(builder);
                        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

                        break;
                    }
                    if(n==2){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj imie:");
                        String imie = scanner.next();

                        System.out.println("Podaj nazwisko:");
                        String nazwisko = scanner.next();

                        System.out.println("Podaj wiek:");
                        Integer wiek = scanner.nextInt();

                        Pacjent pacjent = Pacjent.builder().imie(imie).nazwisko(nazwisko).wiek(wiek).build();

                        XContentBuilder builder = null;
                        builder = XContentFactory.jsonBuilder()
                                .startObject()
                                .field("imie", pacjent.getImie())
                                .field("nazwisko", pacjent.getNazwisko())
                                .field("wiek", pacjent.getWiek())
                                .endObject();
                        IndexRequest indexRequest = new IndexRequest("pacjent");
                        indexRequest.source(builder);
                        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);

                        break;
                    }

                case 2:
                    int p = wyborTabeli();
                    if(p==1){

                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String idLekarza = scanner.next();

                        System.out.println("Podaj imie:");
                        String imie = scanner.next();

                        System.out.println("Podaj nazwisko:");
                        String nazwisko = scanner.next();

                        System.out.println("Podaj pensje:");
                        Integer pensja = scanner.nextInt();

                        Lekarz lekarz = Lekarz.builder().imie(imie).nazwisko(nazwisko).pensja(pensja).build();

                        XContentBuilder builder = null;
                        builder = XContentFactory.jsonBuilder()
                                .startObject()
                                .field("imie", lekarz.getImie())
                                .field("nazwisko", lekarz.getNazwisko())
                                .field("pensja", lekarz.getPensja())
                                .endObject();
                        UpdateRequest indexRequest = new UpdateRequest("lekarz", idLekarza);
                        indexRequest.doc(builder);
                        UpdateResponse response = client.update(indexRequest, RequestOptions.DEFAULT);

                        break;
                    }

                    if(p==2){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String idPacjenta = scanner.next();

                        System.out.println("Podaj imie:");
                        String imie = scanner.next();

                        System.out.println("Podaj nazwisko:");
                        String nazwisko = scanner.next();

                        System.out.println("Podaj wiek:");
                        Integer wiek = scanner.nextInt();

                        Pacjent pacjent = Pacjent.builder().imie(imie).nazwisko(nazwisko).wiek(wiek).build();

                        XContentBuilder builder = null;
                        builder = XContentFactory.jsonBuilder()
                                .startObject()
                                .field("imie", pacjent.getImie())
                                .field("nazwisko", pacjent.getNazwisko())
                                .field("wiek", pacjent.getWiek())
                                .endObject();
                        UpdateRequest indexRequest = new UpdateRequest("pacjent", idPacjenta);
                        indexRequest.doc(builder);
                        UpdateResponse response = client.update(indexRequest, RequestOptions.DEFAULT);

                        break;

                    }
                case 3:
                    int w = wyborTabeli();
                    if(w==1){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String idLekarza = scanner.next();

                        DeleteRequest deleteRequest = new DeleteRequest("lekarz");
                        deleteRequest.id(idLekarza);

                        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);

                        break;
                    }
                    if(w==2){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String idPacjenta = scanner.next();

                        DeleteRequest deleteRequest = new DeleteRequest("pacjent");
                        deleteRequest.id(idPacjenta);

                        DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);


                        break;
                    }


                case 4:
                    int q = wyborTabeli();

                    if(q==1){
                        SearchRequest searchRequest = new SearchRequest("lekarz");
                        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
                        SearchHit[] searchHits = response.getHits().getHits();
                        Arrays.stream(searchHits)
                                .map(hit -> {
                                    Lekarz lekarz = JSON.parseObject(hit.getSourceAsString(), Lekarz.class);
                                    lekarz.setId(hit.getId());
                                    return lekarz;
                                }).collect(Collectors.toList())
                                .forEach(System.out::println);
                        break;
                    }
                    if(q==2){
                        SearchRequest searchRequest = new SearchRequest("pacjent");
                        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
                        SearchHit[] searchHits = response.getHits().getHits();
                        Arrays.stream(searchHits)
                                .map(hit -> {
                                    Pacjent pacjent = JSON.parseObject(hit.getSourceAsString(), Pacjent.class);
                                    pacjent.setId(hit.getId());
                                    return pacjent;
                                }).collect(Collectors.toList())
                                .forEach(System.out::println);

                        break;
                    }


                case 5:
                    int t = wyborTabeli();
                    if(t==1){
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String lekarzId = scanner.next();

                        GetRequest getRequest = new GetRequest("lekarz");
                        getRequest.id(lekarzId);

                        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
                        System.out.println(getResponse);
                        break;

                    }
                    if(t==2){

                        Scanner scanner = new Scanner(System.in);
                        System.out.println("Podaj ID:");
                        String pacjentId = scanner.next();

                        GetRequest getRequest = new GetRequest("pacjent");
                        getRequest.id(pacjentId);

                        GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
                        System.out.println(getResponse);
                        break;

                    }
                    break;
            }
        }


}

    public static Integer wyborTabeli() {
        System.out.println("\nWybierz tabele:");
        System.out.println("1. Lekarz");
        System.out.println("2. Pacjent");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }
}
