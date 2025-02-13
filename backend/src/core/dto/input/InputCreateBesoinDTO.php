<?php

namespace charly\core\dto\input;

class InputCreateBesoinDTO
{
    public string $nomClient;
    public int $idCompetence;
    public string $libelleBesoin;

    public function __construct(string $nomClient, int $idCompetence, string $libelleBesoin)
    {
        $this->nomClient = $nomClient;
        $this->idCompetence = $idCompetence;
        $this->libelleBesoin = $libelleBesoin;
    }

    public function getNomClient(): string
    {
        return $this->nomClient;
    }

    public function getIdCompetence(): int
    {
        return $this->idCompetence;
    }

    public function getLibelleBesoin(): string
    {
        return $this->libelleBesoin;
    }
}