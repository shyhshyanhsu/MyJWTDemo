JSON Web Tokens (JWT) are JSON objects used to send information between parties in a compact and secure manner.

JWTs have three parts: a header, a body, and a signature i.e. three JSON objects.

The header contains info on how the JWT is encoded.
The body is the meat of the token (where the claims live).
The signature provides the security.

This program uses RS256 with a pair of RSA private key to sign and RSA public key decode JWT.

Besides using asymmetric RSA private and public keys, we can also use simple symmetric HMAC signatures.

Header includes token type and algorithm : JWT bearer token and RS256 in this program

Body includes subject, issuer, issuedAt, expiration time, etc. claims.

Signature tells signatureAlgorithm.

