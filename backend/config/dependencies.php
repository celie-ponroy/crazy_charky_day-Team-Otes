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
use charly\infrastructure\repository\AuthRepository;
use charly\core\service\interfaces\UserAuthServiceInterface;
use charly\infrastructure\repository\interfaces\AuthRepositoryInterface;
use charly\infrastructure\repository\ServiceRepository;
use charly\infrastructure\repository\interfaces\ServiceRepositoryInterface;
use charly\core\service\ServiceService;
use charly\core\service\interfaces\ServiceServiceInterface;
use charly\providers\auth\JWTManager;
use charly\core\service\UserAuthService;
use charly\middleware\CorsMiddleware;
use Psr\Container\ContainerInterface;
use charly\core\service\authorization\AuthrzService;
use charly\core\service\authorization\AuthrzServiceInterface;

return [

    //SERVICES
    BesoinServiceInterface::class => DI\autowire(BesoinService::class),
    CompetenceServiceInterface::class => DI\autowire(CompetenceService::class),
    \charly\core\service\interfaces\UserServiceInterface::class=> DI\autowire(\charly\core\service\UserService::class),
    \charly\core\service\interfaces\UserAuthServiceInterface::class=> DI\autowire(\charly\core\service\UserAuthService::class),

    //REPOSITORIES
    BesoinRepositoryInterface::class => DI\autowire(BesoinRepository::class),
    UserRepositoryInterface::class => DI\autowire(UserRepository::class),
    CompetenceRepositoryInterface::class => DI\autowire(CompetenceRepository::class),


    'pdo' => function(ContainerInterface $c){
        $config= parse_ini_file($c->get('db.config'));
        return new PDO($config['driver'].':host='.$config['host'].';port='.$config['port'].';dbname='.$config['dbname'].';user='.$config['user'].';password='.$config['password']);
    },

    AuthRepositoryInterface::class => function (ContainerInterface $container) {
        return new AuthRepository($container->get('pdo'));
    },

    UserAuthServiceInterface::class => function (ContainerInterface $container) {
        $jwtManager = $container->get(JWTManager::class);
        return new UserAuthService($container->get(AuthRepositoryInterface::class), $jwtManager);
    },

    ServiceRepositoryInterface::class => function (ContainerInterface $container) {
        return new ServiceRepository($container->get('pdo'));
    },

    ServiceServiceInterface::class => function (ContainerInterface $container) {
        return new ServiceService($container->get(ServiceRepositoryInterface::class));
    },

    AuthrzServiceInterface::class => function (ContainerInterface $container) {
        return new AuthrzService($container->get(UserAuthServiceInterface::class));
    },

    CorsMiddleware::class => DI\autowire(CorsMiddleware::class),
];
