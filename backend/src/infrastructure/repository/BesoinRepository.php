<?php

namespace charly\infrastructure\repository;


use charly\core\domain\entity\Besoin;
use charly\infrastructure\repository\interfaces\BesoinRepositoryInterface;
use PDO;
use Psr\Container\ContainerInterface;

class BesoinRepository implements BesoinRepositoryInterface
{
    protected PDO $pdo;
    public function __construct(ContainerInterface $cont){
        $this->pdo=$cont->get('pdo');
    }
    public function createBesoin(string $clientId, int $idCompetence, string $libelle, int $timestamp): Besoin
    {
        $stmt = $this->pdo->prepare(
            'INSERT INTO besoin (client_id, competence_id, libelle, date) 
         VALUES (:client_id, :competence_id, :libelle, to_timestamp(:timestamp))'
        );
        $stmt->execute([
            'client_id'     => $clientId,
            'competence_id' => $idCompetence,
            'libelle'       => $libelle,
            'timestamp'     => $timestamp
        ]);
        $id = $this->pdo->lastInsertId();
        return new Besoin($clientId, $libelle, $idCompetence, $id, $timestamp);
    }

}