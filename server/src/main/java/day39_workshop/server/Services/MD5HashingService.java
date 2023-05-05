package day39_workshop.server.Services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MD5HashingService {

    @Value("${spring.api.key}")
    private String publicKey;

    @Value("${spring.api.Secretkey}")
    private String secretKey;

    public String getMD5HAsh(String input){
        
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
    
            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());
    
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
    
            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
    
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public String getTimestamp(){
        Date date = new Date();
		Timestamp timestamp2 = new Timestamp(date.getTime());

        return String.valueOf(timestamp2.getTime());
    }



}
