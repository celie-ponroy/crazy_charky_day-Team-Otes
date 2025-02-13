<?php

namespace charly\application\action;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use charly\core\service\interfaces\UserAuthServiceInterface;
use charly\core\service\AuthenticationException;

class RegisterAction extends AbstractAction
{

    private UserAuthServiceInterface $authService;

    public function __construct(UserAuthServiceInterface $authService)
    {
        $this->authService = $authService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        try {
            $data = $rq->getParsedBody();

            if (!isset($data['nom']) || !isset($data['password'])) {
                return $this->respondWithError($rs, 'Email ou mot de passe requis', 400);
            }

            $this->authService->register($data['nom'], $data['password']);

            $responseData = [
                'success' => true,
                'message' => 'Utilisateur enregistré avec succès'
            ];

            $rs->getBody()->write(json_encode($responseData));
            return $rs->withHeader('Content-Type', 'application/json')->withStatus(201);
        } catch (AuthenticationException $e) {
            return $this->respondWithError($rs, $e->getMessage(), 400);
        } catch (\Exception $e) {
            return $this->respondWithError($rs, $e->getMessage(), 500);
        }
    }

    private function respondWithError(ResponseInterface $response, string $message, int $status): ResponseInterface
    {
        $responseData = [
            'status' => $status,
            'error' => $message
        ];

        $response->getBody()->write(json_encode($responseData, JSON_UNESCAPED_UNICODE | JSON_PRETTY_PRINT));

        return $response->withHeader('Content-Type', 'application/json')->withStatus($status);
    }
}
