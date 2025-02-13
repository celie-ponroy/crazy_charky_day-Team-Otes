<?php
declare(strict_types=1);

use charly\application\action\GetListCompetence;
use charly\application\action\GetSalaries;
use charly\application\action\HomeAction;
use charly\application\action\PostBesoin;
use charly\application\action\PostSalarie;
use charly\application\action\PostSignIn;
use charly\middleware\AuthnMiddleware;
use Slim\Exception\HttpNotFoundException;

return function (\Slim\App $app): \Slim\App {

    $app->get('[/]', HomeAction::class);

    $app->get('/competences[/]', GetListCompetence::class);

    $app->post('/besoins[/]', PostBesoin::class);

    $app->post('/users/salaries[/]', PostSalarie::class);

    $app->post('/signin[/]', PostSignIn::class);

    $app->get('/users/salaries[/]', GetSalaries::class);

    $app->options('/{routes:.+}', function ($request, $response, $args) {
        return $response;
    });

    $app->map(['GET', 'POST', 'PUT', 'DELETE', 'PATCH'], '/{routes:.+}', function ($request, $response) {
        throw new HttpNotFoundException($request);
    });

    return $app;
};
