<?php
declare(strict_types=1);

use charly\application\action\HomeAction;
use Slim\Exception\HttpNotFoundException;

return function (\Slim\App $app): \Slim\App {


    $app->get('[/]', HomeAction::class);

    $app->options('/{routes:.+}', function ($request, $response, $args) {
        return $response;
    });

    $app->map(['GET', 'POST', 'PUT', 'DELETE', 'PATCH'], '/{routes:.+}', function ($request, $response) {
        throw new HttpNotFoundException($request);
    });

    return $app;
};
