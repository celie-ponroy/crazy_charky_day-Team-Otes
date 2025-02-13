<?php

namespace charly\infrastructure\repository;

use charly\core\domain\entity\Competence;
use charly\core\dto\CompetenceDTO;
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
            $competence = new CompetenceDTO($row['libelle'], $row['description'], $row['id']);
            $competences[] = $competence;
        }
        return $competences;
    }
    
    public function addCompetence(Competence $competence): void
    {
        $stmt = $this->pdo->prepare('INSERT INTO competence (libelle, description) VALUES (:libelle, :description)');
        $stmt->execute([
            'libelle' => $competence->getLibelle(),
            'description' => $competence->getDescription()
        ]);

    }

    public function deleteCompetence(int $id): void
    {
        $stmt = $this->pdo->prepare('DELETE FROM competence WHERE id = :id');
        $stmt->execute(['id' => $id]);
    }

    public function updateCompetence(int $id, string $libelle, string $description): void
    {
        $stmt = $this->pdo->prepare('UPDATE competence SET libelle = :libelle, description = :description WHERE id = :id');
        $stmt->execute([
            'id' => $id,
            'libelle' => $libelle,
            'description' => $description
        ]);
    }


}