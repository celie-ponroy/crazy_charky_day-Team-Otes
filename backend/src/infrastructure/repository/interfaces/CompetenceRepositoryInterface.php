<?php

namespace charly\infrastructure\repository\interfaces;

use charly\core\domain\entity\Competence;

interface CompetenceRepositoryInterface
{
    public function getListCompetence(): array;
    public function addCompetence(Competence $competence): void;
    public function deleteCompetence(int $id): void;
    public function updateCompetence(int $id, string $libelle, string $description): void;
    

}