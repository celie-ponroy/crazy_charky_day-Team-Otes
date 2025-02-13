<?php

namespace charly\core\service;

use charly\core\dto\CompetenceDTO;
use charly\core\domain\entity\Competence;
use charly\core\service\interfaces\CompetenceServiceInterface;
use charly\infrastructure\repository\interfaces\CompetenceRepositoryInterface;

class CompetenceService implements CompetenceServiceInterface
{
    protected CompetenceRepositoryInterface $competenceRepository;

    public function __construct(CompetenceRepositoryInterface $competenceRepository)
    {
        $this->competenceRepository = $competenceRepository;
    }

    public function getListCompetence(): array
    {
        return $this->competenceRepository->getListCompetence();
    }

    public function addCompetence(string $libelle, string $description): void
    {
        $competence = new Competence($libelle, $description);
        $this->competenceRepository->addCompetence($competence);
    }

    public function deleteCompetence(int $id): void
    {
        $this->competenceRepository->deleteCompetence($id);
    }

    public function updateCompetence(int $id, string $libelle, string $description): void
    {
        $this->competenceRepository->updateCompetence($id, $libelle, $description);
    }
}