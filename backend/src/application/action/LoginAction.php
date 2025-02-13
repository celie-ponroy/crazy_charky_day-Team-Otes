<?php

namespace charly\application\action;

use charly\core\service\interfaces\UserAuthServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use charly\core\service\AuthenticationException;

class LoginAction extends AbstractAction
{

    protected UserAuthServiceInterface $authService;

    public function __construct(UserAuthServiceInterface $authService)
    {
        $this->authService = $authService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface {
        try {
            $data = $rq->getParsedBody();

            if (!$data || empty($data['nom']) || empty($data['password'])) {
                return $this->respondWithError($rs, 'Il manque un crédential', 400);
            }

            $userDto = $this->authService->login($data['nom'], $data['password']);

            $responseData = [
                'success' => true,
                'data' => [
                    'accessToken' => $userDto->accessToken,
                    'refreshToken' => $userDto->refreshToken,
                ],
            ];        
            
            $rs->getBody()->write(json_encode($responseData));
            return $rs->withHeader('Content-Type', 'application/json')->withStatus(200);

        } catch (AuthenticationException $e) {
            return $this->respondWithError($rs, $e->getMessage(), 401);
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