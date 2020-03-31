import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.util.encoders.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/*

JWTs have three parts: a header, a body, and a signature.
The header contains info on how the JWT is encoded.
The body is the meat of the token (where the claims live).
The signature provides the security.

This program uses RS256 with a pair of RSA private key to sign and RSA public key decode JWT.

 */
public class JWTParser {

    static String subject = "mySubjet";
    static String issuer = "myIssuer";
    static long ttlMillis = 30000;
    static String alg = "R";
//    static String alg = "P";
    static String kid = "07a66ba7319b4cf1b65c74875fb62ae9";

    //ssh-keygen and openssl to get rsa private and public keys
    static String privateKey = "MIIJJwIBAAKCAgEAo11AfI3+El2OMWTBUTK6hfmcZXlPsBnqs//2kWMeajCOVkFO\n" +
        "t67Cj0W7E7vk9RJkRnLZLLSKKszpReHxEZRaRQd8V8CxbN71akwItIHOTA2raype\n" +
        "2HhTdrcK1IN/8CIy/hnBcXInbVwGNZddGMkGWJX2hM9ltUFf965id5bVKK4OUTwx\n" +
        "SroF1L56XFosmvlFkMoeDM+ek5JRBwUg6TMpaWwlygxX7A3QcnqHnuRXSjs8KX8h\n" +
        "SVvOYjqy88tw90RP0RVi5WoYTu7Ywx82nadbf46I1tx1OjWCmK4b6gmMUtBg2Q0z\n" +
        "138EDyLWWilQisNupF7nkmdENkl2/HOqT+IyIGgfknG/7GYvYcgjhmhbVLmNc++J\n" +
        "fOckfWk3RpKIOmeXP2IRpT1KNX9z7vUDEsNWcMg813dJsQmEVwPlBzeZxJ7pKH0t\n" +
        "BjISJBZWWVWYTZu2hpKA7VnWrK8d08MRrrOEdXwbTAHUIWw+Q16hV97NgKy8l0YK\n" +
        "5913E629/aPw2Hhymnwg4cJHB3I2sLcEUOoCtSrNfKdW/h3Kt8hnVoXOHSoByEIu\n" +
        "VJo0Hmb7XctLWv6BCCvEhfTjn6L5nxVA71Lq6sNfAk69JV2Fhl+Pns9lIiVSp4rt\n" +
        "inQZsFMXdNRgRTtfPrb9jgt5a9ig6KOwxMzmZGTE7Y+MqC0Af3MJPf/G3PcCAwEA\n" +
        "AQKCAgAie0dmQu5S4A5oXJT1V5jJOJOLU5y/VekVTzyzGVHNJiyVBjngSXHlndlL\n" +
        "5AgCtnwV5p3ycrULLa87QeKJTF/1XAwh1Zldmn4AjsXzm8Z4hRYei5XzaQps71vI\n" +
        "2WzCueU7fyI2mNq7mYXx/931olDFl/mttL0KEh94fwVnqp2ZdYuuF99rn3IlooLf\n" +
        "RWVO3LiTRCYtnBEjoumZarq0nkjqaJpdWFeiipPp5fUEU7wc/f3H4H6dJlScpTkq\n" +
        "flF8z51tS29RbyBsYLu+C7OSeUW12dcGSwgvXiYPpghcUOGehT878S51GOk5bKgD\n" +
        "5d6LO476ANIuM7/RGZaU2LElJWBg4mCafqMA7W9bUlxxOV0SVjkE4BrmhqSxTrls\n" +
        "508dahSBWbhb3wD0Iy96M6Ai3aO10lHsVSMlnLfyfNEwTV2m4BieTJLmDQONlJae\n" +
        "TMPcGUU/9GABcn8NHKYoK5LAqIFwFaycCY8s05qOxx9zRuP5mJNTBym6pnfSqIOv\n" +
        "z0WQMprLLjbsZCE7wacIErDGXNu4dZ8w3yzErgD9SWFGmFnBEMjQf4j4i6PYkizY\n" +
        "187St7x4wKFl3ELRrodWVpYil0NnClnqc1Ryo1UUxkPJyDsy/dkeB9n9f7iKteup\n" +
        "3gFwDn4pMw56nFE797JUrd1gSFgqZhaPcOcZSRUxBj8KMc9YkQKCAQEA0IXlMjsa\n" +
        "nhzl1UldsHyfgVUunKwcHGPCgK6qcdhZQYnvourgSXKCGMbNzH9/eEZ6b2Ps/0XK\n" +
        "lp9kdPozc5T5TTfiTPn8k0+ol87c3KGC2D7Zq2QyK8ZcDt3UDQTT4b+4YXzse7/m\n" +
        "V0uwtGq2ynwW6LBKABy+YG23VQ8kogOFcFF8NeYMUjgnb+mO/J3Afol+dB7G2OCX\n" +
        "KjWrP8TOCO9DFJZZVVpR1kO+z6TJn2ho3SFcCbEsz+zhs9uuwS2eK7ufePuJuX8j\n" +
        "km1VPEDf2QAGbsGf/iRhRgMLlzvpgaPxhtDADz0H3kOShLtnLqLTTD1jN/SKYjvR\n" +
        "vJFiODDUfj27OQKCAQEAyI80V0WcAxpC6rtyLIw656eRH7eOxUCcWLASkvIVDkEQ\n" +
        "7npVaEUgfoBRdU8/nHFyIuMekDs4fY/syeKIuu7SlAwrrvcL6qTAAKm+Z2q802Ul\n" +
        "cT7JT0FPDvgfgp9GD0OD/a4gorkdxYw04UjQTTPGeRkr0q0wdjLshipfVAiJ5nwZ\n" +
        "nE0uUsTxnFpjMM7PYrfoY0x8ucwlM8TPDGP7U8xWdKf7Mpt9lN6op4FPVXCQ7I2A\n" +
        "/h1eoO0VJgs2c2qqDP+KssQPWcOpTKnauY0cpFGw2x1igoOpnsrHCUggKIkGoIwO\n" +
        "vh0i/FZu/aadHuyhBeq+jAyPh0jD7erm58AH7JXprwKCAQBGst7+pONlcE7Mfc9y\n" +
        "Iw7BfuwK2p+9UGWF07KeXsgEXIwPJKteu6A4Xk8wYpoJYXvpgGu+MR966hfZQWto\n" +
        "aL4N3ECx8KAeIgf7UHu5olkGkGikn5gTF3Hm6HcY4uYjyCjvhLd5TZr/Rf5nbTcE\n" +
        "eAYcZojweM5jS0+5WyUEeUUoy7fdXl4XEkrsurdsFuBJBr0FCdpJdfxKp1+kg0mH\n" +
        "5fdMZ51qPvE4QEfVhv2FOpUzRDg8YQHcjWgI3xblEF5sXXS4RDBx4BVjdAZOI4DB\n" +
        "xixoKOXh+NuyLR4k7H9DIR6moug7QmoEhcyLjC5ztWkGlFmrgxHYNIfr8y7l9khV\n" +
        "dQChAoIBACA2T/PMbFhbOXA2Fwpso3FdioOLmdS4oePks7cCRrt5W9zRrTIIGEX5\n" +
        "Ghw7VD4uQmS0Ec6clGAmY6OxTMCpZwz8P2/kofdX6rawjLJThGx3jGH58Z9XH/mJ\n" +
        "hJYYOspsz6BI/VjlXpDCCiQ866farv/4OP+nbWcumqqZ16HJlUx9lidyhgQpOZ0h\n" +
        "L1lBfjGe1CZOvKGi1j7QV2joDg/Re/lUmZXxP2RPtsrgi/tvDvfPam5mDTLPoVvw\n" +
        "5Rf4g8N+r6oyrzx9no1/om+26GjD7iHjQFUyDGZCr2EgQnoAOB5FIIEMcPgScdUv\n" +
        "k4DKBLrMjzozMkJKP53Q4rFKOHuZQGUCggEAMHO9QJUra1ojsO6UO+RI1wF4E9Dz\n" +
        "f0IWmSTw3LyIlYxzfleGHUb0S3/GMLrNV+7q4QsYD4jyJiHQxyYH8fZyWhjnmMR4\n" +
        "1hUVmoGpuojCqCprqvl3YPWdF0zc2R+hsZ0UGW+z59P9enRO+gShQ08ZK+WGTEKR\n" +
        "oy1xxurfAwTmZ0x2MTrjnxXwK0+lJ01uDbnx3CUj1WPJP4ZW4w0wilo4SfjTqzIO\n" +
        "bWhiNWdvz7E8T3YZtmkAbokY40AUnx3z/IongIdDuc7NDABhI4A5hFliDTQiB3rr\n" +
        "MMBsYq2uVV4JSjcadJvPhNvex/8VczUakIQlNxu349c3nenokS/MAQuCzQ==";

