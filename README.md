JSON Web Tokens (JWT) are JSON objects used to send information between parties in a compact and secure manner.

JWTs have three parts: a header, a body, and a signature i.e. three JSON objects.

The header contains info on how the JWT is encoded.
The body is the meat of the token (where the claims live).
The signature provides the security.

This program uses RS256 with a pair of RSA private key to sign and RSA public key decode JWT.

Besides using asymmetric RSA private and public keys, we can also use simple symmetric HMAC signatures.

Header includes token type and algorithm : typ is JWT bearer token and alg is RS256 in this program

Body (Payload) includes subject, issuer, issuedAt, expiration time, etc. claims.

Signature tells signatureAlgorithm.

There are several type of claims for example:

User claim: external or internal user being authenticated (for example against USER table) and credentials being used to generate jwt.

Service claim: special credentials being used for jwt creation for service to service communciation.

Anonymous claim: special usage.

