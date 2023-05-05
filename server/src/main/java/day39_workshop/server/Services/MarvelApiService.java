package day39_workshop.server.Services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import day39_workshop.server.Models.MarvelCharacter;

@Service
public class MarvelApiService {
    
    @Autowired
    private MD5HashingService hashSvc;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.api.key}")
    private String publicKey;

    @Value("${spring.api.Secretkey}")
    private String secretKey;

    @Value("${spring.api.url}")
    private String marvelApiUrl;


    private String[] getMarvelApiHash(){
        String [] result = new String[2];
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long tsVal = timestamp.getTime();
        String hashVal = tsVal + secretKey + publicKey;
        result[0] = tsVal + ""; // this is to convert long to string.
        result[1] = hashSvc.getMD5HAsh(hashVal);

        return result;
    }

    public Optional<List<MarvelCharacter>> getCharacters(String characterName,Integer limit, Integer offset){
        
        ResponseEntity<String> resp = null;
        List<MarvelCharacter> cArr = null;
        MarvelCharacter c = null;
         String [] r = getMarvelApiHash();

         String marvelApiCharUrl = UriComponentsBuilder.fromUriString(marvelApiUrl)
                                                        .queryParam("ts", r[0])
                                                        .queryParam("apiKey", publicKey)
                                                        .queryParam("hash", r[1])
                                                        .queryParam("nameStartsWith", characterName.replaceAll(" ", "+"))
                                                        .queryParam("limit", limit)
                                                        .queryParam("offset", offset)
                                                        .toUriString();

        resp = restTemplate.getForEntity(marvelApiCharUrl, String.class);
        try{
            cArr = MarvelCharacter.create(resp.getBody());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        c = cArr.get(0);
        
        if (c != null){
            return Optional.of(cArr);
        } else {
            return Optional.empty();
        }

    }

}
