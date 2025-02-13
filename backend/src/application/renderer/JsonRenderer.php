<?php

namespace charly\application\renderer;
use Psr\Http\Message\ResponseInterface;

class JsonRenderer
{
    public static function render(ResponseInterface $rs, int $code, mixed $data = null): ResponseInterface
    {

        $rs = $rs->withStatus($code)
            ->withHeader('Content-Type', 'application/json;charset=utf-8');
        if (!is_null($data)) {
            $rs->getBody()->write(json_encode($data));
        }
        return $rs;
    }
}

