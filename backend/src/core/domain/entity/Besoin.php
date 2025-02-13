<?php

namespace charly\core\domain\entity;
class Besoin
{
    private string $nomClient;
    private string $libelleBesoin;
    private int $idCompetence;
    private int $id;
    private int $timestamp;

    public function __construct(string $nomClient, string $libelleBesoin, int $idCompetence, int $id, int $timestamp)
    {
        $this->nomClient = $nomClient;
        $this->libelleBesoin = $libelleBesoin;
        $this->idCompetence = $idCompetence;
        $this->id = $id;
        $this->timestamp = $timestamp;
    }

    public function getNomClient(): string
    {
        return $this->nomClient;
    }

    public function getLibelleBesoin(): string
    {
        return $this->libelleBesoin;
    }

    public function getIdCompetence(): int
    {
        return $this->idCompetence;
    }

    public function getId(): int
    {
        return $this->id;
    }

    public function getTimestamp(): int
    {
        return $this->timestamp;
    }
}