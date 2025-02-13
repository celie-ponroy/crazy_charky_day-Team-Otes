<?php

namespace charly\infrastructure\repository;

use charly\core\domain\entity\Competence;
use charly\infrastructure\repository\interfaces\CompetenceRepositoryInterface;
use PDO;
use Psr\Container\ContainerInterface;

class CompetenceRepository implements CompetenceRepositoryInterface
{
    protected PDO $pdo;
    public function __construct(ContainerInterface $cont){
        $this->pdo=$cont->get('pdo');
    }

    public function getListCompetence(): array
    {
        $stmt = $this->pdo->prepare('SELECT * FROM competence');
        $stmt->execute();
        $rows = $stmt->fetchAll(PDO::FETCH_ASSOC);

        $competences = [];
        foreach ($rows as $row) {
            $competence = new Competence($row['libelle'], (string)$row['id']);
            $competences[] = $competence;
        }
        return $competences;
    }
}