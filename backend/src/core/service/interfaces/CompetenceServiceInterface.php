<?php

namespace charly\core\service\interfaces;

interface CompetenceServiceInterface
{
    public function getListCompetence(): array;
    public function addCompetence(string $libelle, string $description): void;
    public function deleteCompetence(int $id): void;
    public function updateCompetence(int $id, string $libelle, string $description): void;
}