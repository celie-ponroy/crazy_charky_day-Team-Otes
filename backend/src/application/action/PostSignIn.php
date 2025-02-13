<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\UserAuthServiceInterface;
use charly\providers\auth\JWTManager;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class PostSignIn extends AbstractAction
{

    protected UserAuthServiceInterface $authService;
    protected JWTManager $jwtManager;

    public function __construct(UserAuthServiceInterface $authService, JWTManager $jwtManager)
    {
        $this->authService = $authService;
        $this->jwtManager = $jwtManager;
    }

    public function __invoke(ServerRequestInterface $request, ResponseInterface $response, array $args): ResponseInterface
    {
        $data = $request->getParsedBody();
        $nom = $data['nom'] ?? '';
        $password = $data['password'] ?? '';

        try {
            $userDTO = $this->authService->signIn($nom, $password);

            $token = $this->jwtManager->createAcessToken($userDTO);
            return JsonRenderer::render($response, 200, ['token' => $token]);
        } catch (\Exception $e) {
            return JsonRenderer::render($response, 401, ['error' => $e->getMessage()]);
        }
    }
}