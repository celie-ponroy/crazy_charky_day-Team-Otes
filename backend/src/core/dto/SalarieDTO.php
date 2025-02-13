<?php

namespace charly\core\dto;

class SalarieDTO extends DTO
{
    protected string $id;
    protected string $nom;
    protected int $role;
    /**
     * @var CompetenceWithInterestDTO[]
     */
    protected array $competences;

    public function __construct(string $id, string $nom, int $role, array $competences)
    {
        $this->id = $id;
        $this->nom = $nom;
        $this->role = $role;
        $this->competences = $competences;
    }

    public function getId(): string
    {
        return $this->id;
    }

    public function getNom(): string
    {
        return $this->nom;
    }

    public function getRole(): int
    {
        return $this->role;
    }

    /**
     * @return CompetenceWithInterestDTO[]
     */
    public function getCompetences(): array
    {
        return $this->competences;
    }
}