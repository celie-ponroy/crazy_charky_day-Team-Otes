<?php

namespace charly\core\dto\input;

class InputCreateSalarie
{
    protected string $nom;
    protected array $competences;

    public function __construct(string $nom, array $competences)
    {
        $this->nom = $nom;
        $this->competences = $competences;
    }

    public function getNom(): string
    {
        return $this->nom;
    }

    public function getCompetences(): array
    {
        return $this->competences;
    }

}