    static String publicKey = "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAo11AfI3+El2OMWTBUTK6\n" +
            "hfmcZXlPsBnqs//2kWMeajCOVkFOt67Cj0W7E7vk9RJkRnLZLLSKKszpReHxEZRa\n" +
            "RQd8V8CxbN71akwItIHOTA2raype2HhTdrcK1IN/8CIy/hnBcXInbVwGNZddGMkG\n" +
            "WJX2hM9ltUFf965id5bVKK4OUTwxSroF1L56XFosmvlFkMoeDM+ek5JRBwUg6TMp\n" +
            "aWwlygxX7A3QcnqHnuRXSjs8KX8hSVvOYjqy88tw90RP0RVi5WoYTu7Ywx82nadb\n" +
            "f46I1tx1OjWCmK4b6gmMUtBg2Q0z138EDyLWWilQisNupF7nkmdENkl2/HOqT+Iy\n" +
            "IGgfknG/7GYvYcgjhmhbVLmNc++JfOckfWk3RpKIOmeXP2IRpT1KNX9z7vUDEsNW\n" +
            "cMg813dJsQmEVwPlBzeZxJ7pKH0tBjISJBZWWVWYTZu2hpKA7VnWrK8d08MRrrOE\n" +
            "dXwbTAHUIWw+Q16hV97NgKy8l0YK5913E629/aPw2Hhymnwg4cJHB3I2sLcEUOoC\n" +
            "tSrNfKdW/h3Kt8hnVoXOHSoByEIuVJo0Hmb7XctLWv6BCCvEhfTjn6L5nxVA71Lq\n" +
            "6sNfAk69JV2Fhl+Pns9lIiVSp4rtinQZsFMXdNRgRTtfPrb9jgt5a9ig6KOwxMzm\n" +
            "ZGTE7Y+MqC0Af3MJPf/G3PcCAwEAAQ==";

