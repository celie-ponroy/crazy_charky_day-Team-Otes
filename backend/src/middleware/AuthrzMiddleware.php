<?php
namespace charly\middleware;

use Psr\Http\Message\ServerRequestInterface;
use Psr\Http\Server\RequestHandlerInterface;
use Psr\Http\Message\ResponseInterface;
use charly\providers\auth\JWTManager;
use Slim\Psr7\Response;
use charly\core\service\authorization\AuthrzServiceInterface;
use charly\core\service\authorization\AuthrzInvalidRoleException;

class AuthrzMiddleware
{
    private AuthrzServiceInterface $authrzService;

    public function __construct(AuthrzServiceInterface $authrzService)
    {
        $this->authrzService = $authrzService;
    }

    public function __invoke(ServerRequestInterface $request, RequestHandlerInterface $handler): ResponseInterface
    {
        if (!$request->hasHeader('Authorization')) {
            return $this->respondWithError("Header Authorization manquant", 400);
        }

        $authHeader = $request->getHeaderLine('Authorization');
        if (!preg_match('/Bearer\s(\S+)/', $authHeader, $matches)) {
            return $this->respondWithError("Bearer token manquant", 400);
        }

        $token = $matches[1];

        // Décode le token JWT
        $jwtManager = new JWTManager();
        $auth_data = $jwtManager->decodeToken($token);
        $path = $request->getUri()->getPath();
        $userId = $auth_data['id'];

        if (strpos($path, '/competences') === 0) {
            try {
                $this->authrzService->isGranted($userId);
            } catch (AuthrzInvalidRoleException $e) {
                return $this->respondWithError($e->getMessage(), 403);
            }
        }

        return $handler->handle($request);
    }

    private function respondWithError(string $message, int $status): ResponseInterface
    {
        $responseData = [
            'status' => $status,
            'error' => $message
        ];
        $response = new Response();
        $response->getBody()->write(json_encode($responseData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

        return $response->withHeader('Content-Type', 'application/json')->withStatus($status);
    }
}
