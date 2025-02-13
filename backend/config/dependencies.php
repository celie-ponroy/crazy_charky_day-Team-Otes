<?php

use charly\core\service\BesoinService;
use charly\core\service\CompetenceService;
use charly\core\service\interfaces\BesoinServiceInterface;
use charly\core\service\interfaces\CompetenceServiceInterface;
use charly\infrastructure\repository\BesoinRepository;
use charly\infrastructure\repository\CompetenceRepository;
use charly\infrastructure\repository\interfaces\BesoinRepositoryInterface;
use charly\infrastructure\repository\interfaces\CompetenceRepositoryInterface;
use charly\infrastructure\repository\interfaces\UserRepositoryInterface;
use charly\infrastructure\repository\UserRepository;
use charly\middleware\CorsMiddleware;
use Psr\Container\ContainerInterface;

return [

    //SERVICES
    BesoinServiceInterface::class => DI\autowire(BesoinService::class),
    CompetenceServiceInterface::class => DI\autowire(CompetenceService::class),
    \charly\core\service\interfaces\UserServiceInterface::class=> DI\autowire(\charly\core\service\UserService::class),


    //REPOSITORIES
    BesoinRepositoryInterface::class => DI\autowire(BesoinRepository::class),
    UserRepositoryInterface::class => DI\autowire(UserRepository::class),
    CompetenceRepositoryInterface::class => DI\autowire(CompetenceRepository::class),

    'pdo' => function(ContainerInterface $c){
        $config= parse_ini_file($c->get('db.config'));
        return new PDO($config['driver'].':host='.$config['host'].';port='.$config['port'].';dbname='.$config['dbname'].';user='.$config['user'].';password='.$config['password']);
    },

    CorsMiddleware::class => DI\autowire(CorsMiddleware::class),
];
