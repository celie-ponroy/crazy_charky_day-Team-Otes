<?php

namespace charly\application\action;

use charly\application\renderer\JsonRenderer;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;

class HomeAction extends AbstractAction
{

    public function __invoke(ServerRequestInterface $rq, ResponseInterface $rs, array $args): ResponseInterface
    {
        return JsonRenderer::render($rs, 200, ['message' => 'Hello World!']);
    }
}