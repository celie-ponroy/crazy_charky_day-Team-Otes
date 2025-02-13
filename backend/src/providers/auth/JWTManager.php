<?php
namespace charly\providers\auth;

use Firebase\JWT\JWT;
use Firebase\JWT\Key;
use Firebase\JWT\ExpiredException;
use Firebase\JWT\SignatureInvalidException;


class JWTManager {
    private string $secret;

    public function __construct() {
        $config = include(__DIR__ . '/../../../config/config.php');
        if (!isset($config['jwt']['secret']) || empty($config['jwt']['secret'])) {
            throw new \InvalidArgumentException('JWT secret key is not set in the configuration file.');
        }
        $this->secret = $config['jwt']['secret'];
    }

    /**
     * Crée un Access Token à partir du payload
     * @param array $payload
     * @return string
     */
    public function createAccessToken(array $payload): string {
        $header = ['alg' => 'HS512', 'typ' => 'JWT', 'kid' => 'default-key-id']; 
        return JWT::encode($payload, $this->secret, 'HS512', null, $header);
    }

    /**
     * Crée un Refresh Token à partir du payload
     * @param array $payload
     * @return string
     */
    public function createRefreshToken(array $payload): string {
        $header = ['alg' => 'HS512', 'typ' => 'JWT', 'kid' => 'default-key-id']; 
        return JWT::encode($payload, $this->secret, 'HS512', null, $header);
    }

    /**
     * Décode un token
     * @param string $token
     * @return array
     */
    public function decodeToken(string $token): array {
        try {
            // Décoder l'en-tête du token pour vérifier `kid`
            $header = json_decode(base64_decode(explode('.', $token)[0]), true);

            if (!isset($header['kid']) || $header['kid'] !== 'default-key-id') {
                throw new \Exception('Invalid or missing "kid" in token header');
            }

            // Décodage et validation avec la clé secrète
            $decoded = JWT::decode($token, new Key($this->secret, 'HS512'));

            // Convertir en tableau pour une utilisation plus facile
            return (array) $decoded;
        } catch (ExpiredException $e) {
            throw new \Exception('Token expiré');
        } catch (SignatureInvalidException $e) {
            throw new \Exception('Signature du token invalide');
        } catch (\Exception $e) {
            throw new \Exception('Token invalide : ' . $e->getMessage());
        }
    }    
    
}
