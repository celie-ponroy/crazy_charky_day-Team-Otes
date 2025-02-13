<?php

namespace charly\infrastructure\repository\interfaces;
use charly\core\domain\entity\Besoin;

interface BesoinRepositoryInterface
{
    public function createBesoin (string $clientId, int $idCompetence, string $libelle, int $timestamp): Besoin;

    public function getUserBesoins (string $id):array;

    public function getBesoins():array;
}