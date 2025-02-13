<?php
namespace charly\providers\auth;

use charly\core\dto\UserDTO;
use DI\Container;
use Firebase\JWT\ExpiredException;
use Firebase\JWT\JWT;
use Firebase\JWT\Key;

class JWTManager
{
    protected int $tempsValidite;
    protected string $key, $algo;
    public function __construct(Container $co)
    {
        $this->tempsValidite = 3600;
        $this->key = getenv('JWT_SECRET_KEY');
        $this->algo = 'HS512';
    }

    public function createAcessToken(UserDTO $user): string{

        $payload = [
            'iat'=>time(),
            'exp'=>time()+$this->tempsValidite,
            'sub' => $user->getId(),
            'role' => $user->getRole()
        ] ;

        return JWT::encode($payload, $this->key, $this->algo);
    }
    public function decodeToken(string $token): array
    {
        try {
            return (array) JWT::decode($token, new Key($this->key, $this->algo));
        } catch (ExpiredException $e) {
            throw new \Exception("Token expired");
        } catch (\Exception $e) {
            throw new \Exception("Invalid token: " . $e->getMessage());
        }
    }
}