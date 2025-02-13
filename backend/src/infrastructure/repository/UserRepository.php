<?php

namespace charly\infrastructure\repository;

use charly\infrastructure\repository\interfaces\UserRepositoryInterface;
use PDO;
use Psr\Container\ContainerInterface;

class UserRepository implements UserRepositoryInterface
{
    protected PDO $pdo;

    public function __construct(ContainerInterface $container)
    {
        $this->pdo = $container->get('pdo');
    }
    public function getUserIdByNom(string $nom): ?string
    {
        $stmt = $this->pdo->prepare('SELECT id FROM users WHERE nom = :nom');
        $stmt->execute(['nom' => $nom]);
        $data = $stmt->fetch(PDO::FETCH_ASSOC);
        return $data ? $data['id'] : null;
    }
}