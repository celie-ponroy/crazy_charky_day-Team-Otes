<?php
declare(strict_types=1);

use charly\application\action\GetListCompetence;
use charly\application\action\GetSalaries;
use charly\application\action\HomeAction;
use charly\application\action\PostBesoin;
use charly\application\action\PostSalarie;
use charly\application\action\LoginAction;
use charly\application\action\RegisterAction;
use Slim\Exception\HttpNotFoundException;
use charly\middleware\AuthMiddleware;

return function (\Slim\App $app): \Slim\App {

    $app->get('[/]', HomeAction::class);    

    $app->post('/besoins[/]', PostBesoin::class);

    $app->post('/users/salaries[/]', PostSalarie::class);


    $app->get('/users/salaries[/]', GetSalaries::class);

    //Route pour l'authentification
    $app->post('/register', RegisterAction::class);
    $app->post('/login', LoginAction::class);

    //Route pour les compétences
    $app->get('/competences[/]', GetListCompetence::class)->add(AuthMiddleware::class);

    $app->options('/{routes:.+}', function ($request, $response, $args) {
        return $response;
    });

    $app->map(['GET', 'POST', 'PUT', 'DELETE', 'PATCH'], '/{routes:.+}', function ($request, $response) {
        throw new HttpNotFoundException($request);
    });

    return $app;
};
