<?php

namespace charly\middleware;

use charly\providers\auth\JWTManager;
use Exception;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Psr\Http\Server\MiddlewareInterface;
use Psr\Http\Server\RequestHandlerInterface;
use Slim\Exception\HttpUnauthorizedException;

class AuthnMiddleware implements MiddlewareInterface
{
    protected JWTManager $jwtManager;

    public function __construct(JWTManager $jwtManager)
    {
        $this->jwtManager = $jwtManager;
    }

    public function process(ServerRequestInterface $request, RequestHandlerInterface $handler): ResponseInterface
    {
        if (!$request->hasHeader('Authorization')) {
            throw new HttpUnauthorizedException($request, "Missing Authorization header");
        }

        $authHeader = $request->getHeaderLine('Authorization');
        if (!str_starts_with($authHeader, 'Bearer ')) {
            throw new HttpUnauthorizedException($request, "Invalid Authorization header format");
        }

        $token = substr($authHeader, 7);

        try {
            $decoded = $this->jwtManager->decodeToken($token);

            $request = $request->withAttribute('user_id', $decoded['sub']);
            $request = $request->withAttribute('user_role', $decoded['role']);
        } catch (\Exception $e) {
            throw new HttpUnauthorizedException($request, $e->getMessage());
        }

        return $handler->handle($request);
    }
}
