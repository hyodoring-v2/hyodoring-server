package com.v2.hyodoring.account.application.auth.verifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;

@Component
public abstract class AbstractOIDCTokenVerifier implements OIDCTokenVerifier {
    private final ObjectMapper objectMapper;

    public AbstractOIDCTokenVerifier() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public OIDCPublicKey getPublicKey(String idToken, List<OIDCPublicKey> publicKeys) {
        try {
            Pair<String, String> kidAndAlg = extractKidAndAlg(idToken);
            return publicKeys.stream()
                    .filter(key -> key.getKid().equals(kidAndAlg.a) && key.getAlg().equals(kidAndAlg.b))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No matching public key found for kid"));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid ID Token format", e);
        }
    }

    @Override
    public OIDCPayload verify(String idToken, List<OIDCPublicKey> publicKeys) {
        try {
            // idToken의 헤더에서 kid, alg 추출
            OIDCPublicKey publicKey = getPublicKey(idToken, publicKeys);

            // OIDC Public Key 정보를 통해 RSA Public Key 생성
            PublicKey rsaPublicKey = extractPublicKey(publicKey);

            // RSA Public Key로 idToken 검증 및 payload 추출
            return verifyAndExtractPayload(idToken, rsaPublicKey);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Failed to extract public key", e);
        }
    }

    public abstract boolean supports(AuthProvider provider);

    protected Pair<String, String> extractKidAndAlg(String idToken) throws JsonProcessingException {
        String headerPart = idToken.split("\\.")[0];
        byte[] decodedBytes = Base64.getUrlDecoder().decode(headerPart);

        String headerJson = new String(decodedBytes, StandardCharsets.UTF_8);

        JsonNode node = objectMapper.readTree(headerJson);
        String kid = node.get("kid").asText();
        String alg = node.get("alg").asText();
        return new Pair<>(kid, alg);
    }

    protected PublicKey extractPublicKey(OIDCPublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        KeySpec keySpec = new RSAPublicKeySpec(
                new BigInteger(1, Base64.getUrlDecoder().decode(publicKey.getN())),
                new BigInteger(1, Base64.getUrlDecoder().decode(publicKey.getE()))
        );
        return keyFactory.generatePublic(keySpec);
    }

    protected abstract OIDCPayload verifyAndExtractPayload(String idToken, PublicKey publicKey);
}
