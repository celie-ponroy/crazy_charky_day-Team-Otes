<?php

namespace charly\core\service;

use charly\core\dto\CompetenceDTO;
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
        $competences = $this->competenceRepository->getListCompetence();

        //return array of dto
        $resDTO = [];
        foreach ($competences as $competence) {
            $resDTO[] = $competence->toDTO();
        }
        return $resDTO;
    }
}