    public static void main(String[] args) {
        String jwt1 = createJWT(alg, kid, issuer, null, subject, ttlMillis);
        System.out.println(jwt1);

        Claims claim1 = decodeJWT(jwt1);
        System.out.println(claim1);

        if (claim1 != null && claim1.getIssuer().equals(issuer) && claim1.getSubject().equals(subject)) {
            System.out.println("JWT Parser SUCCEEDED!");
        } else {
            System.out.println("JWT Parser FAILED!");
        }
    }

    private static String createJWT(String alg, String kid, String issuer, String aud, String subject, long ttlMillis) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;
        if("R".equalsIgnoreCase(alg)) {
            signatureAlgorithm = SignatureAlgorithm.RS256;
        } else if("P".equalsIgnoreCase(alg)) {
            signatureAlgorithm = SignatureAlgorithm.PS256;
        }

        System.out.println("JWTParser Alg : " + signatureAlgorithm);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] encodedKey = Base64.decode(privateKey);
        System.out.println("JWTParser Encoded private key : " + encodedKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
        String retStr = "";
        Date exp = new Date();
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            exp = new Date(expMillis);
        }
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey privKey = kf.generatePrivate(keySpec);

            retStr = Jwts.builder().setIssuedAt(now)
                    .setSubject(subject)
                    .setIssuer(issuer)
                    .setAudience(aud)
                    .setExpiration(exp)
                    .signWith(signatureAlgorithm, privKey)
                    .setHeaderParam("kid", kid).compact();
            return retStr;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Claims decodeJWT(String jwt) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256; //alg = "R"
        if("R".equalsIgnoreCase(alg)) {
            signatureAlgorithm = SignatureAlgorithm.RS256;
        } else if("P".equalsIgnoreCase(alg)) {
            signatureAlgorithm = SignatureAlgorithm.PS256;
        }

        System.out.println("JWTParser Alg : " + signatureAlgorithm);

        byte[] encodedKey = Base64.decode(publicKey);
        System.out.println("JWTParser Encoded public key : " + encodedKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedKey);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pubKey = kf.generatePublic(keySpec);

            Claims claims = Jwts.parser().setSigningKey(pubKey).parseClaimsJws(jwt).getBody();
            return claims;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}