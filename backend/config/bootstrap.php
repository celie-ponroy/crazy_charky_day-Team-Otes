<?php

require_once __DIR__ . "/../vendor/autoload.php";

use DI\ContainerBuilder;
use Doctrine\ORM\EntityManager;
use Slim\Factory\AppFactory;

$builder = new ContainerBuilder();
$builder->addDefinitions(__DIR__ . '/settings.php');
$builder->addDefinitions(__DIR__ . '/dependencies.php');
$builder->addDefinitions(__DIR__ . '/actions.php');

$c = $builder->build();
$app = AppFactory::createFromContainer($c);
$app->add(CorsMiddleware::class);
$app->addBodyParsingMiddleware();
$app->addRoutingMiddleware();
$app->addErrorMiddleware($c->get('displayErrorDetails'), false, false);

$app = (require_once __DIR__ . '/routes.php')($app);

return $app;
