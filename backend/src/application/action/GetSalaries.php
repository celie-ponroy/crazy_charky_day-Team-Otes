<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use charly\core\service\interfaces\UserServiceInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class GetSalaries extends AbstractAction
{
    protected UserServiceInterface $userService;

    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        $salaries = $this->userService->getSalaries();
        return JsonRenderer::render($rs, 200, $salaries);
    }